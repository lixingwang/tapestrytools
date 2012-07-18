package org.eclipse.wst.xml.ui.internal.contentassist.tapestry;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage.TapestryCoreComponents;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TapestryRootComponentsProposalComputer {
	private String webXMLPath = null;
	
	public List<Template> getRootComponentsTemplates(IProject project, String contextTypeId, int type){
		findWEBXML(project);
		if(this.webXMLPath == null)
			return null;
		String rootPackage = parseRootPackage(project);
		if(rootPackage == null || rootPackage.isEmpty())
			return null;
		rootPackage = rootPackage + ".components";
		PackageFragment pack = getTapestryRootComponentsPackage(project, rootPackage);
		IJavaElement[] elements;
		try {
			elements = pack.getChildren();
			List<Template> components = new ArrayList<Template>();
			for(IJavaElement ele : elements){
				if(ele.getElementType() == IJavaElement.COMPILATION_UNIT && ele.getElementName().endsWith(".java")){
					TapestryCoreComponents component = new TapestryCoreComponents();
					String name = ele.getElementName().substring(0, ele.getElementName().indexOf('.'));
					component.setName(name);
					component.setElementLabel("t:" + name.toLowerCase());
					//goThroughClass((ICompilationUnit) ele);
					components.add(new Template(component.getName(), buildDescription(component, rootPackage), contextTypeId, buildInsertCode(component, type), true));
				}
			}
			return components;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
/*	private void goThroughClass(ICompilationUnit ClassContent) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(ClassContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public void endVisit(FieldDeclaration node) {
				System.out.println("FieldDeclaration" + node.toString());
			}
			public void endVisit(VariableDeclarationFragment node) {
				System.out.println("VariableDeclarationFragment" + node.getName().toString());
				super.endVisit(node);
			}
		});
	}*/
	
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
			if(ret.length() > 2)
				ret = ret.substring(2);
			break;
		}
		
		return ret;
	}
	
	public PackageFragment getTapestryRootComponentsPackage(IProject project, String packageName) {
		IPackageFragmentRoot[] roots2;
		try {
			roots2 = JavaCore.create(project).getAllPackageFragmentRoots();
			for (IPackageFragmentRoot root : roots2) {
				String dir = root.getPath().toString();
				if (!dir.endsWith(".jar")){
					for (IJavaElement pack : root.getChildren()) {
						PackageFragment packInstance = (PackageFragment) pack;
						if (packageName.equals(packInstance.getElementName())){
							return packInstance;
						}
					}
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
}
