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
 * 说明：前端处理层的基类，基于spring的MultiActionController。建议处理层对象继承该类。<br>
 * 有如下几个功能： <br>
 * <ol>
 * <li>提供了缺省的解析参数名称和缺省处理方法，根据传来的参数决定调用哪个处理方法。 解析参数名和缺省处理方法名也可重新设定。<br>
 * <b>请注意：</b>缺省处理方法名对应的方法必须在之类实现，否则解析参数没有值时会出错。 </li>
 * <li>提供一个方法showMessage()，用来向客户端显示消息。 </li>
 * <li>提供了错误处理机制，如果请求处理过程中发生错误，且用户没有处理，则会自动调用出错显示页面，显示给用户。</li>
 * <li>提供了类型转换注册入口，对于数值，日期等不能自动转换的类型，允许注册特定类型转换器进行转换。<br>
 * <b>注意 </b>：如果在从request参数到VO对象的绑定过程发生错误。程序会忽略，继续向下进行。 <br>
 * (因为我发现绑定错误都是由于类型转换错误引起的，像日期，数字类型，如果传来的是"",会发生错误。 其实这种情况是允许的。) <br>
 * </li>
 * <li>提供了param2Object()方法，用来手动将传来的参数设置进指定VO对象。(CURD子类的方法更方便)</li>
 * <li>增加参数前缀prefix和分隔符prefixSeparator，用来处理传来的参数有前缀的情况。默认参数有前缀p_，设置prefix=null可以去掉前缀。</li>
 * <li>提供令牌token处理功能，可以实现对特定请求生成令牌(generateToken,saveToken)，校验令牌(isTokenValid)，重置令牌(resetToken)等功能。</li>
 * <li>若参数defaultCheckToken=true，则默认处理令牌：对get请求会生成新的token，对post请求会校验其token值。也可不自动处理token，由子类自行处理。</li>
 * </ol>
 * 
 * 该类的处理方法我们常用以下两种定义形式： <br>
 * <ul>
 * <li>ModelAndView XX(HttpServletRequest request, HttpServletResponse
 * response); <br>
 * 这是最通用的方法，用来处理简单的，不需要自动类型转换的情况。推荐使用！<br>
 * 如果需要将传来的参数设置进指定对象，可以使用param2Object()方法实现。 </li>
 * <li>ModelAndView XX(HttpServletRequest request, HttpServletResponse
 * response,VOObject vo); <br>
 * 这个方法可以自动将传来的参数转换成VO对象供使用，如同SimpleFormController所做的一样。 </li>
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

	// 令牌处理器
	private static TokenProcessor token = TokenProcessor.getInstance();

	public static final String DEFAULT_PREFIX = "p";

	public static final String DEFAULT_PREFIX_SEPARATOR = "_";

	private static String DEFAULT_METHOD_NAME = "index"; // 缺省调用的处理方法

	private static String DEFAULT_PARAM_NAME = "m"; // 缺省依据的解析参数的名称

	private static String DEFAULT_PAGE_ON_ERROR = "error"; // 缺省出错时显示的页面

	private String defaultMethodName = DEFAULT_METHOD_NAME; // 缺省调用的处理方法

	private String paramName = DEFAULT_PARAM_NAME; // 依据的解析参数名

	private String errorPage = DEFAULT_PAGE_ON_ERROR;

	private String prefix = DEFAULT_PREFIX; // 参数前缀

	private String prefixSeparator = DEFAULT_PREFIX_SEPARATOR; // 参数前缀与参数之间的分隔符

	private boolean defaultCheckToken = false; // 是否需要对请求进行令牌校验

	/**
	 * 
	 * 空构造方法
	 */
	public BaseController() {
		super();

	}

	/**
	 * 初始化方法，设置解析参数名和缺省的调用方法
	 * 
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		// 设置缺省的方法解析类
		ParameterMethodNameResolver nameResolver = new ParameterMethodNameResolver();
		nameResolver.setParamName(this.paramName);
		nameResolver.setDefaultMethodName(this.defaultMethodName);
		this.setMethodNameResolver(nameResolver);
	}

	// 绑定单个VO对象
	public Object getDataObj(HttpServletRequest request,
			HttpServletResponse response, Class voClass) throws AppException {
		Object command = null;
		try {
			command = newCommandObject(voClass);
		} catch (Exception e) {
			throw new WebException("出错", e);
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

	// 绑定单个VO对象，带附件方式下
	public Object getDataObjWithFile(SmartRequest request,
			HttpServletResponse response, Class voClass) throws AppException {
		// VO类名称，不包括包名
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// 存放上传属性值
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();

		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);

		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			// 存入属性名称列表对象
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

	// 绑定多个VO对象
	public List<BaseEntity> getDataObjs(HttpServletRequest request,
			HttpServletResponse response, Class voClass) throws AppException {

		List<BaseEntity> DataObjlist = new LinkedList<BaseEntity>();
		// VO类名称，不包括包名
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// 存放上传属性值
		List<String[]> list = new LinkedList<String[]>();
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();
		// 先从request中读出所有属性值
		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);
		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			String[] tmpPropList = request.getParameterValues("p_" + str + "_"
					+ pro.getName());
			// 如果没有参数值则处理下一个参数
			if (tmpPropList == null)
				continue;
			// 存入属性值列表对象
			list.add(tmpPropList);
			// 存入属性名称列表对象
			propNameList.add(pro);
		}
		// 如果没有数据则返回一个空列表对象
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

	// 绑定多个VO对象，带附件方式下
	public List<BaseEntity> getDataObjsWithFile(SmartRequest request,
			HttpServletResponse response, Class voClass) throws AppException {

		List<BaseEntity> DataObjlist = new LinkedList<BaseEntity>();
		// VO类名称，不包括包名
		String str = voClass.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		// 存放上传属性值
		List<String[]> list = new LinkedList<String[]>();
		List<PropertyDescriptor> propNameList = new LinkedList<PropertyDescriptor>();
		// 先从request中读出所有属性值
		PropertyDescriptor[] prolist = BeanUtils
				.getPropertyDescriptors(voClass);
		for (PropertyDescriptor pro : prolist) {
			if ("class".equalsIgnoreCase(pro.getName())
					|| "others".equalsIgnoreCase(pro.getName()))
				continue;
			String[] tmpPropList = request.getParameterValues("p_" + str + "_"
					+ pro.getName());
			// 如果没有参数值则处理下一个参数
			if (tmpPropList == null)
				continue;
			// 存入属性值列表对象
			list.add(tmpPropList);
			// 存入属性名称列表对象
			propNameList.add(pro);
		}
		// 如果没有数据则返回一个空列表对象
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
	 * 覆盖父类的处理方法，增加对请求的令牌校验。<br>
	 * 默认地，对post请求应用令牌校验，get请求仅生成令牌。<br>
	 * 只有设置了needCheckToken=true时才进行检验。
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 处理缺省的令牌校验
		if (defaultCheckToken) {
			String method = request.getMethod();
			if ("POST".equalsIgnoreCase(method)) {
				if (!isTokenValid(request)) {
					showMessage(request, "令牌(token)不正确，无法处理该请求。");
					return new ModelAndView(errorPage);
				}
			}

			// 不论是get,post请求，都生成新token
			saveToken(request);
		}

		return super.handleRequestInternal(request, response);
	}

	/**
	 * 向客户端显示消息。其实就是给request附件一个消息对象，需要客户端的配合才能显示 <br>
	 * 只能显示一个消息。调用两次，后面的会冲掉前面的设置。
	 */
	public void showMessage(HttpServletRequest request, String message) {
		showMessage(request, message, null);
	}

	/**
	 * 允许消息附带一个Exception对象，以便于为用户显示详尽的出错信息。
	 */
	public void showMessage(HttpServletRequest request, String message,
			Exception e) {
		// 设置可被js友好显式的消息
		String msg = message.replace('"', '\'').replaceAll("\n", "\\\\n");
		request.setAttribute(WebConfig.MESSAGE_NAME, msg);
		// 设置详细出错消息
		request.setAttribute(WebConfig.MESSAGE_TRACE_NAME, new Exception(
				message, e));
	}

	/*
	 * 处理AppException。该方法由spring在发生异常时自动调用
	 */
	protected ModelAndView handleAppException(HttpServletRequest request,
			HttpServletResponse response, AppException e) {
		return handleAllException(request, response, (Exception) e);
	}

	/*
	 * 处理所有异常情况。该方法由spring在发生异常时自动调用。 <br> 将用户重定向到出错显示页面。
	 */
	protected ModelAndView handleAllException(HttpServletRequest request,
			HttpServletResponse response, Exception e) {

		logger.error("程序异常：" + e.getMessage(), e);

		showMessage(request, "程序发生异常，请与管理员联系。" + e.getMessage(), e);

		return new ModelAndView(errorPage);
	}

	/**
	 * 覆盖父类的方法。为了给子类提供一个注册注册自定义转换器的地方.<br>
	 * 但是，去掉了validation过程，如果需要还需增加
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
		// 忽略绑定过程中的错误。Ace8 2005.5.10
		// binder.closeNoCatch();
	}

	/**
	 * 将传来的参数对象根据注册的editor，转换为command对象
	 * 
	 * @param request
	 * @param command
	 *            空对象
	 * @return 经过赋值后的对象
	 * @throws WebException
	 */
	protected Object param2Object(ServletRequest request, Object command)
			throws WebException {
		try {
			bind(request, command);
		} catch (ServletException e) {
			logger.error("将参数转换为对象时出错", e);
			throw new WebException("将参数转换为对象时出错", e);
		}
		return command;
	}

	/**
	 * 允许子类覆盖，注册自定义的类型转换类
	 */
	protected void registerEditor(DataBinder binder) {
	}

	/** 以下为令牌处理方法 */
	// 生成令牌
	protected String generateToken(HttpServletRequest request) {
		return token.generateToken(request);
	}

	// 校验令牌是否正确。如果request参数传来的令牌与session持有的一致，则返回true，否则返回false
	protected boolean isTokenValid(HttpServletRequest request) {
		return token.isTokenValid(request, false);
	}

	// 校验令牌是否正确，并重置令牌
	protected boolean isTokenValid(HttpServletRequest request, boolean reset) {
		return token.isTokenValid(request, reset);
	}

	// 重置令牌
	protected void resetToken(HttpServletRequest request) {
		token.resetToken(request);
	}

	// 生成新令牌，并存入session
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