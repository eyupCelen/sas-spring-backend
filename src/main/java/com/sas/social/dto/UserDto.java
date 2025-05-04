package com.sas.social.dto;

import java.util.List;
import java.util.Set;

import com.sas.social.entity.Category;
import com.sas.social.entity.Media;
import com.sas.social.entity.Post;
import com.sas.social.entity.User;
import com.sas.social.entity.UserCommunity;

public record UserDto (
		Integer userId,
		String visibleName,
		String username,
		String email,
		Media profilePhoto,
		Media bannerPhoto,
		List<PostDto> posts,
		List<PostDto> likedPosts,
		Set<UserCommunity> participatedCommunities,
		Set<Integer> followsId,
		Set<Integer> blockedUsersId,
		Integer postNumber,
		Integer followerNumber,
		Integer followingNumber
) {

}
