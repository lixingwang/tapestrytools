package org.eclipse.jst.tapestry.core.tapestrylibraryregistry;

import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.osgi.framework.Bundle;

public abstract class TapestryLibraryArchiveFilesDelegate {
	private IConfigurationElement extPtElement;
	private String libID = null;
	private IPath pluginPath = null;
	private String pluginID;
	private String relativeDestLocation = "WEB-INF/lib"; //$NON-NLS-1$

	/**
	 * Returns a Collection of <code>org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile</code>
	 * instances.
	 * 
	 * @return A Collection of <code>org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile</code>
	 * instances
	 */
	public abstract Collection getArchiveFiles();

	/**
	 * Sets the IConfigurationElement instance to be subsequently used to get
	 * the plugin ID and the name defined for the JSF Library.
	 * 
	 * @param extPtElement IConfigurationElement instance
	 */
	public final void setConfigurationElement(IConfigurationElement extPtElement){
		this.extPtElement = extPtElement;
	}

	/**
	 * Constructs an instance.
	 */
	public TapestryLibraryArchiveFilesDelegate() {
		super();
	}

	/**
	 * Sets the relative destination location subsequently used to set the
	 * corresponding property on each created ArchiveFile.
	 * 
	 * @param relPath Relative destination location for ArchiveFile instances
	 */
	protected void setRelativeDestinationLocation(String relPath){
		relativeDestLocation = relPath;
	}
	
	/**
	 * Returns ArchiveFile where the location is set relative to the plugin.   
	 * As long as the ArchiveFile is on the local machine somewhere, it should
	 * be locatable.
	 *  
	 * @param relativePathFileName Relative location of the ArchiveFile
	 * @return ArchiveFile instance.
	 */
	protected ArchiveFile createArchiveFile(String relativePathFileName){
		ArchiveFile file = TapestryLibraryRegistryFactory.eINSTANCE.createArchiveFile();
		file.setRelativeToWorkspace(false);
		file.setSourceLocation(relativePathFileName);
		file.setRelativeDestLocation(relativeDestLocation);
		return file;
	}
	
//	protected ArchiveFile createArchiveFileWithAbsolutePath(String fullPath){
//		ArchiveFile file = JSFLibraryRegistryFactory.eINSTANCE.createArchiveFile();
//		file.setRelativeToWorkspace(false);
//		file.setSourceLocation(fullPath);
//		file.setRelativeDestLocation(relativeDestLocation);
//		return file;
//	}

	/**
	 * Returns the JSFLibrary ID as set on the extension point.
	 * 
	 * @return JSFLibrary ID
	 */
	protected String getLibID(){
		if (libID == null){
			StringBuffer buf = new StringBuffer(getPluginID());
			buf.append("/").append(extPtElement.getAttribute(PluginProvidedTapestryLibraryCreationHelper.NAME)); //$NON-NLS-1$
			libID = buf.toString();
		}
		return libID;
	}

	/**
	 * Returns the plugin's path.
	 * 
	 * @return The plugin's path.
	 */
	protected IPath getPluginPath(){
		if (pluginPath == null){
			Bundle bundle = Platform.getBundle(getPluginID());
			pluginPath = new Path(bundle.getLocation());
		}
		return pluginPath;
	}

	/**
	 * Returns the plugin's ID.
	 * 
	 * @return The plugin's ID.
	 */
	private String getPluginID() {
		if (pluginID == null){
			pluginID = extPtElement.getDeclaringExtension().getContributor().getName();
		}
		return pluginID;
	}
}
