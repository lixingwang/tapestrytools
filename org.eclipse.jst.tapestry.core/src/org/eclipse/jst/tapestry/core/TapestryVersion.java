/*******************************************************************************
 * Copyright (c) 2001, 2007 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.tapestry.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/** 
 * @author gekessle
 *
 */
public enum TapestryVersion {
	
	/**
	 * Unknown version support
	 */
	UNKNOWN,
	/**
	 * Supports JSF Version 1.0
	 */
	V5_0;

	
	// WARNING: you MUST add newer versions to the end; the ordinal value of
	// of the version is used in compareTo.
    @Override
    public String toString() {
        switch(this)
        {
            case V5_0:
                return ITapestryCoreConstants.Tapestry_VERSION_5_0;
            case UNKNOWN:
                return "unknown"; //$NON-NLS-1$
            default:
                throw new IllegalStateException("Missing string conversion: "+this.name()); //$NON-NLS-1$
        }
    }

    /**
     * @param valueAsString
     * @return the reverse mapping to an enum from toString() returns
     */
    public static TapestryVersion valueOfString(String  valueAsString)
    {
        if (ITapestryCoreConstants.Tapestry_VERSION_5_0.equals(valueAsString))
        {
            return V5_0;
        }
        else if ("unknown".equals(valueAsString)) //$NON-NLS-1$
        {
            return UNKNOWN;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * @param facetVersion
     * @return the tapestry version for the facet version
     * @throw IllegalArgumentException if the underlying facet is not a Tapestry facet.
     */
    public static TapestryVersion valueOfFacetVersion(final IProjectFacetVersion facetVersion)
    {
        if (!ITapestryCoreConstants.isTapestryFacet(facetVersion.getProjectFacet()))
        {
            throw new IllegalArgumentException("Not a Tapestry facet: "+facetVersion.getProjectFacet().toString()); //$NON-NLS-1$
        }
        
        String versionString = facetVersion.getVersionString();
        if (versionString != null)
        {
            return valueOfString(versionString);
        }
        return null;
    }
    
    /**
     * @param project
     * @return the project version of the project.
     */
    public static TapestryVersion valueOfProject(final IProject project)
    {
        try
        {
            if (project != null && FacetedProjectFramework.isFacetedProject(project))
            {
                IFacetedProject fProj = ProjectFacetsManager.create(project);
                if (fProj != null)
                {
                    IProjectFacetVersion projectFacetVersion = fProj.getProjectFacetVersion(
                            ProjectFacetsManager.getProjectFacet(ITapestryCoreConstants.Tapestry_CORE_FACET_ID));
                    if (projectFacetVersion != null)
                    {
                        return valueOfFacetVersion(projectFacetVersion);
                    }
                }
            }
        }
        catch(final CoreException ce)
        {
            // ignore and fall-through
            // TODO: is this worth logging?
        }
        return null;
    }
}
