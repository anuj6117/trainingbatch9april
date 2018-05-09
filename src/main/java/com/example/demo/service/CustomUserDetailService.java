package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CustomUserDetails;
import com.example.demo.domain.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {
  @Autowired 
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
	  
	  User user=userRepository.findByUserName(username);
   
	  if(null == user){
    throw new UsernameNotFoundException("No user present with username: "+username);
   }
	 else
	 {
    return new CustomUserDetails(user);
   }
  }
}
