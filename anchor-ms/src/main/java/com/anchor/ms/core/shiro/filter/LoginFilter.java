package com.anchor.ms.core.shiro.filter;

import com.anchor.ms.auth.model.User;
import com.anchor.ms.common.utils.LoggerUtils;
import com.anchor.ms.core.shiro.token.manager.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class LoginFilter  extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
		
		User token = TokenManager.getToken();
		
		if(null != token || isLoginRequest(request, response)){// && isEnabled()

            return Boolean.TRUE;
        } 
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String,Object> resultMap = new HashMap<String, Object>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("code", 300);
			resultMap.put("message", "当前用户没有登录");//当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE ;
            
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE ;
	}
	

}
