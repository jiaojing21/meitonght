package com.itsv.gbp.core.web.springmvc;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.GenericsUtils;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.WebException;

/**
 * ��������ɾ�Ĳ�ǰ�˴����ࡣ<br>
 * ʵ���˶�ָ�������������ɾ�Ĳ鷽����<br>
 * ���⣬�����˴������ݰ󶨺�����У�����������������<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-4 ����04:10:10
 * @version 1.0
 */
public class BaseCURDController<V> extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseCURDController.class);

	private static final String DEFAULT_QUERY_NAME = "query.default";

	private String indexView; //�����б���ʾҳ��

	private String addView; //��������ҳ��

	private String editView; //�����޸�ҳ��

	private String showView; //��ϸ��Ϣ��ʾҳ��

	protected Class<V> voClass;

	public BaseCURDController() {
		voClass = GenericsUtils.getGenericClass(getClass());
		
		
		
	}

	/**
	 * ����һ��command���������Ҫ�������Ĳ����Զ��󶨵�VO���󣬾���Ҫ�÷���
	 * 
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController.newCommandObject()
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController.invokeNamedMethod()
	 */
	protected Object newCommandObject(V vo) throws WebException {

		try {
			return BeanUtils.instantiateClass(getVoClass());
		} catch (Exception e) {
			logger.error("ʵ������[" + getVoClass().getName() + "]ʱ����", e);
			throw new WebException("ʵ������[" + getVoClass().getName() + "]ʱ����", e);
		}
	}

	//��ʾ���Ӷ���ҳ��
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getAddView());
		beforeShowAdd(request, response, mnv);
		return mnv;
	}

	//��ʾ�޸�ҳ��
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getEditView());
		beforeShowEdit(request, response, mnv);
		return mnv;
	}

	//�б���ʾ���󡣲���ҳ���
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getIndexView());
		beforeShowList(request, response, mnv);
		return mnv;
	}

	//��ҳ��ѯ����
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getIndexView());
		//����������״β�ѯ�ͷ�ҳ�й�
		CachePagedList records = PagedListTool.getPagedList(request, getQueryName());
		if (null == records) {
			showMessage(request, "���������ò�ѯ����");
			return mnv;
		}
		if (!records.exists()) {
			doQuery(request, response, mnv, records);
		}

		mnv.addObject(WebConfig.DATA_NAME, records);

		return mnv;
	}

	//��ѯ��session�е����ƣ�Ϊ�����ֲ�ͬ�Ĳ�ѯ���󡣽���������д���Ա�����
	protected String getQueryName() {
		return DEFAULT_QUERY_NAME;
	}

	//����ʵ�ʵķ�ҳ��ѯ����Ҫ������д
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
	}

	//��ʾ����ҳ���ǰ��������һ������һЩ��������
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�ҳ���ǰ��������һ��������޸ĵĶ����������������
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
	}

	//��ʾ�б�ҳ���ǰ����������ѯ�������б����ݣ����������ý�ModelAndView����
	protected void beforeShowList(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
	}

	//����newCommandObject()����ʵ�����������󣬲����ֵ
	protected V param2Object(ServletRequest request) throws WebException {
		V command = null;
		try {
			command = (V) newCommandObject(getVoClass());
		} catch (Exception e) {
			logger.error("ʵ����command��[" + (command == null ? "null" : command.getClass().getName()) + "]ʱ����",
					e);
			throw new WebException("ʵ����command[" + (command == null ? "null" : command.getClass().getName())
					+ "]ʱ����", e);
		}

		param2Object(request, command);
		return command;
	}

	/**
	 * �������õ�validator����������У�顣���У��ʧ�ܣ���������Ϣ��ʾ���ͻ��ˡ�
	 * 
	 * @param request
	 * @param obj
	 * @return ����У���Ƿ�ɹ�
	 */
	public boolean validate(HttpServletRequest request, Object obj) {
		BindingResult errors = new BeanPropertyBindingResult(obj, DEFAULT_COMMAND_NAME);
		Validator[] validators = getValidators();
		//У��
		if (validators != null) {
			for (Validator validator : validators) {
				if (validator.supports(obj.getClass())) {
					ValidationUtils.invokeValidator(validator, obj, errors);
				}
			}
		}

		//����д�����Ϣ����ʾ���ͻ���
		if (errors.hasErrors()) {
			StringBuffer msg = new StringBuffer("����У��ʧ��:\n ");
			for (Object error : errors.getAllErrors()) {
				msg.append(
						getApplicationContext().getMessage((MessageSourceResolvable) error,
								Locale.getDefault())).append("\n ");
			}
			showMessage(request, msg.toString());
		}

		return !errors.hasErrors();
	}

	/** ����Ϊ�򵥵�get,set���� */
	protected Class getVoClass() {
		return voClass;
	}

	public String getAddView() {
		return addView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public String getEditView() {
		return editView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getShowView() {
		return showView;
	}

	public void setShowView(String showView) {
		this.showView = showView;
	}
}
