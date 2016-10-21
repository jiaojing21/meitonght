package com.itsv.annotation.ordersGoods.web;

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

import com.itsv.annotation.ordersGoods.bo.OrdersGoodsService;
import com.itsv.annotation.ordersGoods.vo.OrdersGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��orders_goods��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class OrdersGoodsController extends BaseAnnotationController<OrdersGoods> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(OrdersGoodsController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.ordersGoods";
	@Autowired
	private OrdersGoodsService ordersGoodsService; //�߼������

	public OrdersGoodsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/ordersGoods/add");
		super.setIndexView("meitong/ordersGoods/index");
		super.setEditView("meitong/ordersGoods/edit");

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
		OrdersGoods ordersGoods = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			ordersGoods = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", ordersGoods);
		} else {
			ordersGoods = new OrdersGoods();
		}

		this.ordersGoodsService.queryByVO(records, ordersGoods);
	}

	//��ʾ����orders_goodsҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�orders_goodsҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		OrdersGoods ordersGoods = this.ordersGoodsService.queryById(id);
		if (null == ordersGoods) {
			showMessage(request, "δ�ҵ���Ӧ��orders_goods��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, ordersGoods);
		}
	}

	/**
	 * ��������orders_goods
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		OrdersGoods ordersGoods = null;
		try {
			ordersGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, ordersGoods)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ordersGoods);
			//}

			this.ordersGoodsService.add(ordersGoods);

			showMessage(request, "����orders_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����orders_goods[" + ordersGoods + "]ʧ��", e);
			showMessage(request, "����orders_goodsʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��orders_goods
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ordersGoods);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�orders_goods
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		OrdersGoods ordersGoods = null;
		try {
			ordersGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, ordersGoods)) {
			//	return edit(request, response);
			//}

			this.ordersGoodsService.update(ordersGoods);
			showMessage(request, "�޸�orders_goods�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�orders_goods[" + ordersGoods + "]ʧ��", e);
			showMessage(request, "�޸�orders_goodsʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�orders_goods
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] ordersGoodss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : ordersGoodss) {
				this.ordersGoodsService.delete(id);
			}
			showMessage(request, "ɾ��orders_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��orders_goodsʱʧ��", e);
			showMessage(request, "ɾ��orders_goodsʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setOrdersGoodsService(OrdersGoodsService ordersGoodsService) {
		this.ordersGoodsService = ordersGoodsService;
	}
}