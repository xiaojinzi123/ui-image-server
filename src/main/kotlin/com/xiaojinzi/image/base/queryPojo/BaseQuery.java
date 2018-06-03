package com.xiaojinzi.image.base.queryPojo;

import com.xiaojinzi.image.DefultConstant;

/**
 * Created by xiaojinzi on 2016/9/21.
 * desc:
 */
public class BaseQuery {

    /**
     * 分页的起始点
     */
    private long startIndex = 0;

    /**
     * 每次显示的条数
     */
    private int pageSize = DefultConstant.INSTANCE.getPAGER_SIZE();

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
