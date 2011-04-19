package org.eclipse.jst.tapestry.core.internal.project.facet;

import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetInstallDataModelProperties;

/**
 * Defines properties used by the Tapestry facet install data model.
 * 
 * @author gavingui2011@gmail.com - Beijing China
 */
public interface ITapestryFacetInstallDataModelProperties extends
		IFacetInstallDataModelProperties {
	/**
	 * Refers to the path where the faces config file will be created. Expects a
	 * string that can be interpreted as a web root relative path.
	 */
	public static final String CONFIG_PATH = "ITapestryFacetInstallDataModelProperties.CONFIG_PATH"; //$NON-NLS-1$

	/**
	 * TODO:
	 */
	public static final String SERVLET_NAME = "ITapestryFacetInstallDataModelProperties.SERVLET_NAME"; //$NON-NLS-1$

	/**
	 * TODO:
	 */
	public static final String SERVLET_CLASSNAME = "ITapestryFacetInstallDataModelProperties.SERVLET_CLASSNAME"; //$NON-NLS-1$

	/**
	 * Refers to configuration information about servlet url patterns to add on
	 * install The model value must be a String[].
	 */
	public static final String SERVLET_URL_PATTERNS = "ITapestryFacetInstallDataModelProperties.SERVLET_URL_PATTERNS"; //$NON-NLS-1$

	/**
	 * TODO:
	 */
	public static final String WEBCONTENT_DIR = "ITapestryFacetInstallDataModelProperties.WEBCONTENT_DIR"; //$NON-NLS-1$

	/**
	 * TODO:
	 */
	public static final String LIBRARY_PROVIDER_DELEGATE = "ITapestryFacetInstallDataModelProperties.LIBRARY_PROVIDER_DELEGATE"; //$NON-NLS-1$    

	/**
	 * TODO:
	 */
	public static final String COMPONENT_LIBRARIES = "ITapestryFacetInstallDataModelProperties.COMPONENT_LIBRARIES"; //$NON-NLS-1$	
}
