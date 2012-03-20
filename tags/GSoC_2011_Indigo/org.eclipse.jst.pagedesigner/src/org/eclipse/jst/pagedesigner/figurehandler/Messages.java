/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Oracle - initial API and implementation
 * 
 ********************************************************************************/
package org.eclipse.jst.pagedesigner.figurehandler;

import org.eclipse.osgi.util.NLS;

/**
 * String resource handler
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jst.pagedesigner.figurehandler.messages"; //$NON-NLS-1$
	/**
	 * see messages.properties
	 */
	public static String InputFigureHandler_Browse;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		//
	}
}
