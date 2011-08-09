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



import org.eclipse.wst.html.core.internal.provisional.HTML40Namespace;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMGroup;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * Table record container.
 * (TR)+
 */
final class CtdTrContainer extends ComplexTypeDefinition {

	/**
	 * @param elementCollection ElementCollection
	 */
	public CtdTrContainer(ElementCollection elementCollection) {
		super(elementCollection);
		primaryCandidateName = HTML40Namespace.ElementName.TR;
	}

	/**
	 * (TR)+.
	 */
	protected void createContent() {
		if (content != null)
			return; // already created.
		if (collection == null)
			return;

		// (TR)+
		content = new CMGroupImpl(CMGroup.SEQUENCE, 1, CMContentImpl.UNBOUNDED);
		CMNode tr = collection.getNamedItem(HTML40Namespace.ElementName.TR);
		if (tr != null)
			content.appendChild(tr);
	}

	/**
	 * ELEMENT content.<br>
	 * @return int; Should be one of ANY, EMPTY, ELEMENT, MIXED, PCDATA, CDATA,
	 * those are defined in CMElementDeclaration.
	 */
	public int getContentType() {
		return CMElementDeclaration.ELEMENT;
	}

	/**
	 * Name of complex type definition.
	 * Each singleton must know its own name.
	 * All names should be defined in
	 * {@link <code>ComplexTypeDefinitionFactory</code>} as constants.<br>
	 * @return java.lang.String
	 */
	public String getTypeName() {
		return ComplexTypeDefinitionFactory.CTYPE_TR_CONTAINER;
	}
}
