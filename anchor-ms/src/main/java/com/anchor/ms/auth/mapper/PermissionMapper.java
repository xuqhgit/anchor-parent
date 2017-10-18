package com.anchor.ms.auth.mapper;


import com.anchor.core.common.base.BaseMapper;
import com.anchor.ms.auth.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName: PermissionMapper
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface PermissionMapper extends BaseMapper<Permission,Long> {

    public List<Permission> getPermissionByUserId(@Param("userId") Long userId);

}