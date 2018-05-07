package com.training.demo.dto;

import com.training.demo.enums.RoleType;

public class DtoUser {
	Integer userId;
	RoleType roleType;

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
