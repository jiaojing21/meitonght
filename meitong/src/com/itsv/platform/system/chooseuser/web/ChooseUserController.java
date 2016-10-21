package com.itsv.platform.system.chooseuser.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.itsv.platform.system.chooseunit.bo.ChooseUnitService;
import com.itsv.platform.system.chooseuser.bo.ChooseUserService;
import com.itsv.platform.system.chooseuser.vo.UserRole;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;
import com.itsv.platform.system.chooseuser.bo.UserRoleService;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;

/**
 * ˵�������ӣ��޸ģ�ɾ���û���ǰ�˴�����
 * 
 * @author sgb 2007-07-07
 */
public class ChooseUserController extends BaseCURDController<ChooseUser> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(ChooseUserController.class);

	private ChooseUserService userService; // �߼������

	private UserRoleService roleService; // ��ɫ

	private ChooseUnitService unitService; // ��λ�߼������
	
	private Itsv_dictionaryService dictionaryService; // �ֵ����

	private String treeView; // ��״�б����ͼ

	private String listView; // �û��б���ʾ��ͼ

	// sgb add 070629
	private String showCheckUser; // ѡ���ѡ��λ�û���ҳ��

	private String showRadioUser; // ѡ��ѡ��λ�û���ҳ��

	String USER_LIST = "userList";

	// sgb end

	/**
	 * ��ҳ�������ʾ��λ�����뵥λ����ͬ���ǣ����ڵ�ɵ��
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		// mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryAll());

		return mnv;
	}

	/**
	 * �û��б���ʾҳ�档��ʾָ����λ�µ������û�
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());

		String unitid = "";
		try {
			unitid = ServletRequestUtils.getStringParameter(request, "unitId");
			if (unitid !=null) {
				unitid = ServletRequestUtils.getStringParameter(request, "p_unitId");
			}			
			
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���ָ���˵�λ������ʾ��ǰ��λ���û���������ʾȫ���û�
		if (unitid !=null) {
			mnv.addObject("unitId", unitid);
			mnv.addObject(WebConfig.DATA_NAME, this.userService
					.queryByUnitId(unitid));
		} else {
			mnv.addObject(WebConfig.DATA_NAME, this.userService.queryAll());
		}
		return mnv;
	}

	// �����˵��������������Ϣ
	private TreeConfig getUnitTreeConfig() {
		// ������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta
				.setTitle("<a href='javascript:parent.hitTree(0,0,0,0,0);'>�û��б�</a>");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** ����Ϊset,get���� */
	public void setUserService(ChooseUserService userService) {
		this.userService = userService;
	}

	/** ����Ϊset,get���� */
	public void setRoleService(
			com.itsv.platform.system.chooseuser.bo.UserRoleService roleService) {
		this.roleService = roleService;
	}

	/** ����Ϊset,get���� */
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

	/**
	 * sgb 070703 add ��ҳ����ʾ��ѡ��λ\��ɫ\�����û���
	 */
	public ModelAndView showCheckUnitUser(HttpServletRequest request,
			HttpServletResponse response) {

		String type = request.getParameter("type");// ��ʶ�Ƕ�ѡ���ǵ�ѡ
		ModelAndView mnv = null;
		if (type != null && type.equals("radio"))
			mnv = new ModelAndView(getShowRadioUser());
		else
			mnv = new ModelAndView(getShowCheckUser());


		
		
		
		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());

		// ��õ�λ�б�
		java.util.ArrayList unitList = (java.util.ArrayList) this.unitService
				.queryAll();

		// �����Ա�б�
		java.util.ArrayList userList = (java.util.ArrayList) this.userService
				.queryAll();

		ChooseUser user = null; // �û�����
		ChooseUnit unit = null; // ��λ����
		ChooseUnit unitObj = null;// ��λ����

		String unit_id = "";// ��λid
		String code = "";// ��λ����
		String tmpcode = "";// �����Աʱ���ɵ�λ����
		int idClass = 0;// ��λ�㼶
		String userunit_id = "";// �û����еĵ�λid

		java.util.ArrayList unitUserList = new java.util.ArrayList();
		
		// ѭ����λ�б�,�洢����,�Ա��±߲�Ҫ�����ظ�����
		java.util.HashMap map = new java.util.HashMap();
		for (int i = 0; i < unitList.size(); i++) {
			unit = (com.itsv.platform.system.chooseunit.vo.ChooseUnit) unitList
					.get(i);// ��õ�λ����
			code = unit.getCode();// ��õ�λ����
			map.put(code, code);
		}

		// ѭ����λ�б�
		for (int i = 0; i < unitList.size(); i++) {
			int unitStart = 0;// ��ϵ�λ����
			unit = (com.itsv.platform.system.chooseunit.vo.ChooseUnit) unitList
					.get(i);// ��õ�λ����
			unit.setLeaf(false);
			unitUserList.add(unit);
			
			unit_id = unit.getId();// ��õ�λid
			code = unit.getCode();// ��õ�λ����
			idClass = unit.getIdClass();
			
			// ѭ����Ա�б�
			for (int n = 0; n < userList.size(); n++) {
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);// ����û�����
				userunit_id = user.getUnitId();// ����û���Ӧ�ĵ�λid
				if (unit_id.equals(userunit_id))// �����ǰ�û��Ƿ����ڵ�ǰ��λ
				{

					unitObj = new ChooseUnit();// ʵ������λ����
					unitObj.setId(user.getId());// id
					// һ�����벻�����999
					while (unitStart < 999) {
						unitStart++;
						// ��ϱ���
						tmpcode = String.valueOf(code)
						+ "000".substring(3 - String.valueOf(unitStart)
								.length() - 1)
						+ String.valueOf(unitStart);
						if (!map.containsKey(tmpcode))
							break;
					}
					unitObj.setCode(tmpcode);// ����
					unitObj.setName(user.getRealName());// ����
					unitObj.setLeaf(true);// �Ƿ�Ҷ�ӽڵ�
					unitObj.setIdClass(idClass + 1);// �㼶
					unitObj.setEnabled(user.getEnabled());//�û��Ƿ����
					unitUserList.add(unitObj);// �洢���ݶ���
				}
			}
		}

		// �����Ա��ɫ�б�
		java.util.ArrayList roleList = (java.util.ArrayList) this.roleService
				.queryAll();
		UserRole sysRole = null;// ϵͳ��ɫ�б��еĽ�ɫ����
		UserRole userRole = null;// �û���ɫ�б��еĽ�ɫ����
		java.util.ArrayList roleUserList = new java.util.ArrayList();// �洢���ݶ����б�
		ChooseUnit nodeObj = null;// ��ʱ�ڵ����
		int rolecode = 100;// ��ʼ����ɫ����
		String rolecodestr = "";// ��ʼ����ɫ����
		int roleidClass = 1;// �㼶
		java.util.ArrayList userRoleList = new java.util.ArrayList();// �û���Ӧ��ɫ�б�
		// ѭ����ɫ�б�
		for (int i = 0; i < roleList.size(); i++) {
			int roleStart = 0;// ÿ����ɫ֮����Ա��Ÿ�������
			rolecode++;// ��ɫ�㼶����
			sysRole = (com.itsv.platform.system.chooseuser.vo.UserRole) roleList
					.get(i);// ��ý�ɫ����

			nodeObj = new ChooseUnit();// ʵ������λ����
			nodeObj.setId(sysRole.getId());// ���
			nodeObj.setCode(String.valueOf(rolecode));// ����
			nodeObj.setName(sysRole.getName());// ����
			nodeObj.setLeaf(false);// �Ƿ�Ҷ�ӽڵ�
			nodeObj.setIdClass(roleidClass);// �㼶
			roleUserList.add(nodeObj);// �洢���ݶ���

			// ѭ����Ա�б�
			for (int n = 0; n < userList.size(); n++) {
				// ����û�����
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);
				// ѭ���û��еĽ�ɫ
				for (int s = 0; s < user.getRoles().size(); s++) {
					userRole = (com.itsv.platform.system.chooseuser.vo.UserRole) user
							.getRoles().get(s);// ��ɫ����					
					if (sysRole.getId().equals(userRole.getId()))
							// �����ǰ�û��Ƿ����ڵ�ǰ��ɫ
					{
						nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();// ʵ���ڵ����

						nodeObj.setId(user.getId());// ���
						roleStart++; //��Ա��ŵ���
						rolecodestr = String.valueOf(rolecode)
								+ "000".substring(String.valueOf(roleStart)
										.length())
								+ String.valueOf(roleStart);
						nodeObj.setCode(rolecodestr);// ����
						nodeObj.setName(user.getRealName());// ����
						nodeObj.setLeaf(true);// �Ƿ�Ҷ�ӽڵ�
						nodeObj.setIdClass(roleidClass + 1);// �㼶
						nodeObj.setEnabled(user.getEnabled());//�û��Ƿ����

						roleUserList.add(nodeObj);// �洢�ڵ����
						break;
					}
				}
			}
		}

		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setDictname("�û�״̬");		
		List<Itsv_dictionary> dictList = dictionaryService.queryByVO(itsv_dictionary);
		itsv_dictionary = (Itsv_dictionary)dictList.get(0);
		
		Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
		qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
		List<Itsv_dictionary> statusList = dictionaryService.queryByVO(qryitsv_dictionary);
		
		int statuscode = 500;
		roleidClass = 1;
		java.util.ArrayList statusUserList = new java.util.ArrayList();// �洢���ݶ����б�
		for(Itsv_dictionary dictObj: statusList){
			int statusStart = 0;// ÿ����ɫ֮����Ա��Ÿ�������
			statuscode++;// ��ɫ�㼶����
			nodeObj = new ChooseUnit();// ʵ������λ����
			nodeObj.setId(dictObj.getId());// ���
			nodeObj.setCode(String.valueOf(statuscode));// ����
			nodeObj.setName(dictObj.getDictname());// ����
			nodeObj.setLeaf(false);// �Ƿ�Ҷ�ӽڵ�
			nodeObj.setIdClass(roleidClass);// �㼶
			statusUserList.add(nodeObj);// �洢���ݶ���
			for (int n = 0; n < userList.size(); n++) {
				// ����û�����
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);
				if(dictObj.getHardcode().equalsIgnoreCase(user.getStatus())){
					nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();
					nodeObj.setId(user.getId());// ���
					statusStart++; //��Ա��ŵ���
					rolecodestr = String.valueOf(statuscode)
							+ "000".substring(String.valueOf(statusStart)
									.length())
							+ String.valueOf(statusStart);
					nodeObj.setCode(rolecodestr);// ����
					nodeObj.setName(user.getRealName());// ����
					nodeObj.setLeaf(true);// �Ƿ�Ҷ�ӽڵ�
					nodeObj.setIdClass(roleidClass + 1);// �㼶
					nodeObj.setEnabled(user.getEnabled());//�û��Ƿ����

					statusUserList.add(nodeObj);// �洢�ڵ����
				}
			}
		}

		mnv.addObject(WebConfig.DATA_NAME, unitUserList);// ��λ��Ա�б�
		mnv.addObject(USER_LIST, userList);// ��Ա�б�
		mnv.addObject("roleList", roleUserList);// ��ɫ��Ա�б�
		mnv.addObject("statusUserList", statusUserList);// ״̬��Ա�б�

		return mnv;
	}

	/**
	 * sgb 070629 add
	 */
	public String getShowCheckUser() {
		return showCheckUser;
	}

	/**
	 * sgb 070629 add
	 */
	public void setShowCheckUser(String showCheckUser) {
		this.showCheckUser = showCheckUser;
	}

	/**
	 * sgb 070629 add
	 */
	public String getShowRadioUser() {
		return showRadioUser;
	}

	/**
	 * sgb 070629 add
	 */
	public void setShowRadioUser(String showRadioUser) {
		this.showRadioUser = showRadioUser;
	}

	public Itsv_dictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(Itsv_dictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}