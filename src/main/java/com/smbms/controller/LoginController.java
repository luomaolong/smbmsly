package com.smbms.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.smbms.entity.User;
import com.smbms.service.UserService;
import com.smbms.tools.SysContent;

@Controller
@RequestMapping("/login")
public class LoginController {


	@Autowired
	private UserService userService;
	
	/**
	 * 显示登陆页面
	 * @return
	 */
	@RequestMapping(value="/loginView",method=RequestMethod.GET)
	public String login(){
		return "login";//返回值默认的方式是forword，转发是服务器端行为
		//return "redirect:/login/loginView";//不能重定向到页面，只能重定向到控制器中的方法,重定向是客户端行为
	}
	
	
	/**
	 * 登陆的方法
	 * 如何获取ServletAPI
	 * 答：直接通过方法入参进来
	 * 	在springmvc中如果你要获取HttpServletRequest,HttpServletResponse,HttpSession的话
	 * 	你可将这些API作为方法的参数入参进来
	 * @param loginParam
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String login(String loginParam,
						String password,
						Model model,
						HttpSession session){
		/**
		 * User user = userService.login(loginParam, password);
		 */
		User user=userService.login(loginParam,password);
		if(user==null){//登陆失败
			model.addAttribute("logMsg", "用户名或密码错误!");
			return "login";
		}
		//如果代码能走到这里说明用户的用户名和密码正确
		//1.将用户的信息保存在session中
		session.setAttribute(SysContent.LOGINSESSION, user);
		//2.跳转到首页
		return "redirect:/login/welcome";
	}
	
	/**
	 * 显示系统首页
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/welcome")
	public String main(HttpSession session){
		User user = (User) session.getAttribute(SysContent.LOGINSESSION);
		if(user==null){//说明用户用户没有登录
			return "redirect:/login/loginView";
		}
		return "welcome";
	}
	
	/**
	 * 用户退出系统的方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loginOut",method=RequestMethod.GET)
	public String loginOut(HttpSession session){
		//从session中移除当前登陆的用户的信息
		session.removeAttribute(SysContent.LOGINSESSION);
		session.setAttribute(SysContent.LOGINSESSION, null);
		return "redirect:/login/loginView";
	}
	
	
	
	
	
}
