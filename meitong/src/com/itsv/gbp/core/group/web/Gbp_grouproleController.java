package com.itsv.gbp.core.group.web;

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

import com.itsv.gbp.core.group.bo.Gbp_grouproleService;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;

/**
 * ˵�������ӣ��޸ģ�ɾ��gbp_grouprole��ǰ�˴�����
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_grouproleController extends BaseCURDController<Gbp_grouprole> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(Gbp_grouproleController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.gbp_grouprole";

	private Gbp_grouproleService gbp_grouproleService; //�߼������

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Gbp_grouprole gbp_grouprole = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			gbp_grouprole = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", gbp_grouprole);
		} else {
			gbp_grouprole = new Gbp_grouprole();
		}

		this.gbp_grouproleService.queryByVO(records, gbp_grouprole);
	}

	//��ʾ����gbp_grouproleҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�gbp_grouproleҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		long id = ServletRequestUtils.getLongParameter(request, "p_id", -1);
		Gbp_grouprole gbp_grouprole = this.gbp_grouproleService.queryById(id);
		if (null == gbp_grouprole) {
			showMessage(request, "δ�ҵ���Ӧ��gbp_grouprole��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, gbp_grouprole);
		}
	}

	/**
	 * ��������gbp_grouprole
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Gbp_grouprole gbp_grouprole = null;
		try {
			gbp_grouprole = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ZhengWangli 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, gbp_grouprole)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_grouprole);
			//}

			this.gbp_grouproleService.add(gbp_grouprole);

			showMessage(request, "����gbp_grouprole�ɹ�");
		} catch (AppException e) {
			logger.error("����gbp_grouprole[" + gbp_grouprole + "]ʧ��", e);
			showMessage(request, "����gbp_grouproleʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��gbp_grouprole
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_grouprole);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�gbp_grouprole
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Gbp_grouprole gbp_grouprole = null;
		try {
			gbp_grouprole = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ZhengWangli 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, gbp_grouprole)) {
			//	return edit(request, response);
			//}

			this.gbp_grouproleService.update(gbp_grouprole);
			showMessage(request, "�޸�gbp_grouprole�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�gbp_grouprole[" + gbp_grouprole + "]ʧ��", e);
			showMessage(request, "�޸�gbp_grouproleʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�gbp_grouprole
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		long[] gbp_grouproles = ServletRequestUtils.getLongParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (long id : gbp_grouproles) {
				this.gbp_grouproleService.delete(id);
			}
			showMessage(request, "ɾ��gbp_grouprole�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��gbp_grouproleʱʧ��", e);
			showMessage(request, "ɾ��gbp_grouproleʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setGbp_grouproleService(Gbp_grouproleService gbp_grouproleService) {
		this.gbp_grouproleService = gbp_grouproleService;
	}
}