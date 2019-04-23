package com.smbms.service;

import javax.servlet.http.HttpSession;

import com.smbms.entity.User;
import com.smbms.tools.PageEntity;

public interface UserService {

	public User login(String loginParam, String password);

	public PageEntity findUserByCondition(String findParam, Integer userRole,
                                          PageEntity pageEntity);

	public boolean insert(User user, HttpSession session);

	public User findUserById(Integer userId);

	public boolean update(User user, HttpSession session);

	public boolean checkIsExiste(Integer checkType, String checkData);

	public boolean delete(Integer userId);
}
