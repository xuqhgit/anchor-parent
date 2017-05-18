package com.anchor.core.common.dto;

/**
 * @author xuqh
 * @ClassName: ResultObject
 * @Description:
 * @date 2017/5/18 12:58
 * @since 1.0.1
 */
public class ResultObject<T> extends Result {
    private T data;

    public T getData() {
        return data;
    }
    public ResultObject<T> setData(T data) {
        this.data = data;
        return this;
    }
}
