package com.anchor.core.common.dto;

/**
 * @author apple
 * @ClassName: QueryFilter
 * @Description:
 * @date 2017/5/14 16:17
 * @since version 1.0
 */
public class QueryFilter {
    private int pageNum = 1;
    private int pageSize = 0;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
