package org.eclipse.jst.tapestry.core.internal.tapestrylibraryregistry;

public interface PluginProvidedTapestryLibrary extends TapestryLibrary{

	public static final String ID_SEPARATOR = "$$"; //$NON-NLS-1$

	String copyright = "Copyright (c) open source"; //$NON-NLS-1$


	String getPluginID();

	void setPluginID(String value);


	String getLabel();

	void setLabel(String value);
}
