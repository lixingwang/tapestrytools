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
