package com.anchor.ms.auth.mapper;


import com.anchor.core.common.base.BaseMapper;
import com.anchor.ms.auth.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName: RoleMapper
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface RoleMapper extends BaseMapper<Role,Long> {

    /**
     * 删除角色权限
     * @param roleId
     * @return
     */
    public long deleteRolePermission(@Param("roleId")Long roleId);
    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIds
     * @param creatorId
     * @return
     */
    public long insertRolePermissionBatch(@Param("roleId")Long roleId,@Param("permissionIds") List<Long> permissionIds,@Param("creatorId") long creatorId);
}