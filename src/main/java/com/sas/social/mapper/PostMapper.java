package com.sas.social.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.PostDto;
import com.sas.social.entity.Post;

@Component
public class PostMapper 
	implements Function<Post, PostDto> {

	@Autowired
	CommentMapper commentMapper;
	
	@Override
	public PostDto apply(Post post) {
		return new PostDto(
				post.getPostId(),
				post.getContent(),
				post.getMedia(),
				post.getUser().getUserId()
		);
	}

	public static Post toPost(PostDto postDto) {
		return new Post();
	}
}
