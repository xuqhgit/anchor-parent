package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.UserMapper;
import com.anchor.ms.auth.model.User;

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
}