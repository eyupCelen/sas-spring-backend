package com.sas.social.dto;

import com.sas.social.entity.Media;

// No explicit mapper for this DTO
public record UserSummary(
		Integer userId,
		String visibleName,
		String username,
		Media profilePhoto) {

}
