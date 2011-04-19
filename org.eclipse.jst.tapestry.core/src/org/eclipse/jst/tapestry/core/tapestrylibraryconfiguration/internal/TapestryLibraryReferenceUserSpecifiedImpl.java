package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryInternalReference;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReferenceUserDefined;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReferenceUserSpecified;



public class TapestryLibraryReferenceUserSpecifiedImpl extends AbstractTapestryLibraryReferenceImpl implements TapestryLibraryReferenceUserSpecified, TapestryLibraryReferenceUserDefined{
	/**
	 * @param libRef of type {@link JSFLibraryInternalReference}
	 * @param isDeployed
	 */
	public TapestryLibraryReferenceUserSpecifiedImpl(
			TapestryLibraryInternalReference libRef, boolean isDeployed) {

		super(libRef, isDeployed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jst.jsf.core.jsflibraryconfiguration.internal.AbstractJSFLibraryReferenceImpl#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("UserSpecified: ("); //$NON-NLS-1$
		buf.append(super.toString());
		buf.append(")"); //$NON-NLS-1$
		
		return buf.toString();
	}
}
