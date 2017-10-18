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
			Map<String,String> resultMap = new HashMap<String, String>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
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
