package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryInternalReference;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReferencePluginProvided;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;



public class TapestryLibraryReferencePluginProvidedImpl extends AbstractTapestryLibraryReferenceImpl implements TapestryLibraryReferencePluginProvided {

	/**
	 * Constructor
	 * @param libRef
	 * @param isDeployed 
	 */
	public TapestryLibraryReferencePluginProvidedImpl(TapestryLibraryInternalReference libRef, boolean isDeployed) {
		super(libRef, isDeployed);
	}

	public String getPluginId() {
		if (getLibrary() != null)
			return ((PluginProvidedTapestryLibrary) getLibrary()).getPluginID();
		
		return null;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("PluginProvided: ("); //$NON-NLS-1$
		buf.append(super.toString());
		
		return buf.toString();
	}
}
