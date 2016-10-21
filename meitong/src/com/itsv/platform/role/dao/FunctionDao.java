package com.itsv.platform.role.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.role.vo.Function;

/**
 * 功能对象的数据访问类
 * 
 * @author Houxc
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionDao extends HibernatePagedDao<Function> {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(FunctionDao.class);

    @Override
    public void save(Object o) throws OrmException {
        // 进行基本校验
        check((Function) o);

        super.save(o);
    }

    @Override
    public void update(Object o) throws OrmException {
        // 进行基本校验
        check((Function) o);

        super.update(o);
    }

    /**
     * 组合条件查询
     */
    public List<Function> queryByObject(Function function) {
        return find(buildCriteriaByVO(function));
    }

    /**
     * 分页查询。<br>
     * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
     */
    public IPagedList queryByObject(IPagedList records, Function function) {
        // 如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
        if (records.getTotalNum() == -1) {
            records.setParam(buildCriteriaByVO(function));
        }

        return pagedQuery(records, (DetachedCriteria) records.getParam());
    }

    private DetachedCriteria buildCriteriaByVO(Function function) {
        DetachedCriteria dc = createDetachedCriteria();

        // ID
        if (function.getId() != null) {
            dc.add(Restrictions.eq("id", function.getId()));
        }

        // menu_id
        if (function.getMenu_id() != null) {
            dc.add(Restrictions.eq("menu_id", function.getMenu_id()));
        }

        // 功能名称
        if (function.getName() != null && function.getName().length() > 0) {
            dc.add(Restrictions.like("name", function.getName(),
                                     MatchMode.ANYWHERE));
        }

        // 功能码
        if (function.getCode() != null && function.getCode().length() > 0) {
            dc.add(Restrictions.like("code", function.getCode(),
                                     MatchMode.ANYWHERE));
        }

        // 说明
        if (function.getRemarks() != null && function.getRemarks().length() > 0) {
            dc.add(Restrictions.like("remarks", function.getRemarks(),
                                     MatchMode.ANYWHERE));
        }

        // adddate
        if (function.getAdddate() != null) {
            dc.add(Restrictions.eq("adddate", function.getAdddate()));
        }

        return dc;
    }

    // 数据校验
    private void check(Function function) throws OrmException {

    }

    /**
     * 由‘功能id’取得功能对应权限码
     * @param functionIds
     * @return
     */
    public List<String> findFunctionCodeByIds(List<String> functionIds) {
        List<String> ret = new ArrayList<String>();
        if(functionIds!=null){
            for(String id:functionIds){
                ret.add(this.get(id).getCode());
            }
            return ret;
        }
        return Collections.EMPTY_LIST;
    }

}
