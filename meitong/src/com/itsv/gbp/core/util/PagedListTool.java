package com.itsv.gbp.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.itsv.gbp.core.orm.paged.CachePagedList;

/**
 * ˵���������ҳ��ѯ����Ĺ�����
 * 
 * @author admin 2004-9-24
 */
public class PagedListTool {

	/**
	 * �õ����ݷ�ҳ���󣬲����ݴ���������ҳ�루page�������ö���Ҫ��ʾ��һҳ�����ݡ� ����ǵ�һ������(firstQuery=y)���ᴴ��һ�����󣬴�ŵ�session��
	 * ����Ǻ���������session��ȡ������
	 * 
	 * �������null�����ʾ���ǵ�һ�β�ѯ����session����û�иĶ���
	 * 
	 * @param request ����ҳ��Ϣ��request����
	 * @param queryName session�д�ŵķ�ҳ��ѯ������������
	 * @param pageName ������ҳ�����
	 * @return �´������session��õķ�ҳ����
	 */
	public static CachePagedList getPagedList(HttpServletRequest request, String queryName, String pageName) {
		CachePagedList records = null;

		int page = ServletRequestUtils.getIntParameter(request, pageName, -1);
		if (page == -1) { //��һ�������ѯ
			records = new CachePagedList();
			//����������һ����ѯ���ƣ���ʾ����
			records.setQueryName(queryName);

			//����session
			request.getSession().removeAttribute(CachePagedList.NAME);
			request.getSession().setAttribute(CachePagedList.NAME, records);
		} else { //��̲�ѯ
			records = (CachePagedList) request.getSession().getAttribute(CachePagedList.NAME);
			if (null != records) {
				records.setPage(page);
			}
		}

		return records;

	}

	/**
	 * ���淽���ļ򻯣�Ĭ��ҳ�����Ϊpage
	 */
	public static CachePagedList getPagedList(HttpServletRequest request, String queryName) {
		return getPagedList(request, queryName, "page");
	}
	
	/**
	 * ֧��jqueryEasyUI��д������easyUI��ʼҲ����page=1���淽����Ĭ��page=0��,Ĭ��ҳ�����page
	 */
	public static CachePagedList getEuiPagedList(HttpServletRequest request, String queryName, String pageName) {
		CachePagedList records = null;
		int page = ServletRequestUtils.getIntParameter(request, pageName, -1);
		if (page == 1) { //��һ�������ѯ
			records = new CachePagedList();
			//����������һ����ѯ���ƣ���ʾ����
			records.setQueryName(queryName);

			//����session
			request.getSession().removeAttribute(CachePagedList.NAME);
			request.getSession().setAttribute(CachePagedList.NAME, records);
		} else { //��̲�ѯ
			records = (CachePagedList) request.getSession().getAttribute(CachePagedList.NAME);
			if (null != records) {
				records.setPage(page);
			}
		}
		return records;
	}
	/**
	 * Ҳ�ṩ��д������jqueryEasyUI�ش�����ת��ҳ�����Ϊ�̶�ֵ"page"
	 */
	public static CachePagedList getEuiPagedList(HttpServletRequest request, String queryName) {
		return getEuiPagedList(request, queryName, "page");
	}

}