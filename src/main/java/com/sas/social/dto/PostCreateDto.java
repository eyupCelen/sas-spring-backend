package com.sas.social.dto;

import com.sas.social.entity.Media;

public record PostCreateDto(
		Integer userId,
		String title,
		Media postImage,
		Boolean homepageVisible) {

}
