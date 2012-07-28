package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IPartSelectionListener;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;


public class PackageItemGeneralInfoPart extends SectionPart implements PropertyChangeListener,IPartSelectionListener{

	private CustomComponentsModel model;
	private IManagedForm managedForm;
	
	private Text prefixField;
	private Text pathField;
	
	public static final char[] AUTO_ACTIVATION_CLASSNAME = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890._".toCharArray();
	
	public PackageItemGeneralInfoPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createSection(getSection(), toolkit);
	}
	
	private void createSection(Section section, FormToolkit toolkit) {
		section.setText("Package Basic Information");

		
		Composite composite = toolkit.createComposite(section);
		section.setClient(composite);

		toolkit.createLabel(composite, "Prefix:");
		prefixField = toolkit.createText(composite, "", SWT.BORDER);
		
		toolkit.createLabel(composite, "Path:");
		pathField = toolkit.createText(composite, "", SWT.BORDER);
		pathField.setEditable(false);

		// Layout
		GridLayout layout = new GridLayout(2, false);

		composite.setLayout(layout);

		GridData gd;
		gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd.horizontalIndent = 5;
		prefixField.setLayoutData(gd);

		gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd.horizontalIndent = 5;
		pathField.setLayoutData(gd);

		prefixField.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				markDirty();
			}

			public void keyReleased(KeyEvent e) {
				
			}
		});
	}
	
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}

	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		model.modifyPackagePrefix(this.pathField.getText().trim(), this.prefixField.getText().trim());
		managedForm.dirtyStateChanged();
	}

	@Override
	public void refresh() {
		super.refresh();
	}
	
	@Override
	public void initialize(IManagedForm form) {
		super.initialize(form);
		this.managedForm = form;
		model = (CustomComponentsModel) form.getInput();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	public void selectionChanged(IFormPart part, ISelection selection) {
		Object[] tmp = ((IStructuredSelection) selection).toArray();
		for(int i=0;i<tmp.length;i++){
			String name = tmp[i].toString();
			ComponentPackage cycle = model.getPackageByPath(name);
			prefixField.setText(cycle==null?"":String.valueOf(cycle.getPrefix()));
			pathField.setText(cycle==null?"":String.valueOf(cycle.getPath()));
			break;
		}
		
	}
}
