package io.oodles.springboot1.model;

public class AssignRole {
	Integer userid;
    String roletype;
	
	AssignRole(){}
	public AssignRole(Integer userid, String roletype) {
		super();
		this.userid = userid;
		this.roletype = roletype;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	
	
	

}
