package io.oodles.springboot1.model;

public class AssignRole {
	Integer userId;
    String roleType;
	
	AssignRole(){}
	public AssignRole(Integer userId, String roleType) {
		super();
		this.userId = userId;
		this.roleType = roleType;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	
	

}
