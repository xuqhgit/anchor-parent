package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IDictItemService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.DictItemMapper;
import com.anchor.ms.auth.model.DictItem;

import javax.annotation.Resource;

/**
 * @ClassName: DickItemServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItem,Long> implements IDictItemService {

	@Autowired
	private DictItemMapper dictItemMapper;

	@Resource(name="dictItemMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}
}