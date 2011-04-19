package org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig;

import java.util.ArrayList;
import java.util.List;


public class TapestryLibraryConfigDialogSettingData {
	/**
	 * Delimintor for parsing a persistent property string.
	 */
	final protected static String SEPARATOR =":";	//$NON-NLS-1$

    /**
     * Parsing delimnitor for elements in a tuple.
     */
    final protected static String SPTR_TUPLE = ":"; //$NON-NLS-1$
    /**
     * Parsing delimintor for tuples in a persistent property string.
     */
    final protected static String EO_TUPLE = ";";   //$NON-NLS-1$
    
	final private TapestryLibraryRegistryUtil jsfLibReg;
	private String[] savedCompLibs;
	private List selJSFLibComp;							// lazy initialized
	
	
	/**
	 * Constructor where implementation type was chosen to be USER_SPECIFIED.  Created for backwards compatibilty when server supplied was not an option.
	 * @param compLibs
	 */
	public TapestryLibraryConfigDialogSettingData( String[] compLibs) {
		this.jsfLibReg = TapestryLibraryRegistryUtil.getInstance();
		this.savedCompLibs = compLibs;
		
		// Verify and log a message if a saved component library no longer exists. 
		verifySavedLibAvailability();
	}	
	
	/**
	 * Return the list of saved component libraries and their deployment settings.
	 * 
	 * @return selJSFLibComp List 
	 */
	public List getJSFComponentLibraries() {
		if (selJSFLibComp == null) {
			selJSFLibComp = new ArrayList();		
			
			if (savedCompLibs != null && savedCompLibs.length > 0) {
				TapestryLibraryInternalReference lib = null;
				String item;
				String[] attributes;
				String id;
				boolean deploy = false;
				
				for (int i = 0; i < savedCompLibs.length; i++) {
					item = savedCompLibs[i];
					attributes = item.split(SEPARATOR);
					
					id = attributes[0];				
					deploy = Boolean.valueOf(attributes[1]).booleanValue();
					
					lib = jsfLibReg.getTapestryLibraryReferencebyID(id);
					if (lib != null) {
						selJSFLibComp.add(new TapestryLibraryInternalReference(lib.getLibrary(), true, deploy));
					} /*else {
						// already logged if a saved component library is no longer available.
					}*/
				}
			}
		}
		return selJSFLibComp;
	}

	/**
	 * Only need to verify component library availability from sticky settings.
	 */
 	private void verifySavedLibAvailability() {
 		List validLibs = new ArrayList();
		if (savedCompLibs != null && savedCompLibs.length > 0) {
			String item = null;
			String[] attributes;
			boolean invalidRefs = false;
			for (int i = 0; i < savedCompLibs.length; i++) {
				item = savedCompLibs[i];
				attributes = item.split(SEPARATOR);
													
				if (jsfLibReg.getTapestryLibraryReferencebyID(attributes[0]) != null) {
					validLibs.add(item);
				} else {
					invalidRefs = true;
//					JSFCorePlugin.log(IStatus.INFO, Messages.JSFLibCfgDialogSettingData_Sticky_Component_Lib_Not_Exist);
				}
			}  				
			if (invalidRefs){
				String [] validCompLibs = (String[])validLibs.toArray(new String[validLibs.size()]);
				if (validCompLibs.length > 0)
					System.arraycopy(validCompLibs, 0, savedCompLibs, 0, validCompLibs.length);
				else
					savedCompLibs = new String[]{};
			}
		}	
		

 	}
}
