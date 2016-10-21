package com.itsv.annotation.goodCode.web;

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

import com.itsv.annotation.goodCode.bo.GoodCodeService;
import com.itsv.annotation.goodCode.vo.GoodCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��goodcode��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
@Controller
@RequestMapping("/goodCode.goodCode.vsf")
public class GoodCodeController extends BaseAnnotationController<GoodCode> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(GoodCodeController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.goodCode";
	@Autowired
	private GoodCodeService goodCodeService; //�߼������

	public GoodCodeController(){

		super.setDefaultCheckToken(true);
		super.setAddView("goodCode/goodCode/add");
		super.setIndexView("goodCode/goodCode/index");
		super.setEditView("goodCode/goodCode/edit");

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
		GoodCode goodCode = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			goodCode = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", goodCode);
		} else {
			goodCode = new GoodCode();
		}

		this.goodCodeService.queryByVO(records, goodCode);
	}

	//��ʾ����goodcodeҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�goodcodeҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		GoodCode goodCode = this.goodCodeService.queryById(id);
		if (null == goodCode) {
			showMessage(request, "δ�ҵ���Ӧ��goodcode��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, goodCode);
		}
	}

	/**
	 * ��������goodcode
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		GoodCode goodCode = null;
		try {
			goodCode = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, goodCode)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, goodCode);
			//}

			this.goodCodeService.add(goodCode);

			showMessage(request, "����goodcode�ɹ�");
		} catch (AppException e) {
			logger.error("����goodcode[" + goodCode + "]ʧ��", e);
			showMessage(request, "����goodcodeʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��goodcode
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, goodCode);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�goodcode
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		GoodCode goodCode = null;
		try {
			goodCode = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, goodCode)) {
			//	return edit(request, response);
			//}

			this.goodCodeService.update(goodCode);
			showMessage(request, "�޸�goodcode�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�goodcode[" + goodCode + "]ʧ��", e);
			showMessage(request, "�޸�goodcodeʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�goodcode
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] goodCodes = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : goodCodes) {
				this.goodCodeService.delete(id);
			}
			showMessage(request, "ɾ��goodcode�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��goodcodeʱʧ��", e);
			showMessage(request, "ɾ��goodcodeʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setGoodCodeService(GoodCodeService goodCodeService) {
		this.goodCodeService = goodCodeService;
	}
}