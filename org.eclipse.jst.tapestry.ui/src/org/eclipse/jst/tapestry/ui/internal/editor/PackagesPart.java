package org.eclipse.jst.tapestry.ui.internal.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;
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
    
    private String attributeList;

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
				InputDialog input = new InputDialog(getSection().getShell(),
						"Add new custom components package", "Please input packages which includes Tapestry custom components:",
						"", null);
				if (input.open() == Window.OK) {
					String newBundleName = input.getValue().trim();
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
				IEditorPart editorPart = Workbench.getInstance()
						.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

				if (editorPart != null) {
					IFileEditorInput input = (IFileEditorInput) editorPart
							.getEditorInput();
					IFile file = input.getFile();
					collectCustomComponents(file.getProject());
				}
			}
		});

		GridData gd;

		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 75;
		gd.widthHint = 75;
		table.setLayoutData(gd);
	}


	private void collectCustomComponents(IProject project){
		List<String> srcList = new ArrayList<String>();
		List<ComponentInstance> componentList = new ArrayList<ComponentInstance>();
		try {
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			for (int i = 0; i < roots.length; i++) {
				IPackageFragmentRoot root = roots[i];{
					if(!root.isArchive()) {
						String srcPath = root.getElementName();
						for(String pi : model.getPackageList()){
							String wholePath = "/" + srcPath + "/" + pi.replace(".", "/");
							IFolder folder = project.getFolder(wholePath);
							if(folder.exists()){
								IResource[] fileList = folder.members();
								for(IResource file : fileList){
									String fullPath = file.getFullPath().toString();
									String classFile = fullPath.replace(".tml", ".java").substring(1);
									classFile = classFile.substring(classFile.indexOf("/"));
									IFile classStream = project.getFile(classFile);
									if(fullPath.endsWith(".tml") && file.getType() == IResource.FILE && classStream.exists()){
										IFile componentFile = (IFile) file;
										Element rootElement = getRootElementOfXML(componentFile.getContents());
										if(rootElement.getNodeName().trim().equals("t:container")){
											String componentName = classFile.substring(classFile.lastIndexOf("/"));
											componentName = componentName.substring(0, componentName.indexOf("."));
											if(componentName.startsWith("/")) componentName = componentName.substring(1);
											ComponentInstance ci = new ComponentInstance();
											ci.setId(componentName);
											ci.setName("t:" + componentName);
											ci.setPath(pi);
											ci.setPrefix("t");
											ci.setText(componentName);
											String attributes = goThroughClass(inputStream2String(classStream.getContents()));
											ci.setAttributes(attributes);
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
	
	private String goThroughClass(String ClassContent) {
		attributeList = "";
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(ClassContent.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(FieldDeclaration node) {
				String attr_name = "";
				List list = node.fragments();
				for(int i=0; i<list.size(); i++){
					attr_name = list.get(i).toString();
					break;
				}
				
				List prefixs = node.modifiers();
				for(int i=0; i<prefixs.size(); i++){
					String each = prefixs.get(i).toString();
					if(each.startsWith("@Parameter")){
						int start = each.indexOf("(");
						int end = each.indexOf(")");
						if(start > -1 && end > each.indexOf("(") + 1 ){
							String longName = each.substring(start+1, end).trim();
							if(longName.startsWith("name=\"") && longName.endsWith("\"")) attr_name = longName.substring(6, longName.length()-1);
						}
						if(!attributeList.trim().equals("")) attributeList = attributeList +"," +attr_name;
						else attributeList = attr_name;
					}
						
				}
				return false;
			}
		});
		return attributeList;
	}
	
	private String inputStream2String(InputStream ins) {
		String all_content = null;
		try {
			all_content = new String();
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			byte[] str_b = new byte[1024];
			int i = -1;
			while ((i = ins.read(str_b)) > 0) {
				outputstream.write(str_b, 0, i);
			}
			all_content = outputstream.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_content;
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
