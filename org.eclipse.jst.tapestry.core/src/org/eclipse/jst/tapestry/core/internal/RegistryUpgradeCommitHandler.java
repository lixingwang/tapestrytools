package org.eclipse.jst.tapestry.core.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.TapestryLibraryRegistryUpgradeUtil;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util.UpgradeStatus;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

@SuppressWarnings("deprecation")
public class RegistryUpgradeCommitHandler
{

    /**
     * handle migration
     */
	public static void commitMigrationIfNecessary()
    {
        TapestryLibraryRegistryUpgradeUtil upgradeUtil =
            TapestryLibraryRegistryUpgradeUtil.getInstance();

        UpgradeStatus status = upgradeUtil.getUpgradeStatus();

        if (status.getSeverity() == IStatus.OK)
        {
        	if (status.isUpgradeOccurred())
        	{
        		handle05_to_10(status);
        	}
        }
        else
        {
        	handleErrorInMigration(status);
        }
    }

    private static void handle05_to_10(UpgradeStatus status)
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Handle05_to_10_migration(status));
    }

    private static void handleErrorInMigration(UpgradeStatus status)
    {
    	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
    	{
    		public void run()
    		{
    			final Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
    			MessageDialog.openError(shell, Messages.RegistryUpgradeCommitHandler_Title, Messages.RegistryUpgradeCommitHandler_Text);
    		}
    	});
    }
    
    private static class Handle05_to_10_migration implements Runnable
    {
        private final UpgradeStatus     _status;

        Handle05_to_10_migration(UpgradeStatus status)
        {
            _status = status;
        }

        public void run()
        {
            // no prompting necessary.  just commit.
       		doConfirmed(false);
        }
        
    	private void doConfirmed(boolean userWantsMigrationDocLaunch) {
    		// delete V1 registry, leave backup file
    		IStatus result = _status.commit();
    		
    		if (result.getSeverity() != IStatus.OK)
    		{
    			final Shell  shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    			MessageDialog.openError(shell, Messages.RegistryMigrationStartupHandler_Error_committing_migration, result.getMessage());
    		}
    		
    		// if user confirmed, launch migration doc
    		if (userWantsMigrationDocLaunch)
				try {
					IWorkbenchBrowserSupport browserSupport = 
						PlatformUI.getWorkbench().getBrowserSupport();
					URL url = new URL("http://wiki.eclipse.org/index.php/JSF_Library_Migration"); //$NON-NLS-1$
					browserSupport.createBrowser("JSFMigrationDoc").openURL(url); //$NON-NLS-1$
				} catch (PartInitException e) {
					TapestryCorePlugin.log(e,"Error handling migration"); //$NON-NLS-1$
				} catch (MalformedURLException e) {
					TapestryCorePlugin.log(e,"Error handling migration"); //$NON-NLS-1$
				}
    	}
    	
    }
}
