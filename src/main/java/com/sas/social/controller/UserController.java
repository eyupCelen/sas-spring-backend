package com.sas.social.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sas.social.dto.UserDto;
import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.UserPrincipal;
import com.sas.social.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserRegisterDto userRegisterDto) {
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

    @PostMapping("/{id}/follow")
    @Transactional
    public ResponseEntity<?> followUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer followedId) {

        Integer followerId = userDetails.getUserId();
        try {
            userService.follow(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }        
    }
	
    @PostMapping("/{id}/follow")
    @Transactional
    public ResponseEntity<?> unfollowUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer followedId) {

        Integer followerId = userDetails.getUserId();
        try {
            userService.unfollow(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }        
    }
    
    @PostMapping("/{id}/block")
    @Transactional
    public ResponseEntity<?> blockUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer followedId) {

        Integer followerId = userDetails.getUserId();
        try {
            userService.block(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }        
    }

    @PostMapping("/{id}/unblock")
    @Transactional
    public ResponseEntity<?> unblockUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer followedId) {

        Integer followerId = userDetails.getUserId();
        try {
            userService.unblock(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("The given account does not exist.");
        }        
    }

}
