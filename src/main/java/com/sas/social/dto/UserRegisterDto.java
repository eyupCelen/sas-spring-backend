package com.sas.social.dto;

import java.util.Set;

import com.sas.social.entity.Category;

public record UserRegisterDto(
		Integer userId,
		String visibleName,
		String username,
		String email,
		String password,
		Set<Category> userCategories
		) {

}
