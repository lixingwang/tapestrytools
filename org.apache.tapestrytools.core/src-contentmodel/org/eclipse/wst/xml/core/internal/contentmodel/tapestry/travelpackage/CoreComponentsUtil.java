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

import org.eclipse.jface.text.templates.Template;

public class CoreComponentsUtil {
	public static List<Template> getAttributeList(TapestryCoreComponents[] components, String contextTypeId, String currentTapestryComponentName){
		//String name = currentTapestryComponent.getNodeName();
		List<Template> result = new ArrayList<Template>();
		for(TapestryCoreComponents comp : components){
			if(comp.getElementLabel().equals(currentTapestryComponentName)){
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
			Template template = new Template("t:" + comp.getName(), buildDescription(comp), contextTypeId, comp.getName(), true);
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
			result.add(createComponentTemplate("t", comp, contextTypeId, type));
		return result;
	}
	
	public static Template createComponentTemplate(String prefix, TapestryCoreComponents comp, String contextTypeId, int type){
		Template template = new Template(prefix + ":" + comp.getName(), buildDescription(comp), contextTypeId, buildInsertCode(comp, type), true);
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
