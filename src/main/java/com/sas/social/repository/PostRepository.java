package com.sas.social.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sas.social.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
        
    // Get stats for a post
    
    @NativeQuery(value = "SELECT COUNT(*) FROM post_like WHERE post_id = :postId")
    int getLikeCount(@Param("postId") Integer postId);
        
    @NativeQuery(value = "SELECT COUNT(*) FROM comment WHERE post_id = :postId")
    int getCommentCount(@Param("postId") Integer postId);
    
    // Check if a user has liked a post
    @NativeQuery(value = "SELECT EXISTS(SELECT 1 FROM post_like WHERE post_id = :postId AND user_id = :userId)")
    boolean hasUserLikedPost(@Param("userId") Integer userId, @Param("postId") Integer postId);
        
    // Search posts by caption content
    List<Post> findBycontentContainingIgnoreCase(String keyword);
    
    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId ORDER BY p.createdAt DESC")
    Page<Post> findByUserId_OrderByCreationDate(@Param("userId") Integer userId, Pageable pageable);
    
    boolean existsByPostId(Integer postId);
}