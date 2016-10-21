package com.itsv.annotation.subGood.web;

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

import com.itsv.annotation.subGood.bo.SubGoodService;
import com.itsv.annotation.subGood.vo.SubGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��subgood��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
@Controller
@RequestMapping("/subGood.subGood.vsf")
public class SubGoodController extends BaseAnnotationController<SubGood> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SubGoodController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.subGood";
	@Autowired
	private SubGoodService subGoodService; //�߼������

	public SubGoodController(){

		super.setDefaultCheckToken(true);
		super.setAddView("subGood/subGood/add");
		super.setIndexView("subGood/subGood/index");
		super.setEditView("subGood/subGood/edit");

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
		SubGood subGood = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			subGood = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", subGood);
		} else {
			subGood = new SubGood();
		}

		this.subGoodService.queryByVO(records, subGood);
	}

	//��ʾ����subgoodҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�subgoodҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		SubGood subGood = this.subGoodService.queryById(id);
		if (null == subGood) {
			showMessage(request, "δ�ҵ���Ӧ��subgood��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, subGood);
		}
	}

	/**
	 * ��������subgood
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		SubGood subGood = null;
		try {
			subGood = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subGood)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subGood);
			//}

			this.subGoodService.add(subGood);

			showMessage(request, "����subgood�ɹ�");
		} catch (AppException e) {
			logger.error("����subgood[" + subGood + "]ʧ��", e);
			showMessage(request, "����subgoodʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��subgood
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subGood);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�subgood
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		SubGood subGood = null;
		try {
			subGood = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subGood)) {
			//	return edit(request, response);
			//}

			this.subGoodService.update(subGood);
			showMessage(request, "�޸�subgood�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�subgood[" + subGood + "]ʧ��", e);
			showMessage(request, "�޸�subgoodʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�subgood
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] subGoods = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : subGoods) {
				this.subGoodService.delete(id);
			}
			showMessage(request, "ɾ��subgood�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��subgoodʱʧ��", e);
			showMessage(request, "ɾ��subgoodʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSubGoodService(SubGoodService subGoodService) {
		this.subGoodService = subGoodService;
	}
}