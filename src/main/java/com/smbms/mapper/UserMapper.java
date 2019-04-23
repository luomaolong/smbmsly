package com.smbms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smbms.entity.User;

public interface UserMapper {

	public User login(@Param("loginParam") String loginParam);

	public int totalCount(Map<String, Object> paramValues);

	public List<User> findByCondition(Map<String, Object> paramValues);
	
	public int insert(User user);
	
	public int update(User user);
	
	public List<User> selectByCondition(Map<String, Object> paramValues);

	public Integer checkIsExists(@Param("checkType") Integer checkType,
                                 @Param("checkData") String checkData);

	public int delete(@Param("userId") Integer userId);
}
