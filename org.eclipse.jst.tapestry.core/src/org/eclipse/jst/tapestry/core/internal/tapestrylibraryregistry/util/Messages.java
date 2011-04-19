package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.messages"; //$NON-NLS-1$
	/**
	 * see messages.properties
	 */
	public static String TapestryLibraryRegistryUpgradeUtil_Error;
	/**
	 * see messages.properties
	 */
	public static String TapestryLibraryRegistryUpgradeUtil_Operation;
	/**
	 * see messages.properties
	 */
	public static String TapestryLibraryRegistryUpgradeUtil_v1Tov2Operation;
	/**
	 * see messages.properties
	 */
	public static String UpgradeOperation_Success;
	/**
	 * see messages.properties
	 */
	public static String UpgradeStatus_Error;
	/**
	 * see messages.properties
	 */
	public static String UpgradeStatus_OK;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		//
	}
}