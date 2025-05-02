package com.sas.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.social.entity.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer>{

}
