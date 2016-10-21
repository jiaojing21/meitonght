package com.itsv.annotation.voucherwith.web;

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

import com.itsv.annotation.voucherwith.bo.VoucherWithService;
import com.itsv.annotation.voucherwith.vo.VoucherWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ������ȯ��ϸ��ǰ�˴�����
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
@Controller
@RequestMapping("/voucherwith.voucherWith.vsf")
public class VoucherWithController extends BaseAnnotationController<VoucherWith> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(VoucherWithController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.voucherWith";
	@Autowired
	private VoucherWithService voucherWithService; //�߼������

	public VoucherWithController(){

		super.setDefaultCheckToken(true);
		super.setAddView("voucherwith/voucherWith/add");
		super.setIndexView("voucherwith/voucherWith/index");
		super.setEditView("voucherwith/voucherWith/edit");

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
		VoucherWith voucherWith = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			voucherWith = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", voucherWith);
		} else {
			voucherWith = new VoucherWith();
		}

		this.voucherWithService.queryByVO(records, voucherWith);
	}

	//��ʾ���Ӵ���ȯ��ϸҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸Ĵ���ȯ��ϸҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		VoucherWith voucherWith = this.voucherWithService.queryById(id);
		if (null == voucherWith) {
			showMessage(request, "δ�ҵ���Ӧ�Ĵ���ȯ��ϸ��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, voucherWith);
		}
	}

	/**
	 * ������������ȯ��ϸ
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		VoucherWith voucherWith = null;
		try {
			voucherWith = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, voucherWith)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherWith);
			//}

			this.voucherWithService.add(voucherWith);

			showMessage(request, "��������ȯ��ϸ�ɹ�");
		} catch (AppException e) {
			logger.error("��������ȯ��ϸ[" + voucherWith + "]ʧ��", e);
			showMessage(request, "��������ȯ��ϸʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ������ȯ��ϸ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherWith);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵĴ���ȯ��ϸ
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		VoucherWith voucherWith = null;
		try {
			voucherWith = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, voucherWith)) {
			//	return edit(request, response);
			//}

			this.voucherWithService.update(voucherWith);
			showMessage(request, "�޸Ĵ���ȯ��ϸ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĵ���ȯ��ϸ[" + voucherWith + "]ʧ��", e);
			showMessage(request, "�޸Ĵ���ȯ��ϸʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�еĴ���ȯ��ϸ
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] voucherWiths = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : voucherWiths) {
				this.voucherWithService.delete(id);
			}
			showMessage(request, "ɾ������ȯ��ϸ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ������ȯ��ϸʱʧ��", e);
			showMessage(request, "ɾ������ȯ��ϸʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setVoucherWithService(VoucherWithService voucherWithService) {
		this.voucherWithService = voucherWithService;
	}
}