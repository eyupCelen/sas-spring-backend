package com.sas.social.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class ICommunityDal {

	private EntityManager entityManager;

		@Autowired
		public ICommunityDal(EntityManager entityManager) {
			this.entityManager = entityManager;
		} 


}
