package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.DictMapper;
import com.anchor.ms.auth.model.Dict;

/**
 * @ClassName: DictServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict,Long> implements IDictService {

	@Autowired
	private DictMapper dictMapper;


}