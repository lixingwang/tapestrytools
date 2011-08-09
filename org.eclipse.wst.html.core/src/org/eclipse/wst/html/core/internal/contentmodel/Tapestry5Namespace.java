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
		public static final String ACTIONLINK = "t:actionlink";
		public static final String ADDROWLINK = "t:addrowlink";
		public static final String ANY = "t:any";
		public static final String BEANDISPLAY = "t:beandisplay";
		public static final String BeanEditForm = "t:beaneditform";
		public static final String BeanEditor = "t:beaneditor";
		public static final String Checkbox = "t:checkbox";
		public static final String DateField = "t:datefield";
		public static final String Delegate = "t:delegate";
		public static final String Error = "t:error";
		public static final String Errors = "t:errors";
		public static final String EventLink = "t:eventlink";
		public static final String ExceptionDisplay = "t:exceptiondisplay";
		public static final String Form = "t:form";
		public static final String FormFragment = "t:formfragment";
		public static final String FormInjector = "t:forminjector";
		public static final String Grid = "t:grid";
		public static final String GridCell = "t:gridcell";
		public static final String GridColumns = "t:gridcolumns";
		public static final String GridPager = "t:gridpager";
		public static final String GridRows = "t:gridrows";
		public static final String Hidden = "t:hidden";
		public static final String IfElement = "t:if";
		public static final String Label = "t:label";
		public static final String LinkSubmit = "t:linksubmit";
		public static final String Loop = "t:loop";
		public static final String Output = "t:output";
		public static final String OutputRaw = "t:outputraw";
		public static final String PageLink = "t:pagelink";
		public static final String Palette = "t:palette";
		public static final String PasswordField = "t:passwordfield";
		public static final String ProgressiveDisplay = "t:progressivedisplay";
		public static final String PropertyDisplay = "t:propertydisplay";
		public static final String PropertyEditor = "t:propertyeditor";
		public static final String Radio = "t:radio";
		public static final String RadioGroup = "t:radiogroup";
		public static final String Select = "t:select";
		public static final String Submit = "t:submit";
		public static final String TextArea = "t:textarea";
		public static final String TextField = "t:textfield";
		public static final String TextOutput = "t:textoutput";
		public static final String Trigger = "t:trigger";
		public static final String Unless = "t:unless";
		public static final String Zone = "t:zone";
	}

	public static final String Tapestry_URI = ""; //$NON-NLS-1$
	public static final String Tapestry_TAG_PREFIX = "t"; //$NON-NLS-1$
	
	// actionlink attributes 
	public static final String ATTR_NAME_ANCHOR = "anchor";
	public static final String ATTR_NAME_CONTEXT = "context";
	public static final String ATTR_NAME_DISABLED = "disabled";
	public static final String ATTR_NAME_ZONE = "zone";
	
	//any attributes
	public static final String ATTR_NAME_CLIENTID = "clientId";
	public static final String ATTR_NAME_ELEMENT = "element";
	
	//beandisplay attributes
	public static final String ATTR_NAME_ADD = "add";
	public static final String ATTR_NAME_EXCLUDE = "exclude";
	public static final String ATTR_NAME_INCLUDE = "include";
	public static final String ATTR_NAME_LEAN = "lean";
	public static final String ATTR_NAME_MODEL = "model";
	public static final String ATTR_NAME_OBJECT = "object";
	public static final String ATTR_NAME_OVERRIDES = "overrides";
	public static final String ATTR_NAME_REORDER = "reorder";
	//BeanEditForm attributes
	public static final String ATTR_NAME_AUTOFOCUS = "autofocus";
	public static final String ATTR_NAME_CANCEL = "cancel";
	public static final String ATTR_NAME_CLIENTVALIDATION = "clientValidation";
	public static final String ATTR_NAME_SUBMITLABEL = "submitLabel";
	//Checkbox attributes
	public static final String ATTR_NAME_LABEL = "label";
	public static final String ATTR_NAME_VALUE = "value";
	//DateField attributes
	public static final String ATTR_NAME_FORMAT = "format";
	public static final String ATTR_NAME_HIDETEXTFIELD = "hideTextField";
	public static final String ATTR_NAME_ICON = "icon";
	public static final String ATTR_NAME_MESSAGE = "message";
	public static final String ATTR_NAME_VALIDATE = "validate";
	//Delegate attributes
	public static final String ATTR_NAME_TO = "to";
	//Error attributes
	public static final String ATTR_NAME_CLASS = "class";
	public static final String ATTR_NAME_FOR = "for";
	//Errors attributes
	public static final String ATTR_NAME_BANNER = "banner";
	//EventLink attributes
	public static final String ATTR_NAME_EVENT = "event";
	public static final String ATTR_NAME_EXCEPTION = "exception";
	//Form attributes
	public static final String ATTR_NAME_SECURE = "secure";
	public static final String ATTR_NAME_TRACKER = "tracker";
	public static final String ATTR_NAME_VALIDATIONID = "validationId";
	//FormFragment attributes
	public static final String ATTR_NAME_ALWAYSSUBMIT = "alwaysSubmit";
	public static final String ATTR_NAME_HIDE = "hide";
	public static final String ATTR_NAME_ID = "id";
	public static final String ATTR_NAME_SHOW = "show";
	public static final String ATTR_NAME_VISIBLE = "visible";
	//FormInjector attributes
	public static final String ATTR_NAME_POSITION = "position";
	//Grid attributes
	public static final String ATTR_NAME_COLUMNINDEX = "columnIndex";
	public static final String ATTR_NAME_EMPTY = "empty";
	public static final String ATTR_NAME_ENCODER = "encoder";
	public static final String ATTR_NAME_INPLACE = "inPlace";
	public static final String ATTR_NAME_PAGERPOSITION = "pagerPosition";
	public static final String ATTR_NAME_ROW = "row";
	public static final String ATTR_NAME_ROWCLASS = "rowClass";
	public static final String ATTR_NAME_ROWINDEX = "rowIndex";
	public static final String ATTR_NAME_ROWSPERPAGE = "rowsPerPage";
	public static final String ATTR_NAME_SORTMODEL = "sortModel";
	public static final String ATTR_NAME_SOURCE = "source";
	public static final String ATTR_NAME_VOLATILE = "volatile";
	//GridCell attributes
	public static final String ATTR_NAME_BEANBLOCKSOURCE = "beanBlockSource";
	public static final String ATTR_NAME_GRIDMODEL = "gridModel";
	//GridColumns attributes
	public static final String ATTR_NAME_INDEX = "index";
	//GridPager attributes
	public static final String ATTR_NAME_CURRENTPAGE = "currentPage";
	public static final String ATTR_NAME_RANGE = "range";
	//If attributes
	public static final String ATTR_NAME_NEGATE = "negate";
	public static final String ATTR_NAME_TEST = "test";
	public static final String ATTR_NAME_IGNOREBODY = "ignoreBody";
	//LinkSubmit attributes
	public static final String ATTR_NAME_DEFER = "defer";
	public static final String ATTR_NAME_MODE = "mode";
	//Loop attributes
	public static final String ATTR_NAME_FORMSTATE = "formState";
	public static final String ATTR_NAME_ELEMENTNAME = "elementName";
	public static final String ATTR_NAME_FILTER = "filter";
	//Palette attributes
	public static final String ATTR_NAME_DESELECT = "deselect";
	public static final String ATTR_NAME_MOVEDOWN = "moveDown";
	public static final String ATTR_NAME_MOVEUP = "moveUp";
	public static final String ATTR_NAME_SELECT = "select";
	public static final String ATTR_NAME_SELECTED = "selected";
	public static final String ATTR_NAME_SIZE = "size";
	
	public static final String ATTR_NAME_ANNOTATIONPROVIDER= "annotationProvider";
	public static final String ATTR_NAME_NULLS = "nulls";
	public static final String ATTR_NAME_TRANSLATE = "translate";
	
	public static final String ATTR_NAME_UPDATE = "update";
	public static final String ATTR_NAME_PROPERTY = "property";
	public static final String ATTR_NAME_BLANKLABEL = "blankLabel";
	public static final String ATTR_NAME_BLANKOPTION = "blankOption";
	public static final String ATTR_NAME_IMAGE = "image";
	
	public static final String ATTR_NAME_ELSE = "else";
	
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
