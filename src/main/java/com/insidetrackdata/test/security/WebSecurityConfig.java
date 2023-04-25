package com.insidetrackdata.test.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChainBean(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			//disabling session management among requests
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//allowing all requests
			.and()
			.authorizeRequests()
			.anyRequest().permitAll()
			//disabling CSRF
			.and()
			.csrf().disable();

	    return httpSecurity.build();
	}

}
