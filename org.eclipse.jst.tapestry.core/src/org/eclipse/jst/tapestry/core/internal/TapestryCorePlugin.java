package org.eclipse.jst.tapestry.core.internal;

import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jst.jsf.context.IDelegatingFactory;
import org.eclipse.jst.jsf.context.resolver.structureddocument.internal.IStructuredDocumentContextResolverFactory2;
import org.eclipse.wst.common.frameworks.internal.WTPPlugin;
import org.osgi.framework.BundleContext;

/**
 * Tapestry Core plugin.
 * 
 * @author gavingui2011@gmail.com - Beijing China
 */
public class TapestryCorePlugin extends WTPPlugin
{
    /**
     * The plugin id
     */
    public static final String        PLUGIN_ID = "org.eclipse.jst.tapestry.core"; 
    // //$NON-NLS-1$

    /**
     * The extension point id (plugin relative) for the tag registry factory provider.
     */
    public static final String             TAG_REGISTRY_FACTORY_PROVIDER_ID = "tagRegistryFactory"; //$NON-NLS-1$

    // The shared instance.
    private static TapestryCorePlugin           plugin;

    private IPreferenceStore               preferenceStore;

    /**
     * The constructor.
     */
    public TapestryCorePlugin()
    {
        plugin = this;
    }


	@Override
	public String getPluginID() {
		return PLUGIN_ID;
	}
	
	/**
     * This method is called upon plug-in activation
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void start(final BundleContext context) throws Exception
    {
        super.start(context);
        final IStructuredDocumentContextResolverFactory2 factory = IStructuredDocumentContextResolverFactory2.INSTANCE;
        if (factory instanceof IDelegatingFactory)
        {
            /*_tagLibResolverFactory = new ViewBasedTaglibResolverFactory();
            ((IDelegatingFactory) factory)
            .addFactoryDelegate(_tagLibResolverFactory);*/
        }
        else
        {
            log("Error adding tag resolver delegate", new Throwable()); //$NON-NLS-1$
        }
    }

    /**
     * This method is called when the plug-in is stopped
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void stop(final BundleContext context) throws Exception
    {
        super.stop(context);

        final IStructuredDocumentContextResolverFactory2 factory = IStructuredDocumentContextResolverFactory2.INSTANCE;

        /*if (factory instanceof IDelegatingFactory
                && _tagLibResolverFactory != null)
        {
            ((IDelegatingFactory) factory)
            .removeFactoryDelegate(_tagLibResolverFactory);
        }*/
        plugin = null;
    }
    
	/**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static TapestryCorePlugin getDefault()
    {
        return plugin;
    }
    
    /**
     * @param e
     * @param msg
     */
    public static void log(final Exception e, final String msg)
    {
        final IStatus logStatus = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, e);
        TapestryCorePlugin currentPlugin = getDefault();
        if (currentPlugin != null)
        {
            final ILog log = currentPlugin.getLog();
            if (log != null)
            {
                log.log(logStatus);
            }
        }
        System.err.println(logStatus);
    }

    /**
     * Logs using the default ILog implementation provided by getLog().
     * 
     * @param severity
     *            Severity (IStatus constant) of log entry
     * @param message
     *            Human-readable message describing log entry
     * @param ex
     *            Throwable instance (can be null)
     */
    public static void log(final int severity, final String message,
            final Throwable ex)
    {
    	Status logObject = new Status(severity, PLUGIN_ID, IStatus.OK, message, ex);
    	TapestryCorePlugin default1 = getDefault();
    	if (default1 != null)
    	{
    		ILog log = default1.getLog();
    		if (log != null)
    		{
    			log.log(logObject);
    			return;
    		}
    	}
    	System.err.println(logObject.toString());
    }

    /**
     * Logs using the default ILog implementation provided by getLog().
     * 
     * @param severity
     *            Severity (IStatus constant) of log entry
     * @param message
     *            Human-readable message describing log entry
     */
    public static void log(final int severity, final String message)
    {
        log(severity, message, null);
    }

    /**
     * Logs a message for this plugin
     * 
     * @param message
     * @param t
     */
    public static void log(final String message, final Throwable t)
    {
        final ILog log = plugin.getLog();
        log.log(new Status(IStatus.ERROR, plugin.getBundle().getSymbolicName(),
                0, message, t));
    }
    
    /**
     * @param name
     * @return the extension point called name for this bundle
     */
    public IExtensionPoint getExtension(final String name)
    {
        return Platform.getExtensionRegistry().getExtensionPoint(
                plugin.getBundle().getSymbolicName(), name);
    }
}
