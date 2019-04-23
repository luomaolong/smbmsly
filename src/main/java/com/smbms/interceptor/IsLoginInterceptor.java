package com.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smbms.entity.User;
import com.smbms.tools.SysContent;

/**
 * 用户是否登录的拦截器
 * @author Administrator
 *
 */
public class IsLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SysContent.LOGINSESSION);
		if(user==null){//说明用户用户没有登录
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			System.out.println("path:"+basePath);
			response.sendRedirect(basePath+"login/loginView");
			return false;
		}
		return true;
	}

	

}
