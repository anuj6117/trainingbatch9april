package com.example.demo.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(User user) {
	  super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRole()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLES_"+role.getRoleType()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUserName();
	}
	@Override
	public String getPassword()
	{
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;

 }
}