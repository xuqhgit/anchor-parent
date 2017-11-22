package com.anchor.ms.auth.dto;

import com.anchor.core.common.tree.ITree;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuqh
 * @ClassName: Menu
 * @Description:
 * @date 2017/10/14 11:15
 * @since 1.0.1
 */
public class Menu implements ITree {
    private String text;
    private String url;
    private String icon;
    private String target;
    private Long id;
    private Long pid;
    private List child = new LinkedList<Menu>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List getChild() {
        return child;
    }

    public void setChild(List child) {
        this.child = child;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
