package com.sas.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sas.social.dto.UserDto;
import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.User;
import com.sas.social.mapper.UserMapper;
import com.sas.social.mapper.UserRegisterMapper;
import com.sas.social.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
   
	private UserRepository userRepository;
	private UserMapper userMapper;
	private UserRegisterMapper userRegisterMapper;
	
	@Autowired
    public UserService(UserRepository userRepository, 
    				   UserMapper userMapper,
    				   UserRegisterMapper userRegisterMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegisterMapper = userRegisterMapper;
    }
	
    @Transactional
    public ResponseEntity<String> signUpUser(UserRegisterDto userRegisterDto) {
    	// Username and email should be unique
    	boolean emailTaken = userRepository.existsByEmail(userRegisterDto.email());
    	boolean usernameTaken = userRepository.existsByUsername(userRegisterDto.username());
    	
    	if(emailTaken)
    		return new ResponseEntity<>("This email is in use!",  HttpStatus.CONFLICT);
    	if(usernameTaken)
    		return new ResponseEntity<>("This username is already taken!",  HttpStatus.CONFLICT);
    	
    	User user = userRegisterMapper.ToEntity(userRegisterDto);
    	userRepository.save(user);
    	return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    public Optional<UserDto> getById(Integer userId) {
        return userRepository.findById(userId)
                             .map(userMapper);
    }

    public Optional<UserDto> getByUsername(String username) {
        return userRepository.findByUsername(username)
        					.map(userMapper);
    }

    public Optional<UserDto> getByEmail(String email) {
        return userRepository.findByEmail(email)
        					 .map(userMapper);
    }
    
    public List<User> getFollowers(Integer userId) {
        return userRepository.findFollowersByUserId(userId);
    }

    public List<User> getFollows(Integer userId) {
        return userRepository.findFollowsByUserId(userId);
    }

    public List<User> getBlockedUsers(Integer userId) {
        return userRepository.findBlockedUsersByUserId(userId);
    }

    public int getNumberOfFollowers(int userId) {
    	return userRepository.getNumberOfFollowers(userId);
    }
    
    public int getNumberOfFollowing(int userId) {
    	return userRepository.getNumberOfFollowing(userId);
    }
    
    public int getPostNumber(int userId) {
    	return userRepository.getPostNumber(userId);
    }
    
    // No explicit save needed if in transactional context
    @Transactional
    public void followUser(Integer followerId, Integer followedId) {
        User follower = userRepository.getReferenceById(followerId);
        User followed = userRepository.getReferenceById(followedId);
        follower.getFollows().add(followed);
    }

    @Transactional
    public void unfollowUser(Integer followerId, Integer followedId) {
        User follower = userRepository.getReferenceById(followerId);
        User followed = userRepository.getReferenceById(followedId);
        follower.getFollows().remove(followed);
    }

    @Transactional
    public void blockUser(Integer blockerId, Integer blockedId) {
        User blocker = userRepository.getReferenceById(blockerId);
        User blocked = userRepository.getReferenceById(blockedId);
        blocker.getBlockedUsers().add(blocked);
    }

    @Transactional
    public void unblockUser(Integer blockerId, Integer blockedId) {
        User blocker = userRepository.getReferenceById(blockerId);
        User blocked = userRepository.getReferenceById(blockedId);
        blocker.getBlockedUsers().remove(blocked);
    }

}

