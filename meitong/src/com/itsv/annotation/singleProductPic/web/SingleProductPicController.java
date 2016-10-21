package com.itsv.annotation.singleProductPic.web;

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
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.singleProductPic.bo.SingleProductPicService;
import com.itsv.annotation.singleProductPic.vo.SingleProductPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��single_product_pic��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */
@Controller
public class SingleProductPicController extends BaseAnnotationController<SingleProductPic> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SingleProductPicController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.singleProductPic";
	@Autowired
	private SingleProductPicService singleProductPicService; //�߼������

	@Autowired
	private FileUploadService fileUploadService;//�߼������
	
	public SingleProductPicController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/singleProductPic/add");
		super.setIndexView("meitong/singleProductPic/index");
		super.setEditView("meitong/singleProductPic/edit");

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
		SingleProductPic singleProductPic = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			singleProductPic = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", singleProductPic);
		} else {
			singleProductPic = new SingleProductPic();
		}

		this.singleProductPicService.queryByVO(records, singleProductPic);
	}
	@RequestMapping("/single.beforeadd.htm")
	public ModelAndView addp(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv,String code){
		mnv.addObject("code", code);
		mnv.setViewName("/meitong/goods/addpic");
		return mnv;
	}
	@RequestMapping("/single.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName(getAddView());
		SingleProductPic singleProductPic = null;
		singleProductPic = param2Object(request);
		singleProductPic.setCreatetime(new Date());
		String manypic = request.getParameter("manypic");
		try{
			if(null!=manypic && !"".equals(manypic)){
				String [] manyid = manypic.split(",");
				for(String id : manyid){
					this.singleProductPicService.add(singleProductPic);
					FileUpload fileUpload = this.fileUploadService.queryById(id);
					fileUpload.setFId(singleProductPic.getId());
					this.fileUploadService.update(fileUpload);
				}
			}
		showMessage(request, "�����ɹ�");
		}catch (AppException e) {
			logger.error("����[" + singleProductPic + "]ʧ��", e);
			showMessage(request, "����ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��goods

			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
		}
		mnv.setViewName("/meitong/goods/addpic");
		return mnv;
	}
	//��ʾ����single_product_picҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�single_product_picҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		SingleProductPic singleProductPic = this.singleProductPicService.queryById(id);
		if (null == singleProductPic) {
			showMessage(request, "δ�ҵ���Ӧ��single_product_pic��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, singleProductPic);
		}
	}

	/**
	 * ��������single_product_pic
	 */

	public ModelAndView saveAdd1(HttpServletRequest request, HttpServletResponse response) {
		SingleProductPic singleProductPic = null;
		try {
			singleProductPic = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, singleProductPic)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
			//}

			this.singleProductPicService.add(singleProductPic);

			showMessage(request, "����single_product_pic�ɹ�");
		} catch (AppException e) {
			logger.error("����single_product_pic[" + singleProductPic + "]ʧ��", e);
			showMessage(request, "����single_product_picʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��single_product_pic
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�single_product_pic
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		SingleProductPic singleProductPic = null;
		try {
			singleProductPic = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, singleProductPic)) {
			//	return edit(request, response);
			//}

			this.singleProductPicService.update(singleProductPic);
			showMessage(request, "�޸�single_product_pic�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�single_product_pic[" + singleProductPic + "]ʧ��", e);
			showMessage(request, "�޸�single_product_picʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�single_product_pic
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] singleProductPics = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : singleProductPics) {
				this.singleProductPicService.delete(id);
			}
			showMessage(request, "ɾ��single_product_pic�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��single_product_picʱʧ��", e);
			showMessage(request, "ɾ��single_product_picʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSingleProductPicService(SingleProductPicService singleProductPicService) {
		this.singleProductPicService = singleProductPicService;
	}
}