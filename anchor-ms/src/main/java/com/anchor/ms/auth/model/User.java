package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Email
    private String email;
    /**
     * 用户名称
     */
    @NotBlank@Size(min=2,max=10,message="名称只允许2到10个字符")
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    @Pattern(regexp="^((1[3|5|7|8][0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$",message="请输入正确的手机号")
    private String phone;
    /**
     * QQ
     */
    private String qq;
    /**
     * 账号
     */
    @Pattern(regexp="^[a-zA-Z0-9_-]{4,16}$",message="用户名只允许4到16位（字母，数字，下划线，减号）")
    private String username;

    /**
     * 状态
     */
    private Integer state;


    public User(){

    }
    public User(User user) {

    }


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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}