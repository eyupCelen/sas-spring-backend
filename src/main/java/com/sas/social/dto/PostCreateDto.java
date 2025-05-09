package com.sas.social.dto;

import java.util.Set;

import com.sas.social.entity.Media;

public record PostCreateDto(
		String username,
		String title,
		Media postImage,
		Boolean homepageVisible,
		Set<String> postCategories) {
}
