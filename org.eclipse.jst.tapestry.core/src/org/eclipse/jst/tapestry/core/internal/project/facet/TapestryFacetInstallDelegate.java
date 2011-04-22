package org.eclipse.jst.tapestry.core.internal.project.facet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.ModelProviderManager;
import org.eclipse.jst.javaee.web.WebApp;
import org.eclipse.jst.tapestry.core.internal.Messages;
import org.eclipse.jst.tapestry.core.internal.TapestryCorePlugin;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Tapestry Facet Install Delegate for WTP faceted web projects. Deals with 2.5
 * web app models.
 * 
 * Uses
 * <code>org.eclipse.jst.tapestry.core.internal.project.facet<code> for model
 * 	 <li> creates Tapestry configuration file if not already present.
 * 	 <li> updates web.xml for: servlet, servlet-mapping and context-param
 * 	 <li> adds implementation jars to WEB-INF/lib if user requests
 * 
 */

public final class TapestryFacetInstallDelegate implements IDelegate {

	private final boolean tapestryFacetConfigurationEnabled = TapestryFacetConfigurationUtil
			.isTapestryFacetConfigurationEnabled();

	public void execute(IProject project, IProjectFacetVersion fv, Object cfg,
			IProgressMonitor monitor) throws CoreException {
		if (monitor != null) {
			monitor.beginTask("", 1); //$NON-NLS-1$
		}

		try {
			IDataModel config = null;

			if (cfg != null) {
				config = (IDataModel) cfg;
			} else {
				throw new TapestryFacetException(
						Messages.TapestryFacetInstallDelegate_InternalErr);
			}

			final TapestryUtils tapestryUtil = new TapestryUtilFactory()
					.create(fv, ModelProviderManager.getModelProvider(project));
			if (tapestryUtil == null) {
				throw new TapestryFacetException(NLS.bind(
						Messages.Could_Not_GetTapestryVersion, fv.toString()));
			}

			if (tapestryFacetConfigurationEnabled) {
				// Before we do any configuration, verify that web.xml is //
				// available for update
				final IModelProvider provider = tapestryUtil.getModelProvider();
				if (provider == null) {
					throw new TapestryFacetException(NLS.bind(
							Messages.TapestryFacetInstallDelegate_ConfigErr,
							project.getName()));
				} else if (!(provider.validateEdit(null, null).isOK())) {
					if (!(provider.validateEdit(null, null).isOK())) {// checks
																		// for
																		// web.xml
																		// file
																		// being
																		// read-only
																		// and
																		// allows
																		// user
																		// to
																		// set
																		// writeable
						throw new TapestryFacetException(
								NLS.bind(
										Messages.TapestryFacetInstallDelegate_NonUpdateableWebXML,
										project.getName()));
					}
				}
			}

			Object obj = config
					.getProperty(ITapestryFacetInstallDataModelProperties.LIBRARY_PROVIDER_DELEGATE);
			// Configure libraries
			((LibraryInstallDelegate) obj).execute(new NullProgressMonitor());

			final LibraryInstallDelegate libDelegate = (LibraryInstallDelegate) (config
					.getProperty(ITapestryFacetInstallDataModelProperties.LIBRARY_PROVIDER_DELEGATE));
			final LibraryProviderOperationConfig libConfig = libDelegate
					.getLibraryProviderOperationConfig();

			if (tapestryFacetConfigurationEnabled) {
				// Copy Tapestry jar libraries to web project
				copyTapestryJarLibraires(project, fv, config, monitor,
						tapestryUtil);

				// Update web model
				createServletAndModifyWebXML(project, config, monitor,
						tapestryUtil);

				addTapestryDefaultPackage(project,tapestryUtil);
			}

			if (monitor != null) {
				monitor.worked(1);
			}

		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}

	}

	private IPath resolveConfigPath(final IProject project, final String path) {
		return ComponentCore.createComponent(project).getRootFolder()
				.getUnderlyingFolder().getRawLocation().append(new Path(path));

	}

	private void copyTapestryJarLibraires(final IProject project,
			final IProjectFacetVersion fv, final IDataModel config,
			final IProgressMonitor monitor, final TapestryUtils jsfUtil) {

		final String libPath = "/WEB-INF/lib/";
		IPath configPath = resolveConfigPath(project, libPath);
		final String[] jarList = new String[] { "antlr-runtime-3.1.1.jar",
				"commons-codec-1.3.jar", "javassist-3.9.0.GA.jar",
				"log4j-1.2.14.jar", "slf4j-api-1.5.2.jar",
				"slf4j-log4j12-1.5.2.jar", "stax-api-1.0.1.jar",
				"stax2-api-3.0.1.jar", "tapestry-core-5.1.0.5.jar",
				"tapestry-ioc-5.1.0.5.jar",
				"tapestry5-annotations-5.1.0.5.jar",
				"woodstox-core-asl-4.0.3.jar", };
		try {
			// if this path exists
			if (configPath.toFile().exists()) {// configPath.toFile().exists()
				final IWorkspaceRunnable op = new IWorkspaceRunnable() {
					public void run(final IProgressMonitor monitor_inner)
							throws CoreException {
						String absolutePath = ComponentCore
								.createComponent(project).getRootFolder()
								.getUnderlyingFolder().getRawLocation()
								.toString();
						String webContent = absolutePath.substring(absolutePath
								.lastIndexOf("/"));
						for (int i = 0; i < jarList.length; i++) {
							String jar = jarList[i];
							IFile eachJar = project.getFile(webContent
									+ libPath + jar);
							if (!eachJar.exists()) {
								eachJar.create(this.getClass().getClassLoader()
										.getResourceAsStream("jars/" + jar),
										false, null);
							}
						}
						project.refreshLocal(IResource.DEPTH_INFINITE,
								monitor_inner);
					}
				};
				op.run(monitor);
			}
		} catch (final CoreException e) {
			TapestryCorePlugin.log(e,
					"Exception occured while copying Tapestry jar libraries");
		}

	}
	
	/**
	 * Add default package for Tapestry web project
	 * @param project
	 * @param tapestryUtil
	 */
	private void addTapestryDefaultPackage(final IProject project, final TapestryUtils tapestryUtil) {
	IJavaProject javaProject = JavaCore.create(project);
	IPackageFragmentRoot[] packageFragmentRoots;
	try {
		packageFragmentRoots = javaProject.getAllPackageFragmentRoots();
		IPackageFragmentRoot packageFragmentRoot = packageFragmentRoots[0];
		IPackageFragment ipf=packageFragmentRoot.createPackageFragment(tapestryUtil.Tapestry_DEFAULT_CONFIG_PATH, false,null);
	} catch (JavaModelException e) {
		e.printStackTrace();
	}
	
	}

	/**
	 * Create servlet and URL mappings and update the webapp
	 * 
	 * @param project
	 * @param config
	 * @param monitor
	 */
	private void createServletAndModifyWebXML(final IProject project,
			final IDataModel config, final IProgressMonitor monitor,
			final TapestryUtils tapestryUtil) {

		final IModelProvider provider = tapestryUtil.getModelProvider();
		final IPath webXMLPath = new Path("WEB-INF").append("web.xml"); //$NON-NLS-1$ //$NON-NLS-2$
		if (tapestryUtil.isJavaEE(provider.getModelObject())) {
			provider.modify(new UpdateWebXMLForJavaEE(project, config,
					tapestryUtil),
					doesDDFileExist(project, webXMLPath) ? webXMLPath
							: IModelProvider.FORCESAVE);
		} else {// must be 2.3 or 2.4
			provider.modify(new UpdateWebXMLForJ2EE(project, config,
					tapestryUtil), webXMLPath);
		}
		// TODO: is the MyFaces check a todo?
		// Check if runtime is MyFaces or Sun-RI
	}

	private boolean doesDDFileExist(final IProject project,
			final IPath webXMLPath) {
		return project.getProjectRelativePath().append(webXMLPath).toFile()
				.exists();
	}

	private class UpdateWebXMLForJavaEE implements Runnable {
		private final IProject project;
		private final IDataModel config;
		private final TapestryUtils tapestryUtil;

		UpdateWebXMLForJavaEE(final IProject project, final IDataModel config,
				final TapestryUtils tapestryUtil) {
			this.project = project;
			this.config = config;
			this.tapestryUtil = tapestryUtil;
		}

		public void run() {
			final WebApp webApp = (WebApp) ModelProviderManager
					.getModelProvider(project).getModelObject();
			tapestryUtil.updateWebApp(webApp, config);
		}
	}

	private class UpdateWebXMLForJ2EE implements Runnable {
		private final IProject project;
		private final IDataModel config;
		private final TapestryUtils tapestryUtil;

		UpdateWebXMLForJ2EE(final IProject project, final IDataModel config,
				final TapestryUtils tapestryUtil) {
			this.project = project;
			this.config = config;
			this.tapestryUtil = tapestryUtil;
		}

		public void run() {
			final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
					.getModelProvider(project).getModelObject();
			tapestryUtil.updateWebApp(webApp, config);
		}

	}
}
