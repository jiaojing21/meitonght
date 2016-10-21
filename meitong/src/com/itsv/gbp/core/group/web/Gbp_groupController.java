package com.itsv.gbp.core.group.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.group.bo.Gbp_groupService;
import com.itsv.gbp.core.group.vo.Gbp_group;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;
import com.itsv.platform.system.chooseuser.vo.UserRole;

/**
 * ˵�������ӣ��޸ģ�ɾ�����ǰ�˴�����
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupController extends BaseCURDController<Gbp_group> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(Gbp_groupController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.gbp_group";

	private Gbp_groupService gbp_groupService; // �߼������

	private String treeView; // ��״�б����ͼ

	private String showCheckUser; // ѡ���ѡ��λ�û���ҳ��

	private String showRadioUser; // ѡ��ѡ��λ�û���ҳ��
	
	private String showCheckRole;

	String USER_LIST = "userList";

	public void setShowCheckUser(String showCheckUser) {
		this.showCheckUser = showCheckUser;
	}

	public void setShowRadioUser(String showRadioUser) {
		this.showRadioUser = showRadioUser;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public void setUSER_LIST(String user_list) {
		USER_LIST = user_list;
	}

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
		Gbp_group gbp_group = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			gbp_group = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", gbp_group);
		} else {
			gbp_group = new Gbp_group();
		}

		this.gbp_groupService.queryByVO(records, gbp_group);
	}

	// ��ʾ������ҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// ��ʾ�޸���ҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Gbp_group gbp_group = this.gbp_groupService.queryById(id);
			if (null == gbp_group) {
				showMessage(request, "δ�ҵ���Ӧ�����¼��������");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, gbp_group);
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����������
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Gbp_group gbp_group = null;
		try {
			gbp_group = param2Object(request);

			this.gbp_groupService.add(gbp_group);

			showMessage(request, "������ɹ�");
		} catch (AppException e) {
			logger.error("������[" + gbp_group + "]ʧ��", e);
			showMessage(request, "������ʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ����
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					gbp_group);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ���
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Gbp_group gbp_group = null;
		try {
			gbp_group = param2Object(request);

			this.gbp_groupService.update(gbp_group);
			showMessage(request, "�޸���ɹ�");
		} catch (AppException e) {
			logger.error("�޸���[" + gbp_group + "]ʧ��", e);
			showMessage(request, "�޸���ʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е���
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] gbp_groups = ServletRequestUtils.getStringParameters(request,
				"p_id");
		// ������ɾ���ɹ�
		try {
			for (String id : gbp_groups) {
				this.gbp_groupService.delete(id);
			}
			showMessage(request, "ɾ����ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ����ʱʧ��", e);
			showMessage(request, "ɾ����ʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setGbp_groupService(Gbp_groupService gbp_groupService) {
		this.gbp_groupService = gbp_groupService;
	}

	public String getTreeView() {
		return treeView;
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

	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		// mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv
				.addObject(WebConfig.DATA_NAME, this.gbp_groupService
						.queryAllUnit());

		return mnv;
	}

	/**
	 * ����û����ϵ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addUserGroup(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mnv = null;
		try {
			// ȡ��ѡ����û�id
			String s = ServletRequestUtils.getStringParameter(request, "uids");

			// ȡ�ý��в�������id
			String groupId = ServletRequestUtils.getStringParameter(request,
					"groupId");

			// stub
			System.out.println("Group ID>>>>" + groupId);
			System.out.println("User IDs>>>>" + s);

			//
			if (s != null && groupId != null) {
				String sa[] = s.split(",");
				for (int i = 0; i < sa.length; i++) {
					String userid = sa[i];
					try {
						if (userid != null && userid.length() == 32) {
							if (i == 0) {
								// ɾ������ԭ��Group User��ϵ
								this.gbp_groupService
										.deleteAllGroupUserByGroupId(groupId);
							}
							//ƴװVo
							Gbp_groupuser tmpGu = new Gbp_groupuser();
							tmpGu.setGroupid(groupId);
							tmpGu.setUserid(userid);
							this.gbp_groupService.addGroupUser(tmpGu);

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return mnv;
	}
	/**
	 * ������ɫ��ϵ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addRoleGroup(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mnv = null;
		try {
			// ȡ��ѡ����û�id
			String s = ServletRequestUtils.getStringParameter(request, "rids");

			// ȡ�ý��в�������id
			String groupId = ServletRequestUtils.getStringParameter(request,
					"groupId");

			// stub
			System.out.println("Group ID>>>>" + groupId);
			System.out.println("role IDs>>>>" + s);
			
			//
			if (s != null && groupId != null) {
				String sa[] = s.split(",");
				for (int i = 0; i < sa.length; i++) {
					String roleid = sa[i];
					try {
						if (roleid != null && roleid.length() == 32) {
							if (i == 0) {
								// ɾ������ԭ��Group Role��ϵ
								this.gbp_groupService
										.deleteAllGroupRoleByGroupId(groupId);
							}
							//ƴװVo
							Gbp_grouprole tmpGr = new Gbp_grouprole();
							tmpGr.setGroupid(groupId);
							tmpGr.setRoleid(roleid);
							this.gbp_groupService.addGroupRole(tmpGr);

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			
			
			
			
			
			
			
			
			
		
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return mnv;
	}
	
	
	public ModelAndView showCheckRole(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mnv = null;

			mnv = new ModelAndView(getShowCheckRole());

		// ȡ�õ�ǰ����������Ӧ���û���Ϣ
		String groupId = (String) request.getParameter("gid");
		List checkedRoleIdList = new ArrayList();
		if (groupId != null) {
			checkedRoleIdList = this.gbp_groupService
					.queryRoleIdsByGroupId(groupId);

		}		
		
		List roleList = this.gbp_groupService.queryAllRole();
		mnv.addObject("groupId", groupId);// 
		mnv.addObject("roleList", roleList);// ��ɫ�б�
		mnv.addObject("checkedRoleIdList", checkedRoleIdList);
		
		return mnv;
		
	}
	
	
	
	
	
	/**
	 * sgb 070703 add ��ҳ����ʾ��ѡ��λ\��ɫ\�����û���
	 */
	public ModelAndView showCheckUnitUser(HttpServletRequest request,
			HttpServletResponse response) {

		// ModelAndView depmnv =this.showTree(request, response);
		String type = request.getParameter("type");// ��ʶ�Ƕ�ѡ���ǵ�ѡ
		ModelAndView mnv = null;
		if (type != null && type.equals("radio"))
			mnv = new ModelAndView(getShowRadioUser());
		else
			mnv = new ModelAndView(getShowCheckUser());

		// ȡ�õ�ǰ����������Ӧ���û���Ϣ
		String groupId = (String) request.getParameter("gid");
		List checkedUserIdList = new ArrayList();
		if (groupId != null) {
			checkedUserIdList = this.gbp_groupService
					.queryUserIdByGroupId(groupId);

		}

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());

		// ��õ�λ�б�
		java.util.ArrayList unitList = (java.util.ArrayList) this.gbp_groupService
				.queryAllUnit();

		// �����Ա�б�
		java.util.ArrayList userList = (java.util.ArrayList) this.gbp_groupService
				.queryAllUser();

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
					unitObj.setEnabled(user.getEnabled());// �û��Ƿ����
					unitUserList.add(unitObj);// �洢���ݶ���
				}
			}
		}

		// �����Ա��ɫ�б�
		java.util.ArrayList roleList = (java.util.ArrayList) this.gbp_groupService
				.queryAllRole();
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
						roleStart++; // ��Ա��ŵ���
						rolecodestr = String.valueOf(rolecode)
								+ "000".substring(String.valueOf(roleStart)
										.length()) + String.valueOf(roleStart);
						nodeObj.setCode(rolecodestr);// ����
						nodeObj.setName(user.getRealName());// ����
						nodeObj.setLeaf(true);// �Ƿ�Ҷ�ӽڵ�
						nodeObj.setIdClass(roleidClass + 1);// �㼶
						nodeObj.setEnabled(user.getEnabled());// �û��Ƿ����

						roleUserList.add(nodeObj);// �洢�ڵ����
						break;
					}
				}
			}
		}

		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setDictname("�û�״̬");
		List<Itsv_dictionary> dictList = gbp_groupService
				.queryDictByVO(itsv_dictionary);
		itsv_dictionary = (Itsv_dictionary) dictList.get(0);

		Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
		qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
		List<Itsv_dictionary> statusList = gbp_groupService
				.queryDictByVO(qryitsv_dictionary);

		int statuscode = 500;
		roleidClass = 1;
		java.util.ArrayList statusUserList = new java.util.ArrayList();// �洢���ݶ����б�
		for (Itsv_dictionary dictObj : statusList) {
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
				if (dictObj.getHardcode().equalsIgnoreCase(user.getStatus())) {
					nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();
					nodeObj.setId(user.getId());// ���
					statusStart++; // ��Ա��ŵ���
					rolecodestr = String.valueOf(statuscode)
							+ "000".substring(String.valueOf(statusStart)
									.length()) + String.valueOf(statusStart);
					nodeObj.setCode(rolecodestr);// ����
					nodeObj.setName(user.getRealName());// ����
					nodeObj.setLeaf(true);// �Ƿ�Ҷ�ӽڵ�
					nodeObj.setIdClass(roleidClass + 1);// �㼶
					nodeObj.setEnabled(user.getEnabled());// �û��Ƿ����

					statusUserList.add(nodeObj);// �洢�ڵ����
				}
			}
		}

		mnv.addObject(WebConfig.DATA_NAME, unitUserList);// ��λ��Ա�б�
		mnv.addObject(USER_LIST, userList);// ��Ա�б�
		mnv.addObject("roleList", roleUserList);// ��ɫ��Ա�б�
		mnv.addObject("statusUserList", statusUserList);// ״̬��Ա�б�
		mnv.addObject("UserIdList", checkedUserIdList);// ��ѡ����û�id
		mnv.addObject("groupId", groupId);
		return mnv;
	}

	public String getShowCheckUser() {
		return showCheckUser;
	}

	public String getShowRadioUser() {
		return showRadioUser;
	}

	public String getShowCheckRole() {
		return showCheckRole;
	}

	public void setShowCheckRole(String showCheckRole) {
		this.showCheckRole = showCheckRole;
	}

}