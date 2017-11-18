package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.core.common.query.QueryPage;
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

	public List<Permission> findPermissionByUserId(Long userId) {
		return permissionMapper.getPermissionByUserId(userId);
	}

	@Override
	public List<PermissionTree> findPermissionTree(QueryPage<Permission> queryPage) {
		return permissionMapper.getPermissionTree(queryPage);
	}


	@Override
	public List<PermissionTree> findPermissionTreeAll(QueryPage<Map> queryPage) {
		List<PermissionTree> list = permissionMapper.getRolePermissionTree(queryPage);
		List<PermissionTree> resultList = new LinkedList<>();
		Map<Long,List<PermissionTree>> map = new HashMap<>((int)Math.ceil(list.size()/0.75)+1);
		list.stream().forEach(p->{
			List<PermissionTree> child = map.get(p.getId());
			if(p.isExpandAble()&&child==null){
				child = p.getChild();
				map.put(p.getId(),child);
			}
			if(p.getChild().size()==0&&CollectionUtils.isNotEmpty(child)){
				p.setChild(child);
			}
			if(p.getPid()==null){
				resultList.add(p);
			}
			else{
				List<PermissionTree> parentChild = map.get(p.getPid());
				if(parentChild==null){
					parentChild = new LinkedList<>();
					map.put(p.getPid(), parentChild);
				}
				parentChild.add(p);
			}

		});
		return resultList;
	}

	public List<Menu> findMenu(Long userId) {
		List<Permission> list = findPermissionByUserId(userId);
		List<Menu> menuList = new LinkedList<>();
		Map<Long,List<Menu>> map = new HashMap<>((int)Math.ceil(list.size()/0.75)+1);
		//组装树结构
		list.stream().forEach(p->{
			if(Permission.PermissionType.menu.getCode().equals(p.getType())){
				Menu menu = permissin2Menu(p);
				if(p.getPid()==null)menuList.add(menu);
				if(p.getPid()!=null){

					List<Menu> pList = map.get(p.getPid());
					if(pList==null){
						pList = new LinkedList<>();
						map.put(p.getPid(),pList);
					}
					pList.add(menu);
				}
				List<Menu> mList = map.get(p.getId());
				if(mList==null){
					mList =  menu.getChild();
					map.put(p.getId(),mList);
				}
				menu.setChild(mList);
			}
		});
		return menuList;
	}
	private Menu permissin2Menu(Permission permission){
		Menu menu = new Menu();
		menu.setIcon(permission.getIcon());
		menu.setText(permission.getName());
		menu.setUrl(permission.getUrl());
		menu.setTarget(permission.getTargetType());
		return menu;
	}


}