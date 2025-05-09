package com.sas.social.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sas.social.dto.UserProfileDto;
import com.sas.social.dto.UserRegisterDto;
import com.sas.social.dto.UserUpdateDto;
import com.sas.social.entity.Media;
import com.sas.social.entity.User;
import com.sas.social.mapper.MediaFactory;
import com.sas.social.mapper.UserProfileMapper;
import com.sas.social.mapper.UserRegisterMapper;
import com.sas.social.repository.MediaRepository;
import com.sas.social.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
   
	private UserRepository userRepository;
	private UserProfileMapper userMapper;
	private UserRegisterMapper userRegisterMapper;
	private MediaRepository mediaRepository;
	
	@Autowired
    public UserService(UserRepository userRepository, 
    				   UserProfileMapper userMapper,
    				   UserRegisterMapper userRegisterMapper,
    				   MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegisterMapper = userRegisterMapper;
        this.mediaRepository = mediaRepository;
    }
	
    @Transactional
    public ResponseEntity<?> signUpUser(UserRegisterDto userRegisterDto) {
    	// Username and email should be unique
    	boolean emailTaken = userRepository.existsByEmail(userRegisterDto.email());
    	boolean usernameTaken = userRepository.existsByUsername(userRegisterDto.username());
    	
    	if(emailTaken)
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("This email is in use!");
    	if(usernameTaken)
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("This username is already taken!");
    	
    	// mapper handles hashing the password
    	User user = userRegisterMapper.ToEntity(userRegisterDto);
    	userRepository.save(user);
    		return ResponseEntity.ok().build();
    }

    @Transactional
    public void updateUser(UserUpdateDto dto, Integer userId) throws IOException {
    	User user = userRepository.findById(userId).get();
    	
        if (dto.visibleName() != null) {
            user.setVisibleName( dto.visibleName() );
        }
        if (dto.bio() != null) {
            user.setBio( dto.bio() );
        }
        if (dto.profilePhoto() != null) {
        	Media profilePhoto = MediaFactory.fromMultipartFile( dto.profilePhoto() );
        	mediaRepository.save( profilePhoto );
            user.setProfilePhoto( profilePhoto );
        }
        if (dto.bannerPhoto() != null) {
        	Media bannerPhoto = MediaFactory.fromMultipartFile( dto.bannerPhoto() );
        	mediaRepository.save( bannerPhoto );
            user.setBannerPhoto( bannerPhoto );
	        }
	        
        userRepository.save(user);
    }
    
    public Optional<UserProfileDto> getById(Integer userId) {
        return userRepository.findById(userId)
                             .map(userMapper);
    }

    public Optional<UserProfileDto> getByUsername(String username) {
        return userRepository.findByUsername(username)
        					.map(userMapper);
    }

    public Optional<UserProfileDto> getByEmail(String email) {
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
    
    public void follow(Integer followerId, Integer followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow();
        User followed = userRepository.findById(followedId)
                .orElseThrow();
        
        follower.getFollows().add(followed);
        userRepository.save(follower);
    }

    public void unfollow(Integer followerId, Integer followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow();
        User followed = userRepository.findById(followedId)
                .orElseThrow();
        
        follower.getFollows().remove(followed);
        userRepository.save(follower);
    }

    public void block(Integer blockerId, Integer blockedId) {
        User blocker = userRepository.findById(blockerId)
        		.orElseThrow();
        User blocked = userRepository.findById(blockedId)
        		.orElseThrow();
        
        blocker.getBlockedUsers().add(blocked);
        userRepository.save(blocker);
    }

    public void unblock(Integer blockerId, Integer blockedId) {
        User blocker = userRepository.findById(blockerId)
        		.orElseThrow();
        User blocked = userRepository.findById(blockedId)
        		.orElseThrow();
        
        blocker.getBlockedUsers().remove(blocked);
        userRepository.save(blocker);
    }

}

