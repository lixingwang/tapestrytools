
package org.eclipse.jst.pagedesigner.editors.palette.impl;

import org.eclipse.osgi.util.NLS;

/**
 * String resource handler	
 * author: gavingui2011@gmail.com @ China
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jst.pagedesigner.editors.palette.impl.messages"; //$NON-NLS-1$
	/**
	 * see messages.properties
	 */
	public static String PaletteTapestryCustomSectionLabel;
	/**
	 * see messages.properties
	 */
	public static String PaletteTapestryCustomSectionDesc;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
