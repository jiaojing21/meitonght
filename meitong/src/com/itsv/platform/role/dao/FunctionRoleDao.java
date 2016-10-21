package com.itsv.platform.role.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.role.vo.FunctionRole;

/**
 * 功能角色对象的数据访问类
 * 
 * @author Houxc
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRoleDao extends HibernatePagedDao<FunctionRole> {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(FunctionRoleDao.class);

    @Override
    public void save(Object o) throws OrmException {
        // 进行基本校验
        check((FunctionRole) o);

        super.save(o);
    }

    @Override
    public void update(Object o) throws OrmException {
        // 进行基本校验
        check((FunctionRole) o);

        super.update(o);
    }

    /**
     * 组合条件查询
     */
    public List<FunctionRole> queryByObject(FunctionRole functionRole) {
        return find(buildCriteriaByVO(functionRole));
    }

    /**
     * 分页查询。<br>
     * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
     */
    public IPagedList queryByObject(IPagedList records,
            FunctionRole functionRole) {
        // 如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
        if (records.getTotalNum() == -1) {
            records.setParam(buildCriteriaByVO(functionRole));
        }

        return pagedQuery(records, (DetachedCriteria) records.getParam());
    }

    private DetachedCriteria buildCriteriaByVO(FunctionRole functionRole) {
        DetachedCriteria dc = createDetachedCriteria();

        // ID
        if (functionRole.getId() != null) {
            dc.add(Restrictions.eq("id", functionRole.getId()));
        }

        // function_id
        if (functionRole.getFunction_id() != null) {
            dc.add(Restrictions
                    .eq("function_id", functionRole.getFunction_id()));
        }

        // role_id
        if (functionRole.getRole_id() != null) {
            dc.add(Restrictions.eq("role_id", functionRole.getRole_id()));
        }

        // role_id
        if (functionRole.getMenu_id() != null) {
            dc.add(Restrictions.eq("menu_id", functionRole.getMenu_id()));
        }

        return dc;
    }

    // 数据校验
    private void check(FunctionRole functionRole) throws OrmException {

    }

    public List<String> findFunctionsByRoles(List<Role> roles) {
        // TODO Auto-generated method stub

        List<String> ret = new ArrayList<String>();

        if (roles == null || roles.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        StringBuffer hsql = new StringBuffer(
                                             "select distinct fr from FunctionRole fr where 1=1 and (");
        List<String> params = new ArrayList<String>();
        for (Role role : roles) {
            if (!params.isEmpty()) {
                hsql.append(" or ");
            }
            hsql.append(" fr.role_id = ? ");
            params.add(role.getId());
        }
        hsql.append(") order by fr.id");

        List<FunctionRole> tmp = find(hsql.toString(), params.toArray());

        for (FunctionRole fr : tmp) {
            if(!ret.contains(fr.getFunction_id())){
                ret.add(fr.getFunction_id());
            }
        }

        return ret;
    }

    /*
     * public void removeByFRIds(long fid, long role_id) { String hsql = "FROM
     * FunctionRole o WHERE o.function_id='" + fid + "' AND o.role_id='" +
     * role_id + "'"; this.getHibernateTemplate().delete(hsql); }
     */

}
