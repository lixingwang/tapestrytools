/*******************************************************************************
 *
 * Contributors:
 *     Gavin Lei @ Beijing China    gavingui2011@gmail.com
 *     
 *******************************************************************************/
package org.eclipse.wst.html.core.internal.contentmodel;


/**
 * Tapestry 5 Namespace. 
 */
public interface Tapestry5Namespace {

	public static interface ElementName {
		// Element names
		public static final String ACTIONLINK = "t:actionlink"; //$NON-NLS-1$

	}

	public static final String Tapestry_URI = ""; //$NON-NLS-1$
	public static final String Tapestry_TAG_PREFIX = "t"; //$NON-NLS-1$
	// attribute names
	//   directive.page
	public static final String ATTR_NAME_LANGUAGE = "language"; //$NON-NLS-1$
	public static final String ATTR_NAME_EXTENDS = "extends"; //$NON-NLS-1$
	public static final String ATTR_NAME_CONTENT_TYPE = "contentType"; //$NON-NLS-1$
	public static final String ATTR_NAME_IMPORT = "import"; //$NON-NLS-1$
	public static final String ATTR_NAME_SESSION = "session"; //$NON-NLS-1$
	public static final String ATTR_NAME_BUFFER = "buffer"; //$NON-NLS-1$
	public static final String ATTR_NAME_AUTOFLUSH = "autoFlush"; //$NON-NLS-1$
	public static final String ATTR_NAME_IS_THREAD_SAFE = "isThreadSafe"; //$NON-NLS-1$
	public static final String ATTR_NAME_INFO = "info"; //$NON-NLS-1$
	public static final String ATTR_NAME_ERROR_PAGE = "errorPage"; //$NON-NLS-1$
	public static final String ATTR_NAME_IS_ERROR_PAGE = "isErrorPage"; //$NON-NLS-1$
	public static final String ATTR_NAME_PAGE_ENCODING = "pageEncoding"; //$NON-NLS-1$
	//   directive.include
	public static final String ATTR_NAME_FILE = "file"; //$NON-NLS-1$
	//   directive.taglib
	public static final String ATTR_NAME_URI = "uri"; //$NON-NLS-1$
	public static final String ATTR_NAME_PREFIX = "prefix"; //$NON-NLS-1$
	//   useBean
	public static final String ATTR_NAME_ID = "id"; //$NON-NLS-1$
	public static final String ATTR_NAME_SCOPE = "scope"; //$NON-NLS-1$
	public static final String ATTR_NAME_CLASS = "class"; //$NON-NLS-1$
	public static final String ATTR_NAME_BEAN_NAME = "beanName"; //$NON-NLS-1$
	public static final String ATTR_NAME_TYPE = "type"; //$NON-NLS-1$
	//   setProperty
	public static final String ATTR_NAME_NAME = "name"; //$NON-NLS-1$
	public static final String ATTR_NAME_PROPERTY = "property"; //$NON-NLS-1$
	public static final String ATTR_NAME_VALUE = "value"; //$NON-NLS-1$
	public static final String ATTR_NAME_PARAM = "param"; //$NON-NLS-1$
	//   include
	public static final String ATTR_NAME_PAGE = "page"; //$NON-NLS-1$
	public static final String ATTR_NAME_FLUSH = "flush"; //$NON-NLS-1$
	//   plugin
	public static final String ATTR_NAME_CODE = "code"; //$NON-NLS-1$
	public static final String ATTR_NAME_CODEBASE = "codebase"; //$NON-NLS-1$
	public static final String ATTR_NAME_ALIGN = "align"; //$NON-NLS-1$
	public static final String ATTR_NAME_ARCHIVE = "archive"; //$NON-NLS-1$
	public static final String ATTR_NAME_HEIGHT = "height"; //$NON-NLS-1$
	public static final String ATTR_NAME_HSPACE = "hspace"; //$NON-NLS-1$
	public static final String ATTR_NAME_JREVERSION = "jreversion"; //$NON-NLS-1$
	public static final String ATTR_NAME_VSPACE = "vspace"; //$NON-NLS-1$
	public static final String ATTR_NAME_WIDTH = "width"; //$NON-NLS-1$
	public static final String ATTR_NAME_NSPLUGINURL = "nspluginurl"; //$NON-NLS-1$
	public static final String ATTR_NAME_IEPLUGINURL = "iepluginurl"; //$NON-NLS-1$
	//   root
	public static final String ATTR_NAME_XMLNS_Tapestry = "xmlns:Tapestry"; //$NON-NLS-1$
	public static final String ATTR_NAME_VERSION = "version"; //$NON-NLS-1$
	// attribute values
	public static final String ATTR_VALUE_TRUE = "true"; //$NON-NLS-1$
	public static final String ATTR_VALUE_FALSE = "false"; //$NON-NLS-1$
	public static final String ATTR_VALUE_JAVA = "java"; //$NON-NLS-1$
	public static final String ATTR_VALUE_CT_DEFAULT = "text/html; charset=ISO-8859-1";//D195366 //$NON-NLS-1$
	public static final String ATTR_VALUE_BUFSIZ_DEFAULT = "8kb"; //$NON-NLS-1$
	public static final String ATTR_VALUE_PAGE = "page"; //$NON-NLS-1$
	public static final String ATTR_VALUE_SESSION = "session"; //$NON-NLS-1$
	public static final String ATTR_VALUE_REQUEST = "request"; //$NON-NLS-1$
	public static final String ATTR_VALUE_APPLICATION = "application"; //$NON-NLS-1$
	public static final String ATTR_VALUE_BEAN = "bean"; //$NON-NLS-1$
	public static final String ATTR_VALUE_APPLET = "applet"; //$NON-NLS-1$
	public static final String ATTR_VALUE_TOP = "top"; //$NON-NLS-1$
	public static final String ATTR_VALUE_MIDDLE = "middle"; //$NON-NLS-1$
	public static final String ATTR_VALUE_BOTTOM = "bottom"; //$NON-NLS-1$
	public static final String ATTR_VALUE_LEFT = "left"; //$NON-NLS-1$
	public static final String ATTR_VALUE_RIGHT = "right"; //$NON-NLS-1$
	public static final String ATTR_VALUE_JVER11 = "1.1"; //$NON-NLS-1$
	public static final String ATTR_VALUE_XMLNS_Tapestry = "http://tapestry.apache.org"; //$NON-NLS-1$
}
