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

	public List<String> loadComponentsFromClassFile(IPackageFragmentRoot root,
			String classFileName) {
		List<String> list = new ArrayList<String>();
		if (classFileName.indexOf('/') < 0)
			return list;
		String packageName = classFileName.substring(0,
				classFileName.lastIndexOf('/')).replace('/', '.');
		String className = classFileName.substring(classFileName
				.lastIndexOf('/') + 1) + ".class";
		try {
			for (IJavaElement pack : root.getChildren()) {
				PackageFragment packInstance = (PackageFragment) pack;
				if (packageName.equals(packInstance.getElementName()))
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
								list.addAll(loadComponentsFromClassFile(root, parentClassName));
							}
						}
					}
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return list;
	}

}
