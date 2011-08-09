/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsp.ui.internal.contentassist;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jst.jsp.core.internal.contentmodel.TaglibController;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.CMDocumentImpl;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.TLDCMDocumentManager;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.TaglibTracker;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDFunction;
import org.eclipse.jst.jsp.core.internal.java.JSPTranslation;
import org.eclipse.jst.jsp.core.internal.java.jspel.JSPELParserConstants;
import org.eclipse.jst.jsp.core.internal.java.jspel.JSPELParserTokenManager;
import org.eclipse.jst.jsp.core.internal.java.jspel.SimpleCharStream;
import org.eclipse.jst.jsp.core.internal.java.jspel.Token;
import org.eclipse.jst.jsp.core.internal.regions.DOMJSPRegionContexts;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;

/**
 * <p>
 * Compute JSP EL completion proposals
 * </p>
 */
public class JSPELCompletionProposalComputer extends
		JSPJavaCompletionProposalComputer {

	private String partenerFile = null;
	private ArrayList<String> propList = new ArrayList<String>();
	private ArrayList<String> methodList = new ArrayList<String>();

	/**
	 * @see org.eclipse.jst.jsp.ui.internal.contentassist.JSPJavaCompletionProposalComputer#computeCompletionProposals(org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List computeCompletionProposals(
			CompletionProposalInvocationContext context,
			IProgressMonitor monitor) {

		ITextViewer viewer = context.getViewer();
		int documentPosition = context.getInvocationOffset();

		// get results from JSP completion processor
		// 3 for the "get" at the beginning of the java proposal
		List results = new ArrayList(computeJavaCompletionProposals(viewer,
				documentPosition, 3));

		// get the function proposals for syntax like: ${ fn:| }
		IStructuredDocumentRegion flat = ContentAssistUtils
				.getStructuredDocumentRegion(viewer, documentPosition);
		if (flat != null) {
			ITextRegion cursorRegion = flat
					.getRegionAtCharacterOffset(documentPosition);
			String elText;
			int startOffset = -1;
			// if container then need to get inner region
			// else can use flat region
			if (cursorRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) cursorRegion;
				cursorRegion = container
						.getRegionAtCharacterOffset(documentPosition);
				elText = container.getText(cursorRegion);
				startOffset = container.getStartOffset(cursorRegion);
			} else {
				elText = flat.getText(cursorRegion);
				startOffset = flat.getStartOffset(cursorRegion);
			}

			// sanity check that we are actually in EL region
			if (cursorRegion.getType() == DOMJSPRegionContexts.JSP_EL_CONTENT) {
				String prefix = getPrefix(documentPosition - startOffset,
						elText);
				if (null != prefix) {
					List proposals = getFunctionProposals(prefix, viewer,
							documentPosition);
					results.addAll(proposals);
				}
			}

			// Add Tapestry content assist
			if (startOffset > -1) {
				IEditorPart editorPart = Workbench.getInstance()
						.getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				if (editorPart != null) {
					IFileEditorInput input = (IFileEditorInput) editorPart
							.getEditorInput();
					IFile file = input.getFile();
					String fileName = file.getFullPath().toString();
					if (fileName.endsWith(".tml")) {
						String aimFileName = null;
						String aimNameShort = null;
						IProject activeProject = file.getProject();
						aimFileName = fileName.substring(0,
								fileName.length() - 4) + ".java";
						IResource res = null;
						if (aimFileName.indexOf("/") > -1)
							aimNameShort = aimFileName.substring(aimFileName
									.lastIndexOf("/") + 1);
						res = activeProject.findMember(aimFileName
								.substring(("/" + activeProject.getName())
										.length()));
						if (res == null) {
							searchPartenerFile(activeProject, aimNameShort);
							if (this.partenerFile != null) {
								res = activeProject.findMember(partenerFile
										.substring(("/" + activeProject
												.getName()).length()));
							}
						}

						if (res != null && res.getType() == IResource.FILE)
							results.addAll(getTapestryPropProposals("", viewer,
									startOffset, getTapestryImage(),
									elText.length(), 0, res.getFullPath()));
					}
				}
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

	private String extracted(ITextRegion cursorRegion) {
		return cursorRegion.getType();
	}

	/**
	 * @see org.eclipse.jst.jsp.ui.internal.contentassist.JSPJavaCompletionProposalComputer#getProposalCollector(org.eclipse.jdt.core.ICompilationUnit,
	 *      org.eclipse.jst.jsp.core.internal.java.JSPTranslation)
	 */
	protected JSPProposalCollector getProposalCollector(ICompilationUnit cu,
			JSPTranslation translation) {

		return new JSPELProposalCollector(cu, translation);
	}

	/**
	 * <p>
	 * Gets the EL prefix from the relative position and the given EL text
	 * </p>
	 * 
	 * @param relativePosition
	 * @param elText
	 * @return
	 */
	private String getPrefix(int relativePosition, String elText) {
		java.io.StringReader reader = new java.io.StringReader(elText);
		JSPELParserTokenManager scanner = new JSPELParserTokenManager(
				new SimpleCharStream(reader, 1, 1));
		Token curToken = null, lastIdentifier = null;
		while (JSPELParserConstants.EOF != (curToken = scanner.getNextToken()).kind) {
			if (JSPELParserConstants.COLON == curToken.kind
					&& curToken.endColumn == relativePosition
					&& null != lastIdentifier) {
				return (lastIdentifier.image);
			}

			if (JSPELParserConstants.IDENTIFIER == curToken.kind) {
				lastIdentifier = curToken;
			} else {
				lastIdentifier = null;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Get the EL function proposals, ex: ${fn:| }
	 * </p>
	 * 
	 * @param prefix
	 * @param viewer
	 * @param offset
	 * @return
	 */
	private List getFunctionProposals(String prefix, ITextViewer viewer,
			int offset) {
		TLDCMDocumentManager docMgr = TaglibController
				.getTLDCMDocumentManager(viewer.getDocument());
		ArrayList completionList = new ArrayList();
		if (docMgr == null)
			return null;

		Iterator taglibs = docMgr.getCMDocumentTrackers(offset).iterator();
		while (taglibs.hasNext()) {
			TaglibTracker tracker = (TaglibTracker) taglibs.next();
			if (tracker.getPrefix().equals(prefix)) {
				CMDocumentImpl doc = (CMDocumentImpl) tracker.getDocument();

				List functions = doc.getFunctions();
				for (Iterator it = functions.iterator(); it.hasNext();) {
					TLDFunction function = (TLDFunction) it.next();
					CustomCompletionProposal proposal = new CustomCompletionProposal(
							function.getName() + "()", //$NON-NLS-1$
							offset,
							0,
							function.getName().length() + 1,
							null,
							function.getName()
									+ " - " + function.getSignature(), null, null, 1); //$NON-NLS-1$

					completionList.add(proposal);
				}
			}
		}
		return completionList;
	}

	private Image getTapestryImage() {
		return ImageDescriptor.createFromURL(
				Platform.getBundle("org.eclipse.jst.tapestry.ui").getEntry(
						"icons/tapestry.gif")).createImage();
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
		for(int i=0; i<propList.size(); i++){
			String prop = propList.get(i);
			CustomCompletionProposal each = new CustomCompletionProposal( "prop:"+prop, offset, replacementLength,replacementLength, image, prop, null,
					"variable "+prop, 1);
			completionList.add(each);
		}
		for(int i=0; i<methodList.size(); i++){
			String method = methodList.get(i);
			CustomCompletionProposal each = new CustomCompletionProposal( "prop:"+method, offset, replacementLength,replacementLength, image, method, null,
					"method "+method, 1);
			completionList.add(each);
		}
		return completionList;
	}

	private void sortList(ArrayList<String> list){
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				String strI = list.get(i);
				String strJ = list.get(j);
				if(strJ.compareTo(strI) < 0){
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
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				addIfNotExist(name.toString(),propList);
				return false; 
			}

			public boolean visit(MethodDeclaration node){
				SimpleName name = node.getName();
				String methodName = name.toString();
				if(node.getModifiers() == Modifier.PUBLIC && methodName.startsWith("get") && methodName.length()>3){
					String propName = methodName.substring(3).toLowerCase();
					addIfNotExist(propName,propList);
					methodList.add(methodName+"()");
				}
				return false;
			}
		});
	}
	
	private void addIfNotExist(String newItem, List<String> list){
		for(int i=0; i<list.size(); i++)
			if(list.get(i).trim().equals(newItem.trim())){
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
