package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal.TapestryLibraryReferenceFacadeFactory;

public class TapestryLibraryConfigurationHelper {
	/**
	 * container id for JSF Library Classpath Containers 
	 */
	public static final String Tapestry_LIBRARY_CP_CONTAINER_ID="org.eclipse.jst.tapestry.core.internal.tapestrylibrarycontainer"; //$NON-NLS-1$

	/**
	 * @param project 
	 * @return collection of references
	 */
	public static Collection<TapestryLibraryReference> getTapestryLibraryReferences(IProject project) {
		Collection<TapestryLibraryReference> results = new HashSet<TapestryLibraryReference>();
		IJavaProject jproj = JavaCore.create(project);
		try {
			IClasspathEntry[] entries = jproj.getRawClasspath();
			boolean foundImpl = false;
			for (int i=0;i<entries.length;i++){
				TapestryLibraryReference ref = TapestryLibraryReferenceFacadeFactory.create(entries[i]);
				if (ref != null){
					results.add(ref);
					if (ref.isJSFImplementation())
						foundImpl = true;
				}
			}
			if (! foundImpl){
				results.add(TapestryLibraryReferenceFacadeFactory.createServerSuppliedJSFLibRef());
			}
		} catch (JavaModelException e) {
			TapestryCorePlugin.log(e, "Exception occurred calling getJSFLibraryReferences for "+project.getName()); //$NON-NLS-1$
		}
		return results;
	}

	/**
	 * @param cpEntry
	 * @return boolean indicating that the classpath entry is a JSF Libary Classpath Container
	 */
	public static boolean isTapestryLibraryContainer(IClasspathEntry cpEntry) {
		if (cpEntry.getEntryKind() != IClasspathEntry.CPE_CONTAINER)
			return false;
		
		IPath path = cpEntry.getPath();
		return path != null && path.segmentCount() == 2 && Tapestry_LIBRARY_CP_CONTAINER_ID.equals(path.segment(0));
	}
	
	/**
	 * @param project
	 * @return true if the JSF Faceted project is configured to use system supplied implementation
	 */
	public static boolean isConfiguredForSystemSuppliedImplementation(IProject project) {
		Collection<TapestryLibraryReference> refs = getTapestryLibraryReferences(project);
		for(TapestryLibraryReference ref : refs){			
			if (ref instanceof TapestryLibraryReferenceServerSupplied)
				return true;
		}
		return false;
	}
}
