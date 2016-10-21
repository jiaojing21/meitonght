package com.itsv.gbp.core.admin.vo;

import java.util.List;

/**
 * ˵������session���ŵ��û���Ϣ������ǰ���в������û�
 * 
 * @author admin
 */
public class SessionUser extends User {

	private static final long serialVersionUID = -7878064220555857129L;

	private String style; //�û�ϲ���Ľ�����

	private List<Menu> menus;

	
	private List<Role> grouproles;
	
	
    /**
     * adminh <br>
     * 20070716<br>
     * �����û���Ϣ�еĹ���Ȩ���б�
     */
    private List<String> functions;
	
	public SessionUser() {
	}
	
	public SessionUser(User user){
		this.setId(user.getId());
		this.setUnitId(user.getUnitId());
		this.setUserName(user.getUserName());
		this.setPassword(user.getPassword());
		this.setRealName(user.getRealName());
		this.setRemark(user.getRemark());
		this.setEnabled(user.getEnabled());
		this.setRoles(user.getRoles());
	}
	
	
	
	public List<Role> getGrouproles() {
		return grouproles;
	}

	public void setGrouproles(List<Role> grouproles) {
		this.grouproles = grouproles;
	}

	/** ����Ϊ�����get,set���� */
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

}