package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IPartSelectionListener;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class PackageUsedDetailsPart extends SectionPart implements PropertyChangeListener,IPartSelectionListener{

	private CustomComponentsModel model;
	
	private Table table;
	private TableViewer viewer;
	
	public PackageUsedDetailsPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createSection(getSection(), toolkit);
	}
	
	private void createSection(Section section, FormToolkit toolkit) {
		section.setText("Custom components in this package");
		section.setDescription("This package include the following Tapestry 5 custom components");

		ToolBar toolbar = new ToolBar(section, SWT.FLAT);
		section.setTextClient(toolbar);

		Composite composite = toolkit.createComposite(section);
		section.setClient(composite);

		table = toolkit.createTable(composite, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);

		viewer = new TableViewer(table);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new BundleDetailsTableLabelProvider());

		// Layout
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 75;
		gd.widthHint = 75;
		table.setLayoutData(gd);
	}

	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
	}

	@Override
	public void refresh() {
		super.refresh();
	}
	
	@Override
	public void initialize(IManagedForm form) {
		super.initialize(form);
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
			List<String> classes= model.getComponentList(name);
			viewer.setInput(classes);
			break;
		}
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		IFormPage page = (IFormPage) getManagedForm().getContainer();
		if(page.isActive()) {
			refresh();
		} else {
			markStale();
		}
	}
	
	
	public interface IPackageFilter {
		boolean select(String packageName);
	}
}
