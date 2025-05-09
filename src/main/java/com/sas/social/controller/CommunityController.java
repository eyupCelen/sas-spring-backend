package com.sas.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sas.social.dto.CommunityDto;
import com.sas.social.dto.PostCreateDto;
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
	public ResponseEntity<?> createCommunity(@RequestBody CommunityDto communityDto) {
		return communityService.createCommunity(communityDto);
	}
	
	@GetMapping("/all")
	public List<CommunityDto> getAllCommunities() {
		return communityService.getAll();
	}
	
//	@PostMapping("{communityId}/post") 
//	public void createCommunityPost(@RequestBody PostCreateDto postDto,
//				@PathVariable Integer communityId) {
//		
//		communityService.createCommunityPost(postDto, communityId);
//	}
	
	
//	@GetMapping("/{communityId}/posts")
//	public ResponseEntity<Page<PostResponseDto>> getRecentCommunityPosts(
//	        @PathVariable Integer communityId,
//	        @RequestParam(defaultValue = "0") int offset,
//	        @RequestParam(defaultValue = "10") int size,
//	        @AuthenticationPrincipal UserPrincipal userDetails) {
//
////	    Integer viewerId = userDetails.getUserId();
////	    Pageable pageable = PageRequest.of(offset / size, size); // converting offset to page number
////
////	    Page<PostResponseDto> posts = postService.getPostsOfUser(userId, viewerId, pageable);
////	    return ResponseEntity.ok(posts);
//	}
	
	
}
