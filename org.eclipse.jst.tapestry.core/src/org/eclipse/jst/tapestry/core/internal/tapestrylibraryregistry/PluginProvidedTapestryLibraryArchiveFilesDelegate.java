package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry;

import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.internal.PluginProvidedTapestryLibraryCreationHelper2;


public abstract class PluginProvidedTapestryLibraryArchiveFilesDelegate {
private PluginProvidedTapestryLibraryCreationHelper2 helper;
	
	/**
	 * Constructs an instance.
	 */
	public PluginProvidedTapestryLibraryArchiveFilesDelegate() {
		super();	
	}
	
	/**
	 * Concrete delegate must implement this method to define jars in the library.
	 * Use addJarFile(String pluginRootRelativePath) within this method to add jars to the library.
	 */
	public abstract void getArchiveFiles(); 
	
	/**
	 * Adds jar file to the library.  Verification of file existence does not occur at this point.
	 * 
	 * Jar must be specified as being relative to the plugin.  
	 * ex.  "/lib/MyJar.jar" where the lib directory is a child of the root.
	 *  
	 * @param pluginRootRelativePath
	 */
	protected void addArchiveFile(String pluginRootRelativePath) {
		helper.addArchiveFile(pluginRootRelativePath);//getArchiveFiles().add(helper.createArchiveFile(pluginRootRelativePath));
	}

	/**
	 * Not to be implemented by subclasses
	 * @param helper
	 */
	public void setCreationHelper(
			PluginProvidedTapestryLibraryCreationHelper2 helper) {		
		this.helper = helper;
	}
}
