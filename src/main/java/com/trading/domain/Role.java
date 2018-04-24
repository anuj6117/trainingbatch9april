package com.trading.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.Enum.RoleType;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleId;
	
	@NotNull
	private RoleType roleType;

	@Enumerated(EnumType.STRING)
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "role")
	private List<User> user = new ArrayList<>();

	@JsonIgnore
	public List<User> getuser() {
		return user;
	}

	public void setuser(List<User> user) {
		this.user = user;
	}

}
