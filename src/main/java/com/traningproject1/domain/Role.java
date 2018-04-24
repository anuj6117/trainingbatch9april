package com.traningproject1.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
 private Integer id;
 private String roleType;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getRoleType() {
	return roleType;
}
public void setRoleType(String roleType) {
	this.roleType = roleType;
}
 


}
