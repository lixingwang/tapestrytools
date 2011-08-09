package org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.jst.tapestry.core.internal.RegistryUpgradeCommitHandler;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.internal.TapestryLibraryClasspathContainer;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistry;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.adapter.MaintainDefaultImplementationAdapter;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl.TapestryLibraryRegistryPackageImpl;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.internal.PluginProvidedTapestryLibraryCreationHelper2;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.TapestryLibraryRegistryResourceFactoryImpl;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.TapestryLibraryRegistryResourceImpl;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.TapestryLibraryRegistryUpgradeUtil;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryConfigurationHelper;
import org.eclipse.jst.tapestry.core.tapestrylibraryregistry.PluginProvidedTapestryLibraryCreationHelper;


public class TapestryLibraryRegistryUtil {
private static TapestryLibraryRegistryUtil instance = null;	
	
	private List implLibs = null;
	private List compLibs = null;
	

	// The NS URI of the JSF Library Registry's Ecore package. (Must match
	// setting on package in Ecore model.)
	private static final String JSF_LIBRARY_REGISTRY_NSURI = "http://www.eclipse.org/webtools/jsf/schema/jsflibraryregistry.xsd"; //$NON-NLS-1$

	private static final String LIB_EXT_PT 		= "pluginProvidedJsfLibraries"; //$NON-NLS-1$
	//deprecated ext-pt
	private static final String OLD_LIB_EXT_PT 	= "jsfLibraries"; //$NON-NLS-1$

	// The JSF Library Registry EMF resource instance.
	private static TapestryLibraryRegistryResourceImpl jsfLibraryRegistryResource = null;
	
	//JSFLibraryRegistry singleton
	private TapestryLibraryRegistry tapestryLibraryRegistry;
	
	/**
	 * Private constructor
	 */
	private TapestryLibraryRegistryUtil() {
	    //nothing to do
	}
	
	/**
	 * Return the singleton instance of JSFLibraryRegistryUtil.
	 *   
	 * @return JSFLibraryRegistryUtil
	 */
	public synchronized static TapestryLibraryRegistryUtil getInstance() {
		if ( instance == null ) {
			instance = new TapestryLibraryRegistryUtil();
			instance.loadTapestryLibraryRegistry();
		}
		return instance;
	}

	/**
	 * Convenience method to return the JSFLibraryRegistry instance.
	 * 
	 * @return jsfLibReg JSFLibraryRegistry
	 */
	public TapestryLibraryRegistry getTapestryLibraryRegistry() {
		return tapestryLibraryRegistry;
	}
	
	/**
	 * Get the default Tapestry implementation library instance.
	 * A null is returned when there is no libraries in the registry.
	 * 
	 * @return TapestryLibraryInternalReference
	 */
	public TapestryLibraryInternalReference getDefaultTapestryImplementationLibrary() {
		TapestryLibrary dftImplLib = getTapestryLibraryRegistry().getDefaultImplementation();
		
		return ((dftImplLib != null) ? 
				getTapestryLibraryReferencebyID(dftImplLib.getID()) :
				null);
	}
	
	/**
	 * Get the working copy of JSF implementation libraries.
	 * The list is updated when there are changes in registry.
	 * 
	 * @return List
	 */
	List getTapestryImplementationLibraries() {
		if (implLibs == null) {
			implLibs = wrapTapestryLibraries(getTapestryLibraryRegistry().getImplTapestryLibraries());
		} else {
			if (implLibs.size() != getTapestryLibraryRegistry().getImplTapestryLibraries().size() || 
					isAnyLibraryChanged(implLibs)) {
				implLibs.clear();
				implLibs = wrapTapestryLibraries(getTapestryLibraryRegistry().getImplTapestryLibraries());
			}
		}
		return implLibs;
	}
		
	/**
	 * Get the working copy of JSF component libraries.
	 * The list is updated when there are changes in registry.
	 * 
	 * @return List
	 */
	List getTapestryComponentLibraries() {
		if (compLibs == null) {
			compLibs = wrapTapestryLibraries(getTapestryLibraryRegistry().getNonImplTapestryLibraries());
		} else {
			if (compLibs.size() != getTapestryLibraryRegistry().getNonImplTapestryLibraries().size() || 
					isAnyLibraryChanged(compLibs)) {
				compLibs.clear();
				compLibs = wrapTapestryLibraries(getTapestryLibraryRegistry().getNonImplTapestryLibraries());
			}			
		}
		return compLibs;
	}
	
	/**
	 * Get the JSFLibraryDecorator object from the provided ID. 
	 * A null is returned no library matches the ID.
	 * 
	 * @param id String
	 * @return JSFLibraryDecorator
	 */
	public TapestryLibraryInternalReference getTapestryLibraryReferencebyID(final String id) {
		Iterator it = getTapestryImplementationLibraries().iterator();
		TapestryLibraryInternalReference crtItem = null;
		
		// search implementation libraries
		while(it.hasNext()) {
			crtItem = (TapestryLibraryInternalReference)it.next();
			if (id.equals(crtItem.getID())) {
				return crtItem;
			}
		}
		// search component libraries
		it = getTapestryComponentLibraries().iterator();
		while(it.hasNext()) {
			crtItem = (TapestryLibraryInternalReference)it.next();
			if (id.equals(crtItem.getID())) {
				return crtItem;
			}
		}
		return null;
	}

	/**
	 * Add a JSF Library into collection for either 
	 * JSF implementation libraries or component libraries.  
	 * The decision is based on if a JSF library is an implementation.
	 * 
	 * @param library JSFLibraryLibraryReference
	 */
	public void addTapestryLibrary(final TapestryLibraryInternalReference library) {
		 // Library is added only if it does not exist in registry 
		if (library != null && getTapestryLibraryRegistry().getTapestryLibraryByID(library.getID()) == null) {
			// Add the library working copy into workspace registry.
			TapestryLibrary jsfLib = library.getLibrary();
			getTapestryLibraryRegistry().addTapestryLibrary(jsfLib.getWorkingCopy());
			
			// Add library into the collection depends on its type.
			List list = (library.isImplementation() ? 
							getTapestryImplementationLibraries() :
							getTapestryComponentLibraries());
			list.add(library);
		}
	}	
	
	private List wrapTapestryLibraries(final EList libs) {
		List list = new ArrayList();
		if (libs != null) {
			TapestryLibrary jsfLib;
			TapestryLibraryInternalReference jsfLibDctr;
			
			Iterator it = libs.iterator();
			while (it.hasNext()) {
				jsfLib = (TapestryLibrary) it.next();
				 // Set unselected and undeployed initially.
				jsfLibDctr = new TapestryLibraryInternalReference(jsfLib, //.getWorkingCopy(), 
								false, 
								false);
				list.add(jsfLibDctr);
			}
		}	
		return list;		
	}

	private boolean isAnyLibraryChanged(final List list) {
		Iterator it = list.iterator();
		TapestryLibraryInternalReference wclib = null;		// working copy library
		TapestryLibrary lib = null;
		
		while(it.hasNext()) {
			wclib = (TapestryLibraryInternalReference)it.next();
			lib = getTapestryLibraryRegistry().getTapestryLibraryByID(wclib.getID());
			if (lib == null) {					// removed. Hence, changed.
				return true;
			}
			if (wclib.getArchiveFiles().size() != 
				lib.getArchiveFiles().size()) { // Archives changed..
				return true;
			}
			if (isAnyArchiveFileChanged(wclib.getArchiveFiles(), 
					lib.getArchiveFiles())) {   // Check archive file changes.  I.e., name and location
				return true;
			}
		}
		return false;
	}

	private boolean isAnyArchiveFileChanged(final EList source, EList target) {		
		ArchiveFile arSrc = null;
		Iterator it = source.iterator();
		while (it.hasNext()) {
			arSrc = (ArchiveFile) it.next();
			if (!findMatchedArchive(arSrc, target)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean findMatchedArchive(ArchiveFile source, EList list) {
		ArchiveFile target = null;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			target = (ArchiveFile) it.next();
			if (target.equals(source)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the classpath entries for a JSF Library
	 * @param lib
	 * @return IClasspathEntry[]
	 */
	public IClasspathEntry[] getClasspathEntries(TapestryLibrary lib){		
		//TODO: cache to optimize.   probably belongs inside JSFLibrary model.
		ArrayList res= new ArrayList(lib.getArchiveFiles().size());
		for (Iterator it=lib.getArchiveFiles().iterator();it.hasNext();) {
			ArchiveFile jar= (ArchiveFile)it.next();			
			if (jar != null && jar.exists()) {
				IClasspathEntry entry = getClasspathEntry(jar);
				if (entry != null)
					res.add(entry);
			}
		}
		IClasspathEntry[] entries= (IClasspathEntry[]) res.toArray(new IClasspathEntry[res.size()]);
		return entries;
	}
	
	/**
	 * Create IClasspathEntry for ArchiveFile
	 * @param jar
	 * @return IClasspathEntry
	 */
	public IClasspathEntry getClasspathEntry(ArchiveFile jar){
		IClasspathEntry entry = null;
		if (jar !=null && jar.exists()){
			entry = JavaCore.newLibraryEntry(new Path(jar.getResolvedSourceLocation()), null, null);//, nu, sourceAttachRoot, accessRules, extraAttributes, false/*not exported*/);
		}
		return entry;
	}
	
	/**
	 * Binds JSF Libraries to classpath containers when the library changes.
	 * 
	 * This method will deal with library/cp container renames by removing the old classpath container and then adding.
	 * 
	 * @param oldId
	 * @param newId
	 * @param monitor
	 * @throws JavaModelException
	 */
	public static void rebindClasspathContainerEntries(String oldId, String newId, IProgressMonitor monitor) throws JavaModelException {
		IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
		IJavaProject[] projects= JavaCore.create(root).getJavaProjects();
		IPath containerPath= new Path(TapestryLibraryConfigurationHelper.Tapestry_LIBRARY_CP_CONTAINER_ID).append(newId);
		IPath oldContainerPath = new Path(TapestryLibraryConfigurationHelper.Tapestry_LIBRARY_CP_CONTAINER_ID).append(oldId);
		
		TapestryLibrary lib = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryRegistry().getTapestryLibraryByID(newId);
		List affectedProjects= new ArrayList();
		boolean removeAndAddBecauseOfRename = (!oldId.equals(newId));
		// find all projects using the old container name...
		for (int i= 0; i < projects.length; i++) {
			IJavaProject project= projects[i];
			IClasspathEntry[] entries= project.getRawClasspath();
			for (int k= 0; k < entries.length; k++) {
				IClasspathEntry curr= entries[k];
				if (curr.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
					if (oldContainerPath.equals(curr.getPath())) {
						affectedProjects.add(project);
						break;
					}				
				}
			}
		}
		
		if (!affectedProjects.isEmpty()) {
			IJavaProject[] affected= (IJavaProject[]) affectedProjects.toArray(new IJavaProject[affectedProjects.size()]);
			IClasspathContainer[] containers= new IClasspathContainer[affected.length];
			removeAndAddBecauseOfRename = (!oldId.equals(newId));
			if (removeAndAddBecauseOfRename){//not very pretty... remove and add new container				
				IClasspathEntry newEntry = JavaCore.newContainerEntry(containerPath);
				for (int i= 0; i < affected.length; i++) {
					IJavaProject project= affected[i];
					IClasspathEntry[] entries= project.getRawClasspath();
					List keptEntries = new ArrayList();
					//keep all entries except the old one
					for (int k= 0; k < entries.length; k++) {
						IClasspathEntry curr= entries[k];
						if (curr.getEntryKind() == IClasspathEntry.CPE_CONTAINER){
								if( ! oldContainerPath.equals(curr.getPath())) 
							keptEntries.add(curr);						
						}
						else {
							keptEntries.add(curr);
						}						
					}
					// add new container entry
					keptEntries.add(newEntry);
					setRawClasspath(project, keptEntries, monitor);
				}
				
			}
			else {//rebind

				TapestryLibraryClasspathContainer container= new TapestryLibraryClasspathContainer(lib);
				containers[0] = container;
	
				JavaCore.setClasspathContainer(containerPath, affected, containers, monitor);
			}
		} else {
			if (monitor != null) {
				monitor.done();
			}
		}
	}

	/**
	 * Sets the raw classpath on a project and logs an error if it when a JavaModelException occurs
	 * @param project
	 * @param cpEntries
	 * @param monitor
	 */
	public static void setRawClasspath(IJavaProject project, List cpEntries, IProgressMonitor monitor) {
		IClasspathEntry[] entries = (IClasspathEntry[])cpEntries.toArray(new IClasspathEntry[0]);
		try {
			project.setRawClasspath(entries, monitor);
		} catch (JavaModelException e) {
			TapestryCorePlugin.log(e, "Unable to set classpath for: "+project.getProject().getName()); //$NON-NLS-1$
		}
	}
	
	/**
	 * Loads the JSFLibraryRegistry EMF object from plugin-specfic workspace
	 * settings location.
	 */
	private void loadTapestryLibraryRegistry() {
		try {
			
			EPackage.Registry.INSTANCE.put(JSF_LIBRARY_REGISTRY_NSURI, TapestryLibraryRegistryPackageImpl.init());
			URI jsfLibRegURI = TapestryLibraryRegistryUpgradeUtil.getRegistryURI(TapestryLibraryRegistryUpgradeUtil.JSF_LIBRARY_REGISTRY_LATESTVERSION_URL);			
			TapestryLibraryRegistryUpgradeUtil.getInstance().upgradeRegistryIfNecessary(TapestryLibraryRegistryUpgradeUtil.LATESTVERSION);

			TapestryLibraryRegistryResourceFactoryImpl resourceFactory = new TapestryLibraryRegistryResourceFactoryImpl();
			jsfLibraryRegistryResource = (TapestryLibraryRegistryResourceImpl)resourceFactory.createResource(jsfLibRegURI);
			try {
				Map options = new HashMap();
				//disable notifications during load to avoid changing stored default implementation
				options.put(XMLResource.OPTION_DISABLE_NOTIFY, Boolean.TRUE);
				jsfLibraryRegistryResource.load(options);
				tapestryLibraryRegistry = (TapestryLibraryRegistry)jsfLibraryRegistryResource.getContents().get(0);
			 	
				loadJSFLibraryExtensions();
				loadDeprecatedJSFLibraryExtensions();//to be removed 
				
			} catch(IOException ioe) {
				//Create a new Registry instance
				tapestryLibraryRegistry = TapestryLibraryRegistryFactory.eINSTANCE.createTapestryLibraryRegistry();
				jsfLibraryRegistryResource = (TapestryLibraryRegistryResourceImpl)resourceFactory.createResource(jsfLibRegURI);
				jsfLibraryRegistryResource.getContents().add(tapestryLibraryRegistry);
				loadJSFLibraryExtensions();
				loadDeprecatedJSFLibraryExtensions();//to be removed 
				saveJSFLibraryRegistry();
			}
			//add adapter to maintain default implementation
			if (tapestryLibraryRegistry != null) {				
				//check that a default impl is set.   if not pick first one if available.
				TapestryLibrary defLib = tapestryLibraryRegistry.getDefaultImplementation();
				if (defLib == null && tapestryLibraryRegistry.getImplTapestryLibraries().size() > 0){
					tapestryLibraryRegistry.setDefaultImplementation((TapestryLibrary)tapestryLibraryRegistry.getImplTapestryLibraries().get(0));
					saveJSFLibraryRegistry();
				}
				tapestryLibraryRegistry.eAdapters().add(MaintainDefaultImplementationAdapter.getInstance());
				
				//commit 
				RegistryUpgradeCommitHandler.commitMigrationIfNecessary();
			}
		} catch(MalformedURLException mue) {
			TapestryCorePlugin.log(IStatus.ERROR, Messages.TapestryLibraryRegistry_ErrorCreatingURL, mue);
		}
	}
/////////////////////////////////   Load and Save JSF Library Registry ////////////////////////////////////////////////
	
	/**
	 * Creates library registry items from extension points.
	 */
	private void loadJSFLibraryExtensions() {
		try {
			IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(TapestryCorePlugin.PLUGIN_ID, LIB_EXT_PT);
			IExtension[] extensions = point.getExtensions();
			for (int i=0;i < extensions.length;i++){
				IExtension ext = extensions[i];
				for (int j=0;j < ext.getConfigurationElements().length;j++){
					PluginProvidedTapestryLibraryCreationHelper2 newLibCreator = new PluginProvidedTapestryLibraryCreationHelper2(ext.getConfigurationElements()[j]);						
					TapestryLibrary newLib = newLibCreator.create();
					
					/**
					 * Additional check on if a plug-in contributes jsflibraries is an expanded folder.
					 * Fix related to bug 144954.  
					 * 
					 * It would be ideal to check if a plug-in is distributed as a JAR 
					 * before a JSFLibrary is created.
					 * 
					 * This is a temporary solution since JARs in a JAR case is not 
					 * supported in this release.  Bug 14496.
					 */
					if (newLib != null) //&& isJSFLibinExpandedFolder(newLib))
						tapestryLibraryRegistry.addTapestryLibrary(newLib);
				}
			}
		} catch (InvalidRegistryObjectException e) {
			TapestryCorePlugin.log(IStatus.ERROR, Messages.TapestryLibraryRegistry_ErrorLoadingFromExtPt, e);
		}
	}
	
	/**
	 * Creates deprecated library registry items from extension points.
	 * TO BE REMOVED
	 */
	private void loadDeprecatedJSFLibraryExtensions() {
		try {
			IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(TapestryCorePlugin.PLUGIN_ID, OLD_LIB_EXT_PT);
			IExtension[] extensions = point.getExtensions();
			for (int i=0;i < extensions.length;i++){
				IExtension ext = extensions[i];
				for (int j=0;j < ext.getConfigurationElements().length;j++){
					PluginProvidedTapestryLibraryCreationHelper newLibCreator = new PluginProvidedTapestryLibraryCreationHelper(ext.getConfigurationElements()[j]);						
					TapestryLibrary newLib = newLibCreator.create();
					
					if (newLib != null ) //&& isJSFLibinExpandedFolder(newLib))
						tapestryLibraryRegistry.addTapestryLibrary(newLib);
				}
			}
		} catch (InvalidRegistryObjectException e) {
			TapestryCorePlugin.log(IStatus.ERROR, Messages.TapestryLibraryRegistry_ErrorLoadingFromExtPt, e);
		}
	}
	
	/**
	 * Saves the JSFLibraryRegistry EMF object from plugin-specfic workspace
	 * settings location. (Called from stop(BundleContext).)
	 * @return true if save is successful
	 */
	public boolean saveJSFLibraryRegistry() {
		boolean saved = false;
		if (jsfLibraryRegistryResource != null) {
			try {
				jsfLibraryRegistryResource.save(Collections.EMPTY_MAP);
				saved = true;
			} catch(IOException ioe) {
				TapestryCorePlugin.log(IStatus.ERROR, Messages.TapestryLibraryRegistry_ErrorSaving, ioe);
			}
		} else {
			TapestryCorePlugin.log(IStatus.ERROR, Messages.TapestryLibraryRegistry_ErrorSaving);
		}
		return saved;
	}
}
