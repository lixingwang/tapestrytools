package org.eclipse.jst.tapestry.core.internal.project.facet;

import java.io.PrintWriter;
import java.util.List;

import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.tapestry.core.TapestryVersion;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * JSF Utils instance for JSF 2.0.
 * 
 * @author cbateman
 * 
 */
/* package: use JSFUtilFactory */class TapestryUtils50 extends TapestryUtils
{
    private static final String DEFAULT_DEFAULT_MAPPING_SUFFIX = "xhtml"; //$NON-NLS-1$

    /**
	 * @param modelProvider 
	 * 
	 */
	protected TapestryUtils50(final IModelProvider modelProvider)
    {
        this(TapestryVersion.V5_0, modelProvider);
    }

    /**
     * @param jsfVersion
     * @param modelProvider 
     */
    protected TapestryUtils50(final TapestryVersion tapestryVersion, final IModelProvider modelProvider)
    {
        super(tapestryVersion, modelProvider);
        if (tapestryVersion.compareTo(TapestryVersion.V5_0) < 0)
        {
            throw new IllegalArgumentException(
                    "TapestryVersion must be at least 5.0"); //$NON-NLS-1$
        }
    }

    @Override
    public void doVersionSpecificConfigFile(PrintWriter pw)
    {
        final String QUOTE = new String(new char[]
        { '"' });
        final String schemaVersionString = getVersion().toString().replaceAll("\\.", "_");  //$NON-NLS-1$//$NON-NLS-2$
        pw.write("<?xml version=" + QUOTE + "1.0" + QUOTE + " encoding=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + QUOTE + "UTF-8" + QUOTE + "?>\n\n"); //$NON-NLS-1$ //$NON-NLS-2$
        pw.write("<faces-config\n"); //$NON-NLS-1$
        pw.write("    " + "xmlns=" + QUOTE //$NON-NLS-1$ //$NON-NLS-2$
                + "http://java.sun.com/xml/ns/javaee" + QUOTE + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
        pw.write("    " + "xmlns:xsi=" + QUOTE //$NON-NLS-1$ //$NON-NLS-2$
                + "http://www.w3.org/2001/XMLSchema-instance" + QUOTE //$NON-NLS-1$
                + "\n"); //$NON-NLS-1$
        pw.write("    " //$NON-NLS-1$
                + "xsi:schemaLocation=" //$NON-NLS-1$
                + QUOTE
                + String.format("http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facesconfig_%s.xsd", schemaVersionString) //$NON-NLS-1$
                + QUOTE + "\n"); //$NON-NLS-1$
        pw.write("    " + "version=" + QUOTE + getVersion().toString() + QUOTE + ">\n\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        pw.write("</faces-config>\n"); //$NON-NLS-1$
    }
	
    @Override
    public void updateWebApp(Object webApp, IDataModel config)
    {
    	// setup context params
        setupContextParams(webApp, config);
        
        //create or update servlet ref
        //Object servlet = findTapestryServlet(webApp);
        // check to see if already
        //servlet = createOrUpdateServletRef(webApp, config, servlet);
        
        Object filter = createOrUpdateFilterRef(webApp, config, null);
        // init mappings
        final List listOfMappings = getServletMappings(config);
        setUpURLFilterMappings(webApp, listOfMappings, filter);

    }

    
    @Override
    public void rollbackWebApp(Object webApp)
    {
        Object servlet = findTapestryServlet(webApp);
        if (servlet == null)
        {
            return;
        }
        // remove faces url mappings
        removeURLMappings(webApp, servlet);
        // remove context params
        removeJSFContextParams(webApp);
        // remove servlet
        removeJSFServlet(webApp, servlet);
    }
    

    @Override
    protected String getDefaultDefaultSuffix()
    {
        return DEFAULT_DEFAULT_MAPPING_SUFFIX;
    }
}
