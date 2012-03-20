/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gavingui2011@gmail.com Beijing China
 *******************************************************************************/
package org.eclipse.wst.xml.core.internal.contentmodel.tapestry;



import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMContent;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * Factory for element declarations of the Tapestry 5.
 */
public class TapestryElementCollection extends DeclCollection implements Tapestry5Namespace.ElementName {


	class TypePacket {
		public String name = null;
		public int content = CMElementDeclaration.EMPTY;
		public int omit = HTMLElementDeclaration.OMIT_NONE;
		public int lineBreak = HTMLElementDeclaration.BREAK_NONE;
		public int layout = HTMLElementDeclaration.LAYOUT_NONE;
		public int correct = HTMLElementDeclaration.CORRECT_NONE;
		public int format = HTMLElementDeclaration.FORMAT_XML;
		public boolean indentChild = false;

		public TypePacket() {
		}
	}

	/** Tapestry element declaration. */
	public class ElemDecl extends CMContentImpl implements HTMLElementDeclaration, HTMLPropertyDeclaration, Cloneable {
		private TypePacket type = null;
		private CMGroupImpl content = null;
		private CMNamedNodeMapImpl attributes = null;

		public ElemDecl(TypePacket t) {
			super(t.name, 1, 1);
			type = t;
		}

		public void setContent(CMGroupImpl group) {
			content = group;
		}

		public void setAttributes(CMNamedNodeMapImpl attrs) {
			attributes = attrs;
		}

		// implements CMNode
		public int getNodeType() {
			return CMNode.ELEMENT_DECLARATION;
		}

		public boolean supports(String propertyName) {
			if (propertyName.equals(HTMLCMProperties.SHOULD_IGNORE_CASE)) {
				return true;
			}
			else if (propertyName.equals(HTMLCMProperties.CONTENT_HINT)) {
				return true;
			}
			else {
				//PropertyProvider pp = PropertyProviderFactory.getProvider(propertyName);
				//if (pp == null)
					return false;
				//return pp.supports(this);
			}
		}

		public Object getProperty(String propertyName) {
			if (propertyName.equals(HTMLCMProperties.SHOULD_IGNORE_CASE)) {
				return Boolean.FALSE; //D208839
			}
			else {
				//PropertyProvider pp = PropertyProviderFactory.getProvider(propertyName);
				//if (pp == null)
					return null;
				//return pp.get(this);
			}
		}

		// implementes CMElementDeclaration
		public CMNamedNodeMap getAttributes() {
			return attributes;
		}

		public CMContent getContent() {
			return content;
		}

		public int getContentType() {
			return type.content;
		}

		public CMDataType getDataType() {
			return null;
		}

		public String getElementName() {
			return getNodeName();
		}

		public CMNamedNodeMap getLocalElements() {
			return null;
		}

		// implementes HTMLElementDeclaration
		public HTMLAttributeDeclaration getAttributeDeclaration(String attrName) {
			if (attributes == null)
				return null;
			return (HTMLAttributeDeclaration) attributes.getNamedItem(attrName);
		}

		public int getCorrectionType() {
			return type.correct;
		}

		public CMContent getExclusion() {
			return null;
		}

		public CMContent getInclusion() {
			return null;
		}

		public CMNamedNodeMap getProhibitedAncestors() {
			return EMPTY_MAP;
		}

		public int getFormatType() {
			return type.format;
		}

		public int getLayoutType() {
			return type.layout;
		}

		public int getLineBreakHint() {
			return type.lineBreak;
		}

		public int getOmitType() {
			return type.omit;
		}

		public boolean shouldTerminateAt(HTMLElementDeclaration dec) {
			return false;
		}

		public boolean shouldKeepSpaces() {
			return false;
		}

		public boolean shouldIndentChildSource() {
			return type.indentChild;
		}

		public boolean isJSP() {
			return true;
		}
		public Object clone(){ 
			ElemDecl o = null; 
			try{ 
				o = (ElemDecl)super.clone();
				o.setAttributes(null);
			}catch(CloneNotSupportedException e){ 
				e.printStackTrace(); 
			} 
			return o; 
		}  
	}

	// element IDs
	static class Ids {
		public static final int ID_ACTIONLINK = 0;
		public static final int ID_ADDROWLINK = 1;
		public static final int ID_ANY = 2;
		public static final int ID_BEANDISPLAY = 3;
		public static final int ID_BeanEditForm = 4;
		public static final int ID_BeanEditor = 5;
		public static final int ID_Checkbox = 6;
		public static final int ID_DateField = 7;
		public static final int ID_Delegate = 8;
		public static final int ID_Error = 9;
		public static final int ID_Errors = 10;
		public static final int ID_EventLink = 11;
		public static final int ID_ExceptionDisplay = 12;
		public static final int ID_Form = 13;
		public static final int ID_FormFragment = 14;
		public static final int ID_FormInjector = 15;
		public static final int ID_Grid = 16;
		public static final int ID_GridCell = 17;
		public static final int ID_GridColumns = 18;
		public static final int ID_GridPager = 19;
		public static final int ID_GridRows = 20;
		public static final int ID_Hidden = 21;
		public static final int ID_If = 22;
		public static final int ID_Label = 23;
		public static final int ID_LinkSubmit = 24;
		public static final int ID_Loop = 25;
		public static final int ID_Output = 26;
		public static final int ID_OutputRaw = 27;
		public static final int ID_PageLink = 28;
		public static final int ID_Palette = 29;
		public static final int ID_PasswordField = 30;
		public static final int ID_ProgressiveDisplay = 31;
		public static final int ID_PropertyDisplay = 32;
		public static final int ID_PropertyEditor = 33;
		public static final int ID_Radio = 34;
		public static final int ID_RadioGroup = 35;
		public static final int ID_Select = 36;
		public static final int ID_Submit = 37;
		public static final int ID_TextArea = 38;
		public static final int ID_TextField = 39;
		public static final int ID_TextOutput = 40;
		public static final int ID_Trigger = 41;
		public static final int ID_Unless = 42;
		public static final int ID_Zone = 43;
		
		public static int getNumOfIds() {
			if (numofids != -1)
				return numofids;

			// NOTE: If the reflection is too slow, this method should
			// just return the literal value, like 105.
			// -- 5/25/2001
			Class clazz = Ids.class;
			Field[] fields = clazz.getFields();
			numofids = 0;
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (name.startsWith("ID_"))//$NON-NLS-1$
					numofids++;
			}
			return numofids;
		}

		// chache the result of the reflection.
		private static int numofids = -1;
	}

	// attribute creater
	class JACreater implements Tapestry5Namespace {
		// attribute declaration
		class AttrDecl extends CMNodeImpl implements HTMLAttributeDeclaration {
			HTMLCMDataTypeImpl type = null;
			int usage = CMAttributeDeclaration.OPTIONAL;

			// methods
			public AttrDecl(String attrName) {
				super(attrName);
			}

			public String getAttrName() {
				return getNodeName();
			}

			public CMDataType getAttrType() {
				return type;
			}

			/** @deprecated by superclass */
			public String getDefaultValue() {
				if (type.getImpliedValueKind() != CMDataType.IMPLIED_VALUE_DEFAULT)
					return null;
				return type.getImpliedValue();
			}

			/** @deprecated  by superclass */
			public Enumeration getEnumAttr() {
				Vector v = new Vector(Arrays.asList(type.getEnumeratedValues()));
				return v.elements();
			}

			public int getNodeType() {
				return CMNode.ATTRIBUTE_DECLARATION;
			}

			public int getUsage() {
				return usage;
			}

			public boolean shouldIgnoreCase() {
				return false;
			}
		}

		CMNamedNodeMapImpl declarations = null;

		public JACreater() {
			declarations = new CMNamedNodeMapImpl();
		}
		public CMNamedNodeMapImpl getDeclarations(int eid) {
			switch (eid) {
				//case Ids.ID_DIRECTIVE_PAGE :
				//	createForDirPage();
				//	break;
				case Ids.ID_ACTIONLINK :
					createForActionLink();
					break;
				case Ids.ID_ADDROWLINK :
					createForAddRowLink();
					break;
				case Ids.ID_ANY :
					createForAny();
					break;
				case Ids.ID_BEANDISPLAY :
					createForBeanDisplay();
					break;
				case Ids.ID_BeanEditForm :
					createForBeanEditForm();
					break;
				case Ids.ID_BeanEditor :
					createForBeanEditor();
					break;
				case Ids.ID_Checkbox :
					createForCheckbox();
					break;
				case Ids.ID_DateField :
					createForDateField();
					break;
				case Ids.ID_Delegate :
					createForDelegate();
					break;
				case Ids.ID_Error :
					createForError();
					break;
				case Ids.ID_Errors:
					createForErrors();
					break;
				case Ids.ID_EventLink:
					createForEventLink();
					break;
				case Ids.ID_ExceptionDisplay:
					createForExceptionDisplay();
					break;
				case Ids.ID_Form:
					createForForm();
					break;
				case Ids.ID_FormFragment:
					createForFormFragment();
					break;
				case Ids.ID_FormInjector:
					createForFormInjector();
					break;
				case Ids.ID_Grid:
					createForGrid();
					break;
				case Ids.ID_GridCell:
					createForGridCell();
					break;
				case Ids.ID_GridColumns:
					createForGridColumns();
					break;
				case Ids.ID_GridPager:
					createForGridPager();
					break;
				case Ids.ID_GridRows:
					createForGridRows();
					break;
				case Ids.ID_Hidden:
					createForHidden();
					break;
				case Ids.ID_If:
					createForIf();
					break;
				case Ids.ID_Label:
					createForLabel();
					break;
				case Ids.ID_LinkSubmit:
					createForLinkSubmit();
					break;
				case Ids.ID_Loop:
					createForLoop();
					break;
				case Ids.ID_Output:
					createForOutput();
					break;
				case Ids.ID_OutputRaw:
					createForOutputRaw();
					break;
				case Ids.ID_PageLink:
					createForPageLink();
					break;
				case Ids.ID_Palette:
					createForPalette();
					break;
				case Ids.ID_PasswordField:
					createForPasswordField();
					break;
				case Ids.ID_ProgressiveDisplay:
					createForProgressiveDisplay();
					break;
				case Ids.ID_PropertyDisplay:
					createForPropertyDisplay();
					break;
				case Ids.ID_PropertyEditor:
					createForPropertyEditor();
					break;
				case Ids.ID_Radio:
					createForRadio();
					break;
				case Ids.ID_RadioGroup:
					createForRadioGroup();
					break;
				case Ids.ID_Select:
					createForSelect();
					break;
				case Ids.ID_Submit:
					createForSubmit();
					break;
				case Ids.ID_TextArea:
					createForTextArea();
					break;
				case Ids.ID_TextField:
					createForTextField();
					break;
				case Ids.ID_TextOutput:
					createForTextOutput();
					break;
				case Ids.ID_Trigger:
					createForTrigger();
					break;
				case Ids.ID_Unless:
					createForUnless();
					break;
				case Ids.ID_Zone:
					createForZone();
					break;
				default :
					// should warn.
					break;
			}
			return declarations;
		}

		private void createForActionLink() {
			// ("file" URI REQUIRED); Defect TORO:185241
			AttrDecl adec = new AttrDecl(ATTR_NAME_ANCHOR);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_ANCHOR, adec);
			
			adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			//adec.type.setImpliedValue(CMDataType.IMPLIED_VALUE_DEFAULT, ATTR_VALUE_TRUE);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}

		private void createForAddRowLink() {
		}

		
		private void createForAny() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);

			adec = new AttrDecl(ATTR_NAME_ELEMENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_ELEMENT, adec);
		}

		private void createForBeanDisplay() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_ADD);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.IDREF);
			declarations.putNamedItem(ATTR_NAME_ADD, adec);

			adec = new AttrDecl(ATTR_NAME_EXCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EXCLUDE, adec);

			adec = new AttrDecl(ATTR_NAME_INCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INCLUDE, adec);

			adec = new AttrDecl(ATTR_NAME_LEAN);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_LEAN, adec);

			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OBJECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_OBJECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
			
			adec = new AttrDecl(ATTR_NAME_REORDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_REORDER, adec);
		}

		private void createForBeanEditForm() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_ADD);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ADD, adec);

			adec = new AttrDecl(ATTR_NAME_AUTOFOCUS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_AUTOFOCUS, adec);
			
			adec = new AttrDecl(ATTR_NAME_CANCEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_CANCEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLIENTVALIDATION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTVALIDATION, adec);
			
			adec = new AttrDecl(ATTR_NAME_EXCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EXCLUDE, adec);

			adec = new AttrDecl(ATTR_NAME_INCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INCLUDE, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OBJECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_OBJECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_REORDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_REORDER, adec);
			
			adec = new AttrDecl(ATTR_NAME_SUBMITLABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_SUBMITLABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}

		private void createForBeanEditor() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_ADD);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ADD, adec);
			
			adec = new AttrDecl(ATTR_NAME_EXCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EXCLUDE, adec);

			adec = new AttrDecl(ATTR_NAME_INCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INCLUDE, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OBJECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_OBJECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
			
			adec = new AttrDecl(ATTR_NAME_REORDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_REORDER, adec);
		}

		private void createForCheckbox() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_LABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_LABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}

		private void createForDateField() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);

			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);

			adec = new AttrDecl(ATTR_NAME_FORMAT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_FORMAT, adec);

			adec = new AttrDecl(ATTR_NAME_HIDETEXTFIELD);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_HIDETEXTFIELD, adec);

			adec = new AttrDecl(ATTR_NAME_ICON);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ICON, adec);

			adec = new AttrDecl(ATTR_NAME_LABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_LABEL, adec);

			adec = new AttrDecl(ATTR_NAME_MESSAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MESSAGE, adec);

			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);

			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}

		private void createForDelegate() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_TO);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_TO, adec);
		}

		private void createForError() {
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLASS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLASS, adec);
			
			adec = new AttrDecl(ATTR_NAME_FOR);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_FOR, adec);
		}
		private void createForErrors(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_BANNER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_BANNER, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLASS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLASS, adec);
		}
		private void createForEventLink(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ANCHOR);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_ANCHOR, adec);
			
			adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_EVENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_EVENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.URI);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}
		private void createForExceptionDisplay(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_EXCEPTION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			adec.usage = CMAttributeDeclaration.REQUIRED;
			declarations.putNamedItem(ATTR_NAME_EXCEPTION, adec);
		}
		private void createForForm(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_AUTOFOCUS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_AUTOFOCUS, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLIENTVALIDATION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTVALIDATION, adec);
			
			adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_SECURE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_SECURE, adec);
			
			adec = new AttrDecl(ATTR_NAME_TRACKER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_TRACKER, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATIONID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_VALIDATIONID, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}
		private void createForFormFragment(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ALWAYSSUBMIT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_ALWAYSSUBMIT, adec);
			
			adec = new AttrDecl(ATTR_NAME_ELEMENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ELEMENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_HIDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_HIDE, adec);
			
			adec = new AttrDecl(ATTR_NAME_ID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ID, adec);
			
			adec = new AttrDecl(ATTR_NAME_SHOW);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_SHOW, adec);
			
			adec = new AttrDecl(ATTR_NAME_VISIBLE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VISIBLE, adec);
		}
		private void createForFormInjector(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_ELEMENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_ELEMENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_POSITION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_POSITION, adec);
			
			adec = new AttrDecl(ATTR_NAME_SHOW);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENTITIES);
			declarations.putNamedItem(ATTR_NAME_SHOW, adec);
		}
		private void createForGrid(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ADD);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ADD, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLASS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLASS, adec);
			
			adec = new AttrDecl(ATTR_NAME_COLUMNINDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_COLUMNINDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_EMPTY);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EMPTY, adec);
			
			adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_EXCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EXCLUDE, adec);
			
			adec = new AttrDecl(ATTR_NAME_INPLACE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INPLACE, adec);
			
			adec = new AttrDecl(ATTR_NAME_INCLUDE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INCLUDE, adec);
			
			adec = new AttrDecl(ATTR_NAME_LEAN);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_LEAN, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
			
			adec = new AttrDecl(ATTR_NAME_PAGERPOSITION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_PAGERPOSITION, adec);
			
			adec = new AttrDecl(ATTR_NAME_REORDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_REORDER, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROW);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROW, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWCLASS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWCLASS, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWINDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWINDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWSPERPAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWSPERPAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_SORTMODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SORTMODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_SOURCE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SOURCE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VOLATILE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VOLATILE, adec);
		}
		private void createForGridCell(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_BEANBLOCKSOURCE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_BEANBLOCKSOURCE, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OBJECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OBJECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
		}
		private void createForGridColumns(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_GRIDMODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_GRIDMODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_INDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_LEAN);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_LEAN, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}
		private void createForGridPager(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CURRENTPAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CURRENTPAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_RANGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_RANGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWSPERPAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWSPERPAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_SOURCE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SOURCE, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}
		private void createForGridRows(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_COLUMNINDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_COLUMNINDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_CURRENTPAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CURRENTPAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_GRIDMODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_GRIDMODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_LEAN);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_LEAN, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROW);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROW, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWCLASS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWCLASS, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWINDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWINDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_ROWSPERPAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ROWSPERPAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VOLATILE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VOLATILE, adec);
		}
		private void createForHidden(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForIf(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_NEGATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_NEGATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_TEST);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_TEST, adec);
		}
		private void createForLabel(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_FOR);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_FOR, adec);
			
			adec = new AttrDecl(ATTR_NAME_IGNOREBODY);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_IGNOREBODY, adec);
		}
		private void createForLinkSubmit(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_DEFER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DEFER, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_EVENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EVENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODE, adec);
		}
		private void createForLoop(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ELEMENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ELEMENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_EMPTY);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EMPTY, adec);
			
			adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_FORMSTATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_FORMSTATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_INDEX);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_INDEX, adec);
			
			adec = new AttrDecl(ATTR_NAME_SOURCE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SOURCE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VOLATILE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VOLATILE, adec);
		}
		private void createForOutput(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ELEMENTNAME);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ELEMENTNAME, adec);
			
			adec = new AttrDecl(ATTR_NAME_FILTER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_FILTER, adec);
			
			adec = new AttrDecl(ATTR_NAME_FORMAT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_FORMAT, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForOutputRaw(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForPageLink(){
			createForActionLink();
		}
		private void createForPalette(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);
			
			adec = new AttrDecl(ATTR_NAME_DESELECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_DESELECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_LABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_LABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_MOVEDOWN);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MOVEDOWN, adec);
			
			adec = new AttrDecl(ATTR_NAME_MOVEUP);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MOVEUP, adec);
			
			adec = new AttrDecl(ATTR_NAME_REORDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_REORDER, adec);
			
			adec = new AttrDecl(ATTR_NAME_SELECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SELECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_SELECTED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SELECTED, adec);
			
			adec = new AttrDecl(ATTR_NAME_SIZE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SIZE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);
		}
		private void createForPasswordField(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ANNOTATIONPROVIDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ANNOTATIONPROVIDER, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_LABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_LABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_NULLS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_NULLS, adec);
			
			adec = new AttrDecl(ATTR_NAME_TRANSLATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_TRANSLATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForProgressiveDisplay(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_UPDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_UPDATE, adec);
		}
		private void createForPropertyDisplay(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_BEANBLOCKSOURCE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_BEANBLOCKSOURCE, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_OBJECT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OBJECT, adec);
			
			adec = new AttrDecl(ATTR_NAME_OVERRIDES);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_OVERRIDES, adec);
		}
		private void createForPropertyEditor(){
			createForPropertyDisplay();
			
			AttrDecl adec = new AttrDecl(ATTR_NAME_PROPERTY);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_PROPERTY, adec);
		}
		private void createForRadio(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_LABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_LABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForRadioGroup(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);
			createForRadio();
			
			adec = new AttrDecl(ATTR_NAME_ENCODER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ENCODER, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);
		}
		private void createForSelect(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_BLANKLABEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_BLANKLABEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_BLANKOPTION);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_BLANKOPTION, adec);
			createForRadioGroup();
			
			adec = new AttrDecl(ATTR_NAME_MODEL);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODEL, adec);
			
			adec = new AttrDecl(ATTR_NAME_ZONE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ZONE, adec);
		}
		private void createForSubmit(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_CONTEXT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CONTEXT, adec);
			
			adec = new AttrDecl(ATTR_NAME_DEFER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DEFER, adec);
			
			adec = new AttrDecl(ATTR_NAME_DISABLED);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_DISABLED, adec);
			
			adec = new AttrDecl(ATTR_NAME_EVENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EVENT, adec);
			
			adec = new AttrDecl(ATTR_NAME_IMAGE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_IMAGE, adec);
			
			adec = new AttrDecl(ATTR_NAME_MODE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_MODE, adec);
		}
		private void createForTextArea(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ANNOTATIONPROVIDER);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ANNOTATIONPROVIDER, adec);
			
			adec = new AttrDecl(ATTR_NAME_CLIENTID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_CLIENTID, adec);
			createForRadio();
			
			adec = new AttrDecl(ATTR_NAME_NULLS);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_NULLS, adec);
			
			adec = new AttrDecl(ATTR_NAME_TRANSLATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_TRANSLATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VALIDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALIDATE, adec);
		}
		private void createForTextField(){
			createForTextArea();
		}
		private void createForTextOutput(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_VALUE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_VALUE, adec);
		}
		private void createForTrigger(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_EVENT);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_EVENT, adec);
		}
		private void createForUnless(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ELSE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ELSE, adec);
			
			adec = new AttrDecl(ATTR_NAME_TEST);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_TEST, adec);
		}
		private void createForZone(){
			AttrDecl adec = new AttrDecl(ATTR_NAME_ELEMENTNAME);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ELEMENTNAME, adec);
			
			adec = new AttrDecl(ATTR_NAME_ID);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_ID, adec);
			
			adec = new AttrDecl(ATTR_NAME_SHOW);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_SHOW, adec);
			
			adec = new AttrDecl(ATTR_NAME_UPDATE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.CDATA);
			declarations.putNamedItem(ATTR_NAME_UPDATE, adec);
			
			adec = new AttrDecl(ATTR_NAME_VISIBLE);
			adec.type = new HTMLCMDataTypeImpl(CMDataType.ENUM);
			String[] values = {ATTR_VALUE_TRUE, ATTR_VALUE_FALSE};
			adec.type.setEnumValues(values);
			declarations.putNamedItem(ATTR_NAME_VISIBLE, adec);
		}
	}

	private final static CMNamedNodeMap EMPTY_MAP = new CMNamedNodeMap() {
		public int getLength() {
			return 0;
		}

		public CMNode getNamedItem(String name) {
			return null;
		}

		public CMNode item(int index) {
			return null;
		}

		public Iterator iterator() {
			return new Iterator() {
				public boolean hasNext() {
					return false;
				}

				public Object next() {
					return null;
				}

				public void remove() {
				}
			};
		}
	};
	private static String[] names = null;

	static {
		names = new String[Ids.getNumOfIds()];
		names[Ids.ID_ACTIONLINK] = ACTIONLINK;
		names[Ids.ID_ADDROWLINK] = ADDROWLINK;
		names[Ids.ID_ANY] = ANY;
		names[Ids.ID_BEANDISPLAY] = BEANDISPLAY;
		names[Ids.ID_BeanEditForm] = BeanEditForm;
		names[Ids.ID_BeanEditor] = BeanEditor;
		names[Ids.ID_Checkbox] = Checkbox;
		names[Ids.ID_DateField] = DateField;
		names[Ids.ID_Delegate] = Delegate;
		names[Ids.ID_Error] = Error;
		names[Ids.ID_Errors] = Errors;
		names[Ids.ID_EventLink] = EventLink;
		names[Ids.ID_ExceptionDisplay] = ExceptionDisplay;
		names[Ids.ID_Form] = Form;
		names[Ids.ID_FormFragment] = FormFragment;
		names[Ids.ID_FormInjector] = FormInjector;
		names[Ids.ID_Grid] = Grid;
		names[Ids.ID_GridCell] = GridCell;
		names[Ids.ID_GridColumns] = GridColumns;
		names[Ids.ID_GridPager] = GridPager;
		names[Ids.ID_GridRows] = GridRows;
		names[Ids.ID_Hidden] = Hidden;
		names[Ids.ID_If] = IfElement;
		names[Ids.ID_Label] = Label;
		names[Ids.ID_LinkSubmit] = LinkSubmit;
		names[Ids.ID_Loop] = Loop;
		names[Ids.ID_Output] = Output;
		names[Ids.ID_OutputRaw] = OutputRaw;
		names[Ids.ID_PageLink] = PageLink;
		names[Ids.ID_Palette] = Palette;
		names[Ids.ID_PasswordField] = PasswordField;
		names[Ids.ID_ProgressiveDisplay] = ProgressiveDisplay;
		names[Ids.ID_PropertyDisplay] = PropertyDisplay;
		names[Ids.ID_PropertyEditor] = PropertyEditor;
		names[Ids.ID_Radio] = Radio;
		names[Ids.ID_RadioGroup] = RadioGroup;
		names[Ids.ID_Select] = Select;
		names[Ids.ID_Submit] = Submit;
		names[Ids.ID_TextArea] = TextArea;
		names[Ids.ID_TextField] = TextField;
		names[Ids.ID_TextOutput] = TextOutput;
		names[Ids.ID_Trigger] = Trigger;
		names[Ids.ID_Unless] = Unless;
		names[Ids.ID_Zone] = Zone;
	}

	TapestryElementCollection(String[] names, boolean tolerant) {
		super(names, tolerant);
	}

	/**
	 */
	public TapestryElementCollection() {
		super(names, TOLERANT_CASE);
	}

	/**
	 * @return org.eclipse.wst.xml.core.internal.contentmodel.CMNode
	 * @param elementName java.lang.String
	 */
	protected CMNode create(String elementName) {
		return createElemDecl(getID(elementName));
	}

	/**
	 * @param eid int
	 */
	CMGroupImpl createContent(int eid) {
		if (eid == ID_UNKNOWN)
			return null;

		CMGroupImpl content = null;

		switch (eid) {
			//case Ids.ID_ACTIONLINK :
				
				//content = new CMGroupImpl(CMGroup.SEQUENCE, 0, CMContentImpl.UNBOUNDED);
//				child = item(Ids.ID_PARAM);
//				if (child != null)
//					content.appendChild(child);
				//break;
			/*case Ids.ID_PLUGIN :
				// (jsp:params | jsp:fallback)?
				content = new CMGroupImpl(CMGroup.CHOICE, 0, 1);
				// jsp:params
				child = item(Ids.ID_PARAMS);
				if (child != null)
					content.appendChild(child);
				// jsp:fallback
				child = item(Ids.ID_FALLBACK);
				if (child != null)
					content.appendChild(child);
				break;
			case Ids.ID_PARAMS :
				// (jsp:param)+
				content = new CMGroupImpl(CMGroup.SEQUENCE, 1, CMContentImpl.UNBOUNDED);
				child = item(Ids.ID_PARAM);
				if (child != null)
					content.appendChild(child);
				break;
			case Ids.ID_ROOT :
				// %Body;
				// --> (jsp:text|%Directives;|%Scripts;|%Actions;)*
				//     %Directives --> jsp:directive.page|jsp:directive.include
				//     %Scripts; --> jsp:scriptlet|jsp:declaration|jsp:expression
				//     %Actions --> jsp:useBean|jsp.setProperty|jsp:getProperty
				//                  |jsp:include|jsp:forward|jsp:plugin
				content = new CMGroupImpl(CMGroup.CHOICE, 0, CMContentImpl.UNBOUNDED);
				int validChildren[] = {Ids.ID_TEXT,
				// %Directves;
							Ids.ID_DIRECTIVE_PAGE, Ids.ID_DIRECTIVE_INCLUDE,
							// %Scripts;
							Ids.ID_SCRIPTLET, Ids.ID_DECLARATION, Ids.ID_EXPRESSION,
							// %Actions;
							Ids.ID_USEBEAN, Ids.ID_SETPROPERTY, Ids.ID_GETPROPERTY, Ids.ID_INCLUDE, Ids.ID_FORWARD, Ids.ID_PLUGIN};
				for (int i = 0; i < validChildren.length; i++) {
					child = item(validChildren[i]);
					if (child != null)
						content.appendChild(child);
				}
				break;*/
		}

		return content;
	}

	/**
	 * @param eid int
	 */
	HTMLElementDeclaration createElemDecl(int eid) {
		if (eid == ID_UNKNOWN)
			return null;

		TypePacket packet = new TypePacket();
		switch (eid) {
			case Ids.ID_ACTIONLINK :
				packet.name = ACTIONLINK;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_TAPESTRY_ACTIONLINK;
				break;
			case Ids.ID_ADDROWLINK :
				packet.name = ADDROWLINK;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_SCRIPT;
				break;
			case Ids.ID_ANY :
				packet.name = ANY;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_SCRIPT;
				break;
			case Ids.ID_BEANDISPLAY :
				packet.name = BEANDISPLAY;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_DIRECTIVE;
				break;
			case Ids.ID_BeanEditForm :
				packet.name = BeanEditForm;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_SCRIPT;
				break;
			case Ids.ID_BeanEditor :
				packet.name = BeanEditor;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_DIRECTIVE;
				break;
			case Ids.ID_Checkbox :
				packet.name = Checkbox;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_DIRECTIVE;
				break;
			case Ids.ID_DateField :
				packet.name = DateField;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Delegate :
				packet.name = Delegate;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_Error :
				packet.name = Error;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_Errors:
				packet.name = Errors;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				packet.format = HTMLElementDeclaration.FORMAT_JSP_SCRIPT;
				break;
			case Ids.ID_EventLink :
				packet.name = EventLink;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_ExceptionDisplay :
				packet.name = ExceptionDisplay;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_Form :
				packet.name = Form;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_FormFragment :
				packet.name = FormFragment;
				packet.content = CMElementDeclaration.CDATA;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_FormInjector :
				packet.name = FormInjector;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Grid :
				packet.name = Grid;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_GridCell:
				packet.name = GridCell;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_GridColumns :
				packet.name = GridColumns;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_GridPager:
				packet.name = GridPager;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_GridRows:
				packet.name = GridRows;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Hidden:
				packet.name = Hidden;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_OBJECT;
				break;
			case Ids.ID_If:
				packet.name = IfElement;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Label:
				packet.name = Label;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_LinkSubmit:
				packet.name = LinkSubmit;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Loop:
				packet.name = Loop;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Output:
				packet.name = Output;
				packet.content = CMElementDeclaration.ELEMENT;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_OutputRaw:
				packet.name = OutputRaw;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_PageLink:
				packet.name = PageLink;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Palette:
				packet.name = Palette;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_PasswordField:
				packet.name = PasswordField;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_ProgressiveDisplay:
				packet.name = ProgressiveDisplay;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_PropertyDisplay:
				packet.name = PropertyDisplay;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_PropertyEditor:
				packet.name = PropertyEditor;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Radio:
				packet.name = Radio;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_RadioGroup:
				packet.name = RadioGroup;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Select:
				packet.name = Select;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Submit:
				packet.name = Submit;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_TextArea:
				packet.name = TextArea;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_TextField:
				packet.name = TextField;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_TextOutput:
				packet.name = TextOutput;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Trigger:
				packet.name = Trigger;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Unless:
				packet.name = Unless;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			case Ids.ID_Zone:
				packet.name = Zone;
				packet.omit = HTMLElementDeclaration.OMIT_END;
				packet.layout = HTMLElementDeclaration.LAYOUT_HIDDEN;
				break;
			default :
				// unknown ID
				return null;
		}

		ElemDecl dec = new ElemDecl(packet);

		CMGroupImpl content = createContent(eid);
		if (content != null)
			dec.setContent(content);

		JACreater creater = new JACreater();
		dec.setAttributes(creater.getDeclarations(eid));

		return dec;
	}
}
