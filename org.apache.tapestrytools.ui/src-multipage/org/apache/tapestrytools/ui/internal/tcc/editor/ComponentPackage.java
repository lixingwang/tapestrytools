package org.apache.tapestrytools.ui.internal.tcc.editor;

public class ComponentPackage {
	private String prefix;
	private String path;
	private boolean archive;
	private String fragmentRoot;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public String getFragmentRoot() {
		return fragmentRoot;
	}
	public void setFragmentRoot(String fragmentRoot) {
		this.fragmentRoot = fragmentRoot;
	}

}
