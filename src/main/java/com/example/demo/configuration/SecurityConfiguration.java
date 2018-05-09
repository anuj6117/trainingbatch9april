package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomUserDetailService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses=UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private CustomUserDetailService userDetailsService;
	
	public void configuration(AuthenticationManagerBuilder auth) throws Exception
	{
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}
	@Override
	protected void configure(HttpSecurity httpSecurity)  throws Exception
	{
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests()	
		.antMatchers("/signup","/addwallet","/walletHistory","/assignwallet","/getalluser").hasAnyRole("ADMIN")
		.anyRequest().permitAll()
		.and()
		.formLogin().permitAll()
		.and().logout().permitAll();
	    
//		httpSecurity.authorizeRequests()
//	    .antMatchers("/signup","/addwallet","/walletHistory").access("hasAnyRole('ROLE_USER')")
//	    .anyRequest().permitAll()
//		.and()
//		.formLogin().permitAll();
//		httpSecurity.authorizeRequests()
//		
//	    .antMatchers("").access("hasAnyRole('ROLE_MANAGER')")
//	    .anyRequest().permitAll()
//		.and()
//		.formLogin().permitAll();
		
	}
	
	@Bean
	 PasswordEncoder getPasswordEncoder()
	{
		return new PasswordEncoder() {
		@Override
		public String encode(CharSequence charSequence)
		{
			return charSequence.toString();
		}
		@Override
		public boolean matches(CharSequence charSequence, String s)
		{
			return true;
		}
	};
  }
//	@Bean(name="passwordEncoder")
//    public PasswordEncoder passwordencoder(){
//     return new BCryptPasswordEncoder();
//    }
}
