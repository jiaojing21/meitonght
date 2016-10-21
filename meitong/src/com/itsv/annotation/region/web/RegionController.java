package com.itsv.annotation.region.web;

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

import com.itsv.annotation.region.bo.RegionService;
import com.itsv.annotation.region.vo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��region��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
@Controller
@RequestMapping("/region.region.vsf")
public class RegionController extends BaseAnnotationController<Region> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RegionController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.region";
	@Autowired
	private RegionService regionService; //�߼������

	public RegionController(){

		super.setDefaultCheckToken(true);
		super.setAddView("region/region/add");
		super.setIndexView("region/region/index");
		super.setEditView("region/region/edit");

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
		Region region = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			region = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", region);
		} else {
			region = new Region();
		}

		this.regionService.queryByVO(records, region);
	}

	//��ʾ����regionҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�regionҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Region region = this.regionService.queryById(id);
		if (null == region) {
			showMessage(request, "δ�ҵ���Ӧ��region��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, region);
		}
	}

	/**
	 * ��������region
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Region region = null;
		try {
			region = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, region)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, region);
			//}

			this.regionService.add(region);

			showMessage(request, "����region�ɹ�");
		} catch (AppException e) {
			logger.error("����region[" + region + "]ʧ��", e);
			showMessage(request, "����regionʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��region
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, region);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�region
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Region region = null;
		try {
			region = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, region)) {
			//	return edit(request, response);
			//}

			this.regionService.update(region);
			showMessage(request, "�޸�region�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�region[" + region + "]ʧ��", e);
			showMessage(request, "�޸�regionʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�region
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] regions = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : regions) {
				this.regionService.delete(id);
			}
			showMessage(request, "ɾ��region�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��regionʱʧ��", e);
			showMessage(request, "ɾ��regionʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
}