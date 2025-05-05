package com.sas.social.dto;

import com.sas.social.entity.Media;

public record PostDto(
		Integer postId,
		String content,
		Media media,
		Integer userId
		) {

}
