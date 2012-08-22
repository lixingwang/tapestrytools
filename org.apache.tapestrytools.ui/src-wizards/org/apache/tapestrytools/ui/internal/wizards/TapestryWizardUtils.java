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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class TapestryWizardUtils {
	
	/**
	 * To check whether current project is Maven(m2eclipse) project
	 * 
	 * @param project
	 * @return
	 */
	public static boolean isMavenProject(IProject project){
    	try {
			IProjectNature nature = project.getNature("org.eclipse.m2e.core.maven2Nature");
			return nature != null;
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
    	return false;
    }
}
