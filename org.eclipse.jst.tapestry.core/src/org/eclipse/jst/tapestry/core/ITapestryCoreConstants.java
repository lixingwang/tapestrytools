
package org.eclipse.jst.tapestry.core;

import org.eclipse.wst.common.project.facet.core.IProjectFacet;

/**
 * Tapestry Core framework constants
 * 
 * <p><b>Provisional API - subject to change</b></p>
 * 
 * @author gavingui2011@gmail.com - Beijing China
 *
 */
public final class ITapestryCoreConstants 
{
    /**
     * The global id for the Tapestry facet
     * TODO: align with extensioin point through plugin.properties
     */
    public static final String Tapestry_CORE_FACET_ID = "jst.tapestry"; //$NON-NLS-1$

    /**
     * The facet version for a Tapestry 1.2 project
     * TODO: align with extensioin point through plugin.properties
     */
    public final static String                  FACET_VERSION_5_0 = "5.0";//$NON-NLS-1$
    /**
     * The constant id for a Tapestry 1.2 project
     */
    public final static String                  Tapestry_VERSION_5_0 = FACET_VERSION_5_0;

    
    /**
     * @param facet
     * @return true if the facet is a Tapestry facet.
     * 
     */
    public static boolean isTapestryFacet(final IProjectFacet facet)
    {
        return Tapestry_CORE_FACET_ID.equals(facet.getId());
    }
    private ITapestryCoreConstants()
    {
        // no instantiation
    }
}
