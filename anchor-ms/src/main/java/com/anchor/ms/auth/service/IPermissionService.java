package com.anchor.ms.auth.service;

import com.anchor.core.common.base.BaseService;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.dto.Menu;
import com.anchor.ms.auth.model.Permission;
import com.anchor.ms.auth.model.PermissionTree;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: IPermissionService
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface IPermissionService extends BaseService<Permission,Long> {

    public Set<String> findPermissionCodeByUserId(Long userId);

    public List<Permission> findPermissionByUserId(Long userId);

    public List<Menu> findMenu(Long userId);

    public List<PermissionTree> findPermissionTree(QueryPage<Permission> queryPage);

    public List<PermissionTree> findPermissionTreeAll(QueryPage<Map> queryPage);
}