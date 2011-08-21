package org.eclipse.jst.tapestry.ui.internal.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


public class ComponentsListPage extends FormPage {
	private final CustomComponentsModel model ;
	private Color greyTitleBarColour;
	private PackagesPart bundleListPart;
	
	public ComponentsListPage(FormEditor editor, CustomComponentsModel model, String id, String title) {
		super(editor, id, title);
		this.model = model;
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		managedForm.setInput(model);
		
		ScrolledForm scrolledForm = managedForm.getForm();
		toolkit.decorateFormHeading(scrolledForm.getForm());
		scrolledForm.setText("Tapestry 5 custom component packages list");
		
		Form form = scrolledForm.getForm();
        toolkit.decorateFormHeading(form);
        Composite body = form.getBody();

        greyTitleBarColour = new Color(body.getDisplay(), 210, 245, 210);

        // Create controls
        MDSashForm sashForm = new MDSashForm(body, SWT.HORIZONTAL, managedForm);
        sashForm.setSashWidth(6);
        toolkit.adapt(sashForm, false, false);
        
        Composite leftPanel = toolkit.createComposite(sashForm);
        createLeftPanel(managedForm, leftPanel);
        
        Composite rightPanel = toolkit.createComposite(sashForm);
        createRightPanel(managedForm, rightPanel);
        
        sashForm.setWeights(new int[] { 4, 4 });
        sashForm.hookResizeListener();

        // Layout
        body.setLayout(new FillLayout());
	}

	void createLeftPanel(IManagedForm mform, Composite parent) {
        FormToolkit toolkit = mform.getToolkit();

        bundleListPart = new PackagesPart(parent, toolkit, Section.TITLE_BAR | Section.EXPANDED);
        mform.addPart(bundleListPart);

        // LAYOUT
        GridData gd;
        GridLayout layout;

        layout = new GridLayout(1, false);
        parent.setLayout(layout);

        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        bundleListPart.getSection().setLayoutData(gd);
    }
	
	void createRightPanel(IManagedForm mform, Composite parent) {
        FormToolkit toolkit = mform.getToolkit();

        PackageItemGeneralInfoPart infoPart = new PackageItemGeneralInfoPart(parent, toolkit, Section.TITLE_BAR | Section.EXPANDED);
        mform.addPart(infoPart);
        
        PackageUsedDetailsPart bundleDetailsPart = new PackageUsedDetailsPart(parent, toolkit, Section.TITLE_BAR | Section.EXPANDED);
        mform.addPart(bundleDetailsPart);

        // LAYOUT
        GridData gd;
        GridLayout layout;

        layout = new GridLayout(1, false);
        parent.setLayout(layout);

        gd = new GridData(SWT.FILL, SWT.FILL, true, false);
        infoPart.getSection().setLayoutData(gd);
        
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        bundleDetailsPart.getSection().setLayoutData(gd);
    }
	
	@Override
	public Image getTitleImage() {
		//ImageDescriptor image = Activator.getImageDescriptor("/icons/analyseresult.gif");
		//return image.createImage();
		return null;
	}
}
