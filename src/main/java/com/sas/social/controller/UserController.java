package com.sas.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sas.social.dto.UserDto;
import com.sas.social.dto.UserRegisterDto;
import com.sas.social.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private  UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.signUpUser(userRegisterDto);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
	    return userService.getById(id)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
	    return userService.getByUsername(username)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
	    return userService.getByEmail(email)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	
	
}
