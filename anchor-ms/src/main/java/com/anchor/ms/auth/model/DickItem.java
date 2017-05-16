package com.anchor.ms.auth.model;


import com.anchor.core.common.base.BaseModel;

/**
 * @ClassName: DickItem
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public class DickItem extends BaseModel {
    
    /**
     * 字典ID
     */
    private Long dictId;
    /**
     * 字典键
     */
    private String key;
    /**
     * 状态 1 有效 0 无效
     */
    private String state;
    /**
     * 字典文本
     */
    private String text;

    
    public void setDictId(Long dictId){
        this.dictId = dictId;
    }
    public Long getDictId(){
        return this.dictId;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}