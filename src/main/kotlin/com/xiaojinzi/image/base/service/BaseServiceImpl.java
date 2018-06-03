package com.xiaojinzi.image.base.service;

import com.xiaojinzi.image.base.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by cxj on 2016/8/13.
 * 业务对象的基类
 */
public abstract class BaseServiceImpl<T> implements BaseService<T, Integer> {

    @Transactional
    public T queryById(Integer id) {
        return getBaseDao().queryById(id);
    }

    @Transactional
    public List<T> queryAll() {
        return getBaseDao().queryAll();
    }

    @Transactional
    public boolean insert(T t) {
        try {
            getBaseDao().insert(t);
            return true;
        } catch (Exception e) {
//            result.resultText = e.getMessage();
            e.printStackTrace();
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Transactional
    public boolean update(T t) {
        try {
            getBaseDao().update(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Transactional
    public boolean deleteById(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("id为空");
            }
            getBaseDao().delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    /**
     * 获取数据库操作接口
     *
     * @return
     */
    protected abstract BaseDao<T, Integer> getBaseDao();

}
