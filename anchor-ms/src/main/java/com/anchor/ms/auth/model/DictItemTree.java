package com.anchor.ms.auth.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuqh
 * @ClassName: DictItemTree
 * @Description:
 * @date 2017/11/15 17:50
 * @since 1.0.1
 */
public class DictItemTree extends  DictItem{
    private List<DictItemTree> child = new LinkedList<>();
    public List<DictItemTree> getChild() {
        return child;
    }

    public void setChild(List<DictItemTree> child) {
        this.child = child;
    }
}
