package com.sas.social.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sas.social.entity.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer>{

//	@Query("SELECT cp FROM CommunityPost cp JOIN cp.post p WHERE cp.community.communityId = :communityId ORDER BY p.createdAt DESC")
//	Page<CommunityPost> findByCommunityIdOrderByPostCreatedAtDesc(@Param("communityId") Integer communityId, Pageable pageable);
	
}
