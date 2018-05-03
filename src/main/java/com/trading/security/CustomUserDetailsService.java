package com.trading.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trading.domain.User;
import com.trading.repository.RoleRepository;
import com.trading.repository.UserRepository;

@Service("customUserDetailsService")
	public class CustomUserDetailsService implements UserDetailsService{
	 private final UserRepository userRepository;
	 private final RoleRepository roleRepository;
	 @Autowired
	    public CustomUserDetailsService(UserRepository userRepository,RoleRepository roleRepository) {
	        this.userRepository = userRepository;
	        this.roleRepository=roleRepository;
	    }
	 @Override
	 public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	  User user=userRepository.findByUserName(userName);
	  if(user == null){
	   throw new UsernameNotFoundException("No user present with username: "+userName);
	  }else{
	   List<String> userRoles=roleRepository.findRoleByUser(user);
	   return new CustomUserDetails(user,userRoles);
	  }
	 }
	}


