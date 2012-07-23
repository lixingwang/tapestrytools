package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;


public class CustomComponentSourceEditor extends TextEditor implements IFormPage{

	private final TapestryCustomComponentEditor formEditor;
	private final String id;
	private String lastLoaded;
	
	private int index;
	private boolean stale = false;
	
	private Control control;
	
	public CustomComponentSourceEditor(String id,TapestryCustomComponentEditor formEditor){
		this.id = id;
		this.formEditor = formEditor;
	}
	
	public void dispose() {
		super.dispose();
	}
	
	public void initialize(FormEditor editor) {
		
	}

	public FormEditor getEditor() {
		return formEditor;
	}

	public IManagedForm getManagedForm() {
		return null;
	}

	public void setActive(boolean active) {
		if(active) {
			if(stale)
				refresh();
			lastLoaded = getDocument().get();
		} else {
			commit(false);
		}
	}

	public boolean isActive() {
		return this.equals(formEditor.getActivePageInstance());
	}

	public boolean canLeaveThePage() {
		return true;
	}


	
	public Control getPartControl() {
		return control;
	}

	public String getId() {
		return this.id;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isEditor() {
		return true;
	}

	public boolean selectReveal(Object object) {
		if (object instanceof IMarker) {
			IDE.gotoMarker(this, (IMarker) object);
			return true;
		}
		return false;
	}

	void commit(boolean onSave) {
		try {
			IDocument doc = getDocument();
			String currentContent = doc.get();
			if(!currentContent.equals(lastLoaded))
				formEditor.getReportModel().loadFrom(getDocument());
		} catch (IOException e) {
			System.out.println("Error loading model from document.");
			e.printStackTrace();
		}
	}
	
	void refresh() {
		IDocument document = getDocument();
		formEditor.getReportModel().saveChangesTo(document);
		stale = false;
	}

	private IDocument getDocument() {
		IDocumentProvider docProvider = getDocumentProvider();
		IEditorInput input = getEditorInput();
		IDocument doc = docProvider.getDocument(input);
		return doc;
	}

}
