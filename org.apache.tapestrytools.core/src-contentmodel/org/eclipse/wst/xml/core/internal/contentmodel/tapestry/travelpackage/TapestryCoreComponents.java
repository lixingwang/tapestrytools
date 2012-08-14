/*******************************************************************************
 * Copyright (c) 2012 gavingui2011@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     gavingui2011@gmail.com  - initial API and implementation
 *     
 *******************************************************************************/

package org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage;

import java.util.ArrayList;
import java.util.List;

public class TapestryCoreComponents {
	private String name;
	private String elementLabel;
	private List<String> pamameters = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPamameters() {
		return pamameters;
	}
	public void setPamameters(List<String> pamameters) {
		this.pamameters = pamameters;
	}
	public void addParameter(String parameter){
		this.pamameters.add(parameter);
	}
	public String getElementLabel() {
		return elementLabel;
	}
	public void setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
	}
}
