package com.sas.social.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.PostCreateDto;
import com.sas.social.dto.PostResponseDto;
import com.sas.social.dto.UserSummary;
import com.sas.social.entity.Post;
import com.sas.social.entity.User;
import com.sas.social.repository.PostRepository;
import com.sas.social.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class PostMapper {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PostRepository postRepository;

    public PostResponseDto map(Post post, Integer viewingUserId) {
        Integer postAuthorId = post.getUser().getUserId();
        Integer postId = post.getPostId();

        boolean isPostAuthorFollowed = userRepository.isUserFollowing(viewingUserId, postAuthorId);
        boolean isPostLiked = postRepository.hasUserLikedPost(viewingUserId, postId);
        
        Integer postLikeCount = postRepository.getLikeCount( postId );
        Integer postCommentCount = postRepository.getCommentCount( postId );

        return new PostResponseDto(
            new UserSummary(
                post.getUser().getUserId(),
                post.getUser().getVisibleName(),
                post.getUser().getUsername(),
                post.getUser().getProfilePhoto()
            ),
            post.getPostId(),
            post.getContent(),
            post.getPostImage(),
            
            isPostAuthorFollowed,
            isPostLiked,
            
            postLikeCount,
            postCommentCount
        );
    }
    
    public Post map(PostCreateDto dto) {
	    User user = userRepository.findByUsername( dto.username() )
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    	return new Post(
    			dto.title(),
    			dto.postImage(),
    			dto.homepageVisible(),
    			user,
    			dto.postCategories()
    			);
    }
}
