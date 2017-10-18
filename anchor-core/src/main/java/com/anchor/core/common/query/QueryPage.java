package com.anchor.core.common.query;

import com.anchor.core.common.utils.SortFieldConvertUtil;
import com.anchor.core.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author apple
 * @ClassName: QueryPage
 * @Description:
 * @date 2017/5/14 16:17
 * @since version 1.0
 */
public class QueryPage<T> {
    private Logger logger =  LoggerFactory.getLogger(QueryPage.class);
    public final static String SORT_ORDER_DESC = "desc";
    public final static String SORT_ORDER_ASC = "asc";
    private int pageNum = 1;
    private int pageSize = 13;
    private String sortOrder;
    private String sort;
    private T t;

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

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {

        if(SORT_ORDER_ASC.equals(sortOrder)||SORT_ORDER_DESC.equals(sortOrder)){
            this.sortOrder = sortOrder;
            return;
        }
        if(StringUtils.isNotBlank(sortOrder)){
            logger.error("illegal sort order");
        }
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = SortFieldConvertUtil.getSortField(sort);
    }
}
