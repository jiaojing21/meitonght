package com.itsv.gbp.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.itsv.gbp.core.orm.paged.CachePagedList;

/**
 * 说明：处理分页查询对象的工具类
 * 
 * @author admin 2004-9-24
 */
public class PagedListTool {

	/**
	 * 得到数据分页对象，并根据传来的请求页码（page），设置对象要显示哪一页的数据。 如果是第一次请求(firstQuery=y)，会创建一个对象，存放到session中
	 * 如果是后继请求，则从session中取出对象
	 * 
	 * 如果返回null，则表示不是第一次查询，但session中又没有改对象。
	 * 
	 * @param request 带分页信息的request对象
	 * @param queryName session中存放的分页查询结果对象的名字
	 * @param pageName 传来的页码参数
	 * @return 新创建或从session获得的分页对象
	 */
	public static CachePagedList getPagedList(HttpServletRequest request, String queryName, String pageName) {
		CachePagedList records = null;

		int page = ServletRequestUtils.getIntParameter(request, pageName, -1);
		if (page == -1) { //第一次请求查询
			records = new CachePagedList();
			//给对象设置一个查询名称，以示区别
			records.setQueryName(queryName);

			//存入session
			request.getSession().removeAttribute(CachePagedList.NAME);
			request.getSession().setAttribute(CachePagedList.NAME, records);
		} else { //后继查询
			records = (CachePagedList) request.getSession().getAttribute(CachePagedList.NAME);
			if (null != records) {
				records.setPage(page);
			}
		}

		return records;

	}

	/**
	 * 上面方法的简化，默认页码参数为page
	 */
	public static CachePagedList getPagedList(HttpServletRequest request, String queryName) {
		return getPagedList(request, queryName, "page");
	}
	
	/**
	 * 支持jqueryEasyUI的写法（因easyUI开始也传人page=1上面方法是默认page=0）,默认页码参数page
	 */
	public static CachePagedList getEuiPagedList(HttpServletRequest request, String queryName, String pageName) {
		CachePagedList records = null;
		int page = ServletRequestUtils.getIntParameter(request, pageName, -1);
		if (page == 1) { //第一次请求查询
			records = new CachePagedList();
			//给对象设置一个查询名称，以示区别
			records.setQueryName(queryName);

			//存入session
			request.getSession().removeAttribute(CachePagedList.NAME);
			request.getSession().setAttribute(CachePagedList.NAME, records);
		} else { //后继查询
			records = (CachePagedList) request.getSession().getAttribute(CachePagedList.NAME);
			if (null != records) {
				records.setPage(page);
			}
		}
		return records;
	}
	/**
	 * 也提供简化写法，因jqueryEasyUI回传的跳转至页码参数为固定值"page"
	 */
	public static CachePagedList getEuiPagedList(HttpServletRequest request, String queryName) {
		return getEuiPagedList(request, queryName, "page");
	}

}