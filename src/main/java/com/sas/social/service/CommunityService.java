package com.sas.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sas.social.dto.CommunityDto;
import com.sas.social.dto.PostCreateDto;
import com.sas.social.entity.Community;
import com.sas.social.entity.CommunityPost;
import com.sas.social.entity.Post;
import com.sas.social.mapper.PostMapper;
import com.sas.social.repository.CommunityPostRepository;
import com.sas.social.repository.CommunityRepository;
import com.sas.social.repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class CommunityService {
	
	private CommunityRepository communityRepository;
	private CommunityPostRepository communityPostRepository;
	private PostRepository postRepository;
	private PostMapper postMapper;
	
	@Autowired
	public CommunityService(CommunityRepository communityRepository,
				CommunityPostRepository communityPostRepository,
				PostRepository postRepository,
				PostMapper postMapper) {
		
		this.communityRepository = communityRepository;
		this.communityPostRepository = communityPostRepository;
		this.postRepository = postRepository;
		this.postMapper = postMapper;
	}

	public ResponseEntity<?> createCommunity(CommunityDto dto) {
		boolean nameTaken = communityRepository.existsByCommunityName(dto.communityName());
		
	    if (nameTaken) {
	        return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body("Community name is already taken.");
	    }
	    
	    Community community = new Community(
			dto.communityName(),
			dto.communityDescription(),
			dto.communityImage()
		);
		
		communityRepository.save(community);
		return ResponseEntity.ok().build();
	}
	
	public List<CommunityDto> getAll() {
		return communityRepository.findAll()
				.stream()
				.map(u -> new CommunityDto(
							u.getCommunityName(),
							u.getCommunityDescription(),
							u.getMedia()
						)
					)
				.toList();
	}
	
	@Transactional
	public void createCommunityPost(@RequestBody PostCreateDto postDto,
			Integer communityId) {
		Community community = communityRepository
				.findById( communityId ).get();
		
		Post post = postMapper.map( postDto );
		postRepository.save(post);
		CommunityPost communityPost = new CommunityPost(post, community);
		communityPostRepository.save( communityPost );
	}
 
}
