package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.RoleMapper;
import com.anchor.ms.auth.model.Role;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

	public Set<String> findRoleByUserId(long userId) {
		return null;
	}


	@Override
	@Transactional
	public long saveRolePermission(Long roleId, List<Long> permissionIds,Long userId) {
		roleMapper.deleteRolePermission(roleId);
		return roleMapper.insertRolePermissionBatch(roleId,permissionIds,userId);
	}
}