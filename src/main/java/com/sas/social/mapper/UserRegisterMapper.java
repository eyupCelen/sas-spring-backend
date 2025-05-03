package com.sas.social.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.User;

@Component
public class UserRegisterMapper 
	implements Function<User, UserRegisterDto>{

	@Override
	public UserRegisterDto apply(User user) {
		return new UserRegisterDto(
				user.getUserId(),
				user.getVisibleName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getUserCategories()
				);
	}

	public static User registerDtoToUser(UserRegisterDto registerDto) {
		return new User(
				registerDto.visibleName(),
				registerDto.username(),
				registerDto.email(),
				registerDto.password(),
				registerDto.userCategories()
				);
	}
	
}
