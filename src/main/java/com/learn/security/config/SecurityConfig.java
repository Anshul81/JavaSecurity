package com.learn.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.learn.security.service.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	public void setUserDetailsServiceImpl(UserDetailServiceImpl userDetailServiceImpl) {
		this.userDetailServiceImpl = userDetailServiceImpl;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
	return http.authorizeHttpRequests(request -> request
			.requestMatchers("/public/**").permitAll()
			.requestMatchers("/journal/**", "/user/**").authenticated()
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception{
		managerBuilder.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
