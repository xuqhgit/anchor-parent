package com.anchor.ms.core.shiro.token;

import com.anchor.ms.auth.model.User;
import com.anchor.ms.auth.service.IPermissionService;
import com.anchor.ms.auth.service.IRoleService;
import com.anchor.ms.auth.service.IUserService;
import com.anchor.ms.core.shiro.token.manager.TokenManager;
import com.anchor.ms.core.statics.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author zhou-baicheng
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class SampleRealm extends AuthorizingRealm {

	@Autowired
	IUserService userService;
	@Autowired
	IPermissionService permissionService;
	@Autowired
	IRoleService roleService;
	
	public SampleRealm() {
		super();
	}
	/**
	 *  认证信息，主要针对用户登录， 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		ShiroToken token = (ShiroToken) authcToken;

		User user = userService.login(token.getUsername(),token.getPswd());
		if(null == user){
			throw new AccountException("帐号或密码不正确！");
		/**
		 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
		 */
		}
//		else if(UUser._0.equals(user.getStatus())){
//			throw new DisabledAccountException("帐号已经禁止登录！");
//		}
		else{
			//更新登录时间 last login time
//			user.setLastLoginTime(new Date());
//			userService.updateByPrimaryKeySelective(user);
			SecurityUtils.getSubject().getSession().setAttribute(Constant.LOGIN_USER, user);
		}
		return new SimpleAuthenticationInfo(user,user.getPassword(), getName());
    }

	 /** 
     * 授权 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	Long userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRoleByUserId(userId);
		if(roles==null){
			roles = new HashSet<>();
			roles.add("auth:user:add:index");
		}
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionCodeByUserId(userId);
		if(permissions==null){
			permissions = new HashSet<>();
			permissions.add("auth:user:add:index");
		}
		info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
