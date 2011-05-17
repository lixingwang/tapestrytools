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
package org.eclipse.wst.html.core.internal.contentmodel;



import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * Implementation of CMDocument for Tapestry 5.
 */
class TapestryCMDocImpl extends CMNodeImpl implements TapestryCMDocument {

	/** Namespace for all names of elements, entities and attributes. */
	private CMNamespaceImpl namespace = null;
	private TapestryElementCollection elements = null;

	/**
	 * HCMDocImpl constructor comment.
	 */
	public TapestryCMDocImpl(String docTypeName, CMNamespaceImpl targetNamespace) {
		this(docTypeName, targetNamespace, new TapestryElementCollection());
	}

	TapestryCMDocImpl(String docTypeName, CMNamespaceImpl targetNamespace, TapestryElementCollection collection) {
		super(docTypeName);
		namespace = targetNamespace;
		elements = collection;
	}

	public HTMLElementDeclaration getElementDeclaration(String elementName) {
		if (elements == null)
			return null;
		return (HTMLElementDeclaration) elements.getNamedItem(elementName);
	}

	/**
	 * @see org.eclipse.wst.xml.core.internal.contentmodel.CMDocument
	 */
	public CMNamedNodeMap getElements() {
		return elements;
	}

	/**
	 * @see org.eclipse.wst.xml.core.internal.contentmodel.CMDocument
	 */
	public CMNamedNodeMap getEntities() {
		return null;
	}

	public HTMLEntityDeclaration getEntityDeclaration(String entityName) {
		return null;
	}

	/**
	 * @see org.eclipse.wst.xml.core.internal.contentmodel.CMDocument
	 */
	public org.eclipse.wst.xml.core.internal.contentmodel.CMNamespace getNamespace() {
		return namespace;
	}

	/**
	 * @see org.eclipse.wst.xml.core.internal.contentmodel.CMNode
	 */
	public int getNodeType() {
		return CMNode.DOCUMENT;
	}
}
