package com.traningproject1.Controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.domain.Role;
import com.traningproject1.repository.RoleRepository;
import com.traningproject1.service.RoleService;

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
		roleService.addRole(role);
		return "Creating a new role is Successfully";
	}
	@RequestMapping(value="/getallrole",method=RequestMethod.GET)
	public List<Role> getAllRole()
	{
		return roleService.getAllRole();
	}
	@RequestMapping(value="/getrolebyid",method=RequestMethod.GET)
	public Optional<Role>getRoleById(Integer id)
	{
		return roleService.getRoleById(id);
	}
	@RequestMapping(value="/deleterole",method=RequestMethod.GET)
	public void deleteRoleById(Integer id)
	{
		roleService.deleteRoleById(id);
	}
	/*@RequestMapping(value="/assignrole",method=RequestMethod.POST)
	public Role updateRole(@RequestBody Role role)
	{
		return roleService.updateRole(role);
	}*/
}
