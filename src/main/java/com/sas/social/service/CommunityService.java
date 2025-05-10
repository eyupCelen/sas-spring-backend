package com.sas.social.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sas.social.dto.CommunityCreateDto;
import com.sas.social.dto.CommunityResponseDto;
import com.sas.social.dto.PostCreateDto;
import com.sas.social.dto.PostResponseDto;
import com.sas.social.entity.Community;
import com.sas.social.entity.CommunityPost;
import com.sas.social.entity.Post;
import com.sas.social.mapper.MediaFactory;
import com.sas.social.mapper.PostMapper;
import com.sas.social.repository.CommunityPostRepository;
import com.sas.social.repository.CommunityRepository;
import com.sas.social.repository.MediaRepository;
import com.sas.social.repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class CommunityService {
	
	private CommunityRepository communityRepository;
	private CommunityPostRepository communityPostRepository;
	private PostRepository postRepository;
	private MediaRepository mediaRepository;
	private PostMapper postMapper;
	
	@Autowired
	public CommunityService(CommunityRepository communityRepository,
				CommunityPostRepository communityPostRepository,
				MediaRepository mediaRepository,
				PostRepository postRepository,
				PostMapper postMapper) {
		
		this.communityRepository = communityRepository;
		this.communityPostRepository = communityPostRepository;
		this.postRepository = postRepository;
		this.mediaRepository = mediaRepository;
		this.postMapper = postMapper;
	}

	public ResponseEntity<?> createCommunity(CommunityCreateDto dto) throws IOException {
		
		boolean nameTaken = communityRepository.existsByCommunityName(dto.communityName());
		
	    if (nameTaken) {
	        return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body("Community name is already taken.");
	    }
	    
	    Community community = new Community(
			dto.communityName(),
			dto.communityDescription(),
			MediaFactory.fromMultipartFile( dto.communityPhoto() )
			);

	    mediaRepository.save( community.getMedia() );
		communityRepository.save(community);
		return ResponseEntity.ok().build();
	}
	
	public List<CommunityResponseDto> getAllCommunities() {
		return communityRepository.findAll()
				.stream()
				.map(u -> new CommunityResponseDto(
							u.getCommunityName(),
							u.getCommunityDescription(),
							u.getMedia() != null ? u.getMedia().getMediaId() : null
						)
					)
				.toList();
	}
	
	@Transactional
	public void createCommunityPost(PostCreateDto postDto,
			Integer communityId) {
		Community community = communityRepository
				.findById( communityId ).get();
		
		Post post = postMapper.map( postDto );
		CommunityPost communityPost = new CommunityPost(post, community);
		
		// persist order is important!
		if( post.getPostImage() != null)
			mediaRepository.save( post.getPostImage() );
		postRepository.save(post);
		communityPostRepository.save( communityPost );
	}
 
	public Page<PostResponseDto> getCommunityPosts(Integer viewerId, 
				Integer communityId, Pageable pageable) {
		Page<Post> posts = postRepository.getCommunityPosts(communityId, pageable);
		return posts.map(p -> postMapper.map(p, viewerId));
	}
}
