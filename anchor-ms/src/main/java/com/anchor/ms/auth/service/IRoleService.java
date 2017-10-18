package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.ms.auth.model.Role;

import java.util.Set;

/**
 * @ClassName: IRoleService
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface IRoleService extends BaseService<Role,Long> {


    public Set<String> findRoleByUserId(long userId);
}