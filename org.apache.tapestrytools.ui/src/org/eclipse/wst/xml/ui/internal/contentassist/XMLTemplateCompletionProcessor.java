/*******************************************************************************
 * Copyright (c) 2001, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/
package org.eclipse.wst.xml.ui.internal.contentassist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;
import org.eclipse.wst.xml.core.internal.contentmodel.tapestry.TapestryElementCollection;
import org.eclipse.wst.xml.ui.internal.XMLUIPlugin;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImageHelper;
import org.eclipse.wst.xml.ui.internal.editor.XMLEditorPluginImages;
import org.w3c.dom.Node;


/**
 * <p>Completion computer for XML templates</p>
 */
class XMLTemplateCompletionProcessor extends TemplateCompletionProcessor {
	//Represent Tapestry component when try to pop up attributes list in components
	private Node currentTapestryComponent = null;
	
	private static final class ProposalComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			return ((TemplateProposal) o2).getRelevance() - ((TemplateProposal) o1).getRelevance();
		}
	}

	private static final Comparator fgProposalComparator = new ProposalComparator();
	private String fContextTypeId = null;

	/*
	 * Copied from super class except instead of calling createContext(viewer,
	 * region) call createContext(viewer, region, offset) instead
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {

		ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();

		// adjust offset to end of normalized selection
		if (selection.getOffset() == offset) {
			offset = selection.getOffset() + selection.getLength();
		}

		String prefix = extractPrefix(viewer, offset);
		Region region = new Region(offset - prefix.length(), prefix.length());
		TemplateContext context = createContext(viewer, region, offset);
		if (context == null) {
			return new ICompletionProposal[0];
		}

		IndexedRegion treeNode = ContentAssistUtils.getNodeAt(viewer, offset);

		currentTapestryComponent = (Node) treeNode;
		while ((currentTapestryComponent != null) && (currentTapestryComponent.getNodeType() == Node.TEXT_NODE) && (currentTapestryComponent.getParentNode() != null)) {
			currentTapestryComponent = currentTapestryComponent.getParentNode();
		}

		// name of the selection variables {line, word}_selection
		context.setVariable("selection", selection.getText()); //$NON-NLS-1$
		
		System.out.println(">>>>> Get template list by context id:" + context.getContextType().getId() + "  selection:" + selection.getText());

		Template[] templates = getTemplates(context.getContextType().getId());

		List matches = new ArrayList();
		for (int i = 0; i < templates.length; i++) {
			Template template = templates[i];
			try {
				context.getContextType().validate(template.getPattern());
			}
			catch (TemplateException e) {
				continue;
			}
			if (template.matches(prefix, context.getContextType().getId())) {
			//if (template.matches(prefix, "tml_components")) {//NOTICE: this line is just test code
				System.out.println("PREFIX:" + prefix + "    context.getContextType().getId():" + context.getContextType().getId());
				matches.add(createProposal(template, context, (IRegion) region, getRelevance(template, prefix)));
			}
		}

		Collections.sort(matches, fgProposalComparator);

		return (ICompletionProposal[]) matches.toArray(new ICompletionProposal[matches.size()]);
	}

	/**
	 * Creates a concrete template context for the given region in the
	 * document. This involves finding out which context type is valid at the
	 * given location, and then creating a context of this type. The default
	 * implementation returns a <code>SmartReplaceTemplateContext</code> for
	 * the context type at the given location. This takes the offset at which
	 * content assist was invoked into consideration.
	 * 
	 * @param viewer
	 *            the viewer for which the context is created
	 * @param region
	 *            the region into <code>document</code> for which the
	 *            context is created
	 * @param offset
	 *            the original offset where content assist was invoked
	 * @return a template context that can handle template insertion at the
	 *         given location, or <code>null</code>
	 */
	private TemplateContext createContext(ITextViewer viewer, IRegion region, int offset) {
		// pretty much same code as super.createContext except create
		// SmartReplaceTemplateContext
		TemplateContextType contextType = getContextType(viewer, region);
		if (contextType != null) {
			IDocument document = viewer.getDocument();
			return new ReplaceNameTemplateContext(contextType, document, region.getOffset(), region.getLength(), offset);
		}
		return null;
	}

	protected ICompletionProposal createProposal(Template template, TemplateContext context, IRegion region, int relevance) {
		return new CustomTemplateProposal(template, context, region, getImage(template), relevance);
	}

	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		TemplateContextType type = null;

		ContextTypeRegistry registry = getTemplateContextRegistry();
		if (registry != null) {
			type = registry.getContextType(fContextTypeId);
		}

		return type;
	}

	protected Image getImage(Template template) {
		if(template.getContextTypeId().equals(TapestryElementCollection.componentsContextTypeId))
			return XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_TAPESTRY_DEFAULT);
		else if(template.getContextTypeId().equals(TapestryElementCollection.attributesContextTypeId))
			return XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_TAPESTRY_ATTRIBUTE);
		else 
			return XMLEditorPluginImageHelper.getInstance().getImage(XMLEditorPluginImages.IMG_OBJ_TAG_MACRO);
	}

	private ContextTypeRegistry getTemplateContextRegistry() {
		return XMLUIPlugin.getDefault().getTemplateContextRegistry();
	}

	/**
	 * TODO: 修改这个方法,从TapestryElementCollection中获取 Template[]
	 */
	protected Template[] getTemplates(String contextTypeId) {
		TapestryElementCollection collection = new TapestryElementCollection();
		
		if(contextTypeId.equals(TapestryElementCollection.componentsContextTypeId) ){
			Template[] tapestryTemplates = collection.getTemplateList(contextTypeId);
			return tapestryTemplates;
		}if(contextTypeId.equals(TapestryElementCollection.attributesContextTypeId)){
			Template[] tapestryTemplates = collection.getAttributeList(contextTypeId, currentTapestryComponent);
			return tapestryTemplates;
		}
		else{
			Template templates[] = null;
			TemplateStore store = getTemplateStore();
			if (store != null) {
				templates = store.getTemplates(contextTypeId);
			}

			return templates;
		}
	}

	private TemplateStore getTemplateStore() {
		return XMLUIPlugin.getDefault().getTemplateStore();
	}

	void setContextType(String contextTypeId) {
		fContextTypeId = contextTypeId;
	}

}
