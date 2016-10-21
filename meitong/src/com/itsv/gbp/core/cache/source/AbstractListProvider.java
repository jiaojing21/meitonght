package com.itsv.gbp.core.cache.source;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itsv.gbp.core.cache.key.PropValueKeyGegerator;

/**
 * 抽象的List数据源提供者
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午03:11:22
 * @version 1.0
 */
public abstract class AbstractListProvider implements ICacheDataSource {

	abstract List getListData();

	private PropValueKeyGegerator keyGenerator = new PropValueKeyGegerator();

	public Map getKeyValues() {
		List data = getListData();
		if (data == null || data.isEmpty()) {
			return Collections.EMPTY_MAP;
		}

		Map map = new HashMap(data.size());
		for (Iterator iter = data.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			Object key = this.keyGenerator.generateKey(element);
			map.put(key, element);
		}

		return map;
	}

	public void setKeyProps(String propNames) {
		this.keyGenerator.setPropNames(propNames);
	}

}
