/*******************************************************************************
 * Copyright (c) 2012 gavingui2011@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     gavingui2011@gmail.com  - initial API and implementation
 *     
 *******************************************************************************/

package org.apache.tapestrytools.ui.internal.wizards;

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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
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

/**
 * Tapestry page file creation wizard
 * 
 * @author gavingui2011@gmail.com - Beijing China
 *
 */
public class AddTapestryPageWizard extends Wizard implements INewWizard,
                IExecutableExtension {

        private NewTapestryPageClassWizard page;
        private ISelection selection;

        /**
         * Constructor for AddTapestryPageWizard.
         */
        public AddTapestryPageWizard() {
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
                //final boolean createTemplate = page.createTemplateOrNot();

                IRunnableWithProgress op = new IRunnableWithProgress() {
                        public void run(IProgressMonitor monitor)
                                        throws InvocationTargetException {
                                try {
                                        doFinish(projectName, folderName, packageName, className,
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
                        String packageName, String className, IProgressMonitor monitor)
                        throws CoreException {
        		IProject project = ResourcesPlugin.getWorkspace().getRoot().findMember(projectName).getProject();//ProjectUtilities.getProject(projectName);
    			IJavaProject javaProject = JavaCore.create(project);
                IPackageFragmentRoot src = null;
                IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
                for(IPackageFragmentRoot pfr : roots){
                	if(pfr.getPath().toString().equals(folderName)) {
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

                IPath templatePath = aimPackage.getPath();
                if(TapestryWizardUtils.isMavenProject(project)){
                	String javaPath = aimPackage.getPath().toString();
                	String temPath = javaPath;
                	if(javaPath.indexOf("/main/java/") > -1){
                		temPath = javaPath.replace("/main/java/", "/main/resources/");
                	}else if(javaPath.indexOf("/test/java/") > -1){
                		temPath = javaPath.replace("/test/java/", "/test/resources/");
                	}
                	IPath tmpPath = new Path(temPath);
                	if(tmpPath.isValidPath(temPath)){
                		templatePath = tmpPath;
                	}
                }
                
                IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                IFile pageFile = root.getFile(templatePath.append(className + ".tml"));
                
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
                        e.printStackTrace();
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
                String contents = "<html  xmlns:t=\"http://tapestry.apache.org/schema/tapestry_5_1_0.xsd\"> \n" +
                "<body>\n\r\n"+
                "</body>\n" +
                "</html>";
                return new ByteArrayInputStream(contents.getBytes());
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
