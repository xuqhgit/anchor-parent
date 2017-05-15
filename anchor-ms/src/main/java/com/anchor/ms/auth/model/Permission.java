package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

/**
 * @ClassName: Permission
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public class Permission extends BaseModel {
    
    /**
     * 权限编码
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限父ID
     */
    private Long pid;
    /**
     * 排序 默认为1
     */
    private int rank;
    /**
     * 权限状态 0 无效 1有效
     */
    private String state;
    /**
     * 权限类型 0 为菜单 1 为功能
     */
    private String type;
    /**
     * 权限路径
     */
    private String url;

    
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
    public void setPid(Long pid){
        this.pid = pid;
    }
    public Long getPid(){
        return this.pid;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public int getRank(){
        return this.rank;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}