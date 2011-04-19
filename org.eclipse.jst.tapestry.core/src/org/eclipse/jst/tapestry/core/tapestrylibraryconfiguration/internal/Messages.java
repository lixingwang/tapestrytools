package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import org.eclipse.osgi.util.NLS;

public class Messages {
	private static final String BUNDLE_NAME = "org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal.messages"; //$NON-NLS-1$
	/**
	 * see messages.properties
	 */
	public static String TapestryLibraryReferenceServerSuppliedImpl_Label;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		//
	}
}
