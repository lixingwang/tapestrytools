package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

/**
 * @author Gavin Lei @ Beijing China
 * 
 */
public class TapestryCustomComponentEditor extends FormEditor implements IResourceChangeListener{

	static final String PackageListPage = "__component_list_page";
	
	private final CustomComponentsModel model = new CustomComponentsModel();
	
	private final CustomComponentSourceEditor sourcePage = new CustomComponentSourceEditor("_source_page", this);
	
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        super.init(site, input);
		sourcePage.init(site, input);
		
		setPartName(input.getName());
		
		IResource resource = ResourceUtil.getResource(input);
		if(resource != null) {
		    resource.getWorkspace().addResourceChangeListener(this);
		}

		final IDocumentProvider docProvider = sourcePage.getDocumentProvider();
		IDocument document = docProvider.getDocument(input);
		try {
			model.loadFrom(document);
		} catch (IOException e) {
			throw new PartInitException("Error reading editor input.", e);
		}
		
		// Ensure the field values are updated if the file content is replaced
        docProvider.addElementStateListener(new IElementStateListener() {
            public void elementMoved(Object originalElement, Object movedElement) {
            }

            public void elementDirtyStateChanged(Object element, boolean isDirty) {
            }

            public void elementDeleted(Object element) {
            }

            public void elementContentReplaced(Object element) {
                try {
                    System.out.println("--> Content Replaced");
                    model.loadFrom(docProvider.getDocument(element));
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }

            public void elementContentAboutToBeReplaced(Object element) {
            }
        });
    }

    @Override
    protected void addPages() {
       try {
    	addPage(new ComponentsListPage(this, model, PackageListPage,"Components"));
    	/*int sourcePageIndex = addPage(sourcePage, getEditorInput());
        setPageText(sourcePageIndex, "Source");*/
	} catch (PartInitException e) {
		e.printStackTrace();
	}
    }
    
    protected void ensurePageExists(String pageId, IFormPage page, int index) {
        IFormPage existingPage = findPage(pageId);
        if (existingPage != null)
            return;

        try {
            addPage(index, page);
        } catch (PartInitException e) {
           e.printStackTrace();
        }
    }
    
    protected void removePage(String pageId) {
        IFormPage page = findPage(pageId);
        if (page != null) {
            removePage(page.getIndex());
        }
    }
    
    @Override
	public void doSave(IProgressMonitor monitor) {
		if(sourcePage.isActive() && sourcePage.isDirty()) {
			sourcePage.commit(true);
		} else {
			commitPages(true);
			sourcePage.refresh();
		}
		sourcePage.doSave(monitor);
	}

    @Override
    public void doSaveAs() {
        
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }
    
    @Override
	protected void handlePropertyChange(int propertyId) {
		super.handlePropertyChange(propertyId);
	}

    public CustomComponentsModel getReportModel() {
		return this.model;
	}

	public void resourceChanged(IResourceChangeEvent event) {
		
	}

}