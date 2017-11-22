package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.core.common.query.QueryPage;
import com.anchor.core.common.tree.ITree;
import com.anchor.core.common.tree.TreeUtil;
import com.anchor.ms.auth.dto.Menu;
import com.anchor.ms.auth.mapper.PermissionMapper;
import com.anchor.ms.auth.model.Permission;
import com.anchor.ms.auth.model.PermissionTree;
import com.anchor.ms.auth.service.IPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: PermissionServiceImpl
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long> implements IPermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Resource(name="permissionMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	public Set<String> findPermissionCodeByUserId(Long userId) {
		return permissionMapper.getPermissionCodeByUserId(userId);
	}

	public List<Permission> findPermissionByUserId(Long userId,String permissionType) {
		return permissionMapper.getPermissionByUserId(userId,permissionType);
	}

	@Override
	public List<PermissionTree> findPermissionTree(QueryPage<Permission> queryPage) {
		List list = permissionMapper.getPermissionTree(queryPage);
		return TreeUtil.assembleTree(list);
	}


	@Override
	public List<PermissionTree> findPermissionTreeAll(QueryPage<Map> queryPage) {
		List list = permissionMapper.getRolePermissionTree(queryPage);
		return TreeUtil.assembleTree((List<ITree>)list);
	}

//	public List<PermissionTree> assemblePermissionTree(List<PermissionTree> list){
//		List<PermissionTree> resultList = new LinkedList<>();
//		Map<String,List<PermissionTree>> map = new HashMap<>((int)Math.ceil(list.size()/0.75)+1);
//		Set<String> nodeIds = new HashSet<>();
//		LinkedList<String> existNodeId = new LinkedList<>();
//
//		list.stream().forEach(p->{
//			List<PermissionTree> child = map.get(p.getId().toString());
//			if(child==null){
//				child = p.getChild();
//				map.put(p.getId().toString(),child);
//			}
//			if(p.getChild().size()==0&&CollectionUtils.isNotEmpty(child)){
//				p.setChild(child);
//			}
//			nodeIds.remove(p.getId().toString());
//			existNodeId.add(p.getId().toString());
//			if(p.getPid()==null){
//				resultList.add(p);
//			}
//			else{
//				List<PermissionTree> parentChild = map.get(p.getPid().toString());
//				if(parentChild==null){
//					parentChild = new LinkedList<>();
//					map.put(p.getPid().toString(), parentChild);
//					if(!existNodeId.contains(p.getPid())){
//						nodeIds.add(p.getPid().toString());
//					}
//
//				}
//				parentChild.add(p);
//			}
//		});
//		nodeIds.forEach(n->{
//			List<PermissionTree> noParentChild = map.get(n);
//			resultList.addAll(noParentChild);
//		});
//		return resultList;
//	}

	public List<Menu> findMenu(Long userId) {
		List<Permission> list = findPermissionByUserId(userId,Permission.PermissionType.menu.getCode());
		List menuList = new LinkedList<>();
		list.stream().forEach(p->{
			Menu menu = permissin2Menu(p);
			menuList.add(menu);
		});
		return TreeUtil.assembleTree(menuList);
	}
	private Menu permissin2Menu(Permission permission){
		Menu menu = new Menu();
		menu.setIcon(permission.getIcon());
		menu.setText(permission.getName());
		menu.setUrl(permission.getUrl());
		menu.setTarget(permission.getTargetType());
		menu.setId(permission.getId());
		menu.setPid(permission.getPid());
		return menu;
	}


}