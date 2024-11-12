package com.learn.security.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.learn.security.entity.User;
import com.learn.security.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public void saveUser(User user) {
		userRepo.save(user);
	}

	public void saveAdminUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepo.save(user);
	}

	public void saveNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));   
		user.setRoles(Arrays.asList("USER"));
		userRepo.save(user);
	}
	
	
	
	public void deleteUser(String userName) {
		userRepo.deleteByUserName(userName);
	}
	
	public List<User> getAllUsers(){
		List<User> users = userRepo.findAll();
		return users;
	}
	
	public void updateUser(User user,String userName) {
		Optional<User> optionalUser = userRepo.findByUserName(userName);
		if (optionalUser.isPresent()) {
	    	User oldUser = optionalUser.get();
	    	oldUser.setUserName(user.getUserName());
	    	oldUser.setPassword(user.getPassword());
	    	saveNewUser(oldUser);
	    } 
	}
	
	public Optional<User> getUserByUserName(String userName) {
		Optional<User> user = userRepo.findByUserName(userName);
		return user;
	}
}
