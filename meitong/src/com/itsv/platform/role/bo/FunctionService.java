package com.itsv.platform.role.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.platform.role.dao.FunctionDao;
import com.itsv.platform.role.vo.Function;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * ˵��������Թ��ܵ�ҵ����� @author Houxc
 * 
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionService extends BaseService {

    // ���ݷ��ʲ����
    private FunctionDao functionDao;

    /**
     * ���ӹ���
     */
    public void add(Function function) {
        this.functionDao.save(function);
    }

    /**
     * �޸Ĺ���
     */
    public void update(Function function) {
        this.functionDao.update(function);
    }

    /**
     * ɾ������
     */
    public void delete(Serializable id) {
        this.functionDao.removeById(id);
    }

    /**
     * ����ID��ѯ���ܵ���ϸ��Ϣ
     */
    public Function queryById(Serializable functionid) {
        return this.functionDao.get(functionid);
    }

    /**
     * ��ȡ���еĹ��ܶ���
     */
    public List<Function> queryAll() {
        return this.functionDao.getAll();
    }

    /**
     * ���������ѯ
     */
    public List<Function> queryByVO(Function function) {
        return this.functionDao.queryByObject(function);
    }

    /**
     * ��������ķ�ҳ��ѯ
     */
    public IPagedList queryByVO(IPagedList records, Function function) {
        return this.functionDao.queryByObject(records, function);
    }

    public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    /**
     * �жϹ������Ƿ��Ѿ�����
     * 
     * @param code
     * @return
     */
    public boolean isFxnCodeExist(String code) {
        //
        List<Function> tmp = new ArrayList<Function>();
        Function function = new Function();
        function.setCode(code);
        try {
            tmp = this.queryByVO(function);
            if (tmp.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ϊ�༭����<br>
     * �޸Ĺ���Ҳ�����޸Ĺ��ܵ�code<br>
     * ��Ҫ�������code�ڱ����Ƿ���� ����Ƿ���˸����ڵ�id�ġ����ܡ��ô˹�����<br>
     * 
     * @param function
     * @return
     */
    public boolean isFxnCodeExist(Function function) {

        List<Function> tmps = new ArrayList<Function>();
        Function tmp = new Function();
        tmp.setCode(function.getCode());
        try {
            // �������code��ͬ�ļ�¼
            tmps = this.queryByVO(tmp);
            // ���û���ظ���code˵��û��ʹ��
            if (tmps.size() > 0) {
                // �ж���Щcode�ظ��ļ�¼
                for (Function f : tmps) {
                    // ���code��ͬ����id��ͬ˵������޸���û�и�code
                    // ���code���ڶ���id�������޸ĵĲ�ͬ��˵����code�޸��ظ���
                    if (!f.getId().equals(function.getId())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}