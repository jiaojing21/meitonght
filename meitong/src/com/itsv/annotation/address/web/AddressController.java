package com.itsv.annotation.address.web;

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

import com.itsv.annotation.address.bo.AddressService;
import com.itsv.annotation.address.vo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��address��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
@Controller
@RequestMapping("/address.address.vsf")
public class AddressController extends BaseAnnotationController<Address> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(AddressController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.address";
	@Autowired
	private AddressService addressService; //�߼������

	public AddressController(){

		super.setDefaultCheckToken(true);
		super.setAddView("address/address/add");
		super.setIndexView("address/address/index");
		super.setEditView("address/address/edit");

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
		Address address = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			address = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", address);
		} else {
			address = new Address();
		}

		this.addressService.queryByVO(records, address);
	}

	//��ʾ����addressҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�addressҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Address address = this.addressService.queryById(id);
		if (null == address) {
			showMessage(request, "δ�ҵ���Ӧ��address��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, address);
		}
	}

	/**
	 * ��������address
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Address address = null;
		try {
			address = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, address)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, address);
			//}

			this.addressService.add(address);

			showMessage(request, "����address�ɹ�");
		} catch (AppException e) {
			logger.error("����address[" + address + "]ʧ��", e);
			showMessage(request, "����addressʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��address
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, address);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�address
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Address address = null;
		try {
			address = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, address)) {
			//	return edit(request, response);
			//}

			this.addressService.update(address);
			showMessage(request, "�޸�address�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�address[" + address + "]ʧ��", e);
			showMessage(request, "�޸�addressʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�address
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] addresss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : addresss) {
				this.addressService.delete(id);
			}
			showMessage(request, "ɾ��address�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��addressʱʧ��", e);
			showMessage(request, "ɾ��addressʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
}