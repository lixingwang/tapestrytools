package org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.templates.Template;
import org.w3c.dom.Node;

public class CoreComponentsUtil {
	public static List<Template> getAttributeList(TapestryCoreComponents[] components, String contextTypeId, Node currentTapestryComponent){
		String name = currentTapestryComponent.getNodeName();
		List<Template> result = new ArrayList<Template>();
		for(TapestryCoreComponents comp : components){
			if(comp.getElementLabel().equals(name)){
				for(String parameter : comp.getPamameters()){
					Template template = new Template(parameter, buildAttributeDescription(comp.getName(), parameter), contextTypeId, buildAttributeInsertCode(parameter), true);
					result.add(template);
				}
			}
		}

		return result;
	}
	
	/**
	 * Get Tapestry root components in situation: <span t:type="${component name here}"></span>
	 * 
	 * @param contextTypeId
	 * @return
	 */
	public static List<Template> getTapestryComponentNameList(TapestryCoreComponents[] components, String contextTypeId){
		List result = new ArrayList();
		for(TapestryCoreComponents comp : components){
			Template template = new Template(comp.getName(), buildDescription(comp), contextTypeId, comp.getName(), true);
			result.add(template);
		}
		return result;
	}
	
	/**
	 * Get Tapestry root components in normal auto-complete list
	 * 
	 * @param components
	 * @param contextTypeId
	 * @param type
	 * @return
	 */
	public static List<Template> buildTemplateListFromComponents(TapestryCoreComponents[] components, String contextTypeId, int type){
		List<Template> result = new ArrayList<Template>();
		for(TapestryCoreComponents comp : components)
			result.add(createComponentTemplate(comp, contextTypeId, type));
		return result;
	}
	
	public static Template createComponentTemplate(TapestryCoreComponents comp, String contextTypeId, int type){
		Template template = new Template(comp.getName(), buildDescription(comp), contextTypeId, buildInsertCode(comp, type), true);
		return template;
	}
	
	private static String buildAttributeInsertCode(String parameter){
		String ret = parameter + "=\"${cursor}\"";
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
