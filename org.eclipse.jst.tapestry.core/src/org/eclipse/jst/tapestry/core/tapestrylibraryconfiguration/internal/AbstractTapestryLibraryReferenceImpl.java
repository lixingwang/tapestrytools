package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryInternalReference;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReference;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryVersion;



public abstract class AbstractTapestryLibraryReferenceImpl implements TapestryLibraryReference{
	/**
	 * The {@link org.eclipse.jst.jsf.core.internal.jsflibraryconfig.JSFLibraryInternalReference} being wrapped
	 */
	protected TapestryLibraryInternalReference libRef;
	private String _id;
	private String _label;
	private boolean _isImplementation;
	private boolean _isDeloyed;

	/**
	 * Constructor for "virtual" JSF Library References like "ServerSupplied"
	 * @param id
	 * @param label 
	 * @param isImplementation 
	 */
	public AbstractTapestryLibraryReferenceImpl(String id, String label, boolean isImplementation){
		_id = id;
		_label = label;
		_isImplementation = isImplementation;
	}
	
	/**
	 * Constructor non-virtual library references
	 * @param libRef
	 * @param isDeployed 
	 */
	public AbstractTapestryLibraryReferenceImpl(TapestryLibraryInternalReference libRef, boolean isDeployed){
		this.libRef = libRef;
		_isDeloyed = isDeployed;
	}
	
	public String getId() {
		if (libRef != null)
			return libRef.getID();
		
		return _id;
	}

	public String getLabel() {
		if (libRef != null)
			return libRef.getLabel();

		return _label;
	}

	public boolean isDeployed() {
		return _isDeloyed;
	}

	public boolean isJSFImplementation() {
		if (libRef != null)
			return libRef.isImplementation();
		
		return _isImplementation;
	}
	
	/**
	 * @return the JSFLibrary underpinning the reference.  
	 * May be null if the library is missing or cannot be resolved from the registry.
	 */
	protected TapestryLibrary getLibrary(){
		return libRef.getLibrary();
	}
	
	public Collection<IClasspathEntry> getJars() {
		Set<IClasspathEntry> results = new HashSet<IClasspathEntry>();
		if (getLibrary() != null){
			List jars = getLibrary().getArchiveFiles();
			for (Iterator it= jars.iterator();it.hasNext();){
				ArchiveFile jar = (ArchiveFile)it.next();
				String path = jar.getResolvedSourceLocation();
				results.add(JavaCore.newLibraryEntry(new Path(path), null, null));
			}
		}			
		return results;
	}

	public TapestryVersion getMaxSupportedVersion() {
		if (getLibrary() != null)
			return adaptVersion(getLibrary().getTapestryVersion());
		return null;
	}

	private TapestryVersion adaptVersion(
			org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryVersion version) {
		
		switch (version.getValue()){
			case org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryVersion.V5_0:
				return TapestryVersion.V5_0;					
			default:
				return TapestryVersion.UNKNOWN;			
				
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jst.jsf.core.jsflibraryconfiguration.JSFLibraryReference#getName()
	 */
	public String getName() {
		if (getLibrary()!= null) {
			return getLibrary().getName();
		}
		return getId();
	}

	public String toString(){
		StringBuffer buf = new StringBuffer("id: "); //$NON-NLS-1$
		buf.append(getId());
		buf.append(", label: "); //$NON-NLS-1$
		buf.append(getLabel());
		buf.append(", isDeployed: "); //$NON-NLS-1$
		buf.append(isDeployed());
		buf.append(", isImpl: "); //$NON-NLS-1$
		buf.append(isJSFImplementation());
		buf.append(", version: "); //$NON-NLS-1$
		buf.append(getMaxSupportedVersion().name());
		
		return buf.toString();
	}
}
