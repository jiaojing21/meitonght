package com.itsv.annotation.pictext.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.pictext.bo.PictextService;
import com.itsv.annotation.pictext.vo.Pictext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��pictext��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
@Controller
public class PictextController extends BaseAnnotationController<Pictext> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(PictextController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.pictext";
	@Autowired
	private PictextService pictextService; //�߼������
	
	@Autowired
	private FileUploadService fileUploadService; //�߼������

	public PictextController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/pictext/add");
		super.setIndexView("meitong/pictext/index");
		super.setEditView("meitong/pictext/edit");

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
		Pictext pictext = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			pictext = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", pictext);
		} else {
			pictext = new Pictext();
		}

		this.pictextService.queryByVO(records, pictext);
	}

	//��ʾ����pictextҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�pictextҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Pictext pictext = this.pictextService.queryById(id);
		if (null == pictext) {
			showMessage(request, "δ�ҵ���Ӧ��pictext��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, pictext);
		}
	}
	//��ת����ҳ��
	@RequestMapping("/pictext.add.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response , ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * ��������pictext
	 */
	@RequestMapping("/pictext.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		ModelAndView mnv =new ModelAndView();
		mnv.setViewName(getAddView());
		Pictext pictext = new Pictext();
		try {
			String onlypic = request.getParameter("onlypic");
			String remark = request.getParameter("remark");
			if(null != remark && !"".equals(remark)){
				pictext.setCreatetime(new Date());
				pictext.setUserId(user.getId());
				pictext.setComent(remark);
				this.pictextService.add(pictext);
				if (null != onlypic && !"".equals(onlypic)) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(onlypic);
					fileUpload.setFId(pictext.getId());
					this.fileUploadService.update(fileUpload);
				}
				showMessage(request, "����ͼ�ĳɹ�");
			}
		} catch (AppException e) {
			logger.error("����ͼ��[" + pictext + "]ʧ��", e);
			showMessage(request, "����ͼ��ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��pictext
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, pictext);
		}

		return mnv;
	}

	/**
	 * �����޸ĵ�pictext
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Pictext pictext = null;
		try {
			pictext = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, pictext)) {
			//	return edit(request, response);
			//}

			this.pictextService.update(pictext);
			showMessage(request, "�޸�pictext�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�pictext[" + pictext + "]ʧ��", e);
			showMessage(request, "�޸�pictextʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�pictext
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] pictexts = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : pictexts) {
				this.pictextService.delete(id);
			}
			showMessage(request, "ɾ��pictext�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��pictextʱʧ��", e);
			showMessage(request, "ɾ��pictextʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setPictextService(PictextService pictextService) {
		this.pictextService = pictextService;
	}
}