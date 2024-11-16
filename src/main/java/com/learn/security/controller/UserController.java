package com.learn.security.controller;

import com.learn.security.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.learn.security.entity.User;
import com.learn.security.repository.UserRepo;
import com.learn.security.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	WeatherService weatherService;
	
	@DeleteMapping
	public ResponseEntity<String> deleteUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			userService.deleteUser(userName);
			return new ResponseEntity<>("User Deleted",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/weather")
	public ResponseEntity<?> getGreeting(){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName();
			return new ResponseEntity<>("Hi " + name + " Weather is " + weatherService.getWeather("LONDON").getCurrent().getTempC() + " And Feels Like " + weatherService.getWeather("LONDON").getCurrent().getFeelsLikeC(), HttpStatus.OK);
		}
		catch (Exception e){
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		}
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUser(@RequestBody User newUser) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			userService.updateUser(newUser,userName);
			return new ResponseEntity<>("User Updated",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
