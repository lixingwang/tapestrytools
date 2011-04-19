/*******************************************************************************
 * Copyright (c) 2005 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ian Trimble - initial API and implementation
 *******************************************************************************/ 
package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryRegistryUtil;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistry;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibraryRegistryPackage;

/**
 * EMF adapter that attempts to always maintain a default implementation
 * JSFLibrary upon addition and removal of JSFLibrary instances and upon
 * changing of a JSFLibrary instance's implementation property.
 * 
 * @author Ian Trimble - Oracle
 * @deprecated
 */
public class MaintainDefaultImplementationAdapter extends AdapterImpl {

	private static MaintainDefaultImplementationAdapter INSTANCE =
		new MaintainDefaultImplementationAdapter();

	/**
	 * Gets the single instance of this adapter.
	 * 
	 * @return The single instance of this adapter.
	 */
	public static MaintainDefaultImplementationAdapter getInstance() {
		return INSTANCE;
	}

	/**
	 * Called to notify this adapter that a change has occured.
	 * 
	 * @param notification EMF Notification instance
	 */
	public void notifyChanged(Notification notification) {
		Object objNotifier = notification.getNotifier();
		if (objNotifier instanceof TapestryLibraryRegistry) {
			int eventType = notification.getEventType();
			switch (eventType) {
				case Notification.ADD:
					Object objNewValue = notification.getNewValue();
					if (objNewValue instanceof TapestryLibrary) {
						libraryAdded((TapestryLibrary)objNewValue);
					}
					break;
				case Notification.REMOVE:
					Object objOldValue = notification.getOldValue();
					if (objOldValue instanceof TapestryLibrary) {
						libraryRemoved((TapestryLibrary)objOldValue);
					}
					break;
			}
		} else if (objNotifier instanceof TapestryLibrary) {
			if (notification.getFeatureID(TapestryLibrary.class) == TapestryLibraryRegistryPackage.JSF_LIBRARY__IMPLEMENTATION) {
				implementationFlagSet((TapestryLibrary)objNotifier);
			}
		}
	}

	/**
	 * Checks if the library added is an implementation and, if so, makes it
	 * the default implementation if it is the only implementation.
	 * 
	 * @param library JSFLibrary instance
	 */
	protected void libraryAdded(TapestryLibrary library) {
		if (library != null && library.isImplementation()) {
			TapestryLibraryRegistry jsfLibReg = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryRegistry();
			EList impls = jsfLibReg.getImplTapestryLibraries();
			if (impls.size() == 1) {
				jsfLibReg.setDefaultImplementation(library);
			}
		}
	}

	/**
	 * Checks if the library removed is the default implementation and, if so,
	 * makes the first remaining implementation the new default or nulls out
	 * the default implementation if no other implementation remains. 
	 * 
	 * @param library JSFLibrary instance
	 */
	protected void libraryRemoved(TapestryLibrary library) {
		if (library != null && library.isImplementation()) {
			TapestryLibraryRegistry jsfLibReg = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryRegistry();
			TapestryLibrary defaultImpl = jsfLibReg.getDefaultImplementation(); 
			if (defaultImpl == null || library.getID().equals(defaultImpl.getID())) { 
				setNewDefaultImplementation();
			}
		}
	}

	/**
	 * Checks if the implementation flag of the JSFLibrary has been changed
	 * such that it is now eligible to become the default implementation or
	 * such that it is no longer eligible as the default implementation and
	 * sets the default implementation appropriately. Note that the passed
	 * JSFLibrary instance must have been added to the model before calling
	 * this method for it to have any effect.
	 * 
	 * @param library JSFLibrary instance
	 */
	private void implementationFlagSet(TapestryLibrary library) {
		TapestryLibraryRegistry jsfLibReg = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryRegistry();
		if (jsfLibReg != null) {
			TapestryLibrary defaultImpl = jsfLibReg.getDefaultImplementation();
			if (
					library.isImplementation() &&
					defaultImpl == null
			) {
				jsfLibReg.setDefaultImplementation(library);
			} else if (
					!library.isImplementation() &&
					(defaultImpl != null && library.getID().equals(defaultImpl.getID())))
			{
				setNewDefaultImplementation();
			}
		}
	}

	/**
	 * Sets the first available JSFLibrary marked as an implementation as the
	 * default implementation or sets the default implementation to null if no
	 * JSFLibrary is marked as an implementation.
	 */
	protected void setNewDefaultImplementation() {
		TapestryLibraryRegistry jsfLibReg = TapestryLibraryRegistryUtil.getInstance().getTapestryLibraryRegistry();
		EList impls = jsfLibReg.getImplTapestryLibraries();
		if (impls.size() > 0) {
			jsfLibReg.setDefaultImplementation((TapestryLibrary)impls.get(0));
		} else {
			jsfLibReg.setDefaultImplementation(null);
		}
	}
}
