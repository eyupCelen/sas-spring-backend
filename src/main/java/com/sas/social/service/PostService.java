package com.sas.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sas.social.dto.PostCreateDto;
import com.sas.social.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

//	public void createPost(PostCreateDto postDto) {
//		Post post = new Post()
//		postRepository.save
//	}
	
	public void getPost(Integer postıd) {
		
	}
}
