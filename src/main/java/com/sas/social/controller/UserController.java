package com.sas.social.controller;

import java.util.NoSuchElementException;

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

import com.sas.social.dto.UserProfileDto;
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
	public ResponseEntity<UserProfileDto> getById(@PathVariable Integer id) {
	    return userService.getById(id)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<UserProfileDto> getByUsername(@PathVariable String username) {
	    return userService.getByUsername(username)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserProfileDto> getByEmail(@PathVariable String email) {
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
        if (followerId.equals(followedId)) {
            return ResponseEntity.badRequest().body("Cannot follow self.");
        }

        try {
            userService.follow(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }        
    }
	
    @PostMapping("/{id}/unfollow")
    @Transactional
    public ResponseEntity<?> unfollowUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer followedId) {

        Integer followerId = userDetails.getUserId();
        if (followerId.equals(followedId)) {
            return ResponseEntity.badRequest().body("Cannot unfollow self.");
        }

        try {
            userService.follow(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }        
    }
    
    @PostMapping("/{id}/block")
    public ResponseEntity<?> blockUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer blockedId) {

        Integer blockerId = userDetails.getUserId();

        if (blockerId.equals(blockedId)) {
            return ResponseEntity.badRequest().body("Cannot block self.");
        }

        try {
            userService.block(blockerId, blockedId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }
    }
    
    @PostMapping("/{id}/unblock")
    @Transactional
    public ResponseEntity<?> unblockUser(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable("id") Integer blockedId) {

        Integer blockerId = userDetails.getUserId();

        if (blockerId.equals(blockedId)) {
            return ResponseEntity.badRequest().body("Cannot unblock self.");
        }

        try {
            userService.block(blockerId, blockedId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("This account does not exist.");
        }
    }

}
