package com.itsv.annotation.message.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.message.bo.MessageService;
import com.itsv.annotation.message.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��message��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class MessageController extends BaseAnnotationController<Message> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(MessageController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.message";
	@Autowired
	private MessageService messageService; //�߼������
	
	@Autowired
	private UserService userService;//�߼������

	public MessageController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/message/add");
		super.setIndexView("meitong/message/index");
		super.setEditView("meitong/message/edit");

	}

  /**
   * ע���Զ�������ת���࣬����ת�����ڶ���
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
  }    

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Message message = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			message = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", message);
		} else {
			message = new Message();
		}

		this.messageService.queryByVO(records, message);
	}

	//��ʾ����messageҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�messageҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Message message = this.messageService.queryById(id);
		if (null == message) {
			showMessage(request, "δ�ҵ���Ӧ��message��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, message);
		}
	}

	// ��ѯ����׼��
	@RequestMapping("/message.query.htm")
	public ModelAndView queryMessage(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		mnv.setViewName(getIndexView());
		return mnv;
	}

	// ��ѯ�б�
	@RequestMapping("/message.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexAppControl(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		String shmc = "";
		try {
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.setTitle(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"message");
		this.messageService.queryByVO(records, message);
		for(int i = 0;i<records.getSource().size();i++){
			Message m = (Message)records.getSource().get(i);
			m.setSender(this.userService.queryById(m.getSender()).getRealName());
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//���ǰ����
	@RequestMapping("/message.beforeadd.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * ��������message
	 */
	@RequestMapping("/message.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
  		User user = adapter.getRealUser();
  		ModelAndView mnv = new ModelAndView();
  		mnv.setViewName(getAddView());
		Message message = null;
		try {
			message = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, message)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, message);
			//}
			message.setCreatetime(new Date());
			message.setSender(user.getId());
			this.messageService.add(message);

			showMessage(request, "������Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("������Ϣ[" + message + "]ʧ��", e);
			showMessage(request, "������Ϣʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��message
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, message);
		}

		return mnv;
	}

	/**
	 * �����޸ĵ�message
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Message message = null;
		try {
			message = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, message)) {
			//	return edit(request, response);
			//}

			this.messageService.update(message);
			showMessage(request, "�޸�message�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�message[" + message + "]ʧ��", e);
			showMessage(request, "�޸�messageʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�message
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] messages = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : messages) {
				this.messageService.delete(id);
			}
			showMessage(request, "ɾ��message�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��messageʱʧ��", e);
			showMessage(request, "ɾ��messageʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}