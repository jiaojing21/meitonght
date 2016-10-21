package com.itsv.platform.role.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.platform.role.dao.FunctionDao;
import com.itsv.platform.role.vo.Function;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对功能的业务操作 @author Houxc
 * 
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionService extends BaseService {

    // 数据访问层对象
    private FunctionDao functionDao;

    /**
     * 增加功能
     */
    public void add(Function function) {
        this.functionDao.save(function);
    }

    /**
     * 修改功能
     */
    public void update(Function function) {
        this.functionDao.update(function);
    }

    /**
     * 删除功能
     */
    public void delete(Serializable id) {
        this.functionDao.removeById(id);
    }

    /**
     * 根据ID查询功能的详细信息
     */
    public Function queryById(Serializable functionid) {
        return this.functionDao.get(functionid);
    }

    /**
     * 获取所有的功能对象
     */
    public List<Function> queryAll() {
        return this.functionDao.getAll();
    }

    /**
     * 组合条件查询
     */
    public List<Function> queryByVO(Function function) {
        return this.functionDao.queryByObject(function);
    }

    /**
     * 组合条件的分页查询
     */
    public IPagedList queryByVO(IPagedList records, Function function) {
        return this.functionDao.queryByObject(records, function);
    }

    public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    /**
     * 判断功能码是否已经存在
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
     * 为编辑功能<br>
     * 修改功能也可以修改功能的code<br>
     * 所要检查输入code在表中是否存在 检查是否除此给定期的id的‘功能’用此功能码<br>
     * 
     * @param function
     * @return
     */
    public boolean isFxnCodeExist(Function function) {

        List<Function> tmps = new ArrayList<Function>();
        Function tmp = new Function();
        tmp.setCode(function.getCode());
        try {
            // 查出所有code相同的记录
            tmps = this.queryByVO(tmp);
            // 如果没有重复的code说明没被使用
            if (tmps.size() > 0) {
                // 判断这些code重复的记录
                for (Function f : tmps) {
                    // 如果code相同并且id相同说明这此修改是没有改code
                    // 如果code存在而且id与正在修改的不同则说明此code修改重复了
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