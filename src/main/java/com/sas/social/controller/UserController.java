package com.sas.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sas.social.dto.UserDto;
import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.User;
import com.sas.social.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.signUpUser(userRegisterDto);
	}
	
	@GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getUserById(id).get();
    } 
}
