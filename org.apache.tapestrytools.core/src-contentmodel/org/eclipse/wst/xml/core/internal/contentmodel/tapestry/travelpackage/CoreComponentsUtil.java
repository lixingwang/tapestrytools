package org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.templates.Template;

public class CoreComponentsUtil {
	public static Template[] buildTemplateListFromComponents(TapestryCoreComponents[] components, String contextTypeId, int type){
		List<Template> result = new ArrayList<Template>();
		for(TapestryCoreComponents comp : components){
			Template template = new Template(comp.getName(), buildDescription(comp), contextTypeId, buildInsertCode(comp, type), true);
			result.add(template);
		}
		
		return result.toArray(new Template[0]);
	}
	
	private static String buildDescription(TapestryCoreComponents component){
		return "Tapestry Core Components";
	}
	
	private static String buildInsertCode(TapestryCoreComponents element, int type){
		String ret = "";
		String insertLabel = "t:" + element.getName().toLowerCase();
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
