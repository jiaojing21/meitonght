package com.itsv.gbp.core.web.springmvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.GenericsUtils;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.WebException;
import com.itsv.platform.BizFile;

public class BaseAnnotationController <V> extends BaseController{
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

	public BaseAnnotationController() {
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
		mnv.addObject("TYPE", "vsf");
		beforeShowAdd(request, response, mnv);
		return mnv;
	}

	//��ʾ�޸�ҳ��
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getEditView());
		mnv.addObject("TYPE", "vsf");
		beforeShowEdit(request, response, mnv);
		return mnv;
	}

	//�б���ʾ���󡣲���ҳ���
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getIndexView());
		mnv.addObject("TYPE", "vsf");
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
		mnv.addObject("TYPE", "htm");
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

	public Object param2ObjectByObj(ServletRequest request,Object obj) throws WebException {
		param2Object(request, obj);
		return obj;
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
	
	public BizFile doSingleFileUpload(HttpServletRequest request,
			String fileFieldName ,String dist) throws Exception {
		MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile obj = (CommonsMultipartFile) multipartrequest
				.getFile(fileFieldName);
		if(obj==null||(obj!=null&&obj.getFileItem().getName().trim().equals(""))){
			return null;
		}
		
		BizFile objFile = null;
		String originFullName = obj.getFileItem().getName();
		String name = extractFileName(originFullName);
		String postfix = extractPostfix(originFullName);
		String objName = (new Date()).getTime() + "" + postfix;
		File filePath = new File(request.getSession().getServletContext()
				.getRealPath(dist));
		if(!filePath.exists()){
			filePath.mkdir();
		}
		
		File file = new File(filePath+File.separator + objName);
		
		byte[] bytes = obj.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		objFile = new BizFile(originFullName, name, postfix, objName);
		return objFile;
	}	
	
	/**
	 * �����Ƿ�����
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView testCont(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.getWriter().write("<script language='JavaScript'  type='text/javascript'  >document.write('ok')</script>");
		
		return null;
	}
	
	public BizFile doSingleFileUpload(HttpServletRequest request,
			String fileFieldName) throws Exception {
		MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile obj = (CommonsMultipartFile) multipartrequest
				.getFile(fileFieldName);
		if(obj==null||(obj!=null&&obj.getFileItem().getName().trim().equals(""))){
			return null;
		}
		
		BizFile objFile = null;
		String originFullName = obj.getFileItem().getName();
		String name = extractFileName(originFullName);
		String postfix = extractPostfix(originFullName);
		String objName = (new Date()).getTime() + "" + postfix;
		File filePath = new File(request.getSession().getServletContext()
				.getRealPath("\\"));
		if(!filePath.exists()){
			filePath.mkdir();
		}
		
		File file = new File(filePath+File.separator + objName);
		
		byte[] bytes = obj.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		objFile = new BizFile(originFullName, name, postfix, objName);
		return objFile;
	}	
	
	public String extractPostfix(String name) {
		int index = name.lastIndexOf(".");
		String ret = name.substring(index, name.length());
		return ret;
	}

	public String extractFileName(String name) {
		int index = name.lastIndexOf("\\");
		String ret = name.substring(index+1, name.length());
		return ret;
	}	
	
	//�жϵ�½���Ƿ�Ϊ����Ա
	public  String  panduan(ModelAndView mnv){
		String str = "0";
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = (adapter == null ? null : adapter.getRealUser());
		if(user!=null){
			List<Role> list = user.getRoles();
			for(int i=0;i<list.size();i++){
				Role role = list.get(i);
				if("ϵͳ��������Ա".equals(role.getName())){
				str = "1";
				}
			
			}
		}
		String userName = user.getUserName();
		mnv.addObject("Mobtel", userName);
		mnv.addObject("pandun", str);
		return userName;
	}

}
