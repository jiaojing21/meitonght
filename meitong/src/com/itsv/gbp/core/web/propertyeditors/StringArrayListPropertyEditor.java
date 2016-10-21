package com.itsv.gbp.core.web.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 说明：自定义的类型转换器。将传来的逗号分开的字符串转换为ArrayList对象。
 * 
 * 
 * @see org.springframework.beans.propertyeditors.StringArrayPropertyEditor
 * 
 * @author admin 2005-2-1
 */
public class StringArrayListPropertyEditor extends PropertyEditorSupport {

	public void setAsText(String s) throws IllegalArgumentException {
		List list = new ArrayList();
		StringTokenizer st = new StringTokenizer(s, ",");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		setValue(list);
	}

	public String getAsText() {
		StringBuffer text = new StringBuffer();
		List list = (List) this.getValue();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			text.append(iter.next()).append(",");
		}
		if (text.length() > 0) {
			text.deleteCharAt(text.length() - 1);
		}
		return text.toString();
	}

	public static void main(String[] args) {
		String text = "100,101,102,103,104";
		StringArrayListPropertyEditor self = new StringArrayListPropertyEditor();

		self.setAsText(text);
	}
}