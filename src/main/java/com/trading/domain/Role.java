package com.trading.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)	
private long roleId;

@NotNull
@Column(unique = true)
private String roleType;

	
	public long getRoleId() 
	{
		return roleId;
	}

	public void setRoleId(long roleId) 
	{
		this.roleId = roleId;
	}

	public String getRoleType() 
	{
		return roleType;
	}

	public void setRoleType(String roleType) 
	{
		this.roleType = roleType;
	}

@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "role")
	private List<User> user = new ArrayList<>();

@JsonIgnore
	public List<User> getUser() 
	{
	return user;
	}

	public void setUser(List<User> user)
	{
		this.user = user;
	}

}
