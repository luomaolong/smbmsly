package com.smbms.service.impl;

import com.smbms.entity.User;
import com.smbms.mapper.UserMapper;
import com.smbms.service.UserService;
import com.smbms.tools.PageEntity;
import com.smbms.tools.SysContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	public User login(String loginParam, String password) {
		User user = userMapper.login(loginParam);
		if (user == null) {// 表示登陆失败
			return null;
		}
		// 比对密码
		// 将从控制层传递过来的密码加密
		String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!md5Pass.equals(user.getUserPassword())) {// 密码错误
			return null;
		}
		// 代码能走到这里的话说密码正确
		// 将用户的密码设置为空
		user.setUserPassword(null);
		return user;
	}

	public PageEntity findUserByCondition(String userName, Integer userRole,
			PageEntity pageEntity) {
		// 我们需要为pageEntity对象的属性：totalCount和dataList属性设置属性值
		Map<String, Object> paramValues = new HashMap<String, Object>();
		// 1.totalCount设置属性值
		paramValues.put("userName", userName);
		paramValues.put("userRole", userRole);
		int totalCount = userMapper.totalCount(paramValues);
		pageEntity.setTotalCount(totalCount);
		// 2.dataList设置属性值
		paramValues.put("start",
				(pageEntity.getPageIndex() - 1) * pageEntity.getPageSize());// 设置从那一条数据开始查询
		paramValues.put("size", pageEntity.getPageSize());// 设置查询对少条数据
		List<User> userList = userMapper.findByCondition(paramValues);
		pageEntity.setDataList(userList);
		return pageEntity;
	}

	public boolean insert(User user, HttpSession session) {
		// 位添加的用户设置创建者
		User loginUser = (User) session
				.getAttribute(SysContent.LOGINSESSION);
		user.setCreatedBy(loginUser.getId());
		// 位添加的用户设置创建时间
		user.setCreationDate(new Date());
		//用户的面加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
		user.setUserPassword(md5Pass);
		int num = userMapper.insert(user);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public User findUserById(Integer userId) {
		if(userId==0){//传过来的userId为空
			return null;
		}
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("userId", userId);
		List<User> userList = userMapper.selectByCondition(paramValues);
		if(userList!=null && userList.size()==1){
			return userList.get(0);
		}
		return null;
	}

	public boolean update(User user, HttpSession session) {
		User loginUser = (User) session.getAttribute(SysContent.LOGINSESSION);
		if(loginUser!=null){//修改者是当前登录系统的用户
			user.setModifyBy(loginUser.getId());
		}
		user.setMoifyDate(new Date());
		int num = userMapper.update(user);
		if(num>0){
			return true;
		}
		return false;
	}

	public boolean checkIsExiste(Integer checkType, String checkData) {
		Integer total = userMapper.checkIsExists(checkType,checkData);
		if(total!=null && total>0){
			return true;
		}
		return false;
	}

	public boolean delete(Integer userId) {
		int num = userMapper.delete(userId);
		if(num>0){
			return true;
		}
		return false;
	}

}
