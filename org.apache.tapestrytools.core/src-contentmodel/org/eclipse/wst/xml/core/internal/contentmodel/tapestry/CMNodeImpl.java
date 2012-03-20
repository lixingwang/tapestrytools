package org.eclipse.wst.xml.core.internal.contentmodel.tapestry;

/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;



/**
 */
public abstract class CMNodeImpl implements CMNode {

	private java.lang.String name = null;

	/**
	 * CMNodeImpl constructor comment.
	 */
	public CMNodeImpl(String nm) {
		super();
		name = nm;
	}

	/**
	 * getNodeName method
	 * @return java.lang.String
	 */
	public String getNodeName() {
		return name;
	}
	
	public void setNodeName(String n) {
		name = n;
	}

	/**
	 * getProperty method
	 * @return java.lang.Object
	 *
	 * Returns the object property desciped by the propertyName
	 *
	 */
	public Object getProperty(String propertyName) {
		if (propertyName.equals("isXHTML"))
			return new Boolean(false);
		return null;
	}

	/**
	 * supports method
	 * @return boolean
	 *
	 * Returns true if the CMNode supports a specified property
	 *
	 */
	public boolean supports(String propertyName) {
		if (propertyName.equals("isXHTML"))
			return true;
		return false;
	}
}
