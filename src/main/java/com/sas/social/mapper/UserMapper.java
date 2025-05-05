package com.sas.social.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.UserDto;
import com.sas.social.entity.User;
import com.sas.social.repository.UserRepository;

@Component
public class UserMapper 
	implements Function<User, UserDto> {

	@Autowired
	private PostMapper postMapper;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public UserDto apply(User u) {
		Integer postNumber = userRepository.getPostNumber( u.getUserId() );
		Integer followerNumber = userRepository.getNumberOfFollowers( u.getUserId() );
		Integer followingNumber = userRepository.getNumberOfFollowing( u.getUserId() ); 
		
		return new UserDto(
				u.getUserId(),
				u.getVisibleName(),
				u.getUsername(),
				u.getEmail(),
				u.getProfilePhoto(),
				u.getBannerPhoto(),
				
				u.getPosts()
						.stream()
						.map(postMapper)
						.collect(Collectors.toList()),
						
				u.getLikedPosts()
						.stream()
						.map(postMapper)
						.collect(Collectors.toList()),	
				
				u.getParticipatedCommunities(),
				
				u.getFollows()
						.stream()
						.map(f -> f.getUserId())
						.collect(Collectors.toSet()),
						
				u.getBlockedUsers()
						.stream()
						.map(b -> b.getUserId())
						.collect(Collectors.toSet()),
						
				postNumber,
				followerNumber,
				followingNumber
				);
	}
	
//	public User toUser(UserDto userDto) {
//		
//	}

}
