/*******************************************************************************
 * Copyright (c) 2007 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.pagedesigner.properties.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Externalized strings
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jst.pagedesigner.properties.internal.messages"; //$NON-NLS-1$
	/**
	 * 
	 */
	public static String AttributesTabDescriptor_label;
	/**
	 * 
	 */
	public static String QuickEditTabDescriptor_label;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		//
	}
}
