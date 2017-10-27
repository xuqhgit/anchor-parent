package com.anchor.ms.auth.model;

import com.anchor.core.common.base.BaseModel;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuqh
 * @ClassName: PermissionTree
 * @Description:
 * @date 2017/10/27 14:16
 * @since 1.0.1
 */
public class PermissionTree extends Permission{
    private boolean expandAble = true;
    private List<Permission> child = new LinkedList<>();

    public boolean isExpandAble() {
        return expandAble;
    }

    public void setExpandAble(boolean expandAble) {
        this.expandAble = expandAble;
    }

    public List<Permission> getChild() {
        return child;
    }

    public void setChild(List<Permission> child) {
        this.child = child;
    }
}
