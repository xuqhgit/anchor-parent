package com.anchor.ms.auth.model;

import com.anchor.core.common.base.BaseModel;
import com.anchor.core.common.tree.ITree;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuqh
 * @ClassName: PermissionTree
 * @Description:
 * @date 2017/10/27 14:16
 * @since 1.0.1
 */
public class PermissionTree extends Permission implements ITree{
    private boolean expandAble = true;
    private boolean checked = false;
    private List<PermissionTree> child = new LinkedList<>();



    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpandAble() {

        return expandAble;
    }

    public void setExpandAble(boolean expandAble) {
        this.expandAble = expandAble;
    }


    public List<PermissionTree> getChild() {
        return child;
    }


    public void setChild(List child) {
        this.child = child;
    }


}
