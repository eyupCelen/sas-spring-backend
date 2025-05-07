package com.sas.social.dto;

import java.util.Set;

import com.sas.social.entity.Category;
import com.sas.social.entity.Media;

public record PostCreateDto(
		Integer userId,
		String title,
		Media postImage,
		Boolean homepageVisible,
		Set<Category> postCategories) {

}
