package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibraryArchiveFilesDelegate;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryVersion;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.osgi.util.NLS;

public final class PluginProvidedTapestryLibraryCreationHelper2 {
	private PluginProvidedTapestryLibrary newLib;
	private IConfigurationElement config_element;
	private String relativeDestLocation = "WEB-INF/lib"; //$NON-NLS-1$
	
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
	 * Key of the 'label' attribute of the extension point.
	 */
	public final static String LABEL 		= "label"; //$NON-NLS-1$
	
	/**
	 * Creates an instance with the specified IConfigurationElement instance.
	 * 
	 * @param jsfLibrary IConfigurationElement instance
	 */
	public PluginProvidedTapestryLibraryCreationHelper2 (IConfigurationElement jsfLibrary){
		this.config_element = jsfLibrary;
	}
	
	/**
	 * Add a jar file to the library
	 * @param pluginRootRelativePath
	 */
	public void addArchiveFile(String pluginRootRelativePath) {
		ArchiveFile jar = createArchiveFile(pluginRootRelativePath);
		if (!newLib.containsArchiveFile(jar.getSourceLocation()))
			newLib.getArchiveFiles().add(jar);
	}
	
	/**
	 * Creates a new PluginProvidedJSFLibrary from the JSFLibrary extension point.
	 * 
	 * @return PluginProvidedJSFLibrary instance.
	 */
	public TapestryLibrary create(){
		newLib = TapestryLibraryRegistryFactory.eINSTANCE.createPluginProvidedTapestryLibrary();
		newLib.setPluginID(getPluginID());
		newLib.setName(config_element.getAttribute(NAME));
		String label = config_element.getAttribute(LABEL);
		if (label != null && label.length() > 0){
			newLib.setLabel(label);
		}
		newLib.setImplementation(config_element.getAttribute(IS_IMPL).equals("true") ? true : false); //$NON-NLS-1$		
		newLib.setTapestryVersion(TapestryVersion.getJSFVersion(config_element.getAttribute(VERSION)));
		
		try {
			addArchives();			
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
	 * @throws CoreException on core failure.
	 */
	private void addArchives() throws CoreException {
		PluginProvidedTapestryLibraryArchiveFilesDelegate jarCol = null;

		jarCol = (PluginProvidedTapestryLibraryArchiveFilesDelegate)config_element.createExecutableExtension(DELEGATE);
		if (jarCol != null){
			jarCol.setCreationHelper(this);
			jarCol.getArchiveFiles();
		}
	}
	/**
	 * Returns ArchiveFile where the location is set relative to the plugin.   
	 * As long as the ArchiveFile is on the local machine somewhere, it should
	 * be locatable.
	 *  
	 * @param relativePathFileName Relative location of the ArchiveFile
	 * @return ArchiveFile instance.
	 */
	private ArchiveFile createArchiveFile(String pluginRootRelativePath){
		ArchiveFile file = TapestryLibraryRegistryFactory.eINSTANCE.createArchiveFile();
		file.setRelativeToWorkspace(false);
		file.setSourceLocation(pluginRootRelativePath);
		file.setRelativeDestLocation(relativeDestLocation);
		return file;
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
