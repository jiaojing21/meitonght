package com.itsv.platform.system.reset_pw.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;

import com.itsv.gbp.core.util.MD5;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.platform.system.reset_pw.bo.PWUserService;
import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.platform.system.chooseunit.bo.ChooseUnitService;

/**
 * ˵�������ӣ��޸ģ�ɾ���û���ǰ�˴�����
 * 
 * @author admin 2005-2-1
 */
public class PWUserController extends BaseCURDController<PWUser> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(PWUserController.class);

	private PWUserService userService; // �߼������

	private ChooseUnitService unitService; // ��λ�߼������

	private String treeView; // ��״�б����ͼ

	private String listView; // �û��б���ʾ��ͼ
	
	private String repareView; // �޸�������ͼ

	/**
	 * ��ҳ�������ʾ��λ�����뵥λ����ͬ���ǣ����ڵ�ɵ��
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryAll());

		return mnv;
	}

	/**
	 * �û��б���ʾҳ�档��ʾָ����λ�µ������û�
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());

		String unitid="";
		try {
			unitid = ServletRequestUtils.getStringParameter(request, "unitId");
			if (unitid==null) {
				unitid = ServletRequestUtils.getStringParameter(request, "p_unitId");
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		// ���ָ���˵�λ������ʾ��ǰ��λ���û���������ʾȫ���û�
		if (unitid!=null&&!unitid.equals("")) {
			mnv.addObject("unitId", unitid);
			mnv.addObject(WebConfig.DATA_NAME, this.userService
					.queryByUnitId(unitid));
		} else {
			mnv.addObject(WebConfig.DATA_NAME, this.userService.queryAll());
		}
		return mnv;
	}

	// ��ʾ�����û�ҳ��ǰ��׼������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// ��ʾ�޸��û�ҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	/**
	 * ���������û�
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * �����޸ĵ��û�
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * ɾ��ѡ�е��û�
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * ���޸��������
	 */
	public ModelAndView reparePwd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		mnv.addObject(WebConfig.DATA_NAME, userService.getUserInfo());
		mnv.setViewName(this.getRepareView());
		mnv.addObject("reparePwd", "0");
		return mnv;
	}
	
	/**
	 * �޸�����
	 */
	public ModelAndView savePwd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		mnv.setViewName(this.getRepareView());
		PWUser user = this.param2Object(request);
		PWUser newUser = this.userService.queryById(user.getId());
		
		
		MD5 md = new MD5();
		String oldPassMD5 = newUser.getPassword();
		String confirmPass =request.getParameter("p_PWUser_input");
		if(confirmPass!=null&&md.getMD5ofStr(confirmPass).equalsIgnoreCase(oldPassMD5)){
			newUser.setPassword(md.getMD5ofStr(user.getPassword()).toLowerCase());
			this.userService.update(newUser);
			showMessage(request, "�û������޸���ɣ�");
		}else{
			showMessage(request, "ԭʼ������󣡣�");
		}
		mnv.addObject(WebConfig.DATA_NAME, userService.getUserInfo());
		mnv.addObject("reparePwd", "0");
		return mnv;
	}
	
	// �����˵��������������Ϣ
	private TreeConfig getUnitTreeConfig() {
		// ������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta
				.setTitle("<span onclick='javascript:parent.hitTree(0, 0, 0, 0, 0);' style='cursor:hand;''>��λ�б�</span>");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");
		return meta;
	}

	/**
	 * �����û�����
	 */
	public ModelAndView resetPW(HttpServletRequest request,
			HttpServletResponse response) {
		String[] userIds = new String[0];
		
		/***
		 * ace <br>
		 * �����û���Ĭ������ʹ��md5����<br>
		 * Сд
		 */
		MD5 md = new MD5();
		String s ="123456";
		String initPW =md.getMD5ofStr(s).toLowerCase();
		//String initPW = "123456";
		
		PWUser user = null;
		try {
			userIds = request.getParameterValues("p_id");
			//if (request.getParameter("initPW") != null)
			//	initPW = request.getParameter("initPW");
			for (int i = 0; i < userIds.length; i++) {
				user = this.userService.queryById(userIds[i]);
				user.setPassword(initPW);
				this.userService.update(user);
			}
			showMessage(request, "�����û�����ɹ�");
		} catch (AppException e) {
			logger.error("�����û�����ʧ��", e);
			showMessage(request, "�����û�����ʧ�ܣ�" + e.getMessage(), e);

		}
		return list(request, response);
	}

	/** ����Ϊset,get���� */
	public void setUserService(PWUserService userService) {
		this.userService = userService;
	}

	public void setUnitService(ChooseUnitService unitService) {
		this.unitService = unitService;
	}

	public String getTreeView() {
		return treeView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}
	public String getRepareView() {
		return repareView;
	}

	public void setRepareView(String repareView) {
		this.repareView = repareView;
	}

}