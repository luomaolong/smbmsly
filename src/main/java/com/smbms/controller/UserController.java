package com.smbms.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.smbms.entity.User;
import com.smbms.service.UserService;
import com.smbms.tools.PageEntity;
import com.smbms.tools.SysContent;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping(value="/delete/{userId}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(@PathVariable Integer userId){
		//根据参数查询
		boolean flag = userService.delete(userId);
		//我们规定返回true表示数据中有该数据存在，反知
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	
	/**
	 * 验证用户编码是否存在
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value="/isExists",method=RequestMethod.POST)
	@ResponseBody
	public Object isExists(String checkData,Integer checkType){
		//根据参数查询
		boolean flag = userService.checkIsExiste(checkType,checkData);
		//我们规定返回true表示数据中有该数据存在，反知
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", flag);
		return JSONArray.toJSONString(resultMap);
	}
	
	/**
	 * 根据用户id查询用户的详细信息
	 * @param userId
	 * @return
	 */
	/*@RequestMapping(value="/detail",
					method=RequestMethod.POST,
					produces={"application/json;charset=UTF-8"})*/
	/*@RequestMapping(value="/detail",method=RequestMethod.POST)
	@ResponseBody
	public Object userDetail(Integer userId){
		User user = userService.findUserById(userId);
		return JSON.toJSONString(user);
	}*/
	@RequestMapping(value = "/detail")
	@ResponseBody
	public User userDetail(Integer userId) {
		User user = userService.findUserById(userId);
		return user;
	}
	
	@RequestMapping(value="/detail/{userId}")
	@ResponseBody
	public User userDetailTwo(@PathVariable Integer userId,
			Model model){
		User user = userService.findUserById(userId);
		return user;
	}
	
	@RequestMapping(value="/index")

	public String index(String userName,
					    Integer userRole,
					    Integer pageIndex,
					    Model model){
		//创建分页的pageEntity对象
		PageEntity pageEntity = new PageEntity();
		//设置当前页面
		if(pageIndex!=null && !"".equals(pageIndex)){
			pageEntity.setPageIndex(pageIndex);
		}
		//需要返回到页面上的数据有：
		//查询参数(findParam),
		model.addAttribute("userName", userName);
		//调用业务逻辑层
		try {
			pageEntity = userService.findUserByCondition(userName,userRole,pageEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/index";
	}

	/**
	 * 显示添加页面的方法
	 * @return
	 */
	@RequestMapping(value="/addview",method=RequestMethod.GET)
	public String createView(@ModelAttribute User user){
		return "user/addview";
	}
	
	
	
	
	/**
	 * 添加用户的方法
	 * @return
	 */
	/*@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(User user,
						   Model model,
			               HttpSession session){
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}*/
	
	/**
	 * jsr303数据校验的演示
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 *//*
	@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(@Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/addview";
		}
		
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}*/
	
	
	
	/**
	 * 单文件上传的表单
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 *//*
	@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(MultipartFile attache,
						   @Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		//保存用户
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/addview";
		}
		if(!attache.isEmpty()){//判断用户是否要做文件上传的操作
			//表示用户有证件照需要上传
			//获取上传文件的名称
			String filename = attache.getOriginalFilename();
			//获取文件的大小,单位字节
			long size = attache.getSize();
			//获取文件的后缀
			String suffix = FilenameUtils.getExtension(filename);
			//加入我们规定文件上传的大小不能超过1M
			long maxUploadFileSize = 1*1024*1024;
			if(size>maxUploadFileSize){//超过上传文件的最大值，不允许上传，给予相应的提示
				model.addAttribute("uploadMsg", "上传的文件过大,上传的文件的大小应小于1M");
				return "user/addview";
			}
			if(!("jpg".equalsIgnoreCase(suffix)
					||"png".equalsIgnoreCase(suffix)
					||"gif".equalsIgnoreCase(suffix))){//判断文件的格式
				model.addAttribute("uploadMsg", "上传的文件的格式不正确,上传的文件的格式必须为:jpg,png,fig");
				return "user/addview";
			}
			try {
				//如果代码能走到这里说明符合上传要求,开始文件的上传
				//服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload
				String serverPath = session.getServletContext().getRealPath("/upload");
				//设置文件的上传的文件在服务器上的名称
				String uploadFileName = System.currentTimeMillis()+"."+suffix;
				//上传文件的路径就变成了如下
				////服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload/asdfasdfa.jpg
				String pathname = serverPath+File.separator+uploadFileName;
				
				File file = new File(pathname);//设置上传到服务器的那个文件
				//文件上传
				//attache.transferTo(file);
				
				InputStream is = attache.getInputStream();
				FileUtils.copyInputStreamToFile(is, file);
				//设置User对象中的证件照片的名称
				user.setIdPicPath(uploadFileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}*/
	
	
	
	/**
	 * 多文件上传的表单
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/doCreate",method=RequestMethod.POST)
	public String doCreate(@RequestParam MultipartFile[] attaches,
						   @Valid User user,
						   BindingResult bindResult,
						   Model model,
			               HttpSession session){
		//保存用户
		if(bindResult.hasErrors()){//如果BindingResult中存在错误
			return "user/addview";
		}
		
		for (int i=0;i<attaches.length;i++) {
			MultipartFile attache = attaches[i];
			
			if(!attache.isEmpty()){//判断用户是否要做文件上传的操作
				//表示用户有证件照需要上传
				//获取上传文件的名称
				String filename = attache.getOriginalFilename();
				//获取文件的大小,单位字节
				long size = attache.getSize();
				//获取文件的后缀
				String suffix = FilenameUtils.getExtension(filename);
				//加入我们规定文件上传的大小不能超过1M
				long maxUploadFileSize = 1*1024*1024;
				if(size>maxUploadFileSize){//超过上传文件的最大值，不允许上传，给予相应的提示
					model.addAttribute("uploadMsg", "上传的文件过大,上传的文件的大小应小于1M");
					return "user/addview";
				}
				if(!("jpg".equalsIgnoreCase(suffix)
						||"png".equalsIgnoreCase(suffix)
						||"gif".equalsIgnoreCase(suffix))){//判断文件的格式
					model.addAttribute("uploadMsg", "上传的文件的格式不正确,上传的文件的格式必须为:jpg,png,fig");
					return "user/addview";
				}
				try {
					//如果代码能走到这里说明符合上传要求,开始文件的上传
					//服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload
					String serverPath = session.getServletContext().getRealPath("/upload");
					//设置文件的上传的文件在服务器上的名称
					String uploadFileName = System.currentTimeMillis()+"."+suffix;
					//上传文件的路径就变成了如下
					////服务器的位置G:/dev/myeclipse-tomcat/apache-tomcat-7.0.47/apache-tomcat-7.0.4/webapps/smbms-ssm/upload/asdfasdfa.jpg
					String pathname = serverPath+File.separator+uploadFileName;
					
					File file = new File(pathname);//设置上传到服务器的那个文件
					//文件上传
					//attache.transferTo(file);
					
					InputStream is = attache.getInputStream();
					FileUtils.copyInputStreamToFile(is, file);
					//设置User对象中的证件照片的名称
					if(i==0){//表示上传的是证件照片
						user.setIdPicPath(uploadFileName);
					}
					if(i==1){//表示上传的是工作照片
						user.setWorkPicPath(uploadFileName);
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		boolean flag = userService.insert(user, session);
		String createMsg = "服务器错误!";
		if(flag){//表示添加成功
			createMsg = "添加成功";
		}
		model.addAttribute("createMsg", createMsg);
		return "redirect:/user/index";
	}
	
	
	
	
	
	
	/**
	 * 显示修改页面的方法
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/updateView")
	public String updateView(@RequestParam Integer id,
							@ModelAttribute User user,
							Model model){
		User u = userService.findUserById(id);
		model.addAttribute("user", u);
		return "user/update";
	}
	
	
	/**
	 * 显示修改页面的方法
	 * @param userId
	 * @return 
	 */
	@RequestMapping(value="/updateViewTwo/{userId}")
	public String updateViewTwo(@PathVariable("userId") Integer id,
							@ModelAttribute User user,
							Model model){
		User u = userService.findUserById(id);
		model.addAttribute("user", u);
		return "user/update";
	}
	
	/**
	 * 修改用户的方法
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(User user,HttpSession session){
		boolean flag = userService.update(user,session);
		return "redirect:/user/index";
	}
	
	
	
	
}
