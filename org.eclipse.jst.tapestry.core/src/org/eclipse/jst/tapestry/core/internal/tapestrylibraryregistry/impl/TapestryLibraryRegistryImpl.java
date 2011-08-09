package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistry;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryPackage;

public class TapestryLibraryRegistryImpl extends EObjectImpl implements TapestryLibraryRegistry{
	public static final String copyright = "Copyright (c) 2005 Oracle Corporation"; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getDefaultImplementationID() <em>Default Implementation ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultImplementationID()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_IMPLEMENTATION_ID_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDefaultImplementationID() <em>Default Implementation ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultImplementationID()
	 * @generated
	 * @ordered
	 */
	protected String defaultImplementationID = DEFAULT_IMPLEMENTATION_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJSFLibraries() <em>JSF Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJSFLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList jsfLibraries;

	/**
	 * The cached value of the '{@link #getPluginProvidedJSFLibraries() <em>Plugin Provided JSF Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginProvidedJSFLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList pluginProvidedJSFLibraries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TapestryLibraryRegistryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the static eClass 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TapestryLibraryRegistryPackage.Literals.JSF_LIBRARY_REGISTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the default implementation id 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultImplementationID() {
		return defaultImplementationID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newDefaultImplementationID 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultImplementationID(String newDefaultImplementationID) {
		String oldDefaultImplementationID = defaultImplementationID;
		defaultImplementationID = newDefaultImplementationID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID, oldDefaultImplementationID, defaultImplementationID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the list of jsf libraries
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTapestryLibraries() {
		if (jsfLibraries == null) {
			jsfLibraries = new EObjectContainmentEList(TapestryLibrary.class, this, TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES);
		}
		return jsfLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the list of plugin provided JSF libraries 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPluginProvidedTapestryLibraries() {
		if (pluginProvidedJSFLibraries == null) {
			pluginProvidedJSFLibraries = new EObjectContainmentEList(PluginProvidedTapestryLibrary.class, this, TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES);
		}
		return pluginProvidedJSFLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the default implemention JSF library 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TapestryLibrary getDefaultImplementation() {
		return getTapestryLibraryByID(getDefaultImplementationID());
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param implementation 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setDefaultImplementation(TapestryLibrary implementation) {
		if (implementation != null) {
			setDefaultImplementationID(implementation.getID());
		} else {
			setDefaultImplementationID(null);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES:
				return ((InternalEList)getTapestryLibraries()).basicRemove(otherEnd, msgs);
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES:
				return ((InternalEList)getPluginProvidedTapestryLibraries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID:
				return getDefaultImplementationID();
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES:
				return getTapestryLibraries();
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES:
				return getPluginProvidedTapestryLibraries();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID:
				setDefaultImplementationID((String)newValue);
				return;
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES:
				getTapestryLibraries().clear();
				getTapestryLibraries().addAll((Collection)newValue);
				return;
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES:
				getPluginProvidedTapestryLibraries().clear();
				getPluginProvidedTapestryLibraries().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID:
				setDefaultImplementationID(DEFAULT_IMPLEMENTATION_ID_EDEFAULT);
				return;
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES:
				getTapestryLibraries().clear();
				return;
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES:
				getPluginProvidedTapestryLibraries().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__DEFAULT_IMPLEMENTATION_ID:
				return DEFAULT_IMPLEMENTATION_ID_EDEFAULT == null ? defaultImplementationID != null : !DEFAULT_IMPLEMENTATION_ID_EDEFAULT.equals(defaultImplementationID);
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__JSF_LIBRARIES:
				return jsfLibraries != null && !jsfLibraries.isEmpty();
			case TapestryLibraryRegistryPackage.JSF_LIBRARY_REGISTRY__PLUGIN_PROVIDED_JSF_LIBRARIES:
				return pluginProvidedJSFLibraries != null && !pluginProvidedJSFLibraries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param ID 
	 * @return the jsf library of ID or null if none 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TapestryLibrary getTapestryLibraryByID(String ID) {
		TapestryLibrary library = null;
		if (ID != null) {
			Iterator itLibs = getAllTapestryLibraries().iterator();
			while (itLibs.hasNext()) {
				TapestryLibrary curLib = (TapestryLibrary)itLibs.next();
				if (ID.equals(curLib.getID())) {
					library = curLib;
					break;
				}
			}
		}
		return library;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param name 
	 * @return the list of libraries named 'name' 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getTapestryLibrariesByName(String name) {
		EList libraries = new BasicEList();
		if (name != null) {
			Iterator itLibs = getAllTapestryLibraries().iterator();
			while(itLibs.hasNext()) {
				TapestryLibrary curLib = (TapestryLibrary)itLibs.next();
				if (name.equals(curLib.getName())) {
					libraries.add(curLib);
				}
			}
		}
		return libraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This is a convenience method to return an EList of JSFLibrary instances
	 * that are marked as JSF implementations; while all instances are valid
	 * references, the returned EList should not be used for additions and/or
	 * removals of instances (use the EList returned by getJSFLibraries()).
	 * @return the list of implemention jsf libraries 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getImplTapestryLibraries() {
		EList implementations = new BasicEList();
		Iterator itLibs = getAllTapestryLibraries().iterator();
		while (itLibs.hasNext()) {
			TapestryLibrary lib = (TapestryLibrary)itLibs.next();
			if (lib.isImplementation()) {
				implementations.add(lib);
			}
		}
		return implementations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This is a convenience method to return an EList of JSFLibrary instances
	 * that are not marked as JSF implementations; while all instances are
	 * valid references, the returned EList should not be used for additions
	 * and/or removals of instances (use the EList returned by
	 * getJSFLibraries()).
	 * @return the non-implemention JSF libraries
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getNonImplTapestryLibraries() {
		EList nonImplementations = new BasicEList();
		Iterator itLibs = getAllTapestryLibraries().iterator();
		while (itLibs.hasNext()) {
			TapestryLibrary lib = (TapestryLibrary)itLibs.next();
			if (!lib.isImplementation()) {
				nonImplementations.add(lib);
			}
		}
		return nonImplementations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This is a convenience method to return an EList of JSFLibrary instances
	 * and PluginProvidedJSFLibrary instances; while all instances are valid
	 * references, the returned EList should not be used for additions and/or
	 * removals of instances (use the EList returned by getJSFLibraries()).
	 * @return all JSF libraries 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getAllTapestryLibraries() {
		EList allLibs = new BasicEList();
		allLibs.addAll(getTapestryLibraries());
		allLibs.addAll(getPluginProvidedTapestryLibraries());
		return allLibs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param library 
	 * @return true if library is successfully added 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean addTapestryLibrary(TapestryLibrary library) {
		boolean added = false;
		if (library instanceof PluginProvidedTapestryLibrary) {
			added = getPluginProvidedTapestryLibraries().add(library);
		} else {
			added = getTapestryLibraries().add(library);
		}
		return added;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param library 
	 * @return true if library is successfully removed
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean removeTapestryLibrary(TapestryLibrary library) {
		boolean removed = false;
		if (library instanceof PluginProvidedTapestryLibrary) {
			removed = getPluginProvidedTapestryLibraries().remove(library);
		} else {
			removed = getTapestryLibraries().remove(library);
		}
		return removed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the string representation 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (DefaultImplementationID: "); //$NON-NLS-1$
		result.append(defaultImplementationID);
		result.append(')');
		return result.toString();
	}
}
