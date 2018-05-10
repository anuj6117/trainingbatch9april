package io.oodles.springboot1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.model.Role;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.RoleRepository;
import io.oodles.springboot1.repository.UsersRepository;

@Service
public class Role_Service {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	
	Users user=new Users();
	Role role1;

	public List<Role> getallroles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}
	public Optional<Role> searchbyid(Integer roleid) {
		// TODO Auto-generated method stub
		 return roleRepository.findById(roleid);
	     
	}

	public Role update(Role role, int id) {
		// TODO Auto-generated method stud
		Role role2=roleRepository.getOne(id);
		
		role2.setRoleType(role.getRoleType());
		return roleRepository.save(role2);
	}
	

	public void delete(int id) {
		// TODO Auto-generated method stub
		 roleRepository.deleteById(id);;
	}
	public String create(Role role) {
		// TODO Auto-generated method stub
		//System.out.println("Done2");
		//System.out.println(role.getRoleType());
		Role role1=roleRepository.findByRoleType(role.getRoleType());
	    //System.out.println(role1.getRoleType());
		if(role1!=null) {
			return "Role Already present";}
		else {
			
		if(role.getRoleType().equalsIgnoreCase("USER")||role.getRoleType().equalsIgnoreCase("ADMIN")||role.getRoleType().equalsIgnoreCase("MANAGER")){
		
		roleRepository.save(role);
	
		}
	
		
		}
		return "Role Created";
	
		}
	
	    
	  
	       
           
		
	
		
		
		
	}
	


