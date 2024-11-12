package com.learn.security.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.learn.security.entity.User;
import com.learn.security.repository.UserRepo;

// Used in Authentication 
@Configuration
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	@Lazy
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findByUserName(username);
		User user = optionalUser.get();
		try {
		return org.springframework.security.core.userdetails.User.builder()
							.username(user.getUserName())
							.password(user.getPassword())
							.roles(user.getRoles().toArray(new String[0]))
							.build();
			}
		catch(Exception e) {
			throw new UsernameNotFoundException("USER NOT FOUND" + username);
		}
	}

}
