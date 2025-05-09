package com.sas.social.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sas.social.dto.CommunityCreateDto;
import com.sas.social.dto.CommunityResponseDto;
import com.sas.social.dto.PostCreateDto;
import com.sas.social.dto.PostResponseDto;
import com.sas.social.entity.UserPrincipal;
import com.sas.social.service.CommunityService;

@RestController
@RequestMapping("/community")
public class CommunityController {

	private CommunityService communityService;
	
	@Autowired
	public CommunityController(CommunityService communityService) {
		this.communityService = communityService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCommunity(@ModelAttribute CommunityCreateDto communityDto) throws IOException {
		return communityService.createCommunity(communityDto);
	}
	
	@GetMapping("/all")
	public List<CommunityResponseDto> getAllCommunities() {
		return communityService.getAllCommunities();
	}
	
	@PostMapping("{communityId}/share-post") 
	public void createCommunityPost(@RequestPart PostCreateDto postDto,
				@RequestPart MultipartFile postImage,
				@PathVariable Integer communityId) {
		
		communityService.createCommunityPost(postDto, postImage, communityId);
	}
	
	
	@GetMapping("/{communityId}/posts")
	public ResponseEntity<Page<PostResponseDto>> getLatestCommunityPosts(
	        @PathVariable Integer communityId,
	        @RequestParam(defaultValue = "0") int offset,
	        @RequestParam(defaultValue = "10") int size,
	        @AuthenticationPrincipal UserPrincipal userDetails) {

	    Integer viewerId = userDetails.getUserId();
	    Pageable pageable = PageRequest.of(offset / size, size); // converting offset to page number
	    
	    Page<PostResponseDto> posts = communityService.getCommunityPosts(viewerId, communityId, pageable);
	    return ResponseEntity.ok(posts);
	}
	
	
}
