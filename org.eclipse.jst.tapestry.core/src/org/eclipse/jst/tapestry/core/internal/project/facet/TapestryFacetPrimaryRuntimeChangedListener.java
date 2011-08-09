package org.eclipse.jst.tapestry.core.internal.project.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jst.common.project.facet.core.ClasspathHelper;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.tapestryappconfig.TapestryAppConfigUtils;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IPrimaryRuntimeChangedEvent;

/**
 * Handles primary runtime changed events when the Tapestry Facet is installed
 * 
 * @author gavingui2011@gmail.com
 */
@SuppressWarnings("deprecation")
public class TapestryFacetPrimaryRuntimeChangedListener implements IFacetedProjectListener {

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener#handleEvent(org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent)
	 */
	public void handleEvent(IFacetedProjectEvent event) {
		if (event instanceof IPrimaryRuntimeChangedEvent &&
				getTapestryFacetedVersion(event.getProject().getProject()) != null //must be a Tapestry faceted project
				){
			//remove condition : TapestryLibraryConfigurationHelper.isConfiguredForSystemSuppliedImplementation(event.getProject().getProject())
			try {				
				IProject project = event.getProject().getProject();
				IProjectFacetVersion fv = getTapestryFacetedVersion(project);
				ClasspathHelper.removeClasspathEntries(project, fv);
				ClasspathHelper.addClasspathEntries(project, fv);
			} catch (CoreException e) {
				TapestryCorePlugin.log(IStatus.ERROR, "Unable to replace server supplied implementation when runtime changed.", e);//$NON-NLS-1$
			}
		}
		
	}

	/**
	 * @param project
	 * @return IProjectFacetVersion and null if not Tapestry faceted
	 */
	private IProjectFacetVersion getTapestryFacetedVersion(IProject project) {
		return TapestryAppConfigUtils.getProjectFacet(project);		
	}

}
