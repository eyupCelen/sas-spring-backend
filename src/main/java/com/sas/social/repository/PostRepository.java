package com.sas.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.social.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
