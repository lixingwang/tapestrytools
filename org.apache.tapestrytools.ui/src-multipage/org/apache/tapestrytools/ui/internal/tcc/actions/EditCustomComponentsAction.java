package org.apache.tapestrytools.ui.internal.tcc.actions;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;

public class EditCustomComponentsAction extends Action implements
		IWorkbenchWindowActionDelegate {
	private ISelection selection;

	/**
	 * The constructor.
	 */
	public EditCustomComponentsAction() {
		setId("org.eclipse.jst.tapestry.ui.actions.EditCustomComponentsAction");
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 *
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		// MessageDialog.openInformation(window.getShell(),"Tapesty 5","Hello,Tapestry 5!");
		boolean flag = true;
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}
				if (project != null) {
					openTCCFile(project);
					flag = false;
					break;
				}
			}
		}
		if(flag){
			IEditorPart editorPart = Workbench.getInstance()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

			if (editorPart != null) {
				IFileEditorInput input = (IFileEditorInput) editorPart
						.getEditorInput();
				IFile file = input.getFile();
				IProject acproject = file.getProject();
				openTCCFile(acproject);
			}
		}

	}

	private void openTCCFile(IProject activeProject){
		final IFile res = activeProject.getFile("/components.tcc");
		if(!res.exists()){
			StringBuilder buffer = new StringBuilder(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n");
			buffer.append("<packages>\n");
			buffer.append("</packages>\n");
			buffer.append("<components>\n");
			buffer.append("</components>\n");
			buffer.append("</root>");
			try {
				res.create(new ByteArrayInputStream(buffer.toString().getBytes()), false, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		Runnable runnable = new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, res, true);
				} catch (PartInitException e) {
				}
			}
		};
		runnable.run();
	}



	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 *
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 *
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 *
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		//this.window = window;
	}
}