package org.apache.tapestrytools.ui.internal.tcc.editor;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PackageDetailsTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	
	private Image packageImg = AbstractUIPlugin.imageDescriptorFromPlugin("org.apache.tapestrytools.ui", "icons/package_obj.gif").createImage();

	public Image getColumnImage(Object element, int columnIndex) {
		Image image = null;
		if(columnIndex == 0)
			image = packageImg;
		return image;
	}

	public String getColumnText(Object element, int columnIndex) {
		String text = null;
		if(columnIndex == 0) {
			text = (String) element;
		}
		return text;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		packageImg.dispose();
	}
}
