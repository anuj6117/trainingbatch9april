//package com.trainingproject.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.trainingproject.domain.CustomUserDetails;
//import com.trainingproject.domain.User;
//import com.trainingproject.repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService  implements UserDetailsService{
//
//	
//	@Autowired
//	private UserRepository userRepository;
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		User optionalUser = userRepository.findByuserName(userName);
//		System.out.println(optionalUser.getUserName()+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
//		if(optionalUser==null)
//			throw new UsernameNotFoundException("username not found");	
//		
//		return new CustomUserDetails(optionalUser);
//	}
//
//}
