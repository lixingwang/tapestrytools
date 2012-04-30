/*******************************************************************************
 * Copyright (c) 2001, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/
package org.eclipse.wst.xml.ui.internal.templates;

public class TemplateContextTypeIdsXML {
	public static final String TAPESTRY_COMPONENTS = getTapestryComponents();
	public static final String TAPESTRY_COMPONENTS_ATTR = getTapestryComponentsAttribute();
	public static final String TAPESTRY_COMPONENTS_ATTR_VALUE = getTapestryComponentsAttributeValue();
	public static final String TAPESTRY_ENTITIES = getTapestryEntities();

	public static final String ALL = getAll();

	public static final String ATTRIBUTE = getAttribute();

	public static final String ATTRIBUTE_VALUE = getAttributeValue();

	public static final String NEW = getNew();

	public static final String TAG = getTag();

	private static String getTapestryComponents(){
		return getTapestryPrefix() + "_components";
	}
	
	private static String getTapestryComponentsAttribute(){
		return getTapestryPrefix() + "_attributes";
	}
	
	private static String getTapestryEntities(){
		return getTapestryPrefix() + "_entities";
	}
	
	private static String getTapestryComponentsAttributeValue(){
		return getTapestryPrefix() + "_attributes_value";
	}
	
	private static String getTapestryPrefix(){
		return "tml";
	}
	
	
	private static String getAll() {
		return getPrefix() + "_all"; //$NON-NLS-1$
	}

	private static String getAttribute() {
		return getPrefix() + "_attribute"; //$NON-NLS-1$
	}

	private static String getAttributeValue() {
		return getPrefix() + "_attribute_value"; //$NON-NLS-1$
	}

	private static String getNew() {
		return getPrefix() + "_new"; //$NON-NLS-1$
	}

	private static String getPrefix() {
		return "xml"; //$NON-NLS-1$
	}

	private static String getTag() {
		return getPrefix() + "_tag"; //$NON-NLS-1$
	}
}
