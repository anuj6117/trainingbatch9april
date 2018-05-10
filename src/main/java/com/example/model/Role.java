package com.example.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="role")
public class Role
{
	@Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer roleId;
  private String roleType;
  /*@JsonIgnore
  @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="roles")
  private Set<User> user;
  */
  

public Role()
  {
	  super();
  }
  

public Role(Integer roleId, String roleType) {
	super();
	this.roleId = roleId;
	this.roleType = roleType;
}


public Integer getRoleId() {
	return roleId;
}
public void setRoleId(Integer roleId) {
	this.roleId = roleId;
}

public String getRoleType() {
	return roleType;
}

public void setRoleType(String roleType) {
	this.roleType = roleType;
}


/*public Set<User> getUser() {
	return user;
}

public void setUser(Set<User> user) {
	this.user = user;
}*/

}
