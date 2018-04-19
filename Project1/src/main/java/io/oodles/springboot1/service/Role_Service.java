package io.oodles.springboot1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.model.Role;
import io.oodles.springboot1.repository.RoleRepository;

@Service
public class Role_Service {
	@Autowired
	RoleRepository roleRepository;

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
		return roleRepository.save(role);
	}
	

	public void delete(int id) {
		// TODO Auto-generated method stub
		 roleRepository.deleteById(id);;
	}
	public Role create(Role role) {
		// TODO Auto-generated method stub
		//System.out.println("Done2");
		return roleRepository.save(role);
	}
	

}
