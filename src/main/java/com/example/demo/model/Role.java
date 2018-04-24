package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Role")
public class Role {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer roleId;
	
	@Column(name="roleType")
	private String roleType;
	
	@ManyToMany(mappedBy="role")
	private Set<User> user = new HashSet<>();
	
	@JsonIgnore
	public Set<User> getUser() {
		return  user;
	}	
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
	
	public Integer getUserId() {
		return roleId;
	}

	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}
