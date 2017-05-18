package com.anchor.core.common.dto;

import java.util.List;

/**
 * @author xuqh
 * @ClassName: ResultObject
 * @Description:
 * @date 2017/5/18 12:58
 * @since 1.0.1
 */
public class ResultGrid<T> extends Result {
    private long total;
    private List<T> rows;

    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
