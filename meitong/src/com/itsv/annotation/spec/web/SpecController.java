package com.itsv.annotation.spec.web;

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

import com.itsv.annotation.spec.bo.SpecService;
import com.itsv.annotation.spec.vo.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��spec��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
@Controller
@RequestMapping("/spec.spec.vsf")
public class SpecController extends BaseAnnotationController<Spec> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(SpecController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.spec";
	@Autowired
	private SpecService specService; //�߼������

	public SpecController(){

		super.setDefaultCheckToken(true);
		super.setAddView("spec/spec/add");
		super.setIndexView("spec/spec/index");
		super.setEditView("spec/spec/edit");

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
		Spec spec = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			spec = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", spec);
		} else {
			spec = new Spec();
		}

		this.specService.queryByVO(records, spec);
	}

	//��ʾ����specҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�specҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Spec spec = this.specService.queryById(id);
		if (null == spec) {
			showMessage(request, "δ�ҵ���Ӧ��spec��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, spec);
		}
	}

	/**
	 * ��������spec
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Spec spec = null;
		try {
			spec = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, spec)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, spec);
			//}

			this.specService.add(spec);

			showMessage(request, "����spec�ɹ�");
		} catch (AppException e) {
			logger.error("����spec[" + spec + "]ʧ��", e);
			showMessage(request, "����specʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��spec
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, spec);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�spec
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Spec spec = null;
		try {
			spec = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, spec)) {
			//	return edit(request, response);
			//}

			this.specService.update(spec);
			showMessage(request, "�޸�spec�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�spec[" + spec + "]ʧ��", e);
			showMessage(request, "�޸�specʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�spec
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] specs = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : specs) {
				this.specService.delete(id);
			}
			showMessage(request, "ɾ��spec�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��specʱʧ��", e);
			showMessage(request, "ɾ��specʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}
}