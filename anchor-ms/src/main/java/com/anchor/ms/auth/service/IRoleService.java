package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.ms.auth.model.Role;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: IRoleService
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface IRoleService extends BaseService<Role,Long> {


    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    public Set<String> findRoleByUserId(long userId);

    /**
     * 批量插入角色权限
     * @param roleId
     * @param permissionIds
     * @param userId
     * @return
     */
    public long saveRolePermission(Long roleId,List<Long> permissionIds,Long userId);
}