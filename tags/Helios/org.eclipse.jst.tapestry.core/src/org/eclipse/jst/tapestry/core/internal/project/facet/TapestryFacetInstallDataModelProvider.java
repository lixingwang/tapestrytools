package org.eclipse.jst.tapestry.core.internal.project.facet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.jst.tapestry.core.ITapestryCoreConstants;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryInternalReference;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.IFacetedProject.Action;

/**
 * Provides a data model used by the JSF facet install.
 * 
 * @author gavingui2011@gmail.com - Beijing China
 */
@SuppressWarnings("deprecation")
public class TapestryFacetInstallDataModelProvider extends
		FacetInstallDataModelProvider implements
		ITapestryFacetInstallDataModelProperties {
	private static final String REGEX_FOR_VALID_CONFIG_FILE_NAME = "^(?!.*/{2,}.*$)[-\\w/.]+$"; //$NON-NLS-1$
    private static final Pattern PATTERN_FOR_VALID_CONFIG_FILE_NAME = Pattern.compile(REGEX_FOR_VALID_CONFIG_FILE_NAME);
    
    private final boolean tapestryFacetConfigurationEnabled = TapestryFacetConfigurationUtil.isTapestryFacetConfigurationEnabled();
    private LibraryInstallDelegate libraryInstallDelegate = null;
    
    private void initLibraryInstallDelegate()
    {
        final IFacetedProjectWorkingCopy fpjwc = (IFacetedProjectWorkingCopy) getProperty( FACETED_PROJECT_WORKING_COPY );
        final IProjectFacetVersion fv = (IProjectFacetVersion) getProperty( FACET_VERSION );
        
        if( this.libraryInstallDelegate == null && fpjwc != null && fv != null )
        {
            this.libraryInstallDelegate = new LibraryInstallDelegate( fpjwc, fv );
            
            this.libraryInstallDelegate.addListener
            ( 
                new IPropertyChangeListener()
                {
                    public void propertyChanged( final String property,
                                                 final Object oldValue,
                                                 final Object newValue )
                    {
                        final IDataModel dm = getDataModel();
    
                        if( dm != null )
                        {
                            dm.notifyPropertyChange( LIBRARY_PROVIDER_DELEGATE, IDataModel.VALUE_CHG );
                        }
                    }
                }
            );
        }
    }
    
    private String 	errorMessage;

	
	public Set getPropertyNames() {
		Set names = super.getPropertyNames();
		
		if (tapestryFacetConfigurationEnabled)
		{
    		names.add(CONFIG_PATH);
    		names.add(SERVLET_NAME);
    		names.add(SERVLET_CLASSNAME);
    		names.add(SERVLET_URL_PATTERNS);
    		names.add(COMPONENT_LIBRARIES);
    		names.add(WEBCONTENT_DIR);
    		
		}

		names.add(LIBRARY_PROVIDER_DELEGATE);

        return names;
	}
	
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(CONFIG_PATH)) {
			return TapestryUtils.Tapestry_DEFAULT_CONFIG_PATH; 
		} else if (propertyName.equals(SERVLET_NAME)) {
			return TapestryUtils.Tapestry_DEFAULT_SERVLET_NAME;
		} else if (propertyName.equals(SERVLET_CLASSNAME)) {
			return TapestryUtils.Tapestry_SERVLET_CLASS;	
		} else if (propertyName.equals(SERVLET_URL_PATTERNS)) {
			return new String[] {TapestryUtils.Tapestry_DEFAULT_URL_MAPPING };
		} else if (propertyName.equals(FACET_ID)) {
			return ITapestryCoreConstants.Tapestry_CORE_FACET_ID;
		} else if (propertyName.equals(WEBCONTENT_DIR)){
			return "WebContent";  //not sure I need this //$NON-NLS-1$
        } else if (propertyName.equals(LIBRARY_PROVIDER_DELEGATE)) {
            return this.libraryInstallDelegate;
		} else if (propertyName.equals(COMPONENT_LIBRARIES)) {
			return new TapestryLibraryInternalReference[0];
		}
		return super.getDefaultProperty(propertyName);
	}
    
	@Override
    public boolean propertySet( final String propertyName,
                                final Object propertyValue )
    {
	    if( propertyName.equals( FACETED_PROJECT_WORKING_COPY ) || propertyName.equals( FACET_VERSION ) )
	    {
	        initLibraryInstallDelegate();
	        
	        if( this.libraryInstallDelegate != null && propertyName.equals( FACET_VERSION ) )
	        {
	            final IProjectFacetVersion fv = (IProjectFacetVersion) getProperty( FACET_VERSION );
	            this.libraryInstallDelegate.setProjectFacetVersion( fv );
	        }
	    }

        return super.propertySet( propertyName, propertyValue );
    }
	
	 public IStatus validate(String name) {
			errorMessage = null;
			
			if (tapestryFacetConfigurationEnabled)
			{
	    		if (name.equals(CONFIG_PATH)) {
	    			return validateConfigLocation(getStringProperty(CONFIG_PATH));
	    		} else if (name.equals(SERVLET_NAME)) {			
	    			return validateServletName(getStringProperty(SERVLET_NAME));
	    		}
	    		else if (name.equals(COMPONENT_LIBRARIES)) {
	    			return validateClasspath();
	    		}
	   		}

			if (name.equals(LIBRARY_PROVIDER_DELEGATE)) 
			{
	            return ((LibraryInstallDelegate) getProperty(LIBRARY_PROVIDER_DELEGATE)).validate();
	        }
			
			return super.validate(name);
		}
	
	 private IStatus createErrorStatus(String msg) {		
			return new Status(IStatus.ERROR, TapestryCorePlugin.PLUGIN_ID, msg);
		}
		
		private IStatus validateServletName(String servletName) {
			if (servletName == null || servletName.trim().length() == 0) {
				errorMessage = Messages.TapestryFacetInstallDataModelProvider_ValidateServletName;
				return createErrorStatus(errorMessage);				
			}
			
			return OK_STATUS;
		}

		private IStatus validateConfigLocation(String text) {
			if (text == null || text.trim().equals("")) { //$NON-NLS-1$
				errorMessage = Messages.TapestryFacetInstallDataModelProvider_ValidateConfigFileEmpty;
				return createErrorStatus(errorMessage);
			}
			text = text.trim();
			
			if (getProjectPath() == null) //this circumstance occurs on page init
				return OK_STATUS;
			
			IPath fullPath = getProjectPath().append(text);
			IPath passedPath = new Path(text);
			if (!fullPath.isValidPath(text)){
				errorMessage = Messages.TapestryFacetInstallDataModelProvider_ValidateConfigFilePath;
				return createErrorStatus(errorMessage);
			}
			
			// Configuration path must not contain backslashes.
			// Must use forward slashes instead.
			if (text.lastIndexOf("\\") >= 0){ //$NON-NLS-1$
				errorMessage = Messages.TapestryFacetInstallDataModelProvider_ValidateConfigFileSlashes;
				return createErrorStatus(errorMessage);
			} 

			// Configuration file must NOT be absolute path.
			// It must be specified relative to project.
			if (passedPath.getDevice() != null) {
				errorMessage = NLS.bind(
						Messages.TapestryFacetInstallDataModelProvider_ValidateConfigFileRelative1,
						getWebContentFolderName());
				return createErrorStatus(errorMessage);
			}

			// Configuration file must be located in the project's folder
			IPath webContentFolder = getWebContentFolder();
			IPath setPath = webContentFolder.append(passedPath);
			if (!getWebContentFolder().isPrefixOf(setPath)) {
				errorMessage = NLS.bind(
						Messages.TapestryFacetInstallDataModelProvider_ValidateConfigFileRelative2,
						getWebContentFolderName());
				return createErrorStatus(errorMessage);
			}

	        // Check for other general invalid characters
	        if (!isValidConfigFileName(text))
	        {
	            errorMessage = Messages.TapestryFacetInstallDataModelProvider_INVALID_Tapestry_CONFIG_FILE_NAME;
	            return createErrorStatus(errorMessage);
	        }

			return OK_STATUS;
		}


	    /**
	     * (This method had been made protected to enable JUnit testing.)
	     * 
	     * @param configFileName
	     * @return True if the argument config file name does not have any invalid
	     *         characters.
	     */
	    public static boolean isValidConfigFileName (final String configFileName)
	    {
	        return PATTERN_FOR_VALID_CONFIG_FILE_NAME.matcher(configFileName).matches(); 
	    }


		private IStatus validateClasspath(){
			Set jars = new HashSet();
			if (doesProjectExist()){
				//validate actual classpath by loading jars from cp
				try {
					IClasspathEntry[] entries = getJavaProject().getResolvedClasspath(true);
					for (int i=0;i<entries.length;i++){
						IClasspathEntry entry = entries[i];
						if (entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY){
							jars.add(entry.getPath().makeAbsolute().toString());
						}
					}
				} catch (JavaModelException e) {
				    // FIXME: what should we do in this case?
					TapestryCorePlugin.log(e, "Error searching class path"); //$NON-NLS-1$
				}			
			}
			//else as we do not have a javaProject yet, all we can do is validate that there is no duplicate jars (absolute path)
			
			IStatus status = null;
			
			TapestryLibraryInternalReference[] compLibs = (TapestryLibraryInternalReference[]) getProperty(ITapestryFacetInstallDataModelProperties.COMPONENT_LIBRARIES);
			if (compLibs != null){
				for (int i=0;i<compLibs.length;i++){
					TapestryLibrary lib = compLibs[i].getLibrary();
					status = checkForDupeArchiveFiles(jars, lib);
						if (!OK_STATUS.equals(status)){
							return status;
						}
				}		
			}
			return OK_STATUS;
		}

		private IJavaProject getJavaProject() {
			IProject proj = getProject();
			if (proj != null)
				return JavaCore.create(proj); 
			return null;
		}

		private IProject getProject(){
			String projName = (String)getProperty(FACET_PROJECT_NAME);
			if (projName == null || "".equals(projName)) //$NON-NLS-1$
				return null;
			
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			return project;
		}
		
		private boolean doesProjectExist() {
			IProject project = getProject();
			return (project != null) && project.exists();
		}

		private IStatus checkForDupeArchiveFiles(Set jars,
				TapestryLibrary aJSFLib) {
			if (aJSFLib == null)
				return OK_STATUS;
			
			for (Iterator it=aJSFLib.getArchiveFiles().iterator();it.hasNext();){
				ArchiveFile jar = (ArchiveFile)it.next();
				if (jars.contains(jar.getResolvedSourceLocation())){
					return createErrorStatus(NLS.bind(Messages.TapestryFacetInstallDataModelProvider_DupeJarValidation,jar.getResolvedSourceLocation()));				
				}
	            jars.add(jar.getResolvedSourceLocation());
			}
			return OK_STATUS;
		}
		
		private IPath getProjectPath() {		
			IProject project = getProject();
			if (project == null)
				return null;
			else if (project.exists())
				return project.getLocation();
			
			String projName = (String)getProperty(FACET_PROJECT_NAME);
			IFacetedProjectWorkingCopy projModel = (IFacetedProjectWorkingCopy)getProperty(FACETED_PROJECT_WORKING_COPY );
			
			if (projModel.getProjectLocation() != null)
				return projModel.getProjectLocation().append(projName);

			return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().append(projName);
			
		}

		private IPath getWebContentFolder() {
			//One can get here 2 ways:
			//if web app exists and user is adding a facet, or
			// if creating a new web app.

			IPath webContentPath = null;
			String projName = model.getStringProperty(IFacetDataModelProperties.FACET_PROJECT_NAME);
			IProject proj = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projName);
			
			String webFolder = getWebContentFolderName();
			if (proj.exists()) {
				
				webContentPath = ComponentCore.createComponent(proj).getRootFolder()
							.getUnderlyingFolder().getRawLocation();
			}
			else {			

				if (webFolder == null){
					//we got problems... should not happen
					return proj.getFullPath();
				}
				webContentPath = proj.getFullPath().append(webFolder);

			}
			return webContentPath;
		}
		
		private String getWebContentFolderName() {
			String projName = (String)getProperty(FACET_PROJECT_NAME);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			if (project.exists()){
				IPath webContentPath = ComponentCore.createComponent(project).getRootFolder()
				.getUnderlyingFolder().getProjectRelativePath();

				return webContentPath.toString();
			}
			
			IFacetedProjectWorkingCopy projWC = (IFacetedProjectWorkingCopy)getProperty(FACETED_PROJECT_WORKING_COPY);
			Set<Action> pfas = projWC.getProjectFacetActions();
			for (Action action : pfas){
				if (action.getProjectFacetVersion().getProjectFacet().getId().equals("jst.web")){ //$NON-NLS-1$
					IDataModel webFacet = (IDataModel) action.getConfig();
					return webFacet.getStringProperty(IJ2EEModuleFacetInstallDataModelProperties.CONFIG_FOLDER );
				}
			}
			
			//should not get here.   
			return null;
		}
}
