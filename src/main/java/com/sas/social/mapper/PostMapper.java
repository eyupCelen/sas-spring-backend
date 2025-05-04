package com.sas.social.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.sas.social.dto.PostDto;
import com.sas.social.entity.Post;

@Component
public class PostMapper 
	implements Function<Post, PostDto> {

	@Override
	public PostDto apply(Post t) {
		return null;
	}

	public static Post toPost(PostDto postDto) {
		return new Post();
	}
}
