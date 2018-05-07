package com.example.demo.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableJpaRepositories(basePackageClasses=UserRepository.class)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
				
	}
	
	
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder(){
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}
			
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			csrf().disable();
		http.authorizeRequests().antMatchers("/signup","/verifyuser").permitAll()
		.antMatchers("/getallusers","showalltransaction").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
	    .formLogin().permitAll()
	    .and().logout().permitAll();
	}
	
	
}
