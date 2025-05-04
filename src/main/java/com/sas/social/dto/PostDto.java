package com.sas.social.dto;

import java.util.List;

import com.sas.social.entity.Comment;
import com.sas.social.entity.Media;

public record PostDto(
		Integer postId,
		String content,
		Media media,
		Integer userId,
		List<CommentDto> postComments) {

}
