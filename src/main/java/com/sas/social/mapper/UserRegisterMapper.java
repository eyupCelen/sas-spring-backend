package com.sas.social.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.User;
import com.sas.social.repository.CategoryRepository;

@Component
public class UserRegisterMapper 
	implements Function<User, UserRegisterDto>{

	@Autowired
	static CategoryRepository categoryRepository;
	
	@Override
	public UserRegisterDto apply(User user) {
		return new UserRegisterDto(
				user.getVisibleName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getUserCategories()
						.stream()
						.map(c -> c.getCategoryName() )
						.collect( Collectors.toSet() ) 
				);
	}

	public static User ToUser(UserRegisterDto registerDto) {
		
		return new User(
				registerDto.visibleName(),
				registerDto.username(),
				registerDto.email(),
				registerDto.password(),
				categoryRepository.findAllByCategoryNameIn(
						registerDto.userCategories())
				);
	}
	
}
