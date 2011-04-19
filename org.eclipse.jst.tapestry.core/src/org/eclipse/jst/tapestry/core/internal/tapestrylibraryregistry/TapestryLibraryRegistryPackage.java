package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

@SuppressWarnings("hiding")
public interface TapestryLibraryRegistryPackage extends EPackage{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2005 Oracle Corporation"; //$NON-NLS-1$

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "jsflibraryregistry"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/webtools/jsf/schema/jsflibraryregistry.xsd"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jsflibreg"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TapestryLibraryRegistryPackage eINSTANCE = org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl.TapestryLibraryRegistryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryImpl <em>JSF Library Registry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryImpl
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFLibraryRegistry()
	 * @generated
	 */
	int JSF_LIBRARY_REGISTRY = 0;

	/**
	 * The feature id for the '<em><b>Default Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID = 0;

	/**
	 * The feature id for the '<em><b>JSF Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY_REGISTRY__JSF_LIBRARIES = 1;

	/**
	 * The feature id for the '<em><b>Plugin Provided JSF Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES = 2;

	/**
	 * The number of structural features of the '<em>JSF Library Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY_REGISTRY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryImpl <em>JSF Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryImpl
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFLibrary()
	 * @generated
	 */
	int JSF_LIBRARY = 1;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__NAME = 1;

	/**
	 * The feature id for the '<em><b>JSF Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__JSF_VERSION = 2;

	/**
	 * The feature id for the '<em><b>Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__DEPLOYED = 3;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__IMPLEMENTATION = 4;

	/**
	 * The feature id for the '<em><b>Archive Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY__ARCHIVE_FILES = 5;

	/**
	 * The number of structural features of the '<em>JSF Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSF_LIBRARY_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.ArchiveFileImpl <em>Archive File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.ArchiveFileImpl
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getArchiveFile()
	 * @generated
	 */
	int ARCHIVE_FILE = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.PluginProvidedJSFLibraryImpl <em>Plugin Provided JSF Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.PluginProvidedJSFLibraryImpl
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getPluginProvidedJSFLibrary()
	 * @generated
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY = 2;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__ID = JSF_LIBRARY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__NAME = JSF_LIBRARY__NAME;

	/**
	 * The feature id for the '<em><b>JSF Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__JSF_VERSION = JSF_LIBRARY__JSF_VERSION;

	/**
	 * The feature id for the '<em><b>Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__DEPLOYED = JSF_LIBRARY__DEPLOYED;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__IMPLEMENTATION = JSF_LIBRARY__IMPLEMENTATION;

	/**
	 * The feature id for the '<em><b>Archive Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__ARCHIVE_FILES = JSF_LIBRARY__ARCHIVE_FILES;

	/**
	 * The feature id for the '<em><b>Plugin ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID = JSF_LIBRARY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY__LABEL = JSF_LIBRARY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Plugin Provided JSF Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_PROVIDED_JSF_LIBRARY_FEATURE_COUNT = JSF_LIBRARY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Relative To Workspace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_FILE__RELATIVE_TO_WORKSPACE = 0;

	/**
	 * The feature id for the '<em><b>Source Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_FILE__SOURCE_LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Relative Dest Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_FILE__RELATIVE_DEST_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>JSF Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_FILE__JSF_LIBRARY = 3;

	/**
	 * The number of structural features of the '<em>Archive File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_FILE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion <em>JSF Version</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFVersion()
	 * @generated
	 */
	int JSF_VERSION = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry <em>JSF Library Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSF Library Registry</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry
	 * @generated
	 */
	EClass getTapestryLibraryRegistry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getDefaultImplementationID <em>Default Implementation ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Implementation ID</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getDefaultImplementationID()
	 * @see #getJSFLibraryRegistry()
	 * @generated
	 */
	EAttribute getTapestryLibraryRegistry_DefaultImplementationID();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getJSFLibraries <em>JSF Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>JSF Libraries</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getJSFLibraries()
	 * @see #getJSFLibraryRegistry()
	 * @generated
	 */
	EReference getTapestryLibraryRegistry_TapestryLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getPluginProvidedJSFLibraries <em>Plugin Provided JSF Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plugin Provided JSF Libraries</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibraryRegistry#getPluginProvidedJSFLibraries()
	 * @see #getJSFLibraryRegistry()
	 * @generated
	 */
	EReference getTapestryLibraryRegistry_PluginProvidedTapestryLibraries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary <em>JSF Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSF Library</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary
	 * @generated
	 */
	EClass getTapestryLibrary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getID()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EAttribute getTapestryLibrary_ID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getName()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EAttribute getTapestryLibrary_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getJSFVersion <em>JSF Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>JSF Version</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getJSFVersion()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EAttribute getTapestryLibrary_TapestryVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#isDeployed <em>Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deployed</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#isDeployed()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EAttribute getTapestryLibrary_Deployed();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#isImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#isImplementation()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EAttribute getTapestryLibrary_Implementation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getArchiveFiles <em>Archive Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Archive Files</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFLibrary#getArchiveFiles()
	 * @see #getJSFLibrary()
	 * @generated
	 */
	EReference getTapestryLibrary_ArchiveFiles();

	/**
	 * Returns the meta object for class '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile <em>Archive File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Archive File</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile
	 * @generated
	 */
	EClass getArchiveFile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getSourceLocation <em>Source Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Location</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getSourceLocation()
	 * @see #getArchiveFile()
	 * @generated
	 */
	EAttribute getArchiveFile_SourceLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#isRelativeToWorkspace <em>Relative To Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relative To Workspace</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#isRelativeToWorkspace()
	 * @see #getArchiveFile()
	 * @generated
	 */
	EAttribute getArchiveFile_RelativeToWorkspace();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getRelativeDestLocation <em>Relative Dest Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relative Dest Location</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getRelativeDestLocation()
	 * @see #getArchiveFile()
	 * @generated
	 */
	EAttribute getArchiveFile_RelativeDestLocation();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getJSFLibrary <em>JSF Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>JSF Library</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.ArchiveFile#getJSFLibrary()
	 * @see #getArchiveFile()
	 * @generated
	 */
	EReference getArchiveFile_TapestryLibrary();

	/**
	 * Returns the meta object for class '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary <em>Plugin Provided JSF Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plugin Provided JSF Library</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary
	 * @generated
	 */
	EClass getPluginProvidedTapestryLibrary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary#getPluginID <em>Plugin ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plugin ID</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary#getPluginID()
	 * @see #getPluginProvidedJSFLibrary()
	 * @generated
	 */
	EAttribute getPluginProvidedTapestryLibrary_PluginID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.PluginProvidedJSFLibrary#getLabel()
	 * @see #getPluginProvidedJSFLibrary()
	 * @generated
	 */
	EAttribute getPluginProvidedTapestryLibrary_Label();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion <em>JSF Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>JSF Version</em>'.
	 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion
	 * @generated
	 */
	EEnum getTapestryVersion();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TapestryLibraryRegistryFactory getTapestryLibraryRegistryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryImpl <em>JSF Library Registry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryImpl
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFLibraryRegistry()
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EClass JSF_LIBRARY_REGISTRY = eINSTANCE.getTapestryLibraryRegistry();

		/**
		 * The meta object literal for the '<em><b>Default Implementation ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
        EAttribute JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID = eINSTANCE.getTapestryLibraryRegistry_DefaultImplementationID();

		/**
		 * The meta object literal for the '<em><b>JSF Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EReference JSF_LIBRARY_REGISTRY__JSF_LIBRARIES = eINSTANCE.getTapestryLibraryRegistry_TapestryLibraries();

		/**
		 * The meta object literal for the '<em><b>Plugin Provided JSF Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EReference JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES = eINSTANCE.getTapestryLibraryRegistry_PluginProvidedTapestryLibraries();

		/**
		 * The meta object literal for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryImpl <em>JSF Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryImpl
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFLibrary()
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EClass JSF_LIBRARY = eINSTANCE.getTapestryLibrary();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute JSF_LIBRARY__ID = eINSTANCE.getTapestryLibrary_ID();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute JSF_LIBRARY__NAME = eINSTANCE.getTapestryLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>JSF Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute JSF_LIBRARY__JSF_VERSION = eINSTANCE.getTapestryLibrary_TapestryVersion();

		/**
		 * The meta object literal for the '<em><b>Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute JSF_LIBRARY__DEPLOYED = eINSTANCE.getTapestryLibrary_Deployed();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute JSF_LIBRARY__IMPLEMENTATION = eINSTANCE.getTapestryLibrary_Implementation();

		/**
		 * The meta object literal for the '<em><b>Archive Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EReference JSF_LIBRARY__ARCHIVE_FILES = eINSTANCE.getTapestryLibrary_ArchiveFiles();

		/**
		 * The meta object literal for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.ArchiveFileImpl <em>Archive File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.ArchiveFileImpl
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getArchiveFile()
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EClass ARCHIVE_FILE = eINSTANCE.getArchiveFile();

		/**
		 * The meta object literal for the '<em><b>Relative To Workspace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute ARCHIVE_FILE__RELATIVE_TO_WORKSPACE = eINSTANCE.getArchiveFile_RelativeToWorkspace();

		/**
		 * The meta object literal for the '<em><b>Source Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute ARCHIVE_FILE__SOURCE_LOCATION = eINSTANCE.getArchiveFile_SourceLocation();

		/**
		 * The meta object literal for the '<em><b>Relative Dest Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute ARCHIVE_FILE__RELATIVE_DEST_LOCATION = eINSTANCE.getArchiveFile_RelativeDestLocation();

		/**
		 * The meta object literal for the '<em><b>JSF Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EReference ARCHIVE_FILE__JSF_LIBRARY = eINSTANCE.getArchiveFile_TapestryLibrary();

		/**
		 * The meta object literal for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.PluginProvidedJSFLibraryImpl <em>Plugin Provided JSF Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.PluginProvidedJSFLibraryImpl
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getPluginProvidedJSFLibrary()
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EClass PLUGIN_PROVIDED_JSF_LIBRARY = eINSTANCE.getPluginProvidedTapestryLibrary();

		/**
		 * The meta object literal for the '<em><b>Plugin ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EAttribute PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID = eINSTANCE.getPluginProvidedTapestryLibrary_PluginID();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
//		@SuppressWarnings("hiding")
		EAttribute PLUGIN_PROVIDED_JSF_LIBRARY__LABEL = eINSTANCE.getPluginProvidedTapestryLibrary_Label();

		/**
		 * The meta object literal for the '{@link org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion <em>JSF Version</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.JSFVersion
		 * @see org.eclipse.jst.jsf.core.internal.jsflibraryregistry.impl.JSFLibraryRegistryPackageImpl#getJSFVersion()
		 * @generated
		 */
//        @SuppressWarnings("hiding")
		EEnum JSF_VERSION = eINSTANCE.getTapestryVersion();

	}

}
