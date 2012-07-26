package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.internal.Workbench;

public class Utils {
	public static IProject getCurrentProject() {
		IEditorPart editorPart = Workbench.getInstance()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart
					.getEditorInput();
			IFile file = input.getFile();
			return file.getProject();
		} else
			return null;
	}

	public static Set<IJavaElement> getSrcDirectories(IProject project) {
		Set<IJavaElement> list = new HashSet<IJavaElement>();
		try {
			IPackageFragmentRoot[] sourceFolders = JavaCore.create(project)
					.getAllPackageFragmentRoots();
			for (int i = 0; i < sourceFolders.length; i++) {
				if (sourceFolders[i].getResource() != null
						&& !sourceFolders[i].isArchive()) {
					IJavaElement[] eles = sourceFolders[i].getChildren();
					for (IJavaElement ele : eles) {
						if (!ele.getElementName().trim().isEmpty())
							list.add(ele);
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
}
