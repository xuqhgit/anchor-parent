package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xuqh
 * @ClassName: DictItem
 * @Description: 字典元素
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
public class DictItem extends BaseModel {
    public static final String VALUE_PATTERN = "^[a-zA-Z0-9_/-]{1,32}";
    public static final String VALUE_PATTERN_MESSAGE = "只能包含数字、字母、下划线、减号长度不能超过32个字符";
    public static final String KEY_REQUIRED_MESSAGE = "必填项";
    public static final String TEXT_PATTERN = ".{1,32}";
    public static final String TEXT_PATTERN_MESSAGE = "长度不超过32个字符";


    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 父元素ID
     */
    private Long pid;

    /**
     * 字典键
     */
    @NotNull
    @Pattern(regexp = VALUE_PATTERN, message = VALUE_PATTERN_MESSAGE)
    private String value;

    /**
     * 状态 1 有效 0 无效
     */
    private String status;

    /**
     * 字典文本
     */
    @Pattern(regexp = TEXT_PATTERN, message = TEXT_PATTERN_MESSAGE)
    private String text;

    /**
     * 字典排名
     */
    private int rank;


    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public Long getDictId() {
        return this.dictId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}