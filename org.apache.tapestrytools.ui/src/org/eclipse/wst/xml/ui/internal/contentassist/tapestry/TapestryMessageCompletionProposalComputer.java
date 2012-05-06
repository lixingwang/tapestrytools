package org.eclipse.wst.xml.ui.internal.contentassist.tapestry;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;

/**
 * <p>
 * Compute Tapestry messages completion proposals
 * </p>
 */
public class TapestryMessageCompletionProposalComputer {

	private String partenerFile = null;
	private ArrayList propList = new ArrayList();

	public List computeCompletionProposals(String prefix,CompletionProposalInvocationContext context, IDOMNode node, int cursoroffset) {
		List results = new ArrayList();
		String suffix = computeSuffix(context, node);

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
						+ ".properties";
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
					results.addAll(getMessageProposals(prefix,
							context.getViewer(), context.getInvocationOffset(),
							getTapestryImage(), 0, cursoroffset, res.getFullPath(), suffix));
			}
		}

		return results;
	}

	private String computeSuffix(CompletionProposalInvocationContext context,
			IDOMNode node) {
		String suffix = "}";
		int documentPosition = context.getInvocationOffset();
		for (int i = documentPosition; i < node.getSource().length(); i++) {
			char temp = node.getSource().charAt(i);
			if (temp == '}') {
				suffix = "";
				break;
			} else if (temp == 32 || temp >= 48 && temp <= 57 || temp >= 65
					&& temp <= 90 || temp >= 97 && temp <= 122)
				continue;
			else {
				suffix = "}";
				break;
			}
		}

		return suffix;
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
				XMLEditorPluginImages.IMG_TAPESTRY_MESSAGES);
	}

	private List getMessageProposals(String prefix, ITextViewer viewer,
			int offset, Image image, int replacementLength, int cursorPosition,
			IPath classFile, String suffix) {
		ArrayList completionList = new ArrayList();
		propList.clear();
		
		Properties props = new Properties();
		try {
			InputStream in = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(classFile).getContents();
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				propList.add(new PropertyItem(key, props.getProperty(key)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < propList.size(); i++) {
			PropertyItem message = (PropertyItem) propList.get(i);
			CustomCompletionProposal each = new CustomCompletionProposal(
					prefix + message.key + suffix, offset, replacementLength,
					cursorPosition, image, message.key, null, "Message key: "+message.key+" with value: " + message.value, 1);
			completionList.add(each);
		}
		return completionList;
	}
}
class PropertyItem{
	public String key;
	public String value;
	
	public PropertyItem(String key, String value){
		this.key = key;
		this.value = value;
	}
}