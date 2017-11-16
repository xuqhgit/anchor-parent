package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xuqh
 * @ClassName: Dict
 * @Description: 字典
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
public class Dict extends BaseModel {
    public static final String CODE_PATTERN = "^[a-zA-Z0-9_/-]{1,32}";
    public static final String CODE_PATTERN_MESSAGE = "只能包含数字、字母、下划线、减号长度不能超过32个字符";
    public static final String CODE_REQUIRED_MESSAGE = "必填项";
    public static final String NAME_PATTERN = ".{1,32}";
    public static final String NAME_PATTERN_MESSAGE = "长度不超过32个字符";
    public static final String NAME_REQUIRED_MESSAGE = "必填项";


    /**
     * 字典编码
     */
    @NotNull
    @Pattern(regexp = CODE_PATTERN, message = CODE_PATTERN_MESSAGE)
    private String code;


    /**
     * 创建者ID
     */
    private Long creatorId;


    /**
     * 字典名称
     */
    @NotNull
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    private String name;

    /**
     * 状态 1 有效 0 无效
     */
    private String status;


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}