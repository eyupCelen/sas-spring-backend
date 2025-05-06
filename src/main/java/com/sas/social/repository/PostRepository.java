// Table: post
// Columns:
// - post_id
// - user_id
// - created_at
// - view_count
// - caption
// (Muhtemel diğer kolonlar: image_url, updated_at, vs.)

// Table: post_like
// Columns:
// - post_id
// - user_id

// Table: user
// Columns:
// - user_id
// (ve muhtemelen diğer kullanıcı bilgileri: username, email, vs.)

// Table: user_follow (bu tablo doğrudan yazılmamış ama JOIN'den çıkarıldı)
// Columns:
// - user_id  (takip eden kullanıcı)
// - follow_user_id (takip edilen kullanıcı) 
// (Kodda `u.follows f` olarak geçiyor)

// Table: post_hashtag (bu da ilişki tablosu olarak kullanılıyor)
// Columns:
// - post_id
// - hashtag_id

// Table: hashtag
// Columns:
// - hashtag_id
// - name


package com.sas.social.repository;

import java.util.List;
import java.util.Optional;

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
    
    // Get view count for a post
    @NativeQuery(value = "SELECT view_count FROM post WHERE post_id = :postId")
    int getViewCount(@Param("postId") Integer postId);
    
    // Increment view count for a post
    @NativeQuery(value = "UPDATE post SET view_count = view_count + 1 WHERE post_id = :postId")
    void incrementViewCount(@Param("postId") Integer postId);
    
    // Check if a user has liked a post
    @NativeQuery(value = "SELECT COUNT(*) > 0 FROM post_like WHERE post_id = :postId AND user_id = :userId")
    boolean hasUserLikedPost(@Param("postId") Integer postId, @Param("userId") Integer userId);
    
    // Get posts with a specific hashtag
    @Query("SELECT p FROM Post p JOIN p.hashtags h WHERE h.name = :hashtagName ORDER BY p.createdAt DESC")
    List<Post> findByHashtagName(@Param("hashtagName") String hashtagName);
    
    // Get posts with a specific hashtag with pagination
    @Query("SELECT p FROM Post p JOIN p.hashtags h WHERE h.name = :hashtagName ORDER BY p.createdAt DESC")
    Page<Post> findByHashtagName(@Param("hashtagName") String hashtagName, Pageable pageable);
    
    // Find trending posts (most likes in the last 7 days)
    @Query("SELECT p FROM Post p WHERE p.createdAt >= CURRENT_DATE - 7 ORDER BY SIZE(p.likes) DESC")
    List<Post> findTrendingPosts(Pageable pageable);
    
    // Search posts by caption content
    List<Post> findByCaptionContainingIgnoreCase(String keyword);
}