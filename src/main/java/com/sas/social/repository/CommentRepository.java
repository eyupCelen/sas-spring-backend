// Table: comment
// Columns:
// - comment_id
// - post_id
// - user_id
// - parent_comment_id
// (Muhtemel diğer kolonlar: content, created_at, updated_at, vs.)

// Table: comment_like
// Columns:
// - comment_id
// - user_id

package com.sas.social.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.sas.social.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{
    // Basic CRUD operations are inherited from JpaRepository
    
    // Find comments by post ID
    List<Comment> findByPostId(Integer postId);
    
    // Find comments by post ID with pagination
    Page<Comment> findByPostId(Integer postId, Pageable pageable);
    
    // Find comments by user ID
    List<Comment> findByUserId(Integer userId);
    
    // Find comments by user ID with pagination
    Page<Comment> findByUserId(Integer userId, Pageable pageable);
    
    // Get like count for a comment
    @NativeQuery(value = "SELECT COUNT(*) FROM comment_like WHERE comment_id = :commentId")
    int getLikeCount(@Param("commentId") Integer commentId);
    
    // Check if a user has liked a comment
    @NativeQuery(value = "SELECT COUNT(*) > 0 FROM comment_like WHERE comment_id = :commentId AND user_id = :userId")
    boolean hasUserLikedComment(@Param("commentId") Integer commentId, @Param("userId") Integer userId);
    
    // Count comments for a specific post
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.postId = :postId")
    int countByPostId(@Param("postId") Integer postId);
    
    // Find replies to a specific comment
    @Query("SELECT c FROM Comment c WHERE c.parentComment.commentId = :commentId")
    List<Comment> findRepliesByCommentId(@Param("commentId") Integer commentId);
    
    // Find top-level comments for a post (no parent comment)
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.parentComment IS NULL")
    List<Comment> findTopLevelCommentsByPostId(@Param("postId") Integer postId);
    
    // Find top-level comments for a post with pagination
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.parentComment IS NULL")
    Page<Comment> findTopLevelCommentsByPostId(@Param("postId") Integer postId, Pageable pageable);
}