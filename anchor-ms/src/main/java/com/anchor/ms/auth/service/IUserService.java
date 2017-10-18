package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.ms.auth.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: IUserService
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface IUserService extends BaseService<User,Long> {

    public User findUserByUsername(String username);

    public User login(String username,String password);


}