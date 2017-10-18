package com.anchor.core.common.base;

import com.anchor.core.common.utils.SortFieldConvertUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author apple
 * @ClassName: BaseModel
 * @Description:
 * @date 2017/5/14
 * @since version 1.0
 */
public class BaseModel implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建者ID
     */
    private Long creatorId;
    static {
        SortFieldConvertUtil.setSortField("createTime","create_time");
        SortFieldConvertUtil.setSortField("updateTime","update_time");
        SortFieldConvertUtil.setSortField("creatorId","creator_id");
        SortFieldConvertUtil.setSortField("id","id");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }


}
