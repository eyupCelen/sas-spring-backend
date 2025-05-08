package com.sas.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sas.social.dto.PostCreateDto;
import com.sas.social.dto.PostResponseDto;
import com.sas.social.entity.Post;
import com.sas.social.entity.User;
import com.sas.social.mapper.PostMapper;
import com.sas.social.repository.PostRepository;
import com.sas.social.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

	PostRepository postRepository;
	UserRepository userRepository;
	PostMapper postMapper;
	
	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postMapper = postMapper;
	}

	public ResponseEntity<?> createPost(PostCreateDto postDto) {
		if(postDto.title() == null && postDto.postImage() == null)
			return ResponseEntity.badRequest().body("Cannot create empty post");
		
		Post post = postMapper.map( postDto );
		postRepository.save(post);
		return ResponseEntity.ok().build();
	}
	
	public PostResponseDto getPost(Integer postId, Integer viewerId) {
		Post post = postRepository.findById(postId).get();
		return postMapper.map(post, viewerId);
	}
	
	public Page<PostResponseDto> getPostsOfUser(Integer userId, Integer viewerId, Pageable pageable) {
		Page<Post> posts = postRepository.findByUser(userId, pageable);
		return posts.map(p -> postMapper.map(p, viewerId));
	}
	
	public void likePost(Integer postId, Integer likerId) {
		User user = userRepository.findById(likerId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));
		Post post = postRepository.findById(postId)
	            .orElseThrow(() -> new EntityNotFoundException("Post not found"));

		
		post.getLikingUsers().add(user);
		postRepository.save(post);
	}
	
	public void unlikePost(Integer postId, Integer likerId) {
		User user = userRepository.findById(likerId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));
		Post post = postRepository.findById(postId)
	            .orElseThrow(() -> new EntityNotFoundException("Post not found"));

		
		post.getLikingUsers().remove(user);
		postRepository.save(post);
	}

}
