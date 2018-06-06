package com.xiaojinzi.image.base.service;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by cxj on 2016/8/13.
 */
public interface BaseService<T, PK> {

    @Nullable
    T queryById(PK pk);

    List<T> queryAll();

    boolean insert(T t);

    boolean update(T t);

    boolean deleteById(PK pk);

}
