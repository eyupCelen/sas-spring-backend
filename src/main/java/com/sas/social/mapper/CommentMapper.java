package com.sas.social.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.social.dto.CommentDto;
import com.sas.social.dto.UserSummaryDto;
import com.sas.social.entity.Comment;
import com.sas.social.entity.Post;
import com.sas.social.entity.User;
import com.sas.social.repository.PostRepository;
import com.sas.social.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class CommentMapper 
	implements Function<Comment, CommentDto> {

	@Autowired 
	private static UserRepository userRepository;
	
	@Autowired 
	private static PostRepository postRepository;
	
	@Override
	public CommentDto apply(Comment comment) {
		return new CommentDto(
				comment.getCommentId(),
				comment.getText(),
				
				new UserSummaryDto(
						comment.getUser().getUserId(),
						comment.getUser().getVisibleName(),
						comment.getUser().getUsername(),
						comment.getUser().getProfilePhoto()
				),
				
				comment.getPost().getPostId(),
				comment.getCreatedAt()
				);
	}
	
	public static Comment toComment(CommentDto commentDto) {
	    User user = userRepository.findById( commentDto.userSummary().userId() )
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));
	    Post post = postRepository.findById( commentDto.postId() )
	            .orElseThrow(() -> new EntityNotFoundException("Post not found"));
		
	    return new Comment(
				commentDto.commentId(),
				commentDto.text(),
				user,
				post,
				commentDto.createdAt()
				);
	}
	
}
