package com.trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Role;
import com.trading.repository.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private	RoleRepo rolerepo;
	private final Logger  logger = LoggerFactory.getLogger(this.getClass());
		
	public String insertDetails(Role role) throws Exception {
		if(rolerepo.findByRoleType(role.getRoleType())== null) {
			
			
		
		if(!(rolerepo.save(role)== null)) {
			rolerepo.save(role);
			logger.info("Success");
			
			return "Success";
		}
		else 
			{
			return "Failure";
			}
		}
		else
		{
			return "Role Type Already Exist";
		}
		
	}
	public Role updateDetails(Role role)
	{
		return rolerepo.save(role);
	}


	}


