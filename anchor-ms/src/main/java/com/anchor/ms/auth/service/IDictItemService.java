package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.model.DictItem;
import com.anchor.ms.auth.model.DictItemTree;

import java.util.List;

/**
 * @ClassName: IDictItemService
 * @Description: 字典元素
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
public interface IDictItemService extends BaseService<DictItem,Long> {

    List<DictItemTree> getDictItemTree(QueryPage<DictItem> queryPage);

}