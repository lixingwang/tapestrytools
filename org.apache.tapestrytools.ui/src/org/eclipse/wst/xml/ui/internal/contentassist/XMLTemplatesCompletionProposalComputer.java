/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *     Gavin Lei (gavingui2011@gmail.com) - Tapestry features implemention
 *******************************************************************************/
package org.eclipse.wst.xml.ui.internal.contentassist;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.contentassist.tapestry.TapestryELCompletionProposalComputer;
import org.eclipse.wst.xml.ui.internal.templates.TemplateContextTypeIdsXML;

/**
 * <p>Proposal computer used to computer XML template content assist
 * proposals</p>
 */
public class XMLTemplatesCompletionProposalComputer extends
		DefaultXMLCompletionProposalComputer {

	/** <p>The template processor used to create the proposals</p> */
	private XMLTemplateCompletionProcessor fTemplateProcessor = null;

	private TapestryELCompletionProposalComputer tapestryELProposalComputer = null;
	/**
	 * Create the computer
	 */
	public XMLTemplatesCompletionProposalComputer() {
		super();
		fTemplateProcessor = new XMLTemplateCompletionProcessor();
		tapestryELProposalComputer = new TapestryELCompletionProposalComputer();
	}

	/**
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.DefaultXMLCompletionProposalComputer#addAttributeNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addAttributeNameProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAPESTRY_COMPONENTS_ATTR, context);
		//addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.ATTRIBUTE, context);
	}

	/**
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.DefaultXMLCompletionProposalComputer#addAttributeValueProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addAttributeValueProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAPESTRY_COMPONENTS_ATTR_VALUE, context);
		//addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.ATTRIBUTE_VALUE, context);
	}
	
	/**
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.DefaultXMLCompletionProposalComputer#addEmptyDocumentProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addEmptyDocumentProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		
		//addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.NEW, context);
	}
	
	/**
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.DefaultXMLCompletionProposalComputer#addTagInsertionProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, int, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addTagInsertionProposals(
			ContentAssistRequest contentAssistRequest, int childPosition,
			CompletionProposalInvocationContext context) {
		addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAPESTRY_COMPONENTS, context);
		//addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAG, context);
	}
	
	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addCommentProposal(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addCommentProposal(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addCommentProposal===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addDocTypeProposal(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addDocTypeProposal(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addDocTypeProposal===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEndTagNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addEndTagNameProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addEndTagNameProposals===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEndTagProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addEndTagProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addEndTagProposals===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEntityProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion, org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addEntityProposals(
			ContentAssistRequest contentAssistRequest,
			ITextRegion completionRegion, IDOMNode treeNode,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addEntityProposals===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEntityProposals(java.util.Vector, java.util.Properties, java.lang.String, int, org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion, org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addEntityProposals(Vector proposals, Properties map,
			String key, int nodeOffset, IStructuredDocumentRegion sdRegion,
			ITextRegion completionRegion,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addEntityProposals===========================");
	}
	
	protected void addTapestryAttributesProposals(
			ContentAssistRequest contentAssistRequest,
			ITextRegion completionRegion, IDOMNode treeNode,
			CompletionProposalInvocationContext context){
		System.out.println("=======================addTapestryAttributesProposals===========================");		
		List results = tapestryELProposalComputer.computeCompletionProposals(context, treeNode);
		for(int i=0; i< results.size(); i++){
			CustomCompletionProposal proposal = (CustomCompletionProposal) results.get(i);
			contentAssistRequest.addProposal(proposal);
		}
		/**
		 * We use CustomCompletionProposal here, give up CustomTemplateProposal
		 * 
		 * If we decides to use CustomTemplateProposal, just use the following method
		 * addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAPESTRY_ENTITIES, context);
		 */
		
		/*CustomCompletionProposal each = new CustomCompletionProposal( "prop:name", context.getInvocationOffset(), 0,0, entityImage, "name", null,
				"variable name", 1);
		CustomCompletionProposal each2 = new CustomCompletionProposal( "prop:age", context.getInvocationOffset(), 0,0, entityImage, "age", null,
				"variable age", 1);
		contentAssistRequest.addProposal(each);
		contentAssistRequest.addProposal(each2);*/
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addPCDATAProposal(java.lang.String, org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addPCDATAProposal(String nodeName,
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addPCDATAProposal===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addStartDocumentProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addStartDocumentProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addStartDocumentProposals===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addTagCloseProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addTagCloseProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addTagCloseProposals===========================");
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addTagNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, int, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addTagNameProposals(
			ContentAssistRequest contentAssistRequest, int childPosition,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
		System.out.println("=======================addTagNameProposals===========================");
		addTemplates(contentAssistRequest, TemplateContextTypeIdsXML.TAPESTRY_COMPONENTS, context);
	}
	
	/**
	 * <p>Adds templates to the list of proposals</p>
	 * 
	 * @param contentAssistRequest
	 * @param templateContext
	 * @param context
	 */
	private void addTemplates(ContentAssistRequest contentAssistRequest, String templateContext,
			CompletionProposalInvocationContext context) {
		
		if (contentAssistRequest != null) {

			boolean useProposalList = !contentAssistRequest.shouldSeparate();
	
			//ICompletionProposal = new CustomTemplateProposal(null, null, null, null, 0);
			
			if (fTemplateProcessor != null) {
				fTemplateProcessor.setContextType(templateContext);
				ICompletionProposal[] proposals =
					fTemplateProcessor.computeCompletionProposals(
							context.getViewer(), context.getInvocationOffset());
				for (int i = 0; i < proposals.length; ++i) {
					if (useProposalList) {
						contentAssistRequest.addProposal(proposals[i]);
					}
					else {
						contentAssistRequest.addMacro(proposals[i]);
					}
				}
			}
		}
	}

	/**
	 * The start point of TML content assist
	 */
	public List computeCompletionProposals(CompletionProposalInvocationContext context, IProgressMonitor monitor) {
		
		List list = new ArrayList(super.computeCompletionProposals(context, monitor));

		if (fTemplateProcessor != null) {
			fTemplateProcessor.setContextType(TemplateContextTypeIdsXML.ALL);
			ICompletionProposal[] proposals = fTemplateProcessor.computeCompletionProposals(context.getViewer(), context.getInvocationOffset());
			if (proposals != null) {
				for (int i = 0; i < proposals.length; i++) {
					list.add(proposals[i]);
				}
			}
		}
		return list;
	}
}
