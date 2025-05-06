package com.sas.social.dto;

import com.sas.social.entity.Media;

public record UserProfileDto (
		Integer userId,
		String visibleName,
		String username,
		Media profilePhoto,
		Media bannerPhoto,
		Integer postNumber,
		Integer followerNumber,
		Integer followingNumber
) {

	
}
