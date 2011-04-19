package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.impl;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.ArchiveFile;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.PluginProvidedTapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryFactory;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryPackage;



public class PluginProvidedTapestryLibraryImpl extends TapestryLibraryImpl implements PluginProvidedTapestryLibrary{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("hiding")
	public static final String copyright = "Copyright (c) 2005 Oracle Corporation"; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getPluginID() <em>Plugin ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginID()
	 * @generated
	 * @ordered
	 */
	protected static final String PLUGIN_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPluginID() <em>Plugin ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginID()
	 * @generated
	 * @ordered
	 */
	protected String pluginID = PLUGIN_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PluginProvidedTapestryLibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * @return the static eclass 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TapestryLibraryRegistryPackage.Literals.PLUGIN_PROVIDED_JSF_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
     * @return the plugin id 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPluginID() {
		return pluginID;
	}

	/**
	 * <!-- begin-user-doc -->
     * @param newPluginID 
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPluginID(String newPluginID) {
		String oldPluginID = pluginID;
		pluginID = newPluginID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID, oldPluginID, pluginID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return translatable label
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getLabel() {
		if (label == null)
			return super.getLabel();
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID:
				return getPluginID();
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__LABEL:
				return getLabel();
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
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID:
				setPluginID((String)newValue);
				return;
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__LABEL:
				setLabel((String)newValue);
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
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID:
				setPluginID(PLUGIN_ID_EDEFAULT);
				return;
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__LABEL:
				setLabel(LABEL_EDEFAULT);
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
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__PLUGIN_ID:
				return PLUGIN_ID_EDEFAULT == null ? pluginID != null : !PLUGIN_ID_EDEFAULT.equals(pluginID);
			case TapestryLibraryRegistryPackage.PLUGIN_PROVIDED_JSF_LIBRARY__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getID() {
		return getPluginID() + ID_SEPARATOR + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the working copy 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TapestryLibrary getWorkingCopy() {
		PluginProvidedTapestryLibrary workingCopyLib = TapestryLibraryRegistryFactory.eINSTANCE.createPluginProvidedTapestryLibrary();
//		workingCopyLib.setID(getID());
		workingCopyLib.setName(getName());
		if (label != null) workingCopyLib.setLabel(getLabel());
		workingCopyLib.setTapestryVersion(getTapestryVersion());
		workingCopyLib.setDeployed(isDeployed());
		workingCopyLib.setImplementation(isImplementation());
		workingCopyLib.setPluginID(getPluginID());
		Iterator itArchiveFiles = getArchiveFiles().iterator();
		while (itArchiveFiles.hasNext()) {
			ArchiveFile srcArchiveFile = (ArchiveFile)itArchiveFiles.next();
			ArchiveFile destArchiveFile = TapestryLibraryRegistryFactory.eINSTANCE.createArchiveFile();
			destArchiveFile.setRelativeToWorkspace(srcArchiveFile.isRelativeToWorkspace());
			destArchiveFile.setSourceLocation(srcArchiveFile.getSourceLocation());
			destArchiveFile.setRelativeDestLocation(srcArchiveFile.getRelativeDestLocation());
			workingCopyLib.getArchiveFiles().add(destArchiveFile);
		}
		return workingCopyLib;
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
		result.append(" (pluginID: "); //$NON-NLS-1$
		result.append(pluginID);
		result.append(", Label: "); //$NON-NLS-1$
		result.append(label);
		result.append(')');
		return result.toString();
	}
}
