package com.smbms.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class User {

	private Long id;
	@NotNull(message="用户编码不能为空")
	private String userCode;
	@NotNull(message="用户名不能为空")
	@Length(message="用户名必须是6~20字符之间",min=6,max=20)
	private String userName;
	@NotNull(message="密码不能为空")
	@Length(message="密码必须是6~16字符之间",min=6,max=16)
	private String userPassword;
	/**确认密码*/
	@NotNull(message="确认密码不能为空")
	private String userRemi;
	private Integer gender;
	/*@DateTimeFormat(pattern="yyyy-MM-dd")*/
	/*@JSONField(format="yyyy-MM-dd")*/
	private Date birthday;
	private String phone;
	private String address;
	private Long userRole;
	private Long createdBy;
	private Date creationDate;
	private Long modifyBy;
	private Date moifyDate;
	
	private String roleName;
	
	private String idPicPath;
	
	private String workPicPath;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getUserRole() {
		return userRole;
	}
	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getMoifyDate() {
		return moifyDate;
	}
	public void setMoifyDate(Date moifyDate) {
		this.moifyDate = moifyDate;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserRemi() {
		return userRemi;
	}
	public void setUserRemi(String userRemi) {
		this.userRemi = userRemi;
	}
	public String getIdPicPath() {
		return idPicPath;
	}
	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	
	
	
	
	
}
