package com.example.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserRoleDto;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@RestController
public class RoleController
{

 private Role role;
	
 @Autowired
 private RoleRepository roleRepository;	
 
 @Autowired
 private UserRepository userRepository;
	
   @RequestMapping(value="/createrole",method=RequestMethod.POST)
  public String addUser(@RequestBody Role role)
  {   
	  
	   if(role.getRoleType().equalsIgnoreCase("admin") || role.getRoleType().equalsIgnoreCase("manager"))
	   {
		   
		   roleRepository.save(role);
		   return "Role successfully created";
	   }
	   else
	     return "invalid role type";
  }
   
   @RequestMapping(value="/assignrole",method=RequestMethod.POST)
   public String assignrole(@RequestBody UserRoleDto userRoleDto)
   {
	   if(userRoleDto != null)
	   {
		 Integer k=0;
		   User user = userRepository.findByUserId(userRoleDto.getUserId());
		   Role role = roleRepository.findByRoleType(userRoleDto.getRoleType());
		   if((user!=null))
		   {
			   if(role!=null )
			   {
				   
				Set<Role> roleList=user.getRoles();
				for(Role rol:roleList)
				{
					if(rol.getRoleType()==role.getRoleType())
					{
						k++;
					}
				}
				if(k!=0)
				{
					return "role already exist with user";
				}
				else {
		   		user.getRoles().add(role);
		   		userRepository.save(user);
		        return "Role Assigned Successfully ";}
			   }
			   else return "Invalid role type";
		   }
		   else
			   return "Invalid user";
	   }
	   else
	   {
		   return "Failed";
	   }
   }
	
}
