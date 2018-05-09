package com.example.demo.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@RestController
public class RoleController {
	@Autowired
private RoleService roleService;
	@Autowired
	private RoleRepository roleRepository;
	@RequestMapping(value="/createrole",method=RequestMethod.POST)
	public String addRole(@RequestBody Role role)
	{
		List<Role>getassignrole=roleRepository.findAll();
		Iterator<Role>itr=getassignrole.iterator();
		   while(itr.hasNext())
		   {
			Role r=itr.next();
			if(role.getRoleType().equalsIgnoreCase(r.getRoleType()))
			{
				return "Role Already Exist";
			}
		   }
		   if(role.getRoleType().equalsIgnoreCase("user")||role.getRoleType().equalsIgnoreCase("admin")||role.getRoleType().equalsIgnoreCase("manager"))
		   {
			   roleService.addRole(role);
		       return "Creating a new role is Successfully";
		   }
		   return "Role is not appropriate";
	}
	@RequestMapping(value="/getallrole",method=RequestMethod.GET)
	public List<Role> getAllRole()
	{
		return roleService.getAllRole();
	}
//	@RequestMapping(value="/getrolebyid",method=RequestMethod.GET)
//	public Optional<Role>getRoleById(Integer id)
//	{
//		return roleService.getRoleById(id);
//	}
//	@RequestMapping(value="/deleterole",method=RequestMethod.GET)
//	public void deleteRoleById(Integer id)
//	{
//		roleService.deleteRoleById(id);
//	}
	/*@RequestMapping(value="/assignrole",method=RequestMethod.POST)
	public Role updateRole(@RequestBody Role role)
	{
		return roleService.updateRole(role);
	}*/
}
