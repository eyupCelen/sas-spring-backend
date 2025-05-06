package com.sas.social.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.PostResponseDto;
import com.sas.social.dto.UserSummary;
import com.sas.social.entity.Post;
import com.sas.social.repository.PostRepository;
import com.sas.social.repository.UserRepository;

@Component
public class PostMapper {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PostRepository postRepository;

    public PostResponseDto map(Post post, Integer viewingUserId) {
        Integer postAuthorId = post.getUser().getUserId();

        boolean isPostAuthorFollowed = userRepository.isUserFollowing(viewingUserId, postAuthorId);
        boolean isPostLiked = postRepository.hasUserLikedPost(viewingUserId, post.getPostId());

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
            false
        );
    }
}
