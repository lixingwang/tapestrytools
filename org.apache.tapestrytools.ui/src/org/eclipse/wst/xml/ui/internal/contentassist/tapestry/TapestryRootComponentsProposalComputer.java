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

package org.eclipse.wst.xml.ui.internal.contentassist.tapestry;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tapestrytools.ui.internal.tcc.editor.ComponentPackage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage.TapestryClassLoader;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage.TapestryCoreComponents;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TapestryRootComponentsProposalComputer {
	private String webXMLPath = null;
	private List<Template> components = new ArrayList<Template>();
	
	public List<Template> getRootComponentsAttributes(IProject project, String contextTypeId, String nodeName){
		try {
			IJavaElement[] elements = getComponentsJavaElements(project);
			components.clear();
			for(IJavaElement ele : elements){
				if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
					String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
					if( ("t:" + name).toLowerCase().equals(nodeName.toLowerCase())){
						goThroughClass((ICompilationUnit) ele, contextTypeId);
					}
				}
			}
			return components;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void goThroughClass(ICompilationUnit ClassContent, final String contextTypeId) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(ClassContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private boolean parameter = false;
			private String paramName = "";
			public void endVisit(FieldDeclaration node) {
				paramName = "";
				parameter = false;
				node.accept(new ASTVisitor() {
					public void endVisit(MarkerAnnotation node) {
						parameter = node.getTypeName().toString()
								.equals(TapestryContants.ANNOTATION_PARAMETER);
						super.endVisit(node);
					}

					public void endVisit(NormalAnnotation node) {
						parameter = node.getTypeName().toString()
								.equals(TapestryContants.ANNOTATION_PARAMETER);
						List values = node.values();
						for (int i = 0; i < values.size(); i++) {
							MemberValuePair pair = (MemberValuePair) values
									.get(i);
							if (pair.getName().toString().equals("read")
									&& pair.getValue().toString()
											.equals("false"))
								parameter = false;
						}
						super.endVisit(node);
					}

					public void endVisit(VariableDeclarationFragment node) {
						paramName = node.getName().toString();
						super.endVisit(node);
					}
				});
				super.endVisit(node);
				if (parameter){
					Template template = new Template(paramName, "add attribute " + paramName, contextTypeId, buildAttributeInsertCode(paramName), true);
					components.add(template);
				}
			}
		});
	}
	
	private static String buildAttributeInsertCode(String parameter){
		String ret = parameter + "=\"${cursor}\"";
		return ret;
	}
	
	/**
	 * Get components template list in situation:<span t:type="${component name here}"></span>
	 * 
	 * @param project
	 * @param contextTypeId
	 * @return
	 */
	public List<Template> getRootComponentsNameTemplates(IProject project, String contextTypeId){
		try {
			IJavaElement[] elements = getComponentsJavaElements(project);
			List<Template> components = new ArrayList<Template>();
			for(IJavaElement ele : elements){
				if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
					TapestryCoreComponents component = new TapestryCoreComponents();
					String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
					component.setName(name);
					component.setElementLabel("t:" + name.toLowerCase());
					components.add(new Template("t:" + component.getName(), buildDescription(component, "root package"), contextTypeId, component.getName(), true));
				}
			}
			return components;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Template> getRootComponentsTemplates(IProject project, String contextTypeId, int type){
		try {
			IJavaElement[] elements = getComponentsJavaElements(project);
			List<Template> components = new ArrayList<Template>();
			for(IJavaElement ele : elements){
				if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
					TapestryCoreComponents component = new TapestryCoreComponents();
					String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
					component.setName(name);
					component.setElementLabel("t:" + name.toLowerCase());
					components.add(new Template("t:" + component.getName(), buildDescription(component, "root package"), contextTypeId, buildInsertCode(component, type), true));
				}
			}
			return components;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private IJavaElement[] getComponentsJavaElements(IProject project) throws JavaModelException{
		findWEBXML(project);
		if(this.webXMLPath == null)
			return null;
		String rootPackage = parseRootPackage(project);
		if(rootPackage == null || rootPackage.isEmpty())
			return null;
		rootPackage = rootPackage + ".components";
		IPackageFragment pack = getTapestryRootComponentsPackage(project, rootPackage);
		IJavaElement[] elements = pack.getChildren();
		return elements;
	}
	
	private String buildDescription(TapestryCoreComponents component, String rootPackage){
		return "Custom Component in " + rootPackage;
	}
	
	private String buildInsertCode(TapestryCoreComponents element, int type){
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
			int prefixLength = insertLabel.indexOf(':') + 1;
			if(ret.length() > prefixLength)
				ret = ret.substring(prefixLength);
			break;
		}
		
		return ret;
	}
	
	public IPackageFragment getTapestryRootComponentsPackage(IProject project, String packageName) {
		IPackageFragmentRoot[] roots2;
		try {
			roots2 = JavaCore.create(project).getAllPackageFragmentRoots();
			for (IPackageFragmentRoot root : roots2) {
				if (!root.isArchive()){
					IPackageFragment packInstance = root.getPackageFragment(packageName);
					if(packInstance != null)
						return packInstance;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void findWEBXML(IProject project){
		IResource[] fileList = null;
		try {
			fileList = project.members(false);
			travelAllFolder(fileList);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private void travelAllFolder(IResource[] fileList)
			throws CoreException {
		for (int i = 0; i < fileList.length; i++) {
			IResource eachFile = fileList[i];
			 if(eachFile.getType() == IResource.FILE && eachFile.getName().equals(TapestryContants.WEB_XML)){
				this.webXMLPath = eachFile.getFullPath().toString();
				return;
			} else if (eachFile.getType() == IResource.FOLDER) {
				IFolder file = (IFolder) eachFile;
				travelAllFolder(file.members());
			}
		}
	}
	
	private String parseRootPackage(IProject project){
		IResource webRes = project.findMember(webXMLPath.substring(("/" + project.getName()).length()));
		if(webRes != null && webRes.getType() == IResource.FILE){
			InputStream webStream;
			try {
				webStream = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(webRes.getFullPath()).getContents();
				DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document=db.parse(webStream);
				Element web = document.getDocumentElement();
				NodeList filters = web.getChildNodes();
				boolean tapestryContext = false;
				String rootPath = null;
				for(int i=0; i<filters.getLength(); i++){
		            Node filter=filters.item(i);
		            if(filter.getNodeType() != Node.ELEMENT_NODE || !filter.getNodeName().equals(TapestryContants.CONTEXT_PARAM))
		            	continue;
		            NodeList filterChildren = filter.getChildNodes();
		            for(int j=0; j<filterChildren.getLength(); j++){
		            	Node element = filterChildren.item(j);
		            	if(element.getNodeType() != Node.ELEMENT_NODE)
		            		continue;
		            	if(element.getNodeName().equals(TapestryContants.PARAM_NAME)){
		            		if(element.getTextContent().trim().equals(TapestryContants.PARAM_NAME_DEFINE)){
		            			tapestryContext = true;
		            		}
		            	}else if(element.getNodeName().equals(TapestryContants.PARAM_VALUE)){
		            		rootPath = element.getTextContent().trim();
		            	}
		            }
		            if(rootPath != null && tapestryContext)
		            	return rootPath;
		        }
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	/**
	 * Get all the prefixes including "t:" in current project
	 * 
	 * @param project
	 * @return
	 */
	public Set<String> getComponentsPrefixList(IProject project){
		Set<String> prefixList = new HashSet<String>();
		prefixList.add("t");
		final IFile res = project.getFile(TapestryContants.CUSTOM_COMPONENTS_FILE);
		if(res == null)
			return prefixList;
		List<ComponentPackage> packageList = loadPackageList(res, 1, "");
		for(ComponentPackage cp : packageList){
			prefixList.add(cp.getPrefix());
		}
		return prefixList;
	}
	
	/**
	 * Get custom component attribute templates
	 * 
	 * @param project
	 * @param contextTypeId
	 * @param nodeName
	 * @param tapestryClassLoader
	 * @return
	 */
	public List<Template> getCustomComponentsAttributes(IProject project, String contextTypeId, String nodeName, TapestryClassLoader tapestryClassLoader){
		components.clear();
		String prefix = nodeName.substring(0, nodeName.indexOf(':'));
		try {
			final IFile res = project.getFile(TapestryContants.CUSTOM_COMPONENTS_FILE);
			if(res == null || prefix == null)
				return components;
			List<ComponentPackage> packageList = loadCustomComponentsPackageListWithPrefix(res, prefix);
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for(ComponentPackage cp : packageList){
				for (IPackageFragmentRoot root : roots) {
					if(cp.isArchive() == root.isArchive() && cp.getFragmentRoot().equals(root.getElementName())){
						IPackageFragment packInstance = root.getPackageFragment(cp.getPath());
						if (!root.isArchive()){
							//If current custom component is in source directory
							if(packInstance != null){
								IJavaElement[] elements = packInstance.getChildren();
								for(IJavaElement ele : elements){
									if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
										String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
										if((prefix + ":" + name).toLowerCase().equals(nodeName)){
											goThroughClass((ICompilationUnit) ele, contextTypeId);
											return components;
										}
									}
								}
							}
						}else if(packInstance instanceof PackageFragment){
							//Current custom component is in jar files
							for(Object packo : ((PackageFragment) packInstance).getChildrenOfType(IJavaElement.CLASS_FILE)){
								ClassFile packi = (ClassFile) packo;
								String className = packi.getElementName().substring(0, packi.getElementName().indexOf('.'));
								if(className.indexOf('$') < 0 && (prefix + ":" + className.toLowerCase()).equals(nodeName)){
									TapestryCoreComponents component = null;
									try {
										component = tapestryClassLoader.loadComponentAttributesFromClassFile(root, prefix, packi);
									} catch (ClassFormatException e) {
										e.printStackTrace();
									}
									if(component != null){
										for(String paramName : component.getPamameters()){
											Template template = new Template(paramName, "add attribute " + paramName, contextTypeId, buildAttributeInsertCode(paramName), true);
											components.add(template);
										}
										return components;
									}
								}
							}
						}
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get Tapestry custom components in situation: <span t:type="${component name here}"></span>
	 * 
	 * @param project
	 * @param contextTypeId
	 * @param type
	 * @param prefix
	 * @return
	 */
	public List<Template> getCustomComponentsNameTemplates(IProject project, String contextTypeId){
		List<Template> templateList = new ArrayList<Template>();
		final IFile res = project.getFile(TapestryContants.CUSTOM_COMPONENTS_FILE);
		if(res == null)
			return templateList;
		List<ComponentPackage> packageList = loadPackageList(res, 1, "*");
		IPackageFragmentRoot[] roots;
		try {
			roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for(ComponentPackage cp : packageList){
				loadCustomComponentsNameFromPackage(roots, contextTypeId, templateList, cp);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		

		return templateList;
	}
	
	/**
	 * Get all the template list(including core templates, root templates and custom component templates) in current project
	 * 
	 * @param project
	 * @param contextTypeId
	 * @param type
	 * @param prefix
	 * @return
	 */
	public List<Template> getCustomComponentsTemplates(IProject project, String contextTypeId, int type, String prefix){
		List<Template> templateList = new ArrayList<Template>();
		final IFile res = project.getFile(TapestryContants.CUSTOM_COMPONENTS_FILE);
		if(res == null)
			return templateList;
		List<ComponentPackage> packageList = loadPackageList(res, type, prefix);
		try {
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for(ComponentPackage cp : packageList){
				loadCustomComponentsFromPackage(roots, contextTypeId, type, templateList, cp, prefix);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return templateList;
	}
	
	/**
	 * Load component name template list, used in situation:<span t:type="${component name here}"></span>
	 * 
	 * @param roots
	 * @param contextTypeId
	 * @param type
	 * @param templateList
	 * @param cp
	 */
	private void loadCustomComponentsNameFromPackage(IPackageFragmentRoot[] roots, String contextTypeId, List<Template> templateList, ComponentPackage cp){
		List<String> componentNameList = getCustomComponentsNameList(roots, cp);	
		for(String componentName : componentNameList){
			TapestryCoreComponents component = new TapestryCoreComponents();
			component.setName(componentName);
			component.setElementLabel(cp.getPrefix() + ":" + componentName.toLowerCase());
			templateList.add(new Template(cp.getPrefix() + ":" + component.getName(), buildDescription(component, cp.getPath()), contextTypeId, cp.getPrefix()+ "/" +component.getName(), true));
		}
	}
	
	/**
	 * Load custom component template list, used in situation:<t:prefix/compName
	 * 
	 * @param roots
	 * @param contextTypeId
	 * @param type
	 * @param templateList
	 * @param cp
	 * @param prefix
	 */
	private void loadCustomComponentsFromPackage(IPackageFragmentRoot[] roots, String contextTypeId, int type, List<Template> templateList, ComponentPackage cp, String prefix){
		List<String> componentNameList = getCustomComponentsNameList(roots, cp);	
		//Build templates from name list
		for(String componentName : componentNameList){
			TapestryCoreComponents component = new TapestryCoreComponents();
			component.setName(componentName);
			if (prefix == null || prefix.equals("t"))
				component.setElementLabel("t:" + cp.getPrefix() + "." + componentName.toLowerCase());
			else
				component.setElementLabel(cp.getPrefix() + ":" + componentName.toLowerCase());
			templateList.add(new Template(cp.getPrefix() + ":" + component.getName(), buildDescription(component,
					cp.getPath()), contextTypeId, buildInsertCode(component, type), true));
		}
	}
	
	private List<String> getCustomComponentsNameList(IPackageFragmentRoot[] roots, ComponentPackage cp){
		List<String> componentNameList = new ArrayList<String>();
		try {
			for (IPackageFragmentRoot root : roots) {
				if(root instanceof JarPackageFragmentRoot == cp.isArchive() && root.getElementName().equals(cp.getFragmentRoot())) {
					if (!root.isArchive()) {
						// Load custom components from source directory
						IPackageFragment packInstance = root.getPackageFragment(cp.getPath());
						if (packInstance != null) {
							IJavaElement[] elements = packInstance.getChildren();
							for (IJavaElement ele : elements) {
								if (ele.getElementType() == IJavaElement.COMPILATION_UNIT
										&& ele.getElementName().endsWith(".java")) {
									String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
									componentNameList.add(name);
								}
							}
						}
					} else {
						// Load custom components from jar files
						for (IJavaElement pack : root.getChildren()) {
							if (pack != null && pack instanceof PackageFragment && pack.getElementName().equals(cp.getPath())) {
								for(Object packo : ((PackageFragment) pack).getChildrenOfType(IJavaElement.CLASS_FILE)){
									ClassFile packi = (ClassFile) packo;
									String itemName = packi.getElementName();
									if(itemName.indexOf('$') < 0 && itemName.endsWith(".class"))
										componentNameList.add(itemName.substring(0, itemName.length()-6));
								}
								break;
							}
						}
					}
					return componentNameList;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return componentNameList;
	}
	
	private List<ComponentPackage> loadPackageList(IFile res, int type, String prefix){
		List<ComponentPackage> packageList = new ArrayList<ComponentPackage>();
		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document=db.parse(res.getContents());
			Element root = document.getDocumentElement();
			NodeList bundles = root.getChildNodes();
			if (bundles != null) {
				for (int i = 0; i < bundles.getLength(); i++) {
					Node classCycles = bundles.item(i);
					if (classCycles.getNodeType() == Node.ELEMENT_NODE
							&& classCycles.getNodeName().trim().equals(TapestryContants.CUSTOM_COMPONENTS_PACKAGES)) {
						NodeList classes = classCycles.getChildNodes();
						for(int j=0; j<classes.getLength(); j++){
							Node classItem = classes.item(j);
							if(classItem.getNodeType() == Node.ELEMENT_NODE && classItem.getNodeName().trim().equals(TapestryContants.CUSTOM_COMPONENTS_PACKAGE)){
								ComponentPackage ci = new ComponentPackage();
								getPackageBasicInfo(ci, classItem);
								if(type != 3 || ci.getPrefix().equals(prefix) || prefix.equals("t"))
									packageList.add(ci);
							}
						}
						break;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return packageList;
	}
	
	private List<ComponentPackage> loadCustomComponentsPackageListWithPrefix(IFile res, String prefix){
		List<ComponentPackage> packageList = new ArrayList<ComponentPackage>();
		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document=db.parse(res.getContents());
			Element root = document.getDocumentElement();
			NodeList bundles = root.getChildNodes();
			if (bundles != null) {
				for (int i = 0; i < bundles.getLength(); i++) {
					Node classCycles = bundles.item(i);
					if (classCycles.getNodeType() == Node.ELEMENT_NODE
							&& classCycles.getNodeName().trim().equals(TapestryContants.CUSTOM_COMPONENTS_PACKAGES)) {
						NodeList classes = classCycles.getChildNodes();
						for(int j=0; j<classes.getLength(); j++){
							Node classItem = classes.item(j);
							if(classItem.getNodeType() == Node.ELEMENT_NODE && classItem.getNodeName().trim().equals(TapestryContants.CUSTOM_COMPONENTS_PACKAGE)){
								ComponentPackage ci = new ComponentPackage();
								getPackageBasicInfo(ci, classItem);
								if(ci.getPrefix().equals(prefix))
									packageList.add(ci);
							}
						}
						break;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return packageList;
	}
	
	/**
	 * Parse XML node
	 * 
	 * @param ci
	 * @param classItem
	 */
	private void getPackageBasicInfo(ComponentPackage ci, Node classItem){
		Node prefix = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_PREFIX);
		if (prefix != null)
			ci.setPrefix(prefix.getNodeValue());
		Node path = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_PATH);
		if (path != null)
			ci.setPath(path.getNodeValue());
		Node isArchive = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_ISARCHIVE);
		if(isArchive != null)
			ci.setArchive(isArchive.getNodeValue().equals("true"));
		Node fragmentRoot = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_FRAGMENTROOT);
		if(fragmentRoot != null)
			ci.setFragmentRoot(fragmentRoot.getNodeValue());
	}
}
