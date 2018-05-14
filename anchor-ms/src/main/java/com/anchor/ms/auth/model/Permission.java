package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xuqh
 * @ClassName: Permission
 * @Description:
 * @date 2017-10-26 09:33:23
 * @since version 1.0
 */
public class Permission extends BaseModel {
    public static final String CODE_PATTERN = "^[a-zA-Z0-9_/-:]{1,32}$";
    public static final String CODE_PATTERN_MESSAGE = "请输入格式为:字母、数字、下划线、减号、冒号，1至32位字符";
    public static final String CODE_REQUIRED_MESSAGE = "必填项";
    public static final String NAME_PATTERN = ".{1,16}$";
    public static final String NAME_PATTERN_MESSAGE = "长度1至16位字符";
    public static final String URL_PATTERN = ".{1,200}$";
    public static final String URL_PATTERN_MESSAGE = "长度不能超过200个字符";
    public static final String URL_REQUIRED_MESSAGE = "请填写URL";

    /**
     * 权限编码
     */
    @NotNull
    @Pattern(regexp = CODE_PATTERN, message = CODE_PATTERN_MESSAGE)
    private String code;




    /**
     * 图标
     */
    private String icon;


    /**
     * 权限名称
     */
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
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
    private String status;

    /**
     * 权限类型 0 为菜单 1 为功能
     */
    private String type;

    /**
     * 打开方式类型 href a标签属性默认在content iframe打开
     */
    private String targetType;
    /**
     * 权限路径
     */
    @NotNull
    @Pattern(regexp = URL_PATTERN, message = URL_PATTERN_MESSAGE)
    private String url;


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public enum PermissionType{
        menu("0"),func("1");
        PermissionType(String code){
            this.code = code;
        }
        private String code;
        public String getCode(){
            return this.code;
        }
    }
}