package com.anchor.ms.auth.mapper;


import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.model.DictItem;
import com.anchor.ms.auth.model.DictItemTree;

import java.util.List;


/**
 * @ClassName: DictItemMapper
 * @Description: 字典元素
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
public interface DictItemMapper extends BaseMapper<DictItem,Long> {


    /**
     *
     * @param queryPage
     * @return
     */
    List<DictItemTree> getDictItemTree(QueryPage<DictItem> queryPage);



}