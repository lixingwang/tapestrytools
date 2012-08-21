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
	public static Set<String> BLOCKED_JARS = new HashSet<String>();
	
	static{
		BLOCKED_JARS.add("/resources.jar");
		BLOCKED_JARS.add("/rt.jar");
		BLOCKED_JARS.add("/jsse.jar");
		BLOCKED_JARS.add("/jce.jar");
		BLOCKED_JARS.add("/charsets.jar");
		BLOCKED_JARS.add("/dnsns.jar");
		BLOCKED_JARS.add("/localedata.jar");
		BLOCKED_JARS.add("/sunjce_provider.jar");
		BLOCKED_JARS.add("/sunmscapi.jar");
		BLOCKED_JARS.add("/sunpkcs11.jar");
	}
	
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
				IPackageFragmentRoot item = sourceFolders[i];
 				String jarPath = item.getPath().toString();
 				String jarName = jarPath.substring(jarPath.lastIndexOf('/'));
				if (sourceFolders[i].getResource() != null && !sourceFolders[i].isArchive() || item.isArchive() && jarPath.endsWith(".jar") && !BLOCKED_JARS.contains(jarName)) {
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
