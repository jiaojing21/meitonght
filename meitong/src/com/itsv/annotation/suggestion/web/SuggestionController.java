package com.itsv.annotation.suggestion.web;

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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.suggestion.bo.SuggestionService;
import com.itsv.annotation.suggestion.vo.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��suggestion��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller

public class SuggestionController extends BaseAnnotationController<Suggestion> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SuggestionController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.suggestion";
	@Autowired
	private SuggestionService suggestionService; //�߼������

	public SuggestionController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/suggestion/add");
		super.setIndexView("meitong/suggestion/index");
		super.setEditView("meitong/suggestion/edit");

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
		Suggestion suggestion = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			suggestion = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", suggestion);
		} else {
			suggestion = new Suggestion();
		}

		this.suggestionService.queryByVO(records, suggestion);
	}

	//��ʾ����suggestionҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�suggestionҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Suggestion suggestion = this.suggestionService.queryById(id);
		if (null == suggestion) {
			showMessage(request, "δ�ҵ���Ӧ��suggestion��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, suggestion);
		}
	}
		// ��ѯ����׼��
		@RequestMapping("/suggestion.query.htm")
		public ModelAndView querySuggestion(HttpServletRequest request,
				HttpServletResponse response, ModelAndView mnv) {
			mnv.setViewName(getIndexView());
			return mnv;
		}

		// ��ѯ�б�
		@RequestMapping("/suggestion.BeforeData.htm")
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
			Suggestion suggestion = new Suggestion();
			suggestion.setContent(shmc);
			CachePagedList records = PagedListTool.getEuiPagedList(request,
					"suggestion");
			this.suggestionService.queryByVO(records, suggestion);
			map.put("total", records.getTotalNum());
			map.put("rows", records.getSource());
			return ResponseUtils.sendMap(map);
		}

	// ����
	@RequestMapping("/suggestion.detail.htm")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if (id != "" && id != null) {
			Suggestion suggestion = this.suggestionService.queryById(id);
			mnv.addObject("data", suggestion);
			mnv.setViewName("/meitong/suggestion/detail");
		} else {
			showMessage(request, "û�в鵽������");
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//�ظ�
	@RequestMapping("/suggestion.detailupt.htm")
	public ModelAndView detailupt(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String id = "";
		String answer = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
			answer = request.getParameter("p_Suggestion_answer");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Suggestion suggestion = this.suggestionService.queryById(id);
			suggestion.setFlag("1");
			suggestion.setAnswer(answer);
			this.suggestionService.update(suggestion);
			showMessage(request, "�����ɹ�");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	/**
	 * ��������suggestion
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Suggestion suggestion = null;
		try {
			suggestion = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, suggestion)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, suggestion);
			//}

			this.suggestionService.add(suggestion);

			showMessage(request, "����suggestion�ɹ�");
		} catch (AppException e) {
			logger.error("����suggestion[" + suggestion + "]ʧ��", e);
			showMessage(request, "����suggestionʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��suggestion
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, suggestion);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�suggestion
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Suggestion suggestion = null;
		try {
			suggestion = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, suggestion)) {
			//	return edit(request, response);
			//}

			this.suggestionService.update(suggestion);
			showMessage(request, "�޸�suggestion�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�suggestion[" + suggestion + "]ʧ��", e);
			showMessage(request, "�޸�suggestionʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�suggestion
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] suggestions = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : suggestions) {
				this.suggestionService.delete(id);
			}
			showMessage(request, "ɾ��suggestion�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��suggestionʱʧ��", e);
			showMessage(request, "ɾ��suggestionʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}
}