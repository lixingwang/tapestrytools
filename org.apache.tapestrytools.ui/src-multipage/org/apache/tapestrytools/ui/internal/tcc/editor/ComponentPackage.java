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
