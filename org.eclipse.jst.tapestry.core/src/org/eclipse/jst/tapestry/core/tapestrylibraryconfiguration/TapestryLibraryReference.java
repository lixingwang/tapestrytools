package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration;

import java.util.Collection;

import org.eclipse.jdt.core.IClasspathEntry;

public abstract interface TapestryLibraryReference {
	/**
	 * @return id for the library
	 */
	public String getId();	
	/**
	 * @return name
	 */
	public String getName();
	/**
	 * @return collection of jars as {@link IClasspathEntry}s
	 */
	public Collection<IClasspathEntry> getJars();
	/**
	 * @return {@link JSFVersion} value
	 */
	public TapestryVersion getMaxSupportedVersion();
	/**
	 * @return label user sees for this library
	 */
	public String getLabel();
	/**
	 * @return flag 
	 */
	public boolean isJSFImplementation();
	/**
	 * @return is deployed (marked as J2EE Module Dependency)
	 */
	public boolean isDeployed();	
}
