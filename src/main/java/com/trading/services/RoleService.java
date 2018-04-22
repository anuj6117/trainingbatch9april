package com.trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Role;
import com.trading.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private	RoleRepository rolerepository;
	private final Logger  logger = LoggerFactory.getLogger(this.getClass());
		
	public String insertDetails(Role role) throws Exception {
		if(rolerepository.findByRoleType(role.getRoleType())== null) {
			
			
		
		if(!(rolerepository.save(role)== null)) {
			rolerepository.save(role);
			logger.info("Success");
			
			return "New role has been created";
		}
		else 
			{
			return "Not able to add new role";
			}
		}
		else
		{
			return "Role Type Already Exist";
		}
		
	}
	


	}


