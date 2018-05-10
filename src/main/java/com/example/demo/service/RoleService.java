package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RoleDTO;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	public Map<String, Object> addRole(Role role)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		Role roleFromRepository = roleRepository.findByRoleType(role.getRoleType().toUpperCase());
		
		if(roleFromRepository == null)
		{
			if(role.getRoleType().equalsIgnoreCase("admin") || role.getRoleType().equalsIgnoreCase("manager"))
			{
				String roleType = role.getRoleType().toUpperCase();
				role.setRoleType(roleType);
				roleRepository.save(role);
				result.put("isSuccess", true);
				result.put("message", "Role added successfuly.");
				return result;
			}
			else
			{
				result.put("isSuccess", false);
				result.put("message", "Role is not added.");
				return result;
			}
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Role is already exist.");
			return result;
		}
	}
	
	public Map<String, Object> assignRole(RoleDTO roleDTO)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		User user = null;
		try
		{
			user = userRepository.findByUserId(roleDTO.getUserId());
		}
		catch(Exception e)
		{
			result.put("isSuccess", "false");
			result.put("message", "User is not available.");
			return result;
		}
		
		Role role=roleRepository.findByRoleType(roleDTO.getRoleType());
		if(user.getStatus().equals(UserStatus.ACTIVE))
		{
			if(role != null)
			{
				user.getRoles().add(role);
				userRepository.save(user);
		
				result.put("isSuccess", true);
				result.put("message", "Role assign successfully.");
				return result;
			}
			else
			{
				result.put("isSuccess", "false");
				result.put("message", "Error in role assigning.");
				return result;
			}
		}
		else
		{
			result.put("isSuccess", "false");
			result.put("message", "User is inactive");
			return result;
		}
	}		
}
