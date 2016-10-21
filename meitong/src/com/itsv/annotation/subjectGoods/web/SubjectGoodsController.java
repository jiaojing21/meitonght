package com.itsv.annotation.subjectGoods.web;

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

import com.itsv.annotation.subjectGoods.bo.SubjectGoodsService;
import com.itsv.annotation.subjectGoods.vo.SubjectGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��subject_goods��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
@RequestMapping("/subjectGoods.subjectGoods.vsf")
public class SubjectGoodsController extends BaseAnnotationController<SubjectGoods> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SubjectGoodsController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.subjectGoods";
	@Autowired
	private SubjectGoodsService subjectGoodsService; //�߼������

	public SubjectGoodsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("subjectGoods/subjectGoods/add");
		super.setIndexView("subjectGoods/subjectGoods/index");
		super.setEditView("subjectGoods/subjectGoods/edit");

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
		SubjectGoods subjectGoods = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			subjectGoods = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", subjectGoods);
		} else {
			subjectGoods = new SubjectGoods();
		}

		this.subjectGoodsService.queryByVO(records, subjectGoods);
	}

	//��ʾ����subject_goodsҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�subject_goodsҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		SubjectGoods subjectGoods = this.subjectGoodsService.queryById(id);
		if (null == subjectGoods) {
			showMessage(request, "δ�ҵ���Ӧ��subject_goods��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, subjectGoods);
		}
	}

	/**
	 * ��������subject_goods
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		SubjectGoods subjectGoods = null;
		try {
			subjectGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subjectGoods)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subjectGoods);
			//}

			this.subjectGoodsService.add(subjectGoods);

			showMessage(request, "����subject_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����subject_goods[" + subjectGoods + "]ʧ��", e);
			showMessage(request, "����subject_goodsʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��subject_goods
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subjectGoods);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�subject_goods
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		SubjectGoods subjectGoods = null;
		try {
			subjectGoods = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, subjectGoods)) {
			//	return edit(request, response);
			//}

			this.subjectGoodsService.update(subjectGoods);
			showMessage(request, "�޸�subject_goods�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�subject_goods[" + subjectGoods + "]ʧ��", e);
			showMessage(request, "�޸�subject_goodsʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�subject_goods
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] subjectGoodss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : subjectGoodss) {
				this.subjectGoodsService.delete(id);
			}
			showMessage(request, "ɾ��subject_goods�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��subject_goodsʱʧ��", e);
			showMessage(request, "ɾ��subject_goodsʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSubjectGoodsService(SubjectGoodsService subjectGoodsService) {
		this.subjectGoodsService = subjectGoodsService;
	}
}