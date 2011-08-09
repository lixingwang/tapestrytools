package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jst.tapestry.core.internal.Messages;


public interface TapestryLibraryRegistry extends EObject{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) Open source community under Apache Licence 2.0"; //$NON-NLS-1$


	String getDefaultImplementationID();

	void setDefaultImplementationID(String value);

	/**
	 *The default implementation message string
	 */
	public static final String DEFAULT_IMPL_LABEL = Messages.TapestryLibraryRegistry_DEFAULT_IMPL_LABEL;


	EList getTapestryLibraries();


	EList getPluginProvidedTapestryLibraries();


	TapestryLibrary getDefaultImplementation();


	void setDefaultImplementation(TapestryLibrary implementation);


	TapestryLibrary getTapestryLibraryByID(String ID);


	EList getTapestryLibrariesByName(String name);


	EList getImplTapestryLibraries();


	EList getNonImplTapestryLibraries();


	EList getAllTapestryLibraries();


	boolean addTapestryLibrary(TapestryLibrary library);


	boolean removeTapestryLibrary(TapestryLibrary library);
}
