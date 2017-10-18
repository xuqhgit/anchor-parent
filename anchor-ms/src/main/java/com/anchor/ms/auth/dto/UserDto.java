package com.anchor.ms.auth.dto;

import com.anchor.ms.auth.model.User;

/**
 * @author xuqh
 * @ClassName: UserDto
 * @Description:
 * @date 2017/9/30 18:01
 * @since 1.0.1
 */
public class UserDto {
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
    private int state;
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

    private String createTime;

    private String updateTime;

    private Long id;


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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
