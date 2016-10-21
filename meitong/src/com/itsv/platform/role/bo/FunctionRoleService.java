package com.itsv.platform.role.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.platform.role.dao.FunctionDao;
import com.itsv.platform.role.dao.FunctionRoleDao;
import com.itsv.platform.role.vo.Function;
import com.itsv.platform.role.vo.FunctionRole;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.admin.dao.RoleMenuDao;
import com.itsv.gbp.core.admin.vo.RoleMenu;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对功能角色的业务操作 @author Houxc
 * 
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRoleService extends BaseService {

    // 数据访问层对象
    private FunctionRoleDao functionRoleDao;

    private RoleMenuDao roleMenuDao;

    private FunctionDao functionDao;

    public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    public void setRoleMenuDao(RoleMenuDao roleMenuDao) {
        this.roleMenuDao = roleMenuDao;
    }

    /**
     * 增加功能角色
     */
    public void add(FunctionRole functionRole) {
        this.functionRoleDao.save(functionRole);
    }

    /**
     * 修改功能角色
     */
    public void update(FunctionRole functionRole) {
        this.functionRoleDao.update(functionRole);
    }

    /**
     * 删除功能角色
     */
    public void delete(Serializable id) {
        this.functionRoleDao.removeById(id);
    }

    /**
     * 根据ID查询功能角色的详细信息
     */
    public FunctionRole queryById(Serializable functionRoleid) {
        return this.functionRoleDao.get(functionRoleid);
    }

    /**
     * 获取所有的功能角色对象
     */
    public List<FunctionRole> queryAll() {
        return this.functionRoleDao.getAll();
    }

    /**
     * 组合条件查询
     */
    public List<FunctionRole> queryByVO(FunctionRole functionRole) {
        return this.functionRoleDao.queryByObject(functionRole);
    }

    /**
     * 根据'角色ID'获取对应的'菜单ID'列表
     */
    public List<String> queryMenuIds(String roleid) {
        List<RoleMenu> result = this.roleMenuDao.findBy("roleId", roleid);
        List<String> ids = new ArrayList<String>();
        for (RoleMenu rm : result) {
            ids.add(rm.getMenuId());
        }
        return ids;
    }

    /**
     * 组合条件的分页查询
     */
    public IPagedList queryByVO(IPagedList records, FunctionRole functionRole) {
        return this.functionRoleDao.queryByObject(records, functionRole);
    }

    public void setFunctionRoleDao(FunctionRoleDao functionRoleDao) {
        this.functionRoleDao = functionRoleDao;
    }

    // 用装载‘功能属性’的function查询所有符合条件的记录
    public List queryFunctionByVO(Function function) {
        return this.functionDao.queryByObject(function);
    }

    /**
     * 由角色id、功能id删除记录
     * 
     * @param id
     *            功能id
     * @param role_id
     *            角色id
     */
    /*
     * public void deleteByFRId(long fid, long role_id) { try{
     * this.functionRoleDao.removeByFRIds(fid,role_id); }catch(Exception e){
     * e.printStackTrace(); } }
     */

    /**
     * 
     */
    public void removeByRoleMenuId(String menu_id,String role_id) {
        
        
        FunctionRole qo = new FunctionRole();
        qo.setMenu_id(menu_id);
        qo.setRole_id(role_id);
        
        List<FunctionRole> list = this.functionRoleDao.queryByObject(qo);
        for (FunctionRole fr : list) {
            try {
                this.functionRoleDao.removeById(fr.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新此菜单下所有角色功能记录
     */
    public void updateForMenu(List<FunctionRole> list, String lMenuId,String lRoleId) {
        /**
         * 先删除所有旧记录
         */
        this.removeByRoleMenuId(lMenuId,lRoleId);

        /**
         * 添加新记录
         */
        for (FunctionRole functionRole : list) {
            try {
                this.functionRoleDao.save(functionRole);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}