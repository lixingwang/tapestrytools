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
import org.eclipse.jface.text.templates.Template;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage.TapestryCoreComponents;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TapestryRootComponentsProposalComputer {
	private String webXMLPath = null;
	private List<Template> components = new ArrayList<Template>();
	
	public List<Template> getRootComponentsAttributes(IProject project, String contextTypeId, Node currentTapestryComponent){
		try {
			IJavaElement[] elements = getComponentsJavaElements(project);
			components.clear();
			for(IJavaElement ele : elements){
				if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
					String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
					if( ("t:" + name).toLowerCase().equals(currentTapestryComponent.getNodeName().toLowerCase())){
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
					components.add(new Template(component.getName(), buildDescription(component, "root package"), contextTypeId, buildInsertCode(component, type), true));
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
	
	public List<Template> getCustomComponentsAttributes(IProject project, String contextTypeId, Node currentTapestryComponent){
		components.clear();
		try {
			final IFile res = project.getFile(TapestryContants.CUSTOM_COMPONENTS_FILE);
			if(res == null)
				return components;
			List<ComponentPackage> packageList = loadPackageList(res, 3, currentTapestryComponent.getPrefix());
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for(ComponentPackage cp : packageList){
				for (IPackageFragmentRoot root : roots) {
					if (!root.isArchive()){
						IPackageFragment packInstance = root.getPackageFragment(cp.getPath());
						if(packInstance != null){
							IJavaElement[] elements = packInstance.getChildren();
							for(IJavaElement ele : elements){
								if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
									String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
									if( (currentTapestryComponent.getPrefix() + ":" + name).toLowerCase().equals(currentTapestryComponent.getNodeName().toLowerCase())){
										goThroughClass((ICompilationUnit) ele, contextTypeId);
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
				loadCustomComponentsFromPackage(roots, contextTypeId, type, templateList, cp);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return templateList;
	}
	
	private void loadCustomComponentsFromPackage(IPackageFragmentRoot[] roots, String contextTypeId, int type, List<Template> templateList, ComponentPackage cp){
			try {
				for (IPackageFragmentRoot root : roots) {
					if (!root.isArchive()){
						IPackageFragment packInstance = root.getPackageFragment(cp.getPath());
						if(packInstance != null){
							IJavaElement[] elements = packInstance.getChildren();
							for(IJavaElement ele : elements){
								if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
									TapestryCoreComponents component = new TapestryCoreComponents();
									String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
									component.setName(name);
									component.setElementLabel(cp.getPrefix() + ":" + name.toLowerCase());
									templateList.add(new Template(component.getName(), buildDescription(component, cp.getPath()), contextTypeId, buildInsertCode(component, type), true));
								}
							}
						}
						break;
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
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
								if(type != 3 || ci.getPrefix().equals(prefix))
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
	
	private void getPackageBasicInfo(ComponentPackage ci, Node classItem){
		Node prefix = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_PREFIX);
		if (prefix != null)
			ci.setPrefix(prefix.getNodeValue());
		Node path = classItem.getAttributes().getNamedItem(TapestryContants.CUSTOM_COMPONENTS_PATH);
		if (path != null)
			ci.setPath(path.getNodeValue());
		
	}
}
