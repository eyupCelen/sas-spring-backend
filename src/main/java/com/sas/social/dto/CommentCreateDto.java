package com.sas.social.dto;

public record CommentCreateDto(
		Integer postId,
		Integer userId,
		String text) {

}
