package org.eclipse.jst.tapestry.core.internal.project.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.ModelProviderManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.jst.tapestry.core.TapestryVersion;

/**
 * Creates a new JSFUtil for a particular JSF project version.
 * 
 * @author cbateman
 *
 */
public class TapestryUtilFactory
{
    /**
     * @param project
     * @return the jsf version of the project if it is valid JSF faceted
     * project or null otherwise.
     */
    public TapestryUtils create(final IProject project)
    {
        final TapestryVersion jsfVersion = TapestryVersion.valueOfProject(project);
        if (jsfVersion != null)
        {
            return create(jsfVersion, ModelProviderManager.getModelProvider(project));
        }
        return null;
    }
    /**
     * @param version
     * @param modelProvider 
     * @return the JSF utils object for the version or null if none.
     * @throw {@link IllegalArgumentException} if version is not related
     * to a JSF facet.
     */
    public TapestryUtils create(final IProjectFacetVersion version, final IModelProvider modelProvider)
    {
        final TapestryVersion jsfVersion = TapestryVersion.valueOfFacetVersion(version);
        if (jsfVersion != null)
        {
            return create(jsfVersion, modelProvider);
        }
        return null;
    }

    /**
     * @param version
     * @param modelProvider 
     * @return the JSF utils object for the version or null if none.
     */
    public TapestryUtils create(final TapestryVersion version, final IModelProvider modelProvider)
    {
        switch (version)
        {
        case V5_0:
            return new TapestryUtils50(modelProvider);
        default:
            throw new IllegalArgumentException("Unknown version: "+version); //$NON-NLS-1$
        }
    }
}
