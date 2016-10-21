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

import com.itsv.gbp.core.group.bo.Gbp_groupuserService;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;

/**
 * ˵�������ӣ��޸ģ�ɾ��gbp_groupuser��ǰ�˴�����
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupuserController extends BaseCURDController<Gbp_groupuser> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(Gbp_groupuserController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.gbp_groupuser";

	private Gbp_groupuserService gbp_groupuserService; //�߼������

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Gbp_groupuser gbp_groupuser = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			gbp_groupuser = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", gbp_groupuser);
		} else {
			gbp_groupuser = new Gbp_groupuser();
		}

		this.gbp_groupuserService.queryByVO(records, gbp_groupuser);
	}

	//��ʾ����gbp_groupuserҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�gbp_groupuserҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		long id = ServletRequestUtils.getLongParameter(request, "p_id", -1);
		Gbp_groupuser gbp_groupuser = this.gbp_groupuserService.queryById(id);
		if (null == gbp_groupuser) {
			showMessage(request, "δ�ҵ���Ӧ��gbp_groupuser��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, gbp_groupuser);
		}
	}

	/**
	 * ��������gbp_groupuser
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Gbp_groupuser gbp_groupuser = null;
		try {
			gbp_groupuser = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ZhengWangli 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, gbp_groupuser)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_groupuser);
			//}

			this.gbp_groupuserService.add(gbp_groupuser);

			showMessage(request, "����gbp_groupuser�ɹ�");
		} catch (AppException e) {
			logger.error("����gbp_groupuser[" + gbp_groupuser + "]ʧ��", e);
			showMessage(request, "����gbp_groupuserʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��gbp_groupuser
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_groupuser);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�gbp_groupuser
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Gbp_groupuser gbp_groupuser = null;
		try {
			gbp_groupuser = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ZhengWangli 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, gbp_groupuser)) {
			//	return edit(request, response);
			//}

			this.gbp_groupuserService.update(gbp_groupuser);
			showMessage(request, "�޸�gbp_groupuser�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�gbp_groupuser[" + gbp_groupuser + "]ʧ��", e);
			showMessage(request, "�޸�gbp_groupuserʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�gbp_groupuser
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		long[] gbp_groupusers = ServletRequestUtils.getLongParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (long id : gbp_groupusers) {
				this.gbp_groupuserService.delete(id);
			}
			showMessage(request, "ɾ��gbp_groupuser�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��gbp_groupuserʱʧ��", e);
			showMessage(request, "ɾ��gbp_groupuserʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setGbp_groupuserService(Gbp_groupuserService gbp_groupuserService) {
		this.gbp_groupuserService = gbp_groupuserService;
	}
}