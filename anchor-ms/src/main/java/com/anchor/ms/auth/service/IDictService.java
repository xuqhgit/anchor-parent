package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.ms.auth.model.Dict;

import java.util.List;

/**
 * @ClassName: IDictService
 * @Description: 字典
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
public interface IDictService extends BaseService<Dict,Long> {


    List<Dict> getDictMapList();
}