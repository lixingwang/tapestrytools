package org.apache.tapestrytools.ui.internal.tcc.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tapestrytools.ui.internal.wizards.WizardConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class PackagesPart extends SectionPart implements PropertyChangeListener{

	private CustomComponentsModel model;
	private List<String> cycles = new ArrayList<String>();
	private final DocumentBuilderFactory domfac = DocumentBuilderFactory
			.newInstance();

	private Table table;
	private TableViewer viewer;
    private IManagedForm managedForm;

	public PackagesPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createSection(getSection(), toolkit);
	}
	
	void createSection(Section section, FormToolkit toolkit) {
		section.setText("Packages with custom component");
		section.setDescription("This project includes these packages.");

		ToolBar toolbar = new ToolBar(section, SWT.FLAT);
		section.setTextClient(toolbar);
		

		Composite composite = toolkit.createComposite(section);
		section.setClient(composite);

		table = toolkit.createTable(composite, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);
		
		final ToolItem collectItem = new ToolItem(toolbar, SWT.PUSH);
		//collectItem.setImage(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jst.tapestry.ui", "/icons/export_bundles.png").createImage());
		collectItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED));
		collectItem.setToolTipText("Export Bundles");
		
		final ToolItem addItem = new ToolItem(toolbar, SWT.PUSH);
		addItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		addItem.setToolTipText("Add");

		final ToolItem removeItem = new ToolItem(toolbar, SWT.PUSH);
		removeItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		removeItem.setDisabledImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		removeItem.setToolTipText("Remove");
		removeItem.setEnabled(false);

		viewer = new TableViewer(table);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new PackageDetailsTableLabelProvider());

		// Listeners
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
			    managedForm.fireSelectionChanged(PackagesPart.this, event.getSelection());
			    removeItem.setEnabled(!viewer.getSelection().isEmpty());
			}
		});

		// Layout
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		addItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IProject project = Utils.getCurrentProject();
				Set<IJavaElement> list = Utils.getSrcDirectories(project);
				if(project != null){
					ElementListSelectionDialog dialog = new ElementListSelectionDialog(getSection().getShell(), new JavaElementLabelProvider(1));
					dialog.setTitle(WizardConstants.PACKAGE_SELECTION_DIALOG_TITLE);
					dialog.setMessage(WizardConstants.PACKAGE_SELECTION_DIALOG_DESC);
					dialog.setEmptyListMessage(WizardConstants.PACKAGE_SELECTION_DIALOG_MSG_NONE);
					dialog.setElements(list.toArray(new IJavaElement[0]));
					if (dialog.open() == Window.OK) {
						IPackageFragment fragment = (IPackageFragment) dialog.getFirstResult();
						String newBundleName = fragment.getElementName();
						List<String> tmp = model.getPackageList();
						if(newBundleName != null && !newBundleName.equals("")&& !tmp.contains(newBundleName)){
							List<String> added = new LinkedList<String>();
							model.addPackageByPath(newBundleName);
							added.add(newBundleName);
							
							// Update the model and view
							if(!added.isEmpty()) {
								viewer.add(added.toArray(new String[added.size()]));
								markDirty();
							}
						}
					}
				}
			}
		});
		removeItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean b = MessageDialog.openQuestion(getSection().getShell(), "Delete Confirm", "Are you confirm to delete this package?");
		        if(b){
		        	IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		    		if(!selection.isEmpty()) {
		    			Iterator<?> elements = selection.iterator();
		    			List<Object> removed = new LinkedList<Object>();
		    			while(elements.hasNext()) {
		    				Object pkg = elements.next();
		    				model.removePackageByPath(pkg.toString());
		    				removed.add(pkg);
		    			}

		    			if(!removed.isEmpty()) {
		    				viewer.remove(removed.toArray(new String[removed.size()]));
		    				markDirty();
		    			}
		    		}
		        }
			}
		});
		collectItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				collectCustomComponents(Utils.getCurrentProject());
			}
		});

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 75;
		gd.widthHint = 75;
		table.setLayoutData(gd);
	}


	private void collectCustomComponents(IProject project){
		List<ComponentInstance> componentList = new ArrayList<ComponentInstance>();
		try {
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for (int i = 0; i < roots.length; i++) {
				IPackageFragmentRoot root = roots[i];{
					if(!root.isArchive()) {
						String srcPath = root.getElementName();
						for(ComponentPackage pi : model.getCustomPackageList()){
							String wholePath = "/" + srcPath + "/" + pi.getPath().replace(".", "/");
							IFolder folder = project.getFolder(wholePath);
							if(folder.exists()){
								IResource[] fileList = folder.members();
								for(IResource file : fileList){
									String fullPath = file.getFullPath().toString();
									String classFile = fullPath.replace(".tml", ".java").substring(1);
									classFile = classFile.substring(classFile.indexOf("/"));
									if(fullPath.endsWith(".tml") && file.getType() == IResource.FILE && project.getFile(classFile).exists()){
										IFile componentFile = (IFile) file;
										Element rootElement = getRootElementOfXML(componentFile.getContents());
										if(rootElement.getNodeName().trim().equals("t:container")){
											String componentName = classFile.substring(classFile.lastIndexOf("/"));
											componentName = componentName.substring(0, componentName.indexOf("."));
											if(componentName.startsWith("/")) componentName = componentName.substring(1);
											ComponentInstance ci = new ComponentInstance();
											ci.setId(componentName);
											ci.setName(pi.getPrefix() + ":" + componentName);
											ci.setPath(pi.getPath());
											ci.setPrefix(pi.getPrefix());
											ci.setText(componentName);
											componentList.add(ci);
										}
									}
								}
							}
						}
					}
				}
			}
			model.setComponentList(componentList);
			markDirty();
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private Element getRootElementOfXML(InputStream stream){
		DocumentBuilder dombuilder;
		try {
			dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(stream);
			Element root = doc.getDocumentElement();
			return root;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		//this.refresh();
		
		managedForm.dirtyStateChanged();
	}

	@Override
	public void refresh() {
		List<String> tmp = model.getPackageList();
		if(tmp != null)
			cycles = new ArrayList<String>(tmp);
		else
			cycles = new ArrayList<String>();
		viewer.setInput(cycles);
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
	
	public void propertyChange(PropertyChangeEvent evt) {
		IFormPage page = (IFormPage) getManagedForm().getContainer();
		if(page.isActive()) {
			refresh();
		} else {
			markStale();
		}
	}

	public ISelectionProvider getSelectionProvider() {
	    return viewer;
	}
}
