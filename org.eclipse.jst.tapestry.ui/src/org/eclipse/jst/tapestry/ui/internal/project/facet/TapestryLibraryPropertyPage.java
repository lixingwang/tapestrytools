package org.eclipse.jst.tapestry.ui.internal.project.facet;

import org.eclipse.jst.common.project.facet.ui.libprov.FacetLibraryPropertyPage;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * TODO
 *
 */
@SuppressWarnings("deprecation")
public final class TapestryLibraryPropertyPage

    extends FacetLibraryPropertyPage
    
{
    @Override
    public IProjectFacetVersion getProjectFacetVersion()
    {
        final IProjectFacet tapestryFacet = ProjectFacetsManager.getProjectFacet( "jst.tapestry" ); //$NON-NLS-1$
        final IFacetedProject fproj = getFacetedProject();
        return fproj.getInstalledVersion( tapestryFacet );
    }
    
}
