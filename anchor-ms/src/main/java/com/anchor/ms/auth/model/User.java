package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

/**
 * @ClassName: User
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public class User extends BaseModel {
    
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * QQ
     */
    private String qq;
    /**
     * 账号
     */
    private String username;

    
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setQq(String qq){
        this.qq = qq;
    }
    public String getQq(){
        return this.qq;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
}