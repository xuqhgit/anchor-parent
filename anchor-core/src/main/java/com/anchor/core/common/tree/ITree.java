package com.anchor.core.common.tree;
/**
 * Created by apple on 2017/11/21.
 */

import java.util.List;

/**
 * @author apple
 * @ClassName: ITree
 * @Description: 树接口
 * @date 2017/11/21 20:17
 * @since version 1.0
 */
public interface ITree {

    Object getPid();

    Object getId();

    <T>List<T> getChildren();

    void setChildren(List children);


}
