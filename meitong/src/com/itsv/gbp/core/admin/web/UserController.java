package com.itsv.gbp.core.admin.web;

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
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.KeyGenerator;

/**
 * ˵�������ӣ��޸ģ�ɾ���û���ǰ�˴�����
 * 
 * @author admin 2005-2-1
 */
public class UserController extends BaseCURDController<User> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(UserController.class);

	private UserService userService; // �߼������

	private String treeView; // ��״�б����ͼ

	private String listView; // �û��б���ʾ��ͼ

	/**
	 * ��ҳ�������ʾ��λ�����뵥λ����ͬ���ǣ����ڵ�ɵ��
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));

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

		} catch (ServletRequestBindingException e) {
			if (unitid == null || (unitid != null && unitid.equals(""))) {
				try {
					unitid = ServletRequestUtils.getStringParameter(request,
							"p_User_unitId");
				} catch (ServletRequestBindingException e1) {
					e1.printStackTrace();
				}
			}
		}

		// ���ָ���˵�λ������ʾ��ǰ��λ���û���������ʾȫ���û�
		if (unitid != null && !unitid.equals("")) {
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
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);

		mnv.addObject("unitId", uid);
	}

	// ��ʾ�޸��û�ҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		mnv.addObject("unitId", uid);
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = this.userService.queryById(id);
		if (null == user) {
			showMessage(request, "δ�ҵ���Ӧ���û���¼��������");
			mnv = list(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, user);
		}
	}

	/**
	 * ���������û�
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		String uid = null;
		User user = null;
		try {
			uid = ServletRequestUtils.getStringParameter(request, "p_unitId");

			request.setAttribute("unitId", uid);

			user = param2Object(request);

			// ����У�飬��ʧ��ֱ�ӷ���
			if (!validate(request, user)) {
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME, user);
			}
			
			// �����û���Ӧ�Ľ�ɫ
			fillRoles(user, ServletRequestUtils.getStringParameters(request,
					"roleIds"));
			this.userService.add(user);

			showMessage(request, "�����û��ɹ�");
		} catch (AppException e) {
			logger.error("�����û�[" + user + "]ʧ��", e);
			showMessage(request, "�����û�ʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ���û�
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, user);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

		return list(request, response);
	}

	/**
	 * �����޸ĵ��û�
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		request.setAttribute("unitId", uid);
		User user = null;
		try {
			user = param2Object(request);
			// ����У�飬��ʧ��ֱ�ӷ���
			if (!validate(request, user)) {
				return edit(request, response);
			}

			// �����û���Ӧ�Ľ�ɫ
			fillRoles(user, ServletRequestUtils.getStringParameters(request,
					"roleIds"));
			User pwduser = this.userService.queryById(user.getId());
			user.setPassword(pwduser.getPassword());
			this.userService.update(user);

			showMessage(request, "�޸��û��ɹ�");
		} catch (AppException e) {
			logger.error("�޸��û�[" + user + "]ʧ��", e);
			showMessage(request, "�޸��û�ʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return list(request, response);
	}

	/**
	 * ���ݴ����Ľ�ɫID��������û���Ӧ�Ľ�ɫ����
	 * 
	 * @param user
	 * @param intParameters
	 */
	private void fillRoles(User user, String[] roleIds) {
		if (roleIds == null || roleIds.length == 0)
			return;
		List<Role> roles = new ArrayList<Role>();
		for (String id : roleIds) {
			Role role = new Role();
			role.setId(id);
			roles.add(role);
		}
		user.setRoles(roles);
	}

	/**
	 * ɾ��ѡ�е��û�
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		request.setAttribute("unitId", uid);

		String[] users = ServletRequestUtils.getStringParameters(request, "p_id");
		// ������ɾ���ɹ�
		try {
			for (String id : users) {
				this.userService.delete(id);
			}
			showMessage(request, "ɾ���û��ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ���û�ʱʧ��", e);
			showMessage(request, "ɾ���û�ʧ�ܣ�" + e.getMessage(), e);
		}

		return list(request, response);
	}

	// �����˵��������������Ϣ
	private TreeConfig getUnitTreeConfig() {
		// ������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("��λ�б�");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/**
	 * �����û���ʾ˳��
	 */
	public ModelAndView sort(HttpServletRequest request,
			HttpServletResponse response) {
		User user = null;
		try {
			int type = 0;
			try {
				type = ServletRequestUtils.getIntParameter(request, "p_type");
			} catch (Exception ex) {
			}
			user = param2Object(request);
			request.setAttribute("unitId", user.getUnitId());
			List<User> list = this.userService.queryByUnitId(user.getUnitId());
			User obj = null;
			for (int i = 0; i < list.size(); i++) {
				obj = (User) list.get(i);
				if (obj.getId().equals(user.getId())) {
					if (type < 0) {
						// ����
						if (list.size() != 1 && i > 0) {
							// ѭ���ƶ�
							for (int k = 1; k <= (-1 * type); k++) {
								if (i - k < 0)
									break;
								Long sortno = obj.getSortno();
								User upobj = (User) list.get(i - k);
								obj.setSortno(upobj.getSortno());
								upobj.setSortno(sortno);
								this.userService.update(upobj);
							}
							this.userService.update(obj);
						}
					} else if (type > 0) {
						// ����
						if (list.size() != 1 && i < list.size() - 1) {
							// ѭ���ƶ�
							for (int k = 1; k <= type; k++) {
								if (i + k >= list.size())
									break;
								Long sortno = obj.getSortno();
								User downobj = (User) list.get(i + k);
								obj.setSortno(downobj.getSortno());
								downobj.setSortno(sortno);
								this.userService.update(downobj);
							}
							this.userService.update(obj);
						}
					}
					break;
				}
			}

		} catch (AppException e) {
		}

		return list(request, response);
	}

	/** ����Ϊset,get���� */
	public void setUserService(UserService userService) {
		this.userService = userService;
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
}