package com.learn.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.security.entity.User;
import com.learn.security.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "Health is OK";
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			userService.saveNewUser(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
