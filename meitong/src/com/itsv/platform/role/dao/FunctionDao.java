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
 * ���ܶ�������ݷ�����
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
        // ���л���У��
        check((Function) o);

        super.save(o);
    }

    @Override
    public void update(Object o) throws OrmException {
        // ���л���У��
        check((Function) o);

        super.update(o);
    }

    /**
     * ���������ѯ
     */
    public List<Function> queryByObject(Function function) {
        return find(buildCriteriaByVO(function));
    }

    /**
     * ��ҳ��ѯ��<br>
     * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
     */
    public IPagedList queryByObject(IPagedList records, Function function) {
        // ���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

        // ��������
        if (function.getName() != null && function.getName().length() > 0) {
            dc.add(Restrictions.like("name", function.getName(),
                                     MatchMode.ANYWHERE));
        }

        // ������
        if (function.getCode() != null && function.getCode().length() > 0) {
            dc.add(Restrictions.like("code", function.getCode(),
                                     MatchMode.ANYWHERE));
        }

        // ˵��
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

    // ����У��
    private void check(Function function) throws OrmException {

    }

    /**
     * �ɡ�����id��ȡ�ù��ܶ�ӦȨ����
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
