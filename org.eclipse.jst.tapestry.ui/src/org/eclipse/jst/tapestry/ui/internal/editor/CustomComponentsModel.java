package org.eclipse.jst.tapestry.ui.internal.editor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.text.IDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomComponentsModel  {

	private static final String UTF8 = "UTF-8";

	private final DocumentBuilderFactory domfac = DocumentBuilderFactory
			.newInstance();
	private DocumentBuilder dombuilder = null;

	private List<ComponentPackage> packageList = null;
	private List<ComponentInstance> componentList = null;

	private static final String PACKAGELIST = "packageList";
	private static final String COMPONENTLIST = "componentList";

	private static final String[] KNOWN_PROPERTIES = new String[] { PACKAGELIST, COMPONENTLIST };

	private Properties properties = new Properties();
	
	
	public void loadFrom(IDocument document) throws IOException {
		if (packageList == null) packageList = new ArrayList<ComponentPackage>();
		else packageList.clear();
		if (componentList == null) componentList = new ArrayList<ComponentInstance>();
		else componentList.clear();


		InputStream stream = new ByteArrayInputStream(document.get().getBytes(UTF8));

		try {
			dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(stream);
			Element root = doc.getDocumentElement();
			loadPackageList(root);
			loadComponentList(root);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		properties.put(PACKAGELIST, packageList);
	}
	
	private void getPackageBasicInfo(ComponentPackage ci, Node classItem){
		Node prefix = classItem.getAttributes().getNamedItem("prefix");
		if (prefix != null)
			ci.setPrefix(prefix.getNodeValue());
		Node path = classItem.getAttributes().getNamedItem("path");
		if (path != null)
			ci.setPath(path.getNodeValue());
		
	}
	
	public void removePackageByPath(String path){
		for(int i=0; i<this.packageList.size(); i++)
			if(this.packageList.get(i).getPath().equals(path)){
				this.packageList.remove(i);
				break;
			}
	}
	
	public void addPackageByPath(String path){
		ComponentPackage cp = new ComponentPackage();
		cp.setPath(path);
		cp.setPrefix("t");
		this.packageList.add(cp);
	}
	
	public void modifyPackagePrefix(String path, String prefix){
		for(int i=0; i<this.packageList.size(); i++){
			ComponentPackage ci = this.packageList.get(i);
			if(ci.getPath().trim().equals(path.trim())){
				ci.setPrefix(prefix);
				break;
			}
		}
	}
	
	private void loadPackageList(Element root){
		NodeList bundles = root.getChildNodes();
		if (bundles != null) {
			for (int i = 0; i < bundles.getLength(); i++) {
				Node classCycles = bundles.item(i);
				if (classCycles.getNodeType() == Node.ELEMENT_NODE
						&& classCycles.getNodeName().trim().equals("packages")) {
					NodeList classes = classCycles.getChildNodes();
					for(int j=0; j<classes.getLength(); j++){
						Node classItem = classes.item(j);
						if(classItem.getNodeType() == Node.ELEMENT_NODE && classItem.getNodeName().trim().equals("package")){
							ComponentPackage ci = new ComponentPackage();
							getPackageBasicInfo(ci, classItem);
							this.packageList.add(ci);
						}
					}
					break;
				}
			}
		}
		//sort
		for(int i=0; i<packageList.size(); i++)
			for(int j=i+1; j<packageList.size(); j++){
				ComponentPackage ci = packageList.get(i);
				ComponentPackage cj = packageList.get(j);
				if(cj.getPath().compareTo(ci.getPath()) < 0){
					ComponentPackage temp = ci;
					packageList.set(i, cj);
					packageList.set(j, temp);
				}
			}
	}
	
	private void loadComponentList(Element root){
		NodeList bundles = root.getChildNodes();
		if (bundles != null) {
			for (int i = 0; i < bundles.getLength(); i++) {
				Node classCycles = bundles.item(i);
				if (classCycles.getNodeType() == Node.ELEMENT_NODE
						&& classCycles.getNodeName().trim().equals("components")) {
					NodeList classes = classCycles.getChildNodes();
					for(int j=0; j<classes.getLength(); j++){
						Node classItem = classes.item(j);
						if(classItem.getNodeType() == Node.ELEMENT_NODE && classItem.getNodeName().trim().equals("component")){
							ComponentInstance ci = new ComponentInstance();
							getComponentBasicInfo(ci, classItem);
							this.componentList.add(ci);
						}
					}
					break;
				}
			}
		}
		//sort classes
		for(int i=0; i<componentList.size(); i++)
			for(int j=i+1; j<componentList.size(); j++){
				ComponentInstance ci = componentList.get(i);
				ComponentInstance cj = componentList.get(j);
				if(cj.getName().compareTo(ci.getName()) < 0){
					ComponentInstance temp = ci;
					componentList.set(i, cj);
					componentList.set(j, temp);
				}
			}
	}
	
	private void getComponentBasicInfo(ComponentInstance cc, Node cycle){
		Node id = cycle.getAttributes().getNamedItem("id");
		if (id != null)
			cc.setId(id.getNodeValue());
		Node name = cycle.getAttributes().getNamedItem("name");
		if (name != null)
			cc.setName(name.getNodeValue());
		Node text = cycle.getAttributes().getNamedItem("text");
		if (text != null)
			cc.setText(text.getNodeValue());
		Node prefix = cycle.getAttributes().getNamedItem("prefix");
		if (prefix != null)
			cc.setPrefix(prefix.getNodeValue());
		Node path = cycle.getAttributes().getNamedItem("path");
		if (path != null)
			cc.setPath(path.getNodeValue());
	}
	
	
	public List<String> getPackageList() {
		List<String> classesList = new ArrayList<String>();
		for(int i=0; i<packageList.size(); i++){
			ComponentPackage cc = packageList.get(i);
			classesList.add(cc.getPath());
		}
		return classesList;
	}
	
	public List<String> getComponentList() {
		List<String> classesList = new ArrayList<String>();
		for(int i=0; i<this.componentList.size(); i++){
			ComponentInstance cc = componentList.get(i);
			classesList.add(cc.getText());
		}
		return classesList;
	}
	
	public List<String> getComponentList(String path) {
		List<String> classesList = new ArrayList<String>();
		for(int i=0; i<this.componentList.size(); i++){
			ComponentInstance cc = componentList.get(i);
			if(cc.getPath().trim().equals(path.trim())) classesList.add(cc.getText());
		}
		return classesList;
	}
	
	public ComponentPackage getPackageByPath(String path){
		for(ComponentPackage cp : this.packageList)
			if(cp.getPath().equals(path)) return cp;
		return null;
	}
	
	public void saveChangesTo(IDocument document) {
		StringBuilder buffer = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n");
		buffer.append("<packages>\n");
		for(ComponentPackage cp : this.packageList)
			buffer.append(getPackagesContent(cp));
		buffer.append("</packages>\n");
		
		buffer.append("<components>\n");
		for(ComponentInstance ci : this.componentList)
			buffer.append(getCustomComponentContent(ci));
		buffer.append("</components>\n");
		buffer.append("</root>");
		document.set(buffer.toString());
	}
	
	
	private String getPackagesContent(ComponentPackage cp){
		String ret ="<package prefix=\"$prefix$\" path=\"$path$\"/>\n";
		ret = ret.replace("$prefix$", cp.getPrefix());
		ret = ret.replace("$path$", cp.getPath());
		return ret;
	}
	private String getCustomComponentContent(ComponentInstance ci){
		String ret ="<component id=\"$id$\" name=\"$name$\" text=\"$text$\" prefix=\"$prefix$\" path=\"$path$\"/>\n";
		ret = ret.replace("$id$", ci.getId());
		ret = ret.replace("$name$", ci.getName());
		ret = ret.replace("$text$", ci.getText());
		ret = ret.replace("$prefix$", ci.getPrefix());
		ret = ret.replace("$path$", ci.getPath());
		return ret;
	}

	public void setComponentList(List<ComponentInstance> componentList) {
		this.componentList = componentList;
	}
	
}
