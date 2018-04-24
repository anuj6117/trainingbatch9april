package com.trading.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Role;
import com.trading.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository rolerepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<String, Object> insertDetails(Role role) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (rolerepository.findByRoleType(role.getRoleType()) == null) {

			if (!(rolerepository.save(role) == null)) {
				rolerepository.save(role);
				logger.info("Success");
				result.put("isSuccess", true);
				result.put("message", "New role has been created");
				return result;
			} else {
				result.put("isSuccess", false);
				result.put("message", "Failed to add new role");
				return result;
			}
		} else {
			result.put("isSuccess", false);
			result.put("message", "Role type already exists");
			return result;
		}

	}
}
