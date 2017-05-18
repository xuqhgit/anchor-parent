package com.anchor.core.common.dto;

import java.io.Serializable;

/**
 * @author xuqh
 * @ClassName: Result
 * @Description:
 * @date 2017/5/18 12:57
 * @since 1.0.1
 */
public class Result implements Serializable{
    private int code = 1;
    private String message ="SUCCESS";

    public Result success(String message){
        this.message = message;
        return this;
    }
    public Result error(String message){
        this.code = -1;
        this.message = message;
        return this;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
