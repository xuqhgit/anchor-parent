package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IDickItemService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.DickItemMapper;
import com.anchor.ms.auth.model.DickItem;

/**
 * @ClassName: DickItemServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class DickItemServiceImpl extends BaseServiceImpl<DickItem,Long> implements IDickItemService {

	@Autowired
	private DickItemMapper dickItemMapper;


}