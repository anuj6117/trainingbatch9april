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
  private String type;
  /*@JsonIgnore
  @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="roles")
  private Set<User> user;
  */
  

public Role()
  {
	  super();
  }
  
public Role(Integer roleId, String type) {
	super();
	this.roleId = roleId;
	this.type = type;
}
public Integer getRoleId() {
	return roleId;
}
public void setRoleId(Integer roleId) {
	this.roleId = roleId;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}

/*public Set<User> getUser() {
	return user;
}

public void setUser(Set<User> user) {
	this.user = user;
}*/

}
