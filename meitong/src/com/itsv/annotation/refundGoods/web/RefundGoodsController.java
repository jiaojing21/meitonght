package com.itsv.annotation.refundGoods.web;

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

import com.itsv.annotation.refundGoods.bo.RefundGoodsService;
import com.itsv.annotation.refundGoods.vo.RefundGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��refund_goods��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
@RequestMapping("/refundGoods.refundGoods.vsf")
public class RefundGoodsController extends BaseAnnotationController<RefundGoods> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RefundGoodsController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.refundGoods";
	@Autowired
	private RefundGoodsService refundGoodsService; //�߼������

	public RefundGoodsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("refundGoods/refundGoods/add");
		super.setIndexView("refundGoods/refundGoods/index");
		super.setEditView("refundGoods/refundGoods/edit");

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
		RefundGoods refundGoods = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			refundGoods = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", refundGoods);
		} else {
			refundGoods = new RefundGoods();
		}

		this.refundGoodsService.queryByVO(records, refundGoods);
	}

	//��ʾ����refund_goodsҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�refund_goodsҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		RefundGoods refundGoods = this.refundGoodsService.queryById(id);
		if (null == refundGoods) {
			showMessage(request, "δ�ҵ���Ӧ��refund_goods��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, refundGoods);
		}
	}

	/**
	 * ��������refund_goods
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		RefundGoods refundGoods = null;
		try {
			refundGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, refundGoods)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, refundGoods);
			//}

			this.refundGoodsService.add(refundGoods);

			showMessage(request, "����refund_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����refund_goods[" + refundGoods + "]ʧ��", e);
			showMessage(request, "����refund_goodsʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��refund_goods
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, refundGoods);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�refund_goods
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		RefundGoods refundGoods = null;
		try {
			refundGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, refundGoods)) {
			//	return edit(request, response);
			//}

			this.refundGoodsService.update(refundGoods);
			showMessage(request, "�޸�refund_goods�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�refund_goods[" + refundGoods + "]ʧ��", e);
			showMessage(request, "�޸�refund_goodsʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�refund_goods
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] refundGoodss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : refundGoodss) {
				this.refundGoodsService.delete(id);
			}
			showMessage(request, "ɾ��refund_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��refund_goodsʱʧ��", e);
			showMessage(request, "ɾ��refund_goodsʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setRefundGoodsService(RefundGoodsService refundGoodsService) {
		this.refundGoodsService = refundGoodsService;
	}
}