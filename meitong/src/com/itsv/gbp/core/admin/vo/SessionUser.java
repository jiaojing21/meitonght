package com.itsv.gbp.core.admin.vo;

import java.util.List;

/**
 * 说明：在session里存放的用户信息。代表当前进行操作的用户
 * 
 * @author admin
 */
public class SessionUser extends User {

	private static final long serialVersionUID = -7878064220555857129L;

	private String style; //用户喜欢的界面风格

	private List<Menu> menus;

	
	private List<Role> grouproles;
	
	
    /**
     * adminh <br>
     * 20070716<br>
     * 增加用户信息中的功能权限列表
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

	/** 以下为常规的get,set方法 */
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