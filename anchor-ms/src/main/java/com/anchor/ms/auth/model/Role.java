package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;
import com.anchor.core.common.utils.SortFieldConvertUtil;

/**
 * @ClassName: Role
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public class Role extends BaseModel {
    
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色状态 0无效 1 有效
     */
    private String state;

    static {
        SortFieldConvertUtil.setSortField("state","state");
        SortFieldConvertUtil.setSortField("code","code");
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
}