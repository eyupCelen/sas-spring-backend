package com.sas.social.dto;

import java.time.LocalDateTime;

import com.sas.social.entity.Media;

public record PostResponseDto(
		UserSummary userSummary,
		Integer postId,
		String content,
		Media postImage,
		Boolean isPostAuthorFollowed,
		Boolean isPostLiked,
		Integer numberOfPostLike,
		Integer numberOfPostComment,
		LocalDateTime createdAt
		) {

}
