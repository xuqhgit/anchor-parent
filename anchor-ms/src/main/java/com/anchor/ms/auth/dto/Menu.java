package com.anchor.ms.auth.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuqh
 * @ClassName: Menu
 * @Description:
 * @date 2017/10/14 11:15
 * @since 1.0.1
 */
public class Menu {
    private String text;
    private String url;
    private String icon;
    private String target;
    private List<Menu> child = new LinkedList<Menu>();

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

    public List<Menu> getChild() {
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
