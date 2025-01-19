package com.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return httpSecurity.build();
	}

	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService usersDetailsService() {
		UserDetails userDetails1 = User.builder()
				.username("sample@mail.com")
				.password(passwordEncoder().encode("sample@123456"))
				.authorities("USER")
				.build();
		
		UserDetails userDetails2 = User.builder()
				.username("test@mail.com")
				.password(passwordEncoder().encode("test@123456"))
				.authorities("USER", "ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

}
