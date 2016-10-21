package com.itsv.annotation.voucheruser.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

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

import com.itsv.annotation.voucheruser.bo.VoucherUserService;
import com.itsv.annotation.voucheruser.vo.VoucherUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ���û�����ȯ���ǰ�˴�����
 * 
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */
@Controller
@RequestMapping("/voucheruser.voucherUser.vsf")
public class VoucherUserController extends BaseAnnotationController<VoucherUser> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(VoucherUserController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.voucherUser";
	@Autowired
	private VoucherUserService voucherUserService; //�߼������

	public VoucherUserController(){

		super.setDefaultCheckToken(true);
		super.setAddView("voucheruser/voucherUser/add");
		super.setIndexView("voucheruser/voucherUser/index");
		super.setEditView("voucheruser/voucherUser/edit");

	}

  /**
   * ע���Զ�������ת���࣬����ת�����ڶ���
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
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
		VoucherUser voucherUser = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			voucherUser = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", voucherUser);
		} else {
			voucherUser = new VoucherUser();
		}

		this.voucherUserService.queryByVO(records, voucherUser);
	}

	//��ʾ�����û�����ȯ��ҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸��û�����ȯ��ҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		VoucherUser voucherUser = this.voucherUserService.queryById(id);
		if (null == voucherUser) {
			showMessage(request, "δ�ҵ���Ӧ���û�����ȯ���¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, voucherUser);
		}
	}

	/**
	 * ���������û�����ȯ��
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		VoucherUser voucherUser = null;
		try {
			voucherUser = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, voucherUser)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherUser);
			//}

			this.voucherUserService.add(voucherUser);

			showMessage(request, "�����û�����ȯ��ɹ�");
		} catch (AppException e) {
			logger.error("�����û�����ȯ��[" + voucherUser + "]ʧ��", e);
			showMessage(request, "�����û�����ȯ��ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ���û�����ȯ��
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherUser);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ��û�����ȯ��
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		VoucherUser voucherUser = null;
		try {
			voucherUser = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, voucherUser)) {
			//	return edit(request, response);
			//}

			this.voucherUserService.update(voucherUser);
			showMessage(request, "�޸��û�����ȯ��ɹ�");
		} catch (AppException e) {
			logger.error("�޸��û�����ȯ��[" + voucherUser + "]ʧ��", e);
			showMessage(request, "�޸��û�����ȯ��ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е��û�����ȯ��
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] voucherUsers = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : voucherUsers) {
				this.voucherUserService.delete(id);
			}
			showMessage(request, "ɾ���û�����ȯ��ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ���û�����ȯ��ʱʧ��", e);
			showMessage(request, "ɾ���û�����ȯ��ʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setVoucherUserService(VoucherUserService voucherUserService) {
		this.voucherUserService = voucherUserService;
	}
}