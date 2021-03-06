package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.UserMapper;
import com.anchor.ms.auth.model.User;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements IUserService {

	@Autowired
	private UserMapper userMapper;


	public User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	@Resource(name="userMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	public User login(String username, String password) {
		return userMapper.login(username,password);
	}


	@Override
	@Transactional
	public void setRole(Long userId, Long roleId, Long creatorId) {
		Long count = userMapper.deleteRole(userId);
		userMapper.setRole(userId,roleId,creatorId);
	}
}