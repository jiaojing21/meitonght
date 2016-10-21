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
 * ˵��������Թ��ܽ�ɫ��ҵ����� @author Houxc
 * 
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRoleService extends BaseService {

    // ���ݷ��ʲ����
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
     * ���ӹ��ܽ�ɫ
     */
    public void add(FunctionRole functionRole) {
        this.functionRoleDao.save(functionRole);
    }

    /**
     * �޸Ĺ��ܽ�ɫ
     */
    public void update(FunctionRole functionRole) {
        this.functionRoleDao.update(functionRole);
    }

    /**
     * ɾ�����ܽ�ɫ
     */
    public void delete(Serializable id) {
        this.functionRoleDao.removeById(id);
    }

    /**
     * ����ID��ѯ���ܽ�ɫ����ϸ��Ϣ
     */
    public FunctionRole queryById(Serializable functionRoleid) {
        return this.functionRoleDao.get(functionRoleid);
    }

    /**
     * ��ȡ���еĹ��ܽ�ɫ����
     */
    public List<FunctionRole> queryAll() {
        return this.functionRoleDao.getAll();
    }

    /**
     * ���������ѯ
     */
    public List<FunctionRole> queryByVO(FunctionRole functionRole) {
        return this.functionRoleDao.queryByObject(functionRole);
    }

    /**
     * ����'��ɫID'��ȡ��Ӧ��'�˵�ID'�б�
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
     * ��������ķ�ҳ��ѯ
     */
    public IPagedList queryByVO(IPagedList records, FunctionRole functionRole) {
        return this.functionRoleDao.queryByObject(records, functionRole);
    }

    public void setFunctionRoleDao(FunctionRoleDao functionRoleDao) {
        this.functionRoleDao = functionRoleDao;
    }

    // ��װ�ء��������ԡ���function��ѯ���з��������ļ�¼
    public List queryFunctionByVO(Function function) {
        return this.functionDao.queryByObject(function);
    }

    /**
     * �ɽ�ɫid������idɾ����¼
     * 
     * @param id
     *            ����id
     * @param role_id
     *            ��ɫid
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
     * ���´˲˵������н�ɫ���ܼ�¼
     */
    public void updateForMenu(List<FunctionRole> list, String lMenuId,String lRoleId) {
        /**
         * ��ɾ�����оɼ�¼
         */
        this.removeByRoleMenuId(lMenuId,lRoleId);

        /**
         * ����¼�¼
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