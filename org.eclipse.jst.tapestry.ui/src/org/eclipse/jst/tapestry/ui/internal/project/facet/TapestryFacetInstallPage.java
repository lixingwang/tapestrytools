
package org.eclipse.jst.tapestry.ui.internal.project.facet;

import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.jst.tapestry.core.internal.project.facet.ITapestryFacetInstallDataModelProperties;
import org.eclipse.jst.tapestry.core.internal.project.facet.TapestryFacetConfigurationUtil;
import org.eclipse.jst.tapestry.ui.internal.TapestryUiPlugin;
import org.eclipse.jst.tapestry.ui.internal.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.ui.IFacetWizardPage;
import org.eclipse.wst.common.project.facet.ui.IWizardContext;

/**
 * Tapestry Facet installation wizard page.
 * 
 * @author gavingui2011@gmail.com - Beijing China
 */
public class TapestryFacetInstallPage extends DataModelWizardPage implements
		ITapestryFacetInstallDataModelProperties, IFacetWizardPage {
    
    private final boolean tapestryFacetConfigurationEnabled = TapestryFacetConfigurationUtil.isTapestryFacetConfigurationEnabled();
    
	// UI
	private Label lblTapestryConfig;
	private Text txtTapestryConfig;
	private Label lblTapestryServletName;
	private Text txtTapestryServletName;
	private Label lblTapestryServletClassName;
	private Text txtTapestryServletClassName;	
	private Label lblTapestryServletURLPatterns;
	private List lstTapestryServletURLPatterns;
	private Button btnAddPattern;
	private Button btnRemovePattern;

	private IDialogSettings dialogSettings;
	private IDataModel webAppDataModel;
	private static final String SETTINGS_ROOT = TapestryUiPlugin.PLUGIN_ID
			+ ".tapestryFacetInstall"; //$NON-NLS-1$
	private static final String SETTINGS_CONFIG = "configPath"; //$NON-NLS-1$
	private static final String SETTINGS_SERVLET = "servletName"; //$NON-NLS-1$
	private static final String SETTINGS_SERVLET_CLASSNAME = "servletClassname"; //$NON-NLS-1$
	private static final String SETTINGS_URL_MAPPINGS = "urlMappings"; //$NON-NLS-1$
	private static final String SETTINGS_URL_PATTERN = "pattern"; //$NON-NLS-1$
	
	// private String projectName = null;
	private Composite composite = null;

	/**
	 * Zero argument constructor
	 */
	public TapestryFacetInstallPage() {
		// FIXME: following WebFacetInstallPage pattern which will be fixed at somepoint
		super(DataModelFactory.createDataModel(new AbstractDataModelProvider() {/*
																				 * do
																				 * nothing
																				 */
		}), "tapestry.facet.install.page"); //$NON-NLS-1$
		setTitle(Messages.TapestryFacetInstallPage_title);
		setDescription(Messages.TapestryFacetInstallPage_description);
		dialogSettings = TapestryUiPlugin.getDefault().getDialogSettings();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	protected Composite createTopLevelComposite(final Composite parent) {
		initializeDialogUnits(parent);
		composite = new Composite(parent, SWT.NONE);
		final GridLayout jsfCompositeLayout = new GridLayout(3, false);
		jsfCompositeLayout.marginTop = 0;
		jsfCompositeLayout.marginBottom = 0;
		jsfCompositeLayout.marginRight = 0;
		jsfCompositeLayout.marginLeft = 0;
		composite.setLayout(jsfCompositeLayout);
		
		final LibraryInstallDelegate librariesInstallDelegate
		    = (LibraryInstallDelegate) getDataModel().getProperty( LIBRARY_PROVIDER_DELEGATE );
		
		final Control librariesComposite
		    = LibraryProviderFrameworkUi.createInstallLibraryPanel( composite, librariesInstallDelegate,
		                                                            Messages.TapestryFacetInstallPage_TapestryImplementationLibrariesFrame );
		
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = 3;
		
		librariesComposite.setLayoutData( gd );
		
		final Label spacer = new Label( composite, SWT.NONE );
		spacer.setText( "" ); //$NON-NLS-1$

        gd = new GridData( GridData.FILL_HORIZONTAL );
        gd.horizontalSpan = 3;
        
		spacer.setLayoutData( gd );

        if (tapestryFacetConfigurationEnabled)
        {
        	lblTapestryServletClassName = new Label(composite, SWT.NONE);
    		lblTapestryServletClassName
    				.setText(Messages.TapestryFacetInstallPage_TapestryServletClassNameLabel);
    		lblTapestryServletClassName.setLayoutData(new GridData(GridData.BEGINNING));
    
    		txtTapestryServletClassName = new Text(composite, SWT.BORDER);
    		txtTapestryServletClassName.setEditable(false);
    		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
    		gd1.horizontalSpan = 2;
    		txtTapestryServletClassName.setLayoutData(gd1);
        	
        	
        	lblTapestryConfig = new Label(composite, SWT.NONE);
    		lblTapestryConfig.setText(Messages.TapestryFacetInstallPage_TapestryConfigLabel);
    		lblTapestryConfig.setLayoutData(new GridData(GridData.BEGINNING));
    
    		txtTapestryConfig = new Text(composite, SWT.BORDER);
    		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
    		gd2.horizontalSpan = 2;
    		txtTapestryConfig.setLayoutData(gd2);
    
    		lblTapestryServletName = new Label(composite, SWT.NONE);
    		lblTapestryServletName
    				.setText(Messages.TapestryFacetInstallPage_TapestryServletNameLabel);
    		lblTapestryServletName.setLayoutData(new GridData(GridData.BEGINNING));
    
    		txtTapestryServletName = new Text(composite, SWT.BORDER);
    		GridData gd2c = new GridData(GridData.FILL_HORIZONTAL);
    		gd2c.horizontalSpan = 2;
    		txtTapestryServletName.setLayoutData(gd2c);
    
    		
    		
    		lblTapestryServletURLPatterns = new Label(composite, SWT.NULL);
    		lblTapestryServletURLPatterns
    				.setText(Messages.TapestryFacetInstallPage_TapestryURLMappingLabel);
    		lblTapestryServletURLPatterns.setLayoutData(new GridData(GridData.BEGINNING
    				| GridData.VERTICAL_ALIGN_BEGINNING));
    		lstTapestryServletURLPatterns = new List(composite, SWT.BORDER);
    		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
    		gd3.heightHint = convertHeightInCharsToPixels(5);
    		lstTapestryServletURLPatterns.setLayoutData(gd3);
    		lstTapestryServletURLPatterns.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent e) {
    				btnRemovePattern.setEnabled(lstTapestryServletURLPatterns
    						.getSelectionCount() > 0);
    			}
    		});
    
    		Composite btnComposite = new Composite(composite, SWT.NONE);
    		GridLayout gl = new GridLayout(1, false);
    		// gl.marginBottom = 0;
    		// gl.marginTop = 0;
    		// gl.marginRight = 0;
    		gl.marginLeft = 0;
    		btnComposite.setLayout(gl);
    		btnComposite.setLayoutData(new GridData(GridData.END
    				| GridData.VERTICAL_ALIGN_FILL));
    
    		btnAddPattern = new Button(btnComposite, SWT.NONE);
    		btnAddPattern.setText(Messages.TapestryFacetInstallPage_Add2);
    		btnAddPattern.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
    				| GridData.VERTICAL_ALIGN_BEGINNING));
    		btnAddPattern.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent e) {
    				InputDialog dialog = new InputDialog(getShell(),
    						Messages.TapestryFacetInstallPage_PatternDialogTitle,
    						Messages.TapestryFacetInstallPage_PatternDialogDesc, null,
    						new IInputValidator() {
    
    							public String isValid(String newText) {
    								return isValidPattern(newText);
    							}
    
    						});
    				dialog.open();
    				if (dialog.getReturnCode() == Window.OK) {
    					addItemToList(dialog.getValue(), true);
    				}
    			}
    		});
    
    		btnRemovePattern = new Button(btnComposite, SWT.NONE);
    		btnRemovePattern.setText(Messages.TapestryFacetInstallPage_Remove);
    		btnRemovePattern.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
    				| GridData.VERTICAL_ALIGN_BEGINNING));
    		btnRemovePattern.setEnabled(false);
    		btnRemovePattern.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent e) {
    				removeItemFromList(lstTapestryServletURLPatterns.getSelection());
    				btnRemovePattern.setEnabled(false);
    			}
    		});
    
    		addModificationListeners();
        }
		
		return composite;
	}

	private void initializeValues() {
		IDialogSettings root = dialogSettings.getSection(SETTINGS_ROOT);

		initTapestryCfgCtrlValues(root);

		String conf = null;
		if (root != null)
			conf = root.get(SETTINGS_CONFIG);
		if (conf == null || conf.equals("")) { //$NON-NLS-1$
			conf = (String) model
					.getDefaultProperty(ITapestryFacetInstallDataModelProperties.CONFIG_PATH);
		}
		txtTapestryConfig.setText(conf);

		String servletName = null;
		if (root != null)
			servletName = root.get(SETTINGS_SERVLET);
		if (servletName == null || servletName.equals("")) { //$NON-NLS-1$
			servletName = (String) model
					.getDefaultProperty(ITapestryFacetInstallDataModelProperties.SERVLET_NAME);
		}
		txtTapestryServletName.setText(servletName);

		String servletClassname = null;
		if (root != null)
			servletClassname = root.get(SETTINGS_SERVLET_CLASSNAME);
		if (servletClassname == null || servletClassname.equals("")) { //$NON-NLS-1$
			servletClassname = (String) model
					.getDefaultProperty(ITapestryFacetInstallDataModelProperties.SERVLET_CLASSNAME);
		}
		txtTapestryServletClassName.setText(servletClassname);

		loadURLMappingPatterns(root);
	}

	private void initTapestryCfgCtrlValues(IDialogSettings root) {
		/*IDialogSettings complibs = null;
		if (root != null) {
			complibs = root.getSection(SETTINGS_COMPLIB);
		}

		String[] selection = null;
		if (complibs != null) {
			selection = complibs.getArray(SETTINGS_COMPLIB_SELECT_DEPLOY);
		}

		TapestryLibraryConfigDialogSettingData source = new TapestryLibraryConfigDialogSettingData(selection);
		jsfLibCfgComp.loadControlValuesFromModel(source);*/
	}

	
	private void saveSettings() {
		DialogSettings root = new DialogSettings(SETTINGS_ROOT);
		dialogSettings.addSection(root);

		root.put(SETTINGS_CONFIG, getTapestryConfig());
		root.put(SETTINGS_SERVLET, getTapestryServletName());
		root.put(SETTINGS_SERVLET_CLASSNAME, getTapestryServletClassname());
		DialogSettings mappings = new DialogSettings(SETTINGS_URL_MAPPINGS);
		root.addSection(mappings);
		mappings.put(SETTINGS_URL_PATTERN, getTapestryPatterns());
	}

	private String getTapestryConfig() {
		return txtTapestryConfig.getText().trim();
	}

	private String getTapestryServletName() {
		return txtTapestryServletName.getText().trim();
	}

	private String getTapestryServletClassname() {
		return txtTapestryServletClassName.getText().trim();
	}
	
	private String[] getTapestryPatterns() {
		return lstTapestryServletURLPatterns.getItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.common.project.facet.ui.IFacetWizardPage#setConfig(java.lang.Object)
	 */
	public void setConfig(Object config) {
		model.removeListener(this);
		synchHelper.dispose();

		model = (IDataModel) config;
		model.addListener(this);
		synchHelper = initializeSynchHelper(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.common.project.facet.ui.IFacetWizardPage#transferStateToConfig()
	 */
	public void transferStateToConfig() {
	    if (tapestryFacetConfigurationEnabled)
	    {
    		saveSettings(); // convenient place for this. don't want to save if user
    						// cancelled.
	    }
	}

	private void addModificationListeners() {
		 synchHelper.synchText(txtTapestryConfig, CONFIG_PATH, null);
		 synchHelper.synchText(txtTapestryServletName, SERVLET_NAME, null);
		 synchHelper.synchText(txtTapestryServletClassName, SERVLET_CLASSNAME, null);
		 synchHelper.synchList(lstTapestryServletURLPatterns, SERVLET_URL_PATTERNS, null);
	}

	private String isValidPattern(String value) {
		if (value == null || value.trim().equals("")) //$NON-NLS-1$
			return Messages.TapestryFacetInstallPage_PatternEmptyMsg;
		if (lstTapestryServletURLPatterns.indexOf(value) >= 0)
			return Messages.TapestryFacetInstallPage_PatternSpecifiedMsg;

		return null;
	}

	private void loadURLMappingPatterns(IDialogSettings root) {
		lstTapestryServletURLPatterns.removeAll();
		IDialogSettings mappings = null;
		if (root != null)
			mappings = root.getSection(SETTINGS_URL_MAPPINGS);
		String[] patterns = null;
		if (mappings != null)
			patterns = mappings.getArray(SETTINGS_URL_PATTERN);

		if (patterns == null || patterns.length == 0) {
			patterns = (String[]) model
					.getDefaultProperty(ITapestryFacetInstallDataModelProperties.SERVLET_URL_PATTERNS);
		}
		for (int i = 0; i < patterns.length; i++) {
			addItemToList(patterns[i], false);
		}
	}

	private void addItemToList(String pattern, boolean selectMe) {
		lstTapestryServletURLPatterns.add(pattern == null ? "" : pattern); //$NON-NLS-1$
		if (pattern == null && selectMe)
			lstTapestryServletURLPatterns.setSelection(lstTapestryServletURLPatterns
					.getItemCount() - 1);
		// When 119321 is fixed - remove code below
		updateModelForURLPattern();
	}

	private void removeItemFromList(String[] selection) {
		for (int i = 0; i < selection.length; i++) {
			String sel = selection[i];
			lstTapestryServletURLPatterns.remove(sel);
		}
		// When 119321 is fixed - remove code below
		updateModelForURLPattern();
	}

	private void updateModelForURLPattern() {
		model.setProperty(
				ITapestryFacetInstallDataModelProperties.SERVLET_URL_PATTERNS,
				lstTapestryServletURLPatterns.getItems());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#getValidationPropertyNames()
	 */
	protected String[] getValidationPropertyNames() {

	    if (tapestryFacetConfigurationEnabled)
	    {
	        return new String[] { CONFIG_PATH, SERVLET_NAME, SERVLET_CLASSNAME, COMPONENT_LIBRARIES, LIBRARY_PROVIDER_DELEGATE };
	    }
	    
        return new String[] { LIBRARY_PROVIDER_DELEGATE };
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.project.facet.ui.IFacetWizardPage#setWizardContext(org.eclipse.wst.common.project.facet.ui.IWizardContext)
	 */
	public void setWizardContext(IWizardContext context) {
		// hook into web datamodel if new project wizard.
		Iterator it = context.getSelectedProjectFacets().iterator();
		IProjectFacetVersion webFacetVersion = null;
		while (it.hasNext()) {
			// find Web facet
			IProjectFacetVersion pfv = (IProjectFacetVersion) it.next();
			if (pfv.getProjectFacet().getId().equals("jst.web")) { //$NON-NLS-1$
				webFacetVersion = pfv;
				break;
			}
		}
		if (webFacetVersion != null) {
			try {
				webAppDataModel = (IDataModel) context.getConfig(
						webFacetVersion, IFacetedProject.Action.Type.INSTALL,
						context.getProjectName());
				if (webAppDataModel != null) {
					webAppDataModel.addListener(this);
				}
			} catch (CoreException e) {
				TapestryUiPlugin.log(IStatus.ERROR,
						Messages.TapestryFacetInstallPage_ErrorNoWebAppDataModel, e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#propertyChanged(org.eclipse.wst.common.frameworks.datamodel.DataModelEvent)
	 */
	public void propertyChanged(DataModelEvent event) {
		if (webAppDataModel != null) {
			String propertyName = event.getPropertyName();
			if (propertyName
					.equals(IJ2EEModuleFacetInstallDataModelProperties.CONFIG_FOLDER)) {
				model.setStringProperty(WEBCONTENT_DIR, event.getProperty()
						.toString());
			}
		}
		super.propertyChanged(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#dispose()
	 */
	public void dispose() {
		if (webAppDataModel != null)
			webAppDataModel.removeListener(this);
		
		//jsfLibCfgComp.dispose();
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#restoreDefaultSettings()
	 */
	protected void restoreDefaultSettings() {
	    if (tapestryFacetConfigurationEnabled)
	    {
	        initializeValues();
	    }
	}
	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#showValidationErrorsOnEnter()
	 */
	protected boolean showValidationErrorsOnEnter() {
		return true;
	}


    /**
     * Fix for Bug Bug 300454: "Finish button in New Project wizard is enabled
     * even if Tapestry facet does not have library information"
     * https://bugs.eclipse.org/bugs/show_bug.cgi?id=300454
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete()
    {
        final LibraryInstallDelegate librariesInstallDelegate = (LibraryInstallDelegate) getDataModel().getProperty(LIBRARY_PROVIDER_DELEGATE);
        if (librariesInstallDelegate == null)
            throw new IllegalArgumentException("LibraryInstallDelegate is expected to be non-null"); //$NON-NLS-1$

        return super.isPageComplete() && (librariesInstallDelegate.validate().getSeverity() != IStatus.ERROR);
    }
}
