package com.sas.social.dto;

public record UserProfileDto (
		Integer userId,
		String visibleName,
		String username,
		Integer profilePhotoId,
		Integer bannerPhotoId,
		Integer postNumber,
		Integer followerNumber,
		Integer followingNumber
) {

	
}
