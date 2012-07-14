package org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.templates.Template;
import org.w3c.dom.Node;

public class CoreComponentsUtil {
	public static Template[] getAttributeList(TapestryCoreComponents[] components, String contextTypeId, Node currentTapestryComponent){
		String name = currentTapestryComponent.getNodeName();
		List result = new ArrayList();
		for(TapestryCoreComponents comp : components){
			if(comp.getElementLabel().equals(name)){
				for(String parameter : comp.getPamameters()){
					Template template = new Template(parameter, buildAttributeDescription(comp.getName(), parameter), contextTypeId, buildAttributeInsertCode(parameter), true);
					result.add(template);
				}
			}
		}

		return (Template[])result.toArray(new Template[0]);
	}
	
	
	public static Template[] buildTemplateListFromComponents(TapestryCoreComponents[] components, String contextTypeId, int type){
		List<Template> result = new ArrayList<Template>();
		for(TapestryCoreComponents comp : components){
			Template template = new Template(comp.getName(), buildDescription(comp), contextTypeId, buildInsertCode(comp, type), true);
			result.add(template);
		}
		
		return result.toArray(new Template[0]);
	}
	
	private static String buildAttributeInsertCode(String parameter){
		String ret = parameter + "=\"\"";
		return ret;
	}
	
	private static String buildAttributeDescription(String comp, String parameter){
		return comp + " Attrubite";
	}
	
	private static String buildDescription(TapestryCoreComponents component){
		return "Tapestry Core Components";
	}
	
	private static String buildInsertCode(TapestryCoreComponents element, int type){
		String ret = "";
		String insertLabel = element.getElementLabel();
		switch(type){
		case 1:
			ret = "<"+insertLabel+"></"+insertLabel+">";
			break;
		case 2:
			ret = insertLabel+"></"+insertLabel+">";
			break;
		case 3:
			ret = insertLabel+"></"+insertLabel+">";
			if(ret.length() > 2)
				ret = ret.substring(2);
			break;
		}
		
		return ret;
	}
}
