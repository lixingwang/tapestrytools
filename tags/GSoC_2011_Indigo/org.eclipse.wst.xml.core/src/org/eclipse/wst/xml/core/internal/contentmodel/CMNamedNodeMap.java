/*******************************************************************************
 * Copyright (c) 2002, 2006 IBM Corporation and others.
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
package org.eclipse.wst.xml.core.internal.contentmodel;
import java.util.Iterator;
/**
 * NamedCMNodeMap
 */
public interface CMNamedNodeMap {
/**
 * getLength method
 * @return int
 */
int getLength();
/**
 * getNamedItem method
 * @return CMNode
 * @param name java.lang.String
 */
CMNode getNamedItem(String name);
/**
 * item method
 * @return CMNode
 * @param index int
 */
CMNode item(int index);

/**
 *
 */
Iterator iterator();
}
