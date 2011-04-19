package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistry;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryPackage;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryVersion;

public class TapestryLibraryRegistryFactoryImpl extends EFactoryImpl implements TapestryLibraryRegistryFactory{

	public static final String copyright = "Copyright (c) 2005 Oracle Corporation"; //$NON-NLS-1$
	
	public static TapestryLibraryRegistryFactory init(){
		try {
			TapestryLibraryRegistryFactory theTapestryLibraryRegistryFactory = (TapestryLibraryRegistryFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/webtools/tapestry/schema/tapestrytapelibraryregistry.xsd");  //$NON-NLS-1$
			if (theTapestryLibraryRegistryFactory != null) {
				return theTapestryLibraryRegistryFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TapestryLibraryRegistryFactoryImpl();
	}
	
	public TapestryLibraryRegistryFactoryImpl() {
		super();
	}
	
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY: return createTapestryLibraryRegistry();
			case TapestryLibraryRegistryPackage.JSF_LIBRARY: return createTapestryLibrary();
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY: return createPluginProvidedTapestryLibrary();
			case TapestryLibraryRegistryPackage.ARCHIVE_FILE: return createArchiveFile();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TapestryLibraryRegistryPackage.JSF_VERSION:
				return createTapestryVersionFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TapestryLibraryRegistryPackage.JSF_VERSION:
				return convertTapestryVersionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the jsf library registry
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TapestryLibraryRegistry createTapestryLibraryRegistry() {
		TapestryLibraryRegistryImpl jsfLibraryRegistry = new TapestryLibraryRegistryImpl();
		return jsfLibraryRegistry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the jsf library 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TapestryLibrary createTapestryLibrary() {
		TapestryLibraryImpl jsfLibrary = new TapestryLibraryImpl();
		return jsfLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the archive file 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveFile createArchiveFile() {
		ArchiveFileImpl archiveFile = new ArchiveFileImpl();
		return archiveFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the plugin provided JSF library 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginProvidedTapestryLibrary createPluginProvidedTapestryLibrary() {
		PluginProvidedTapestryLibraryImpl pluginProvidedJSFLibrary = new PluginProvidedTapestryLibraryImpl();
		return pluginProvidedJSFLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
     * @param eDataType 
     * @param initialValue 
     * @return the jsfVersion the dataType 
     * <!-- end-user-doc -->
	 * @generated
	 */
	public TapestryVersion createTapestryVersionFromString(EDataType eDataType, String initialValue) {
		TapestryVersion result = TapestryVersion.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
     * @param eDataType 
     * @param instanceValue 
     * @return the string version of the data type 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTapestryVersionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the registry package 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TapestryLibraryRegistryPackage getTapestryLibraryRegistryPackage() {
		return (TapestryLibraryRegistryPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the package 
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static TapestryLibraryRegistryPackage getPackage() {
		return TapestryLibraryRegistryPackage.eINSTANCE;
	}

}
