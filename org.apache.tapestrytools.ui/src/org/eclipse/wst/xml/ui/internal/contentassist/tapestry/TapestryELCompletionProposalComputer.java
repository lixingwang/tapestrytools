package org.eclipse.wst.xml.ui.internal.contentassist.tapestry;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;

/**
 * <p>
 * Compute Tapestry EL completion proposals
 * </p>
 */
public class TapestryELCompletionProposalComputer {

	private String partenerFile = null;
	private ArrayList propList = new ArrayList();
	private ArrayList methodList = new ArrayList();

	/**
	 * @see org.eclipse.jst.jsp.ui.internal.contentassist.JSPJavaCompletionProposalComputer#computeCompletionProposals(org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List computeCompletionProposals(
			CompletionProposalInvocationContext context) {
		List results = new ArrayList();
		// Add Tapestry content assist
		IEditorPart editorPart = Workbench.getInstance()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart
					.getEditorInput();
			IFile file = input.getFile();
			String fileName = file.getFullPath().toString();
			if (fileName.endsWith(".tml")) {
				String aimFileName = null;
				String aimNameShort = null;
				IProject activeProject = file.getProject();
				aimFileName = fileName.substring(0, fileName.length() - 4)
						+ ".java";
				IResource res = null;
				if (aimFileName.indexOf("/") > -1)
					aimNameShort = aimFileName.substring(aimFileName
							.lastIndexOf("/") + 1);
				res = activeProject.findMember(aimFileName
						.substring(("/" + activeProject.getName()).length()));
				if (res == null) {
					searchPartenerFile(activeProject, aimNameShort);
					if (this.partenerFile != null) {
						res = activeProject.findMember(partenerFile
								.substring(("/" + activeProject.getName())
										.length()));
					}
				}

				if (res != null && res.getType() == IResource.FILE)
					results.addAll(getTapestryPropProposals("",
							context.getViewer(), context.getInvocationOffset(),
							getTapestryImage(), 0, 0, res.getFullPath()));
			}
		}

		return results;
	}

	private void searchPartenerFile(IProject project, String fileName) {
		IResource[] fileList = null;
		try {
			fileList = project.members(false);
			travelAllFolder(fileList, fileName);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void travelAllFolder(IResource[] fileList, String fileName)
			throws CoreException {
		for (int i = 0; i < fileList.length; i++) {
			IResource eachFile = fileList[i];
			if (eachFile.getType() == IResource.FILE
					&& eachFile.getName().equals(fileName)) {
				this.partenerFile = eachFile.getFullPath().toString();
			} else if (eachFile.getType() == IResource.FOLDER) {
				IFolder file = (IFolder) eachFile;
				travelAllFolder(file.members(), fileName);
			}
		}
	}

	private Image getTapestryImage() {
		return XMLEditorPluginImageHelper.getInstance().getImage(
				XMLEditorPluginImages.IMG_TAPESTRY_ENTITY);
	}

	private List getTapestryPropProposals(String prefix, ITextViewer viewer,
			int offset, Image image, int replacementLength, int cursorPosition,
			IPath classFile) {
		ArrayList completionList = new ArrayList();
		try {
			propList.clear();
			methodList.clear();
			goThroughClass(inputStream2String(ResourcesPlugin.getWorkspace()
					.getRoot().getFile(classFile).getContents()));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		sortList(propList);
		sortList(methodList);
		for (int i = 0; i < propList.size(); i++) {
			String prop = (String) propList.get(i);
			CustomCompletionProposal each = new CustomCompletionProposal(
					"prop:" + prop, offset, replacementLength,
					replacementLength, image, prop, null, "variable " + prop, 1);
			completionList.add(each);
		}
		for (int i = 0; i < methodList.size(); i++) {
			String method = (String) methodList.get(i);
			CustomCompletionProposal each = new CustomCompletionProposal(
					"prop:" + method, offset, replacementLength,
					replacementLength, image, method, null, "method " + method,
					1);
			completionList.add(each);
		}
		return completionList;
	}

	private void sortList(ArrayList list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				String strI = (String) list.get(i);
				String strJ = (String) list.get(j);
				if (strJ.compareTo(strI) < 0) {
					list.set(i, strJ);
					list.set(j, strI);
				}
			}
		}
	}

	private void goThroughClass(String ClassContent) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(ClassContent.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			/*
			 * public boolean visit(VariableDeclarationFragment node) {
			 * SimpleName name = node.getName(); addIfNotExist(name.toString(),
			 * propList); return false; }
			 */

			private String elNodeName;
			private boolean intoEL;

			public void endVisit(FieldDeclaration node) {
				elNodeName = "";
				intoEL= false;
				node.accept(new ASTVisitor() {
					public void endVisit(MarkerAnnotation node) {
						intoEL = node.getTypeName().toString().equals("Property");
						super.endVisit(node);
					}
					
					public void endVisit(NormalAnnotation node) {
						intoEL = node.getTypeName().toString().equals("Property");
						List values = node.values();
						for(int i=0; i< values.size(); i++){
							MemberValuePair pair = (MemberValuePair) values.get(i);
							if(pair.getName().toString().equals("read") && pair.getValue().toString().equals("false"))
								intoEL = false;
						}
						super.endVisit(node);
					}

					public void endVisit(VariableDeclarationFragment node) {
						elNodeName = node.getName().toString();
						super.endVisit(node);
					}
				});
				super.endVisit(node);
				if(intoEL)
					addIfNotExist(elNodeName, propList);
			}

			public boolean visit(MethodDeclaration node) {
				SimpleName name = node.getName();
				String methodName = name.toString();
				if (node.getModifiers() == Modifier.PUBLIC
						&& methodName.startsWith("get")
						&& methodName.length() > 3) {
					String propName = getPropertyName(methodName.substring(3));
					addIfNotExist(propName, propList);
					// methodList.add(methodName + "()");
				}
				
				if(node.getReturnType2().isPrimitiveType()){
					PrimitiveType type = (PrimitiveType) node.getReturnType2();
					if(type.getPrimitiveTypeCode() == PrimitiveType.BOOLEAN
							&& node.getModifiers() == Modifier.PUBLIC
							&& methodName.startsWith("is")
							&& methodName.length() > 2){
						String propName = getPropertyName(methodName.substring(2));
						addIfNotExist(propName, propList);
					}
				}
				return false;
			}
			
			private String getPropertyName(String name){
				if(name.length() > 1)
					return name.substring(0,1).toLowerCase() + name.substring(1);
				else
					return name.toLowerCase();
			}
		});
	}

	private void addIfNotExist(String newItem, List list) {
		for (int i = 0; i < list.size(); i++)
			if (((String) list.get(i)).trim().equals(newItem.trim())) {
				return;
			}
		list.add(newItem);
	}

	private String inputStream2String(InputStream ins) {
		String all_content = null;
		try {
			all_content = new String();
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			byte[] str_b = new byte[1024];
			int i = -1;
			while ((i = ins.read(str_b)) > 0) {
				outputstream.write(str_b, 0, i);
			}
			all_content = outputstream.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_content;
	}
}
