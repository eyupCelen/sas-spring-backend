package com.sas.social.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.sas.social.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // Basic CRUD operations are inherited from JpaRepository
    
    // Find posts by user ID
    List<Post> findByUserId(Integer userId);
    
    // Find posts by user ID with pagination
    Page<Post> findByUserId(Integer userId, Pageable pageable);
    
    // Find posts from users that a specific user follows
    @Query("SELECT p FROM Post p WHERE p.user.userId IN " +
           "(SELECT f.userId FROM User u JOIN u.follows f WHERE u.userId = :userId) " +
           "ORDER BY p.createdAt DESC")
    List<Post> findPostsFromFollowedUsers(@Param("userId") Integer userId);
    
    // Find posts from users that a specific user follows with pagination
    @Query("SELECT p FROM Post p WHERE p.user.userId IN " +
           "(SELECT f.userId FROM User u JOIN u.follows f WHERE u.userId = :userId) " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findPostsFromFollowedUsers(@Param("userId") Integer userId, Pageable pageable);
    
    // Get like count for a post
    @NativeQuery(value = "SELECT COUNT(*) FROM post_like WHERE post_id = :postId")
    int getLikeCount(@Param("postId") Integer postId);
        
    // Check if a user has liked a post
    @NativeQuery(value = "SELECT EXISTS(SELECT 1 FROM post_like WHERE post_id = :postId AND user_id = :userId)")
    boolean hasUserLikedPost(@Param("userId") Integer userId, @Param("postId") Integer postId);
    
    // Find trending posts (most likes in the last 7 days)
    @Query("SELECT p FROM Post p WHERE p.createdAt >= CURRENT_DATE - 7 ORDER BY SIZE(p.likes) DESC")
    List<Post> findTrendingPosts(Pageable pageable);
    
    // Search posts by caption content
    List<Post> findBycontentContainingIgnoreCase(String keyword);
}