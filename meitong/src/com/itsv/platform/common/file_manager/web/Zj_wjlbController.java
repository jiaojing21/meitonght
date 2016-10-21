package com.itsv.platform.common.file_manager.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.common.fileMgr.vo.ZjWjlb;
import com.itsv.platform.common.file_manager.bo.Zj_wjlbService;

/**
 * ˵�������ӣ��޸ģ�ɾ���洢�����Ϣ��ǰ�˴�����
 * 
 * @author milu
 * @since 2007-07-17
 * @version 1.0
 */
public class Zj_wjlbController extends BaseCURDController<ZjWjlb> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(Zj_wjlbController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.ZjWjlb";

	private Zj_wjlbService zj_wjlbService; // �߼������

	// ���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		ZjWjlb ZjWjlb = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			ZjWjlb = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", ZjWjlb);
		} else {
			ZjWjlb = new ZjWjlb();
		}

		this.zj_wjlbService.queryByVO(records, ZjWjlb);
	}

	// ��ʾ���Ӵ洢�����Ϣҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// ��ʾ�޸Ĵ洢�����Ϣҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			ZjWjlb ZjWjlb = this.zj_wjlbService.queryById(id);
			if (null == ZjWjlb) {
				showMessage(request, "δ�ҵ���Ӧ�Ĵ洢�����Ϣ��¼��������");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, ZjWjlb);
			}			
			
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���������洢�����Ϣ
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		ZjWjlb ZjWjlb = null;
		try {
			ZjWjlb = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, ZjWjlb)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// ZjWjlb);
			// }

			this.zj_wjlbService.add(ZjWjlb);

			showMessage(request, "�����洢�����Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("�����洢�����Ϣ[" + ZjWjlb + "]ʧ��", e);
			showMessage(request, "�����洢�����Ϣʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ���洢�����Ϣ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ZjWjlb);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵĴ洢�����Ϣ
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		ZjWjlb ZjWjlb = null;
		try {
			ZjWjlb = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, ZjWjlb)) {
			// return edit(request, response);
			// }

			this.zj_wjlbService.update(ZjWjlb);
			showMessage(request, "�޸Ĵ洢�����Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĵ洢�����Ϣ[" + ZjWjlb + "]ʧ��", e);
			showMessage(request, "�޸Ĵ洢�����Ϣʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�еĴ洢�����Ϣ
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] zj_wjlbs = ServletRequestUtils
				.getStringParameters(request, "p_id");
		// ������ɾ���ɹ�
		try {
			for (String id : zj_wjlbs) {
				this.zj_wjlbService.delete(id);
			}
			showMessage(request, "ɾ���洢�����Ϣ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ���洢�����Ϣʱʧ��", e);
			showMessage(request, "ɾ���洢�����Ϣʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setZj_wjlbService(Zj_wjlbService zj_wjlbService) {
		this.zj_wjlbService = zj_wjlbService;
	}
}