package org.apache.tapestrytools.ui.internal.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.contentoutline.ContentOutline;
/**
 * Tapestry component creation wizard page
 * 
 * @author gavingui2011@gmail.com - Beijing China
 *
 */
public class NewTapestryComponentPage extends WizardPage {
	private Text folderText;
	private Button folderButton;
	protected Text packageText;
	protected Button packageButton;
	protected Label packageLabel;
	protected Text classText;
	protected Label classLabel;
	protected Label projectNameLabel;
	private Combo projectNameCombo;	
	protected String projectType = "java";//
	private String projectName;
	private Button createTemplate;
	private HashMap<String,Object> model = new HashMap<String,Object>();

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewTapestryComponentPage(ISelection selection) {
		super("wizardPage");
		setTitle(WizardConstants.NEW_COMPONENT_TITLE);
		setDescription(WizardConstants.NEW_COMPONENT_DESC);
	}
	
	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.widthHint = 300;
		composite.setLayoutData(data);

		addProjectNameGroup(composite);
		addFolderGroup(composite);
		addSeperator(composite, 3);
		addPackageGroup(composite);
		addClassnameGroup(composite);
		addCreateTemplate(composite);

		// set the cursor focus
		//   - to the "Java package" if it is empty
		//   - to the "Class name" - otherwise
		if (packageText.getText().trim().length() == 0) {
			packageText.setFocus();
		} else {
			classText.setFocus();
		}
		setControl(composite);
	}
	/**
	 * Add classname group to composite
	 */
	private void addClassnameGroup(Composite composite) {
		// class name
		classLabel = new Label(composite, SWT.LEFT);
		classLabel.setText("Component Name:");
		classLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		classText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		classText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(composite, SWT.LEFT);
	}
	/**
	 * In Tapestry it is possible to create components without templates. 
	 * Please provide an additional checkbox, labeled with "Create template" which is checked by default. 
	 */
	private void addCreateTemplate(Composite composite){
		Label templateLabel = new Label(composite, SWT.LEFT);
		templateLabel.setText("Create Template:");
		templateLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		createTemplate = new Button(composite, SWT.CHECK);
		createTemplate.setText("Use Default Component Template");
		createTemplate.setToolTipText("Select this checkbox button to create template file for Tapestry 5 component");
		createTemplate.setSelection(true);
		createTemplate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	/**
	 * Add package group to composite
	 */
	private void addPackageGroup(Composite composite) {
		// package
		packageLabel = new Label(composite, SWT.LEFT);
		packageLabel.setText("Package");
		packageLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		packageText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		packageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		IPackageFragment packageFragment = getSelectedPackageFragment();
		String targetProject = projectNameCombo.getText(); 
		if (packageFragment != null && packageFragment.exists() && 
				packageFragment.getJavaProject().getElementName().equals(targetProject)) {
			IPackageFragmentRoot root = getPackageFragmentRoot(packageFragment);
			model.put("JAVA_PACKAGE_FRAGMENT_ROOT", root);
			if (root != null)
				folderText.setText(root.getPath().toString());
			//model.put(INewJavaClassDataModelProperties.JAVA_PACKAGE, packageFragment.getElementName());
			packageText.setText(packageFragment.getElementName());
		}

		packageButton = new Button(composite, SWT.PUSH);
		packageButton.setText("Browse");
		packageButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		packageButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handlePackageButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});
	}
	protected void handlePackageButtonPressed()  {
		IPackageFragmentRoot packRoot = (IPackageFragmentRoot) model.get("JAVA_PACKAGE_FRAGMENT_ROOT");
		if (packRoot == null && !this.folderText.getText().isEmpty()){
			String projectName = this.projectNameCombo.getText();
			String folderName = this.folderText.getText();
			if (projectName != null && projectName.length() > 0) {
				IProject targetProject = ResourcesPlugin.getWorkspace().getRoot().findMember(projectName).getProject();
				try {
					IPackageFragmentRoot[] roots = JavaCore.create(targetProject).getAllPackageFragmentRoots();
					for(IPackageFragmentRoot root : roots){
						if(folderName.equals("/" + projectName + "/" + root.getElementName())){
							packRoot = root;
							break;
						}
					}
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}
		}
		if (packRoot == null)
			return;
		IJavaElement[] packages = null;
		try {
			packages = packRoot.getChildren();
		} catch (JavaModelException e) {
			// Do nothing
		}
		if (packages == null)
			packages = new IJavaElement[0];
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), new JavaElementLabelProvider(1));
		dialog.setTitle(WizardConstants.PACKAGE_SELECTION_DIALOG_TITLE);
		dialog.setMessage(WizardConstants.PACKAGE_SELECTION_DIALOG_DESC);
		dialog.setEmptyListMessage(WizardConstants.PACKAGE_SELECTION_DIALOG_MSG_NONE);
		dialog.setElements(packages);
		if (dialog.open() == Window.OK) {
			IPackageFragment fragment = (IPackageFragment) dialog.getFirstResult();
			if (fragment != null) {
				packageText.setText(fragment.getElementName());
			} else {
				packageText.setText("EMPTY_STRING");
			}
		}
	}
	private IPackageFragment getSelectedPackageFragment() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null)
			return null;
		IJavaElement element = getInitialJavaElement(selection);
		if (element != null) {
			if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
				return (IPackageFragment) element;
			} else if (element.getElementType() == IJavaElement.COMPILATION_UNIT) { 
				IJavaElement parent = ((ICompilationUnit) element).getParent();
				if (parent.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
					return (IPackageFragment) parent;
				}
			} else if (element.getElementType() == IJavaElement.TYPE) {
				return ((IType) element).getPackageFragment();
			}
		}
		return null;
	}
	protected IPackageFragmentRoot getPackageFragmentRoot(IPackageFragment packageFragment) {
		if (packageFragment == null)
			return null;
		else if (packageFragment.getParent() instanceof IPackageFragment)
			return getPackageFragmentRoot((IPackageFragment) packageFragment.getParent());
		else if (packageFragment.getParent() instanceof IPackageFragmentRoot)
			return (IPackageFragmentRoot) packageFragment.getParent();
		else
			return null;
	}
	/**
	 * Add seperator to composite
	 */
	protected void addSeperator(Composite composite, int horSpan) {
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.widthHint = 300;
		// Separator label
		Label seperator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = horSpan;
		seperator.setLayoutData(data);
	}
	/**
	 * Add folder group to composite
	 */
	private void addFolderGroup(Composite composite) {
		// folder
		Label folderLabel = new Label(composite, SWT.LEFT);
		folderLabel.setText("Source folder:");
		folderLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		folderText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		folderText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		IPackageFragmentRoot root = getSelectedPackageFragmentRoot();
		String projectName = projectNameCombo.getText();
		if (projectName != null && projectName.length() > 0) {
			IProject targetProject = ResourcesPlugin.getWorkspace().getRoot().findMember(projectName).getProject();
			if (root == null || !root.getJavaProject().getProject().equals(targetProject)) {
				IFolder folder = getDefaultJavaSourceFolder(targetProject);
				if (folder != null)
					folderText.setText(folder.getFullPath().toOSString());
			} else {
				folderText.setText(root.getPath().toString());
			}
		}
		
		folderButton = new Button(composite, SWT.PUSH);
		folderButton.setText("Browse");
		folderButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		folderButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleFolderButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});
	}
	/**
	 * Browse for a new Destination Folder
	 */
	protected void handleFolderButtonPressed() {
		ISelectionStatusValidator validator = getContainerDialogSelectionValidator();
		ViewerFilter filter = getContainerDialogViewerFilter();
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new DecoratingLabelProvider(new WorkbenchLabelProvider(), PlatformUI.getWorkbench()
				.getDecoratorManager().getLabelDecorator());
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), labelProvider, contentProvider);
		dialog.setValidator(validator);
		dialog.setTitle(WizardConstants.CONTAINER_SELECTION_DIALOG_TITLE);
		dialog.setMessage(WizardConstants.CONTAINER_SELECTION_DIALOG_DESC);
		dialog.addFilter(filter);
		String projectName = projectNameCombo.getText();;
		if (projectName==null || projectName.length()==0)
			return;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().findMember(projectName).getProject();//ProjectUtilities.getProject(projectName);
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

		if (project != null)
			dialog.setInitialSelection(project);
		if (dialog.open() == Window.OK) {
			Object element = dialog.getFirstResult();
			try {
				if (element instanceof IContainer) {
					IContainer container = (IContainer) element;
					folderText.setText(container.getFullPath().toString());
					// dealWithSelectedContainerResource(container);
				}
			} catch (Exception ex) {
				// Do nothing
			}

		}
	}
	/**
	 * Returns a new instance of the Selection Listner for the Container
	 * Selection Dialog
	 */
	protected ViewerFilter getContainerDialogViewerFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					return project.getName().equals(projectNameCombo.getText());
				} else if (element instanceof IFolder) {
					IFolder folder = (IFolder) element;
					// only show source folders
					IProject project = ResourcesPlugin.getWorkspace().getRoot().findMember(projectNameCombo.getText()).getProject();
					IPackageFragmentRoot[] sourceFolders;
					try {
						sourceFolders = JavaCore.create(project).getAllPackageFragmentRoots();
						for (int i = 0; i < sourceFolders.length; i++) {
							if (sourceFolders[i].getResource()!= null && sourceFolders[i].getResource().equals(folder))
								return true;
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
					
				}
				return false;
			}
		};
	}
	protected ISelectionStatusValidator getContainerDialogSelectionValidator() {
		/*return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {
				if (selection != null && selection[0] != null && !(selection[0] instanceof IProject))
					return WTPCommonPlugin.OK_STATUS;
				return WTPCommonPlugin.createErrorStatus(J2EEUIMessages.CONTAINER_SELECTION_DIALOG_VALIDATOR_MESG);
			}
		};*/
		return null;
	}
	private IPackageFragmentRoot getSelectedPackageFragmentRoot() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null)
			return null;
		IJavaElement element = getInitialJavaElement(selection);
		if (element != null) {
			if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT)
				return (IPackageFragmentRoot) element;
		}
		return null;
	}
	
	private IFolder getDefaultJavaSourceFolder(IProject project) {
		if (project == null)
			return null;
		try {
			IPackageFragmentRoot[] roots = JavaCore.create(project).getAllPackageFragmentRoots();
			if(roots != null && roots.length > 0){
				return (IFolder) roots[0].getCorrespondingResource();
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Add project group
	 */
	private void addProjectNameGroup(Composite parent) {
		// set up project name label
		projectNameLabel = new Label(parent, SWT.NONE);
		projectNameLabel.setText("Project:"); 
		GridData data = new GridData();
		projectNameLabel.setLayoutData(data);
		// set up project name entry field
		projectNameCombo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		projectNameCombo.setLayoutData(data);
		projectNameCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
			}
		});
		initializeProjectList();
		new Label(parent, SWT.NONE);
	}
	
	private void initializeProjectList() {
		IProject[] workspaceProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List items = new ArrayList();
		for (int i = 0; i < workspaceProjects.length; i++) {
			IProject project = workspaceProjects[i];
			if (isProjectValid(project))
				items.add(project.getName());
		}
		if (items.isEmpty()) return;
		String[] names = new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			names[i] = (String) items.get(i);
		}
		projectNameCombo.setItems(names);
		IProject selectedProject = null;

		if (selectedProject == null)
			selectedProject = getSelectedProject();
		if (selectedProject != null && selectedProject.isAccessible()) {
			projectNameCombo.setText(selectedProject.getName());
		}

		if (projectName == null && names.length > 0)
			projectName = names[0];

		if ((projectNameCombo.getText() == null || projectNameCombo.getText().length() == 0) && projectName != null) {
			projectNameCombo.setText(projectName);
		}

	}
	
	protected boolean isProjectValid(IProject project) {
		boolean result = true;
/*		try {
			result = project.isAccessible() && 
				project.hasNature(IModuleConstants.MODULE_NATURE_ID) && 
			 	JavaEEProjectUtilities.getJ2EEProjectType(project).equals(projectType);
		} catch (CoreException ce) {
			result = false;
		}*/
		return result;
	}
	
	private IProject getSelectedProject() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null)
			return null;
		if (!(selection instanceof IStructuredSelection)) 
			return null;
		IJavaElement element = getInitialJavaElement(selection);
		if (element != null && element.getJavaProject() != null)
			return element.getJavaProject().getProject();
		IStructuredSelection stucturedSelection = (IStructuredSelection) selection;
		IProject project = getExtendedSelectedProject(stucturedSelection.getFirstElement());
		if(project != null) {
			return project;
		}
		if(selection instanceof TreeSelection && (((TreeSelection)selection).getPaths().length > 0)){
			TreePath path = (((TreeSelection)selection).getPaths()[0]);
			if(path.getSegmentCount() > 0 && path.getSegment(0) instanceof IProject) {
				return (IProject) path.getSegment(0);
			}
		}
		return null;
	}
	
	protected IProject getExtendedSelectedProject(Object selection) {
		/*if (selection instanceof CompressedJavaProject) {
			return ((CompressedJavaProject) selection).getProject().getProject();
		}*/
		return null;
	}
	
	protected IJavaElement getInitialJavaElement(ISelection selection) {
		IJavaElement jelem = null;
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			jelem = getJavaElement(selectedElement);
			if (jelem == null) {
				IResource resource = getResource(selectedElement);
				if (resource != null && resource.getType() != IResource.ROOT) {
					while (jelem == null && resource.getType() != IResource.PROJECT) {
						resource = resource.getParent();
						jelem = (IJavaElement) resource.getAdapter(IJavaElement.class);
					}
					if (jelem == null) {
						jelem = JavaCore.create(resource); 
					}
				}
			}
		}
		if (jelem == null) {
			IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null)
				return null;
			IWorkbenchPart part = window.getActivePage().getActivePart();
			if (part instanceof ContentOutline) {
				part = window.getActivePage().getActiveEditor();
			}

			/*if (part instanceof IViewPartInputProvider) {
				Object elem = ((IViewPartInputProvider) part).getViewPartInput();
				if (elem instanceof IJavaElement) {
					jelem = (IJavaElement) elem;
				}
			}*/
		}

		if (jelem == null || jelem.getElementType() == IJavaElement.JAVA_MODEL) {
			try {
				IJavaProject[] projects = JavaCore.create(getWorkspaceRoot()).getJavaProjects();
				if (projects.length == 1) {
					jelem = projects[0];
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return jelem;
	}

	protected IJavaElement getJavaElement(Object obj) {
		if (obj == null)
			return null;
		
		if (obj instanceof IJavaElement) 
			return (IJavaElement) obj;
		
		if (obj instanceof IAdaptable) 
			return (IJavaElement) ((IAdaptable) obj).getAdapter(IJavaElement.class);
			
		return (IJavaElement) Platform.getAdapterManager().getAdapter(obj, IJavaElement.class);
	}
	
	protected IResource getResource(Object obj) {
		if (obj == null)
			return null;
		
		if (obj instanceof IResource) 
			return (IResource) obj;
		
		if (obj instanceof IAdaptable) 
			return (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
			
		return (IResource) Platform.getAdapterManager().getAdapter(obj, IResource.class);
	}
	
	protected IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	public String getProjectName(){
		return projectNameCombo.getText();
	}
	public String getFolderName(){
		return folderText.getText();
	}
	public String getPackageName(){
		return packageText.getText();
	}
	public String getClassName(){
		return classText.getText();
	}
	public boolean createTemplateOrNot(){
		return createTemplate.getSelection();
	}
}