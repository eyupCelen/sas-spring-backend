package com.sas.social.dto;

import com.sas.social.entity.Media;

public record CommunityDto(
		String communityName,
		String communityDescription,
		Media communityImage) {

}
