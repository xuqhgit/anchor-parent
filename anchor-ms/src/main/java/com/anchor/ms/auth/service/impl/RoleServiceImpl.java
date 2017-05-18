package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.RoleMapper;
import com.anchor.ms.auth.model.Role;

import javax.annotation.Resource;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Resource(name="roleMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}
}