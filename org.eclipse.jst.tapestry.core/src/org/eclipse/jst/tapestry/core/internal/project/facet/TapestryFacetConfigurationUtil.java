package org.eclipse.jst.tapestry.core.internal.project.facet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;

/**
 * 
 * @author gavingui2011@gmail.com - Beijing China
 *
 */
public class TapestryFacetConfigurationUtil {
	private static final String TAPESTRY_FACES_CONFIG_EXTENSION_ID = "tapestryFacetConfiguration"; //$NON-NLS-1$
	private static final String DISABLED_ATTR_NAME = "disabled"; //$NON-NLS-1$
	private static final String TRUE_STRING = "true"; //$NON-NLS-1$

	/**
	 * @return True if the disabled attribute is not set to "true".
	 */
	public static boolean isTapestryFacetConfigurationEnabled() {
		return !isTapestryFacetConfigurationDisabled();
	}

	/**
	 * @return True if disabled="true" is set in the extension point.
	 */
	public static boolean isTapestryFacetConfigurationDisabled() {
		IExtensionPoint jsfFacetConfigExtensionPoint = TapestryCorePlugin
				.getDefault().getExtension(TAPESTRY_FACES_CONFIG_EXTENSION_ID);
		for (final IExtension extension : jsfFacetConfigExtensionPoint
				.getExtensions()) {
			for (final IConfigurationElement configElement : extension
					.getConfigurationElements()) {
				if (configElement != null
						&& configElement.getName().equals(
								TAPESTRY_FACES_CONFIG_EXTENSION_ID)) {
					final String attrValue = configElement
							.getAttribute(DISABLED_ATTR_NAME);
					if (attrValue != null) {
						return attrValue.equalsIgnoreCase(TRUE_STRING);
					}
				}
			}
		}

		return false;
	}
}
