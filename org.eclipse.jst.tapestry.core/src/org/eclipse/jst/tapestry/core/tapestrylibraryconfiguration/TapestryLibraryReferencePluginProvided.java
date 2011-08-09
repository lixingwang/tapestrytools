package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration;



public interface TapestryLibraryReferencePluginProvided extends TapestryLibraryReferenceUserSpecified, TapestryLibraryReferenceUserDefined {
	/**
	 * @return plugin id.  May return null if plugin id cannot be determined.  
	 */
	public String getPluginId();
}
