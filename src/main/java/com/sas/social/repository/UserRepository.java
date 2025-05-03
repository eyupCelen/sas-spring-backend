package com.sas.social.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sas.social.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.follows f WHERE f.userId = :userId")
    List<User> findFollowersByUserId(@Param("userId") Integer userId);

    @Query("SELECT f FROM User u JOIN u.follows f WHERE u.userId = :userId")
    List<User> findFollowsByUserId(@Param("userId") Integer userId);

    @Query("SELECT b FROM User u JOIN u.blockedUsers b WHERE u.userId = :userId")
    List<User> findBlockedUsersByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(u.userId) FROM User u JOIN u.follows f WHERE f.userId = :userId")
    int getNumberOfFollowers(@Param("userId") Integer userId);
   
    @Query("SELECT COUNT(f.userId) FROM User u JOIN u.follows f WHERE u.userId = :userId")
    int getNumberOfFollowing(@Param("userId") Integer userId);
    
    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.userId = :userId")
    int getPostNumber(@Param("userId") Integer userId);
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
