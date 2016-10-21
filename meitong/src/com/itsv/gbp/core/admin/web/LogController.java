package com.itsv.gbp.core.admin.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.LogService;
import com.itsv.gbp.core.admin.vo.AppLog;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

/**
 * ˵������־ǰ�˴����ࡣ���������־�ķ�ҳ��ѯ��<br>
 * 
 * ������Ҫ��ʾ���¹��ܣ�
 * <ol>
 * <li>���ڲ����Զ�ת�������ͣ�ͨ��registerEditor()����ע���Զ��������ת������<li>
 * <li>ͨ����дdoQuery(),getQueryName()����ʵ�ִ�����ķ�ҳ��ѯ.</li>
 * </ol>
 * 
 * @author admin 2005-2-4
 */
public class LogController extends BaseCURDController<AppLog> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(LogController.class);

	private static final String QUERY_NAME = "query.log"; //��ѯ�����session��Ĵ洢����

	private LogService logService; //�߼������

	/**
	 * ע���Զ�������ת���࣬����ѯ����ʼ���ںͽ��������ַ���ת��ΪDate����
	 */
	protected void registerEditor(DataBinder binder) {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
	}

	//���Ǹ��෽����Ĭ��ִ��query()
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		AppLog log = param2Object(request);
		this.logService.queryByVO(records, log);

		//����ѯ�������ظ�ҳ��
		mnv.addObject("log", log);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}