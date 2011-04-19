package org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.internal;

import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jst.j2ee.classpathdep.IClasspathDependencyConstants;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryRegistryUtil;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryConfigurationHelper;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReference;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryReferenceServerSupplied;

public class TapestryLibraryReferenceFacadeFactory {

	public static TapestryLibraryReference create(final IClasspathEntry classpathEntry) {
		if (TapestryLibraryConfigurationHelper.isTapestryLibraryContainer(classpathEntry)){
			return createReference(classpathEntry);
		}
		return null;
	}

	/**
	 * @return instance of {@link JSFLibraryReferenceServerSupplied}
	 */
	public static TapestryLibraryReferenceServerSupplied createServerSuppliedJSFLibRef(){
		return new TapestryLibraryReferenceServerSuppliedImpl();
	}


	/**
	 * @param classpathEntry
	 * @return {@link JSFLibraryReference}
	 */
	private static TapestryLibraryReference createReference(
			final IClasspathEntry classpathEntry) {
		
		String libID = classpathEntry.getPath().segment(1);
		org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryInternalReference libRef = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryReferencebyID(libID);
		if (libRef!= null){
			boolean isDeployed = getJ2EEModuleDependency(classpathEntry);
			if (libRef.getLibrary() instanceof PluginProvidedTapestryLibrary)
				return new TapestryLibraryReferencePluginProvidedImpl(libRef, isDeployed);
			
			return new TapestryLibraryReferenceUserSpecifiedImpl(libRef, isDeployed);
		}
		return null;
	}

	private static boolean getJ2EEModuleDependency(IClasspathEntry classpathEntry) {
		IClasspathAttribute[] attrs = classpathEntry.getExtraAttributes();
		for (int i=0;i<attrs.length;i++){
			IClasspathAttribute attr = attrs[i];
			if (attr.getName().equals(IClasspathDependencyConstants.CLASSPATH_COMPONENT_DEPENDENCY)){
				return true;
			}
		}
		return false;
	}
}
