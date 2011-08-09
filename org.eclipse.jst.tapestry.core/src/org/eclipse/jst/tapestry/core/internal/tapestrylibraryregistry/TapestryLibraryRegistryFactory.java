package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry;

import org.eclipse.emf.ecore.EFactory;

public interface TapestryLibraryRegistryFactory extends EFactory{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2005 Oracle Corporation"; //$NON-NLS-1$


	TapestryLibraryRegistryFactory eINSTANCE = org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl.TapestryLibraryRegistryFactoryImpl.init();


	TapestryLibraryRegistry createTapestryLibraryRegistry();


	TapestryLibrary createTapestryLibrary();


	ArchiveFile createArchiveFile();


	PluginProvidedTapestryLibrary createPluginProvidedTapestryLibrary();


	TapestryLibraryRegistryPackage getTapestryLibraryRegistryPackage();

}
