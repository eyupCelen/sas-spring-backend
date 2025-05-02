package com.sas.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.social.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
