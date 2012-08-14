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

package org.eclipse.wst.xml.core.internal.contentmodel.tapestry.travelpackage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.PackageFragment;

public class TapestryClassLoader extends ClassLoader {

	public IPackageFragmentRoot getTapestryCoreJar(IProject project) {
		IPackageFragmentRoot[] roots2;
		try {
			roots2 = JavaCore.create(project).getAllPackageFragmentRoots();
			for (IPackageFragmentRoot root : roots2) {
				if(!root.isArchive())
					continue;
				String jarName = root.getPath().toString()
						.substring(root.getPath().toString().lastIndexOf('/'));
				if (jarName.startsWith("/tapestry-core")
						&& jarName.endsWith(".jar"))
					return root;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TapestryCoreComponents loadComponentAttributesFromClassFile(IPackageFragmentRoot fragmentRoot, String prefix, ClassFile packi) throws JavaModelException, ClassFormatException{
		ClassFileReader reader = new ClassFileReader(packi.getBytes(), null);	
		TapestryCoreComponents component = new TapestryCoreComponents();
		component.setName(String.valueOf(reader.getSourceName()));
		component.setElementLabel(prefix + ":" + component.getName().toLowerCase());
		if(reader.getFields() != null)
		for(IBinaryField  field : reader.getFields()){
			boolean parameter = false;
			if(field.getAnnotations() == null)
				continue;
			for(IBinaryAnnotation anno : field.getAnnotations()){
				if(String.valueOf(anno.getTypeName()).endsWith("/Parameter;")){
					parameter = true;
					break;
				}
			}
			if(parameter){
				component.addParameter(String.valueOf(field.getName()));
			}
		}
		
		String parentClassName = String.valueOf(reader.getSuperclassName());
		if(parentClassName != null && !parentClassName.isEmpty() && !parentClassName.equals("java/lang/Object")){
			List<String> parameters =loadParametersFromParentClass(fragmentRoot, parentClassName);
			for(String parameter : parameters){
				component.addParameter(parameter);
			}
		}
		
		return component;
	}
	
	public List<String> loadParametersFromParentClass(IPackageFragmentRoot root,
			String classFileName) {
		List<String> list = new ArrayList<String>();
		if (classFileName.indexOf('/') < 0)
			return list;
		String packageName = classFileName.substring(0,
				classFileName.lastIndexOf('/')).replace('/', '.');
		String className = classFileName.substring(classFileName
				.lastIndexOf('/') + 1) + ".class";
		try {
			PackageFragment packInstance = (PackageFragment) root.getPackageFragment(packageName);
			for (Object packo : packInstance.getChildrenOfType(IJavaElement.CLASS_FILE)) {
				ClassFile packi = (ClassFile) packo;
				if (packi.getElementName().equals(className)) {
					ClassFileReader reader = null;
					try {
						reader = new ClassFileReader(packi.getBytes(),
								null);
					} catch (ClassFormatException e) {
						e.printStackTrace();
					}

					if (reader.getFields() != null)
						for (IBinaryField field : reader.getFields()) {
							boolean parameter = false;
							if (field.getAnnotations() == null)
								continue;
							for (IBinaryAnnotation anno : field.getAnnotations()) {
								if (String.valueOf(anno.getTypeName()).endsWith("/Parameter;")) {
									parameter = true;
									break;
								}
							}
							if (parameter) {
								list.add(String.valueOf(field.getName()));
							}
						}
					String parentClassName = String.valueOf(reader.getSuperclassName()); 
					if(parentClassName != null && !parentClassName.isEmpty() && !parentClassName.equals("java/lang/Object")){
						list.addAll(loadParametersFromParentClass(root, parentClassName));
					}
					return list;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return list;
	}

}
