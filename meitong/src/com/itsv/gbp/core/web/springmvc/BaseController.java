package com.itsv.gbp.core.web.springmvc;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.service.BaseEntity;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.WebException;
import com.itsv.gbp.core.web.springmvc.support.MyRequestDataBinder;
import com.itsv.gbp.core.web.token.TokenProcessor;
import com.itsv.platform.common.fileMgr.util.SmartRequest;

/**
 * ˵����ǰ�˴����Ļ��࣬����spring��MultiActionController�����鴦������̳и��ࡣ<br>
 * �����¼������ܣ� <br>
 * <ol>
 * <li>�ṩ��ȱʡ�Ľ����������ƺ�ȱʡ�����������ݴ����Ĳ������������ĸ��������� ������������ȱʡ��������Ҳ�������趨��<br>
 * <b>��ע�⣺</b>ȱʡ����������Ӧ�ķ���������֮��ʵ�֣������������û��ֵʱ����� </li>
 * <li>�ṩһ������showMessage()��������ͻ�����ʾ��Ϣ�� </li>
 * <li>�ṩ�˴�������ƣ��������������з����������û�û�д�������Զ����ó�����ʾҳ�棬��ʾ���û���</li>
 * <li>�ṩ������ת��ע����ڣ�������ֵ�����ڵȲ����Զ�ת�������ͣ�����ע���ض�����ת��������ת����<br>
 * <b>ע�� </b>������ڴ�request������VO����İ󶨹��̷������󡣳������ԣ��������½��С� <br>
 * (��Ϊ�ҷ��ְ󶨴�������������ת����������ģ������ڣ��������ͣ������������"",�ᷢ������ ��ʵ�������������ġ�) <br>
 * </li>
 * <li>�ṩ��param2Object()�����������ֶ��������Ĳ������ý�ָ��VO����(CURD����ķ���������)</li>
 * <li>���Ӳ���ǰ׺prefix�ͷָ���prefixSeparator�������������Ĳ�����ǰ׺�������Ĭ�ϲ�����ǰ׺p_������prefix=null����ȥ��ǰ׺��</li>
 * <li>�ṩ����token�����ܣ�����ʵ�ֶ��ض�������������(generateToken,saveToken)��У������(isTokenValid)����������(resetToken)�ȹ��ܡ�</li>
 * <li>������defaultCheckToken=true����Ĭ�ϴ������ƣ���get����������µ�token����post�����У����tokenֵ��Ҳ�ɲ��Զ�����token�����������д���</li>
 * </ol>
 * 
 * ����Ĵ��������ǳ����������ֶ�����ʽ�� <br>
 * <ul>
 * <li>ModelAndView XX(HttpServletRequest request, HttpServletResponse
 * response); <br>
 * ������ͨ�õķ�������������򵥵ģ�����Ҫ�Զ�����ת����������Ƽ�ʹ�ã�<br>
 * �����Ҫ�������Ĳ������ý�ָ�����󣬿���ʹ��param2Object()����ʵ�֡� </li>
 * <li>ModelAndView XX(HttpServletRequest request, HttpServletResponse
 * response,VOObject vo); <br>
 * ������������Զ��������Ĳ���ת����VO����ʹ�ã���ͬSimpleFormController������һ���� </li>
 * </ul>
 * 
 * @author admin 2005-1-11
 */
public class BaseController extends MultiActionController implements
		InitializingBean {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseController.class);

	// ���ƴ�����
	private static TokenProcessor token = TokenProcessor.getInstance();

	public static final String DEFAULT_PREFIX = "p";

	public static final String DEFAULT_PREFIX_SEPARATOR = "_";

	private static String DEFAULT_METHOD_NAME = "index"; // ȱʡ���õĴ�����

	private static String DEFAULT_PARAM_NAME = "m"; // ȱʡ���ݵĽ�������������

	private static String DEFAULT_PAGE_ON_ERROR = "error"; // ȱʡ����ʱ��ʾ��ҳ��

	private String defaultMethodName = DEFAULT_METHOD_NAME; // ȱʡ���õĴ�����

	private String paramName = DEFAULT_PARAM_NAME; // ���ݵĽ���������

	private String errorPage = DEFAULT_PAGE_ON_ERROR;

	private String prefix = DEFAULT_PREFIX; // ����ǰ׺

	private String prefixSeparator = DEFAULT_PREFIX_SEPARATOR; // ����ǰ׺�����֮��ķָ���

	private boolean defaultCheckToken = false; // �Ƿ���Ҫ�������������У��

	/**
	 * 
	 * �չ��췽��
	 */
	public BaseController() {
		super();

	}

	/**
	 * ��ʼ�����������ý�����������ȱʡ�ĵ��÷���
	 * 
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		// ����ȱʡ�ķ���������
		ParameterMethodNameResolver nameResolver = new ParameterMethodNameResolver();
		nameResolver.setParamName(this.paramName);
		nameResolver.setDefaultMethodName(this.defaultMethodName);
		this.setMethodNameResolver(nameResolver);
	}

	// �󶨵���VO����
	public Object getDataObj(HttpServletRequest request,
			HttpServletResponse response, Class voClass) throws AppException {
		Object command = null;
		try {
			command = newCommandObject(voClass);
		} catch (Exception e) {
			throw new WebException("����", e);
		}
		MyRequestDataBinder binder = new MyRequestDataBinder(command, "command");
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		binder.setPrefix(this.getPrefix() + this.getPrefixSeparator() + str);
		binder.setPrefixSeparator("_");
		registerEditor(binder);
		binder.bind(request);
		return command;
	}

	// �󶨵���VO���󣬴�������ʽ��
	public Object getDataObjWithFile(SmartRequest request,
			HttpServletResponse response, Class voClass) throws AppException {
		// VO�����ƣ�����������
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// ����ϴ�����ֵ
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();

		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);

		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			// �������������б����
			propNameList.add(pro);
		}
		
		BaseEntity dataObj = null;
		try {
			dataObj = (BaseEntity) voClass.newInstance();
		} catch (Exception e) {
		}
		for (int j = 0; j < propNameList.size(); j++) {
			PropertyDescriptor popObj = propNameList.get(j);
			if ("class".equalsIgnoreCase(popObj.getName())
					|| "others".equalsIgnoreCase(popObj.getName()))
				continue;
			if ("java.lang.String".equalsIgnoreCase(popObj
					.getPropertyType().getName())) {
				try {
					String value = request.getParameter("p_" + str + "_" + popObj.getName());
					PropertyUtils.setProperty(dataObj, popObj.getName(),
							value);
				} catch (Exception e) {
				}
			} else if ("java.lang.Integer".equalsIgnoreCase(popObj
					.getPropertyType().getName())) {
				try {
					int value = Integer.valueOf(request.getParameter("p_" + str + "_" + popObj.getName()));
					PropertyUtils.setProperty(dataObj, popObj.getName(),
							value);
				} catch (Exception e) {
				}
			} else if ("java.lang.Long".equalsIgnoreCase(popObj
					.getPropertyType().getName())) {
				try {
					long value = Long.valueOf(request.getParameter("p_" + str + "_" + popObj.getName()));
					PropertyUtils.setProperty(dataObj, popObj.getName(),
							value);
				} catch (Exception e) {
				}
			} else if ("java.util.Date".equalsIgnoreCase(popObj
					.getPropertyType().getName())) {
				try {
					Date value = java.sql.Date.valueOf(request.getParameter("p_" + str + "_" + popObj.getName()));
					PropertyUtils.setProperty(dataObj, popObj.getName(),
							value);
				} catch (Exception e) {
				}
			}
		}
		return dataObj;
	}

	// �󶨶��VO����
	public List<BaseEntity> getDataObjs(HttpServletRequest request,
			HttpServletResponse response, Class voClass) throws AppException {

		List<BaseEntity> DataObjlist = new LinkedList<BaseEntity>();
		// VO�����ƣ�����������
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// ����ϴ�����ֵ
		List<String[]> list = new LinkedList<String[]>();
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();
		// �ȴ�request�ж�����������ֵ
		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);
		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			String[] tmpPropList = request.getParameterValues("p_" + str + "_"
					+ pro.getName());
			// ���û�в���ֵ������һ������
			if (tmpPropList == null)
				continue;
			// ��������ֵ�б����
			list.add(tmpPropList);
			// �������������б����
			propNameList.add(pro);
		}
		// ���û�������򷵻�һ�����б����
		if (list.size() <= 0)
			return DataObjlist;
		for (int i = 0; i < list.get(0).length; i++) {
			BaseEntity dataObj = null;
			try {
				dataObj = (BaseEntity) voClass.newInstance();
			} catch (Exception e) {
			}
			for (int j = 0; j < propNameList.size(); j++) {
				PropertyDescriptor popObj = propNameList.get(j);
				if ("java.lang.String".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						String value = list.get(j)[i];
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.lang.Integer".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						int value = Integer.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.lang.Long".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						long value = Long.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.util.Date".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						Date value = java.sql.Date.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				}
			}
			DataObjlist.add(dataObj);
		}
		return DataObjlist;
	}

	// �󶨶��VO���󣬴�������ʽ��
	public List<BaseEntity> getDataObjsWithFile(SmartRequest request,
			HttpServletResponse response, Class voClass) throws AppException {

		List<BaseEntity> DataObjlist = new LinkedList<BaseEntity>();
		// VO�����ƣ�����������
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// ����ϴ�����ֵ
		List<String[]> list = new LinkedList<String[]>();
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();
		// �ȴ�request�ж�����������ֵ
		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);
		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			String[] tmpPropList = request.getParameterValues("p_" + str + "_"
					+ pro.getName());
			// ���û�в���ֵ������һ������
			if (tmpPropList == null)
				continue;
			// ��������ֵ�б����
			list.add(tmpPropList);
			// �������������б����
			propNameList.add(pro);
		}
		// ���û�������򷵻�һ�����б����
		if (list.size() <= 0)
			return DataObjlist;
		for (int i = 0; i < list.get(0).length; i++) {
			BaseEntity dataObj = null;
			try {
				dataObj = (BaseEntity) voClass.newInstance();
			} catch (Exception e) {
			}
			for (int j = 0; j < propNameList.size(); j++) {
				PropertyDescriptor popObj = propNameList.get(j);
				if ("java.lang.String".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						String value = list.get(j)[i];
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.lang.Integer".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						int value = Integer.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.lang.Long".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						long value = Long.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				} else if ("java.util.Date".equalsIgnoreCase(popObj
						.getPropertyType().getName())) {
					try {
						Date value = java.sql.Date.valueOf(list.get(j)[i]);
						PropertyUtils.setProperty(dataObj, popObj.getName(),
								value);
					} catch (Exception e) {
					}
				}
			}
			DataObjlist.add(dataObj);
		}
		return DataObjlist;
	}

	/**
	 * ���Ǹ���Ĵ����������Ӷ����������У�顣<br>
	 * Ĭ�ϵأ���post����Ӧ������У�飬get������������ơ�<br>
	 * ֻ��������needCheckToken=trueʱ�Ž��м��顣
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ����ȱʡ������У��
		if (defaultCheckToken) {
			String method = request.getMethod();
			if ("POST".equalsIgnoreCase(method)) {
				if (!isTokenValid(request)) {
					showMessage(request, "����(token)����ȷ���޷����������");
					return new ModelAndView(errorPage);
				}
			}

			// ������get,post���󣬶�������token
			saveToken(request);
		}

		return super.handleRequestInternal(request, response);
	}

	/**
	 * ��ͻ�����ʾ��Ϣ����ʵ���Ǹ�request����һ����Ϣ������Ҫ�ͻ��˵���ϲ�����ʾ <br>
	 * ֻ����ʾһ����Ϣ���������Σ�����Ļ���ǰ������á�
	 */
	public void showMessage(HttpServletRequest request, String message) {
		showMessage(request, message, null);
	}

	/**
	 * ������Ϣ����һ��Exception�����Ա���Ϊ�û���ʾ�꾡�ĳ�����Ϣ��
	 */
	public void showMessage(HttpServletRequest request, String message,
			Exception e) {
		// ���ÿɱ�js�Ѻ���ʽ����Ϣ
		String msg = message.replace('"', '\'').replaceAll("\n", "\\\\n");
		request.setAttribute(WebConfig.MESSAGE_NAME, msg);
		// ������ϸ������Ϣ
		request.setAttribute(WebConfig.MESSAGE_TRACE_NAME, new Exception(
				message, e));
	}

	/*
	 * ����AppException���÷�����spring�ڷ����쳣ʱ�Զ�����
	 */
	protected ModelAndView handleAppException(HttpServletRequest request,
			HttpServletResponse response, AppException e) {
		return handleAllException(request, response, (Exception) e);
	}

	/*
	 * ���������쳣������÷�����spring�ڷ����쳣ʱ�Զ����á� <br> ���û��ض��򵽳�����ʾҳ�档
	 */
	protected ModelAndView handleAllException(HttpServletRequest request,
			HttpServletResponse response, Exception e) {

		logger.error("�����쳣��" + e.getMessage(), e);

		showMessage(request, "�������쳣���������Ա��ϵ��" + e.getMessage(), e);

		return new ModelAndView(errorPage);
	}

	/**
	 * ���Ǹ���ķ�����Ϊ�˸������ṩһ��ע��ע���Զ���ת�����ĵط�.<br>
	 * ���ǣ�ȥ����validation���̣������Ҫ��������
	 */
	protected void bind(ServletRequest request, Object command)
			throws ServletException {
		MyRequestDataBinder binder = new MyRequestDataBinder(command, "command");
		String str = command.getClass().getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		binder.setPrefix(this.getPrefix() + this.getPrefixSeparator() + str);
		binder.setPrefixSeparator(getPrefixSeparator());

		registerEditor(binder);

		binder.bind(request);
		// ���԰󶨹����еĴ���Ace8 2005.5.10
		// binder.closeNoCatch();
	}

	/**
	 * �������Ĳ����������ע���editor��ת��Ϊcommand����
	 * 
	 * @param request
	 * @param command
	 *            �ն���
	 * @return ������ֵ��Ķ���
	 * @throws WebException
	 */
	protected Object param2Object(ServletRequest request, Object command)
			throws WebException {
		try {
			bind(request, command);
		} catch (ServletException e) {
			logger.error("������ת��Ϊ����ʱ����", e);
			throw new WebException("������ת��Ϊ����ʱ����", e);
		}
		return command;
	}

	/**
	 * �������า�ǣ�ע���Զ��������ת����
	 */
	protected void registerEditor(DataBinder binder) {
	}

	/** ����Ϊ���ƴ����� */
	// ��������
	protected String generateToken(HttpServletRequest request) {
		return token.generateToken(request);
	}

	// У�������Ƿ���ȷ�����request����������������session���е�һ�£��򷵻�true�����򷵻�false
	protected boolean isTokenValid(HttpServletRequest request) {
		return token.isTokenValid(request, false);
	}

	// У�������Ƿ���ȷ������������
	protected boolean isTokenValid(HttpServletRequest request, boolean reset) {
		return token.isTokenValid(request, reset);
	}

	// ��������
	protected void resetToken(HttpServletRequest request) {
		token.resetToken(request);
	}

	// ���������ƣ�������session
	protected void saveToken(HttpServletRequest request) {
		token.saveToken(request);
	}

	/** get,set */
	public String getDefaultMethodName() {
		return defaultMethodName;
	}

	public void setDefaultMethodName(String defaultMethodName) {
		this.defaultMethodName = defaultMethodName;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefixSeparator() {
		return prefixSeparator;
	}

	public void setPrefixSeparator(String prefixSeparator) {
		this.prefixSeparator = prefixSeparator;
	}

	public boolean isDefaultCheckToken() {
		return defaultCheckToken;
	}

	public void setDefaultCheckToken(boolean defaultCheckToken) {
		this.defaultCheckToken = defaultCheckToken;
	}

}