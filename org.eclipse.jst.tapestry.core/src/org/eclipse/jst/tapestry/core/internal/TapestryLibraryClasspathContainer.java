package org.eclipse.jst.tapestry.core.internal;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryconfig.TapestryLibraryRegistryUtil;
import org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry.TapestryLibrary;
import org.eclipse.jst.tapestry.core.tapestrylibraryconfiguration.TapestryLibraryConfigurationHelper;

public class TapestryLibraryClasspathContainer implements IClasspathContainer {
	private static final String NON_IMPL_DESC = Messages.TapestryLibraryClasspathContainer_NON_IMPL_LIBRARY;
	private static final String IMPL_DESC = Messages.TapestryLibraryClasspathContainer_IMPL_LIBRARY;
	
	private TapestryLibrary lib;
	
	/**
	 * @param lib 
	 */
	public TapestryLibraryClasspathContainer(TapestryLibrary lib) {
		this.lib = lib;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
	 */
	public IClasspathEntry[] getClasspathEntries() {
		return TapestryLibraryRegistryUtil.getInstance().getClasspathEntries(lib);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
	 */
	public String getDescription() {
		StringBuffer buf = new StringBuffer(lib.getLabel());
		buf.append(" "); //$NON-NLS-1$
		if (lib.isImplementation())
			buf.append(IMPL_DESC);
		else
			buf.append(NON_IMPL_DESC);
		
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
	 */
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
	 */
	public IPath getPath() {		
		return new Path(TapestryLibraryConfigurationHelper.Tapestry_LIBRARY_CP_CONTAINER_ID).append(this.lib.getID());
	}

}

