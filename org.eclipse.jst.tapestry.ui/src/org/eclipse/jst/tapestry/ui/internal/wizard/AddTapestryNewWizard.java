package org.eclipse.jst.tapestry.ui.internal.wizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.componentcore.ComponentCore;

public class AddTapestryNewWizard extends Wizard implements INewWizard,
		IExecutableExtension {

	private NewTapestryPageClassWizard page;
	private ISelection selection;

	/**
	 * Constructor for AddTapestryPageWizard.
	 */
	public AddTapestryNewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewTapestryPageClassWizard(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		final String projectName = page.getProjectName();
		final String folderName = page.getFolderName();
		final String packageName = page.getPackageName();
		final String className = page.getClassName();
		final String pageLocation = page.getPageLocation();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(projectName, folderName, packageName, className,pageLocation,
							monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */

	private void doFinish(String projectName, String folderName,
			String packageName, String className,String pageLocation, IProgressMonitor monitor)
			throws CoreException {
		IProject project = ProjectUtilities.getProject(projectName);
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragmentRoot src = null;
		IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
		//Get a writeable IPackageFragmentRoot instance
		String[] steps = folderName.split("/");
		String srcName = steps[steps.length-1];
		for(IPackageFragmentRoot pfr : roots){
			if(pfr.getElementName().equals(srcName)) {
				src = pfr;
				break;
			}
		}
		monitor.beginTask("Creating " + className, 3);
		IPackageFragment aimPackage = src.getPackageFragment(packageName);
		String classContent="package "+packageName+";\n\n";
		classContent+="public class "+className+" {\n\n";
		classContent+="}";
		aimPackage.createCompilationUnit(className+".java", classContent, false, null);
		monitor.worked(1);
		
		IFile pageFile = null;
		if (pageLocation.equals("src")) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			pageFile = root.getFile(aimPackage.getPath().append(
					className + ".tml"));
		} else {
			String absolutePath = ComponentCore.createComponent(project)
					.getRootFolder().getUnderlyingFolder().getRawLocation()
					.toString();
			String webContent = absolutePath.substring(absolutePath
					.lastIndexOf("/"));
			pageFile = project.getFile(webContent + "/" + className + ".tml");
		}
		
		final IFile file = pageFile;
		try {
			InputStream stream = openContentStream();
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);

	}

	/**
	 * We will initialize file contents with a sample text.
	 */

	private InputStream openContentStream() {
		String contents = "<div  xmlns:t=\"http://tapestry.apache.org/schema/tapestry_5_1_0.xsd\"> "+
		"\n"+
		"</div>";
		return new ByteArrayInputStream(contents.getBytes());
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "plug", IStatus.OK, message,
				null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		// TODO Auto-generated method stub

	}

}
