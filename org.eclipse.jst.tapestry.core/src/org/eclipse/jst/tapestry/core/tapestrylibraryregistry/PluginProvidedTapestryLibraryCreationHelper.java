package org.eclipse.jst.tapestry.core.tapestrylibraryregistry;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryVersion;
import org.eclipse.osgi.util.NLS;

public final class PluginProvidedTapestryLibraryCreationHelper {
	private IConfigurationElement config_element;

	/**
	 * Key of the 'name' attribute of the extension point.
	 */
	public final static String NAME 		= "name"; //$NON-NLS-1$
	/**
	 * Key of the 'isImplementation' attribute of the extension point.
	 */
	public final static String IS_IMPL 		= "isImplementation"; //$NON-NLS-1$
	/**
	 * Key of the 'maxVersionSupported' attribute of the extension point.
	 */
	public final static String VERSION 		= "maxVersionSupported"; //$NON-NLS-1$
	/**
	 * Key of the 'archiveFilesDelegate' attribute of the extension point.
	 */
	public final static String DELEGATE 	= "archiveFilesDelegate"; //$NON-NLS-1$

	/**
	 * Creates an instance with the specified IConfigurationElement instance.
	 * 
	 * @param jsfLibrary IConfigurationElement instance
	 */
	public PluginProvidedTapestryLibraryCreationHelper (IConfigurationElement jsfLibrary){
		this.config_element = jsfLibrary;
	}

	/**
	 * Creates a new PluginProvidedJSFLibrary from the JSFLibrary extension point.
	 * 
	 * @return PluginProvidedJSFLibrary instance.
	 */
	public TapestryLibrary create(){
		PluginProvidedTapestryLibrary newLib = TapestryLibraryRegistryFactory.eINSTANCE.createPluginProvidedTapestryLibrary();
//		newLib.setID(getLibID());
		newLib.setPluginID(getPluginID());
		newLib.setName(config_element.getAttribute(NAME));
		newLib.setImplementation(config_element.getAttribute(IS_IMPL).equals("true") ? true : false); //$NON-NLS-1$		
		newLib.setTapestryVersion(TapestryVersion.getJSFVersion(config_element.getAttribute(VERSION)));
		
		try {
			addArchives(newLib);			
			return newLib;
		} catch (Exception e) {
			TapestryCorePlugin.log(
					e,
					NLS.bind(
							Messages.PluginProvidedTapestryLibraryCreationHelper_ErrorCreating,
							newLib.getName()));
		}
		return null;
	}

	/**
	 * Adds ArchiveFile instances to the specified JSFLibrary instance.
	 * 
	 * @param newLib JSFLibrary instance
	 * @throws InvalidArchiveFilesCreationException on attempt to create
	 * multiple instances at same location.
	 * @throws CoreException on core failure.
	 */
	private void addArchives(TapestryLibrary newLib) throws InvalidArchiveFilesCreationException, CoreException {
		TapestryLibraryArchiveFilesDelegate jarCol = null;
		ArchiveFile jar = null;

		jarCol = (TapestryLibraryArchiveFilesDelegate)config_element.createExecutableExtension(DELEGATE);
		if (jarCol != null){
			jarCol.setConfigurationElement(config_element);
			Collection jars = jarCol.getArchiveFiles();
			if (jars == null)//TODO: better validation and error handling
				return;
			Iterator it = jars.iterator();
			while (it.hasNext()){
				Object aJar = it.next();
				if (aJar instanceof ArchiveFile){//for now check to see ArchiveFiles were being returned
					jar = (ArchiveFile)aJar;
					if (!newLib.containsArchiveFile(jar.getSourceLocation()))
						newLib.getArchiveFiles().add(jar);
				}
				else {
					throw new InvalidArchiveFilesCreationException(
							NLS.bind(
									Messages.PluginProvidedTapestryLibraryCreationHelper_ErrorMultipleDefinition,
									jar.getSourceLocation(),
									config_element.getName()));
				}
					
			}
		}
	}
	
	/**
	 * Returns the plugin's ID.
	 * 
	 * @return The plugin's ID
	 */
	private String getPluginID() {
		return config_element.getDeclaringExtension().getContributor().getName();
	}
}
