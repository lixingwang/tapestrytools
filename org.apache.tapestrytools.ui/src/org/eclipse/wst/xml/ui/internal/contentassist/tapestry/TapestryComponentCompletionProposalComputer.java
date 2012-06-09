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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.TapestryElementCollection;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;

/**
 * <p>
 * Compute Tapestry components completion proposals Such as:
 * 
 * @Component(parameters = { "page=start" })
 * private PageLink goToStart;
 * </p>
 */
public class TapestryComponentCompletionProposalComputer {
	private static TapestryComponentCompletionProposalComputer computer = null;

	public static TapestryComponentCompletionProposalComputer getInstance() {
		if (computer == null)
			computer = new TapestryComponentCompletionProposalComputer();
		return computer;
	}

	private TapestryComponentCompletionProposalComputer() {

	}

	private String partenerFile = null;
	private ArrayList componentList = new ArrayList();

	/**
	 * @see org.eclipse.jst.jsp.ui.internal.contentassist.JSPJavaCompletionProposalComputer#computeCompletionProposals(org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List computeCompletionProposals(String prefix, IDOMNode node,
			int cursoroffset) {
		List results = new ArrayList();
		String suffix = "";// computeSuffix(context, node);

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
					results.addAll(getTapestryComponentProposals(prefix,
							cursoroffset, getTapestryImage(), 0, cursoroffset,
							res.getFullPath(), suffix));
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
				XMLEditorPluginImages.IMG_TAPESTRY_DEFAULT);
	}

	private List getTapestryComponentProposals(String prefix, int offset,
			Image image, int replacementLength, int cursorPosition,
			IPath classFile, String suffix) {
		ArrayList completionList = new ArrayList();
		try {
			this.componentList.clear();
			goThroughClass(inputStream2String(ResourcesPlugin.getWorkspace()
					.getRoot().getFile(classFile).getContents()));
		} catch (CoreException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < this.componentList.size(); i++) {
			String prop = (String) this.componentList.get(i);
			Template template = new Template(prop, "Defined Component", TapestryElementCollection.attributesValueContextTypeId, prop, true);
			completionList.add(template);
		}
		return completionList;
	}

	private void goThroughClass(String ClassContent) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(ClassContent.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {

			private String elNodeName;
			private boolean intoEL;
			private boolean definedId;

			public void endVisit(FieldDeclaration node) {
				elNodeName = "";
				intoEL = false;
				node.accept(new ASTVisitor() {
					public void endVisit(MarkerAnnotation node) {
						intoEL = node.getTypeName().toString()
								.equals(TapestryContants.COMPONENT_PROPERTY);
						super.endVisit(node);
					}

					public void endVisit(NormalAnnotation node) {
						intoEL = node.getTypeName().toString()
								.equals(TapestryContants.COMPONENT_PROPERTY);
						List values = node.values();
						for (int i = 0; i < values.size(); i++) {
							MemberValuePair pair = (MemberValuePair) values
									.get(i);
							if (pair.getName().toString().equals("id")
									&& !pair.getValue().toString().trim().isEmpty()){
								definedId = true;
								elNodeName = pair.getValue().toString().trim();
								if(elNodeName.startsWith("\""))
									elNodeName = elNodeName.substring(1);
								if(elNodeName.endsWith("\""))
									elNodeName = elNodeName.substring(0, elNodeName.length() - 1);
							}
						}
						super.endVisit(node);
					}

					public void endVisit(VariableDeclarationFragment node) {
						if(!definedId)
							elNodeName = node.getName().toString();
						super.endVisit(node);
					}
				});
				super.endVisit(node);
				if (intoEL)
					componentList.add(elNodeName);
			}

		});
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
