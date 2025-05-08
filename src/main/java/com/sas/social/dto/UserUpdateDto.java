package com.sas.social.dto;

import com.sas.social.entity.Media;

public record UserUpdateDto(
		String visibleName,
		String bio,
		Media profilePhoto,
		Media bannerPhoto) {

}
