package com.itsv.annotation.subGoodGoods.web;

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

import com.itsv.annotation.subGoodGoods.bo.SubGoodGoodsService;
import com.itsv.annotation.subGoodGoods.vo.SubGoodGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��subgood_goods��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
@Controller
@RequestMapping("/subGoodGoods.subGoodGoods.vsf")
public class SubGoodGoodsController extends BaseAnnotationController<SubGoodGoods> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SubGoodGoodsController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.subGoodGoods";
	@Autowired
	private SubGoodGoodsService subGoodGoodsService; //�߼������

	public SubGoodGoodsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("subGoodGoods/subGoodGoods/add");
		super.setIndexView("subGoodGoods/subGoodGoods/index");
		super.setEditView("subGoodGoods/subGoodGoods/edit");

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
		SubGoodGoods subGoodGoods = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			subGoodGoods = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", subGoodGoods);
		} else {
			subGoodGoods = new SubGoodGoods();
		}

		this.subGoodGoodsService.queryByVO(records, subGoodGoods);
	}

	//��ʾ����subgood_goodsҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�subgood_goodsҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		SubGoodGoods subGoodGoods = this.subGoodGoodsService.queryById(id);
		if (null == subGoodGoods) {
			showMessage(request, "δ�ҵ���Ӧ��subgood_goods��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, subGoodGoods);
		}
	}

	/**
	 * ��������subgood_goods
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		SubGoodGoods subGoodGoods = null;
		try {
			subGoodGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subGoodGoods)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subGoodGoods);
			//}

			this.subGoodGoodsService.add(subGoodGoods);

			showMessage(request, "����subgood_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����subgood_goods[" + subGoodGoods + "]ʧ��", e);
			showMessage(request, "����subgood_goodsʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��subgood_goods
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subGoodGoods);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�subgood_goods
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		SubGoodGoods subGoodGoods = null;
		try {
			subGoodGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subGoodGoods)) {
			//	return edit(request, response);
			//}

			this.subGoodGoodsService.update(subGoodGoods);
			showMessage(request, "�޸�subgood_goods�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�subgood_goods[" + subGoodGoods + "]ʧ��", e);
			showMessage(request, "�޸�subgood_goodsʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�subgood_goods
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] subGoodGoodss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : subGoodGoodss) {
				this.subGoodGoodsService.delete(id);
			}
			showMessage(request, "ɾ��subgood_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��subgood_goodsʱʧ��", e);
			showMessage(request, "ɾ��subgood_goodsʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSubGoodGoodsService(SubGoodGoodsService subGoodGoodsService) {
		this.subGoodGoodsService = subGoodGoodsService;
	}
}