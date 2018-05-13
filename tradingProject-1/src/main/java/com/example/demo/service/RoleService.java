package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public Role createRole(String roleType){
        if(roleRepository.findOneByRoleType(roleType)==null){
            Role role=new Role();
            role.setRoleType(roleType);
            roleRepository.save(role);
            return role;
        }else{
            return null;
        }
    }

    public List<Role> getAllRoles(){
        return  roleRepository.findAll();
    }
}
