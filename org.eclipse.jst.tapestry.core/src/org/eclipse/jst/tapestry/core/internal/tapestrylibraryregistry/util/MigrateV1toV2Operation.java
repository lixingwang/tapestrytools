package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryRegistryUtil;


public class MigrateV1toV2Operation extends VersionUpgradeOperation {

	private final URI		_v1Registry;
	private final URI		_v2Registry;
	
	/**
	 * @param label
	 * @param v1Registry
	 * @param v2Registry 
	 */
	public MigrateV1toV2Operation(String label, URI v1Registry, URI v2Registry) {
		super(label, 1, 2);
		_v1Registry = v1Registry;
		_v2Registry = v2Registry;
	}

	public IStatus doCommit() {
		TapestryLibraryRegistryUpgradeUtil.deleteFile(_v1Registry.toFileString());
		return Status.OK_STATUS;
	}

	public IStatus doExecute(IProgressMonitor monitor, IAdaptable info)
	{
		TapestryLibraryRegistryUpgradeUtil.copyFile
			(_v1Registry.toFileString(), TapestryLibraryRegistryUpgradeUtil.getBackupFileName(_v1Registry.toFileString()));
		TapestryLibraryRegistryResourceFactoryImpl resourceFactory = new TapestryLibraryRegistryResourceFactoryImpl();
		TapestryLibraryRegistryResourceImpl res = (TapestryLibraryRegistryResourceImpl)resourceFactory.createResource(_v1Registry);
		try {
			URI newRegURI = 
				TapestryLibraryRegistryUpgradeUtil.getRegistryURI
					(TapestryLibraryRegistryUpgradeUtil.JSF_LIBRARY_REGISTRY_V2_URL);
			Map options = new HashMap();
			//disable notifications during load to avoid changing stored default implementation
			options.put(XMLResource.OPTION_DISABLE_NOTIFY, Boolean.TRUE);
			res.load(options);
			//if we got this far then the registry was empty
			//"upgrade" to v2 and then delete old.   no point in upgrade status being sent
			TapestryLibraryRegistryUtil.getInstance().saveJSFLibraryRegistry();
			TapestryLibraryRegistryUpgradeUtil.copyFile(_v1Registry.toFileString(), newRegURI.toFileString());//save as v2 file	
			TapestryLibraryRegistryUpgradeUtil.deleteFile(_v1Registry.toFileString());

			return new UpgradeStatus();//all is ok and no need to alert user
			
		} catch(IOException ioe) {
			//this was expected... if there was actual v1 contents in the regsistry... upgrade by saving
			//perform save which will lose the ID
			try {
				res.save(Collections.EMPTY_MAP);
				//create v2 xml file
				URI newRegURI = 
					TapestryLibraryRegistryUpgradeUtil.getRegistryURI
						(TapestryLibraryRegistryUpgradeUtil.JSF_LIBRARY_REGISTRY_V2_URL);
				TapestryLibraryRegistryUpgradeUtil.copyFile(_v1Registry.toFileString(), newRegURI.toFileString());
				//delete upgraded v1
				TapestryLibraryRegistryUpgradeUtil.deleteFile(_v1Registry.toFileString());
				//restore backup to v1 name
				TapestryLibraryRegistryUpgradeUtil.copyFile(_v1Registry.toFileString().concat(".bkp"), _v1Registry.toFileString()); //$NON-NLS-1$
				//Alert end user
				return new UpgradeStatus(IStatus.OK, true, Messages.TapestryRegistryMigration05_to_10_customMessage);
			} catch(IOException e) {
				TapestryCorePlugin.log(IStatus.ERROR, "Error during repository upgrade from v1 to v2", e); //$NON-NLS-1$
				return new UpgradeStatus(IStatus.ERROR, false, 	
						Messages.TapestryRegistryMigrationCannot05_to_10_customMessage);
			}
		}
		//return ;
	}

	public IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return doExecute(monitor, info);
	}

	
	public boolean canUndo() {
		// commit is undoable for this operation
		return super.canUndo() && !hasCommitted();
	}

	public IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException 
	{
		//restore backup to v1 name
		TapestryLibraryRegistryUpgradeUtil.copyFile(_v1Registry.toFileString().concat(".bkp"), _v1Registry.toFileString()); //$NON-NLS-1$

		// delete the new registry 
		TapestryLibraryRegistryUpgradeUtil.deleteFile(_v2Registry.toFileString());
		
		//and the backup
		TapestryLibraryRegistryUpgradeUtil.deleteFile(_v1Registry.toFileString().concat(".bkp")); //$NON-NLS-1$
		
		return Status.OK_STATUS;
	}
}
