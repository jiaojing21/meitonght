package com.itsv.annotation.ratio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.annotation.ratio.bo.RatioSubService;
import com.itsv.annotation.ratio.vo.RatioSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ������ͼ�ӱ��ǰ�˴�����
 * 
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */
@Controller
@RequestMapping("/ratio.ratioSub.vsf")
public class RatioSubController extends BaseAnnotationController<RatioSub> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RatioSubController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.ratioSub";
	@Autowired
	private RatioSubService ratioSubService; //�߼������

	public RatioSubController(){

		super.setDefaultCheckToken(true);
		super.setAddView("ratio/ratioSub/add");
		super.setIndexView("ratio/ratioSub/index");
		super.setEditView("ratio/ratioSub/edit");

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
		RatioSub ratioSub = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			ratioSub = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", ratioSub);
		} else {
			ratioSub = new RatioSub();
		}

		this.ratioSubService.queryByVO(records, ratioSub);
	}

	//��ʾ���ӱ���ͼ�ӱ�ҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸ı���ͼ�ӱ�ҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		RatioSub ratioSub = this.ratioSubService.queryById(id);
		if (null == ratioSub) {
			showMessage(request, "δ�ҵ���Ӧ�ı���ͼ�ӱ��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, ratioSub);
		}
	}

	/**
	 * ������������ͼ�ӱ�
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		RatioSub ratioSub = null;
		try {
			ratioSub = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, ratioSub)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratioSub);
			//}

			this.ratioSubService.add(ratioSub);

			showMessage(request, "��������ͼ�ӱ�ɹ�");
		} catch (AppException e) {
			logger.error("��������ͼ�ӱ�[" + ratioSub + "]ʧ��", e);
			showMessage(request, "��������ͼ�ӱ�ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ������ͼ�ӱ�
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratioSub);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵı���ͼ�ӱ�
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		RatioSub ratioSub = null;
		try {
			ratioSub = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, ratioSub)) {
			//	return edit(request, response);
			//}

			this.ratioSubService.update(ratioSub);
			showMessage(request, "�޸ı���ͼ�ӱ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸ı���ͼ�ӱ�[" + ratioSub + "]ʧ��", e);
			showMessage(request, "�޸ı���ͼ�ӱ�ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�еı���ͼ�ӱ�
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] ratioSubs = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : ratioSubs) {
				this.ratioSubService.delete(id);
			}
			showMessage(request, "ɾ������ͼ�ӱ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ������ͼ�ӱ�ʱʧ��", e);
			showMessage(request, "ɾ������ͼ�ӱ�ʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setRatioSubService(RatioSubService ratioSubService) {
		this.ratioSubService = ratioSubService;
	}
}