package com.sas.social.dto;

import java.time.LocalDateTime;

public record CommentDto(
		Integer commentId,
		String text,
		UserSummaryDto userSummary,
		Integer postId,
		LocalDateTime createdAt) {

}
