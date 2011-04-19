package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReferenceServerSupplied;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryVersion;



public class TapestryLibraryReferenceServerSuppliedImpl extends AbstractTapestryLibraryReferenceImpl 
implements TapestryLibraryReferenceServerSupplied{

	/**
	 * Library display Label 
	 */
	public final static String SERVER_SUPPLIED = Messages.TapestryLibraryReferenceServerSuppliedImpl_Label; 
	/**
	 * Constructor
	 */
	public TapestryLibraryReferenceServerSuppliedImpl(){
		//TODO: replace label with constant
		super(TapestryLibraryReferenceServerSupplied.ID, SERVER_SUPPLIED, true); 
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("ServerSupplied: ("); //$NON-NLS-1$
		buf.append(super.toString());
		buf.append(")"); //$NON-NLS-1$
		
		return buf.toString();
	}
	
	protected TapestryLibrary getLibrary(){
		return null;
	}
	
	public TapestryVersion getMaxSupportedVersion() {
		return TapestryVersion.UNKNOWN;
	}

}
