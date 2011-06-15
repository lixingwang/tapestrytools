package org.eclipse.jst.tapestry.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;

public class FindCorrespondingFileAction extends Action implements
		IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private String partenerFile = null;

	/**
	 * The constructor.
	 */
	public FindCorrespondingFileAction() {
		setId("org.eclipse.jst.tapestry.ui.actions.FindCorrespondingFileAction");
		setActionDefinitionId("org.eclipse.jst.tapestry.ui.command.actions.FindCorrespondingFile");
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		// MessageDialog.openInformation(window.getShell(),"Tapesty 5","Hello,Tapestry 5!");

		IEditorPart editorPart = Workbench.getInstance()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart
					.getEditorInput();
			IFile file = input.getFile();
			IProject activeProject = file.getProject();
			
			String fileName = file.getFullPath().toString();
			//String fileNameShort = fileName;
			//if(fileName.indexOf("/") > -1) fileNameShort = fileName.substring(fileName.lastIndexOf("/") +1);
			String aimFileName = null;
			String aimNameShort = null;
			if(fileName.endsWith(".tml"))
				aimFileName = fileName.substring(0,fileName.length()-4) + ".java";
			else if(fileName.endsWith(".java"))
				aimFileName = fileName.substring(0,fileName.length()-5) + ".tml";
			else {
				MessageDialog.openInformation(window.getShell(),"Tapesty 5 tips","Only Tapestry 5 page class or .tml page file can use this function!");
				return ;
			}
			if(aimFileName.indexOf("/") > -1)
				aimNameShort = aimFileName.substring(aimFileName.lastIndexOf("/") + 1);
			final IResource res = activeProject.findMember(aimFileName.substring(("/" + activeProject.getName()).length()));
			if(res != null && res.getType() == IResource.FILE){
				window.getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						IWorkbenchPage page = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage();
						try {
							IDE.openEditor(page, (IFile)res, true);
						} catch (PartInitException e) {
						}
					}
				});
			}else{
				searchPartenerFile(activeProject, aimNameShort);
				if(this.partenerFile != null){
					final IResource res2 = activeProject.findMember(partenerFile.substring(("/" + activeProject.getName()).length()));
					if(res2 != null && res2.getType() == IResource.FILE){
						window.getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								IWorkbenchPage page = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getActivePage();
								try {
									IDE.openEditor(page, (IFile)res2, true);
								} catch (PartInitException e) {
								}finally{
									partenerFile = null;
								}
							}
						});
					}
				}else{
					if(fileName.endsWith(".tml"))
						MessageDialog.openInformation(window.getShell(),"Tapesty 5 tips","Sorry, we can not find page class file for this .tml file!");
					else 
						MessageDialog.openInformation(window.getShell(),"Tapesty 5 tips","Sorry, we do not think this is a tapestry page class file or you just miss its corresponding .tml file!");
				}
			}
		}
	}
	
	private void searchPartenerFile(IProject project, String fileName){
		IResource[] fileList = null;
		try {
			fileList = project.members(false);
			travelAllFolder(fileList, fileName);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private void travelAllFolder(IResource[] fileList, String fileName) throws CoreException{
		for(int i=0; i<fileList.length; i++){
			IResource eachFile = fileList[i];
			if(eachFile.getType() == IResource.FILE && eachFile.getName().equals(fileName)){
				this.partenerFile = eachFile.getFullPath().toString();
			}else if(eachFile.getType() == IResource.FOLDER){
				IFolder file = (IFolder) eachFile;
				travelAllFolder(file.members(), fileName);
			}
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {

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
		this.window = window;
	}
}
