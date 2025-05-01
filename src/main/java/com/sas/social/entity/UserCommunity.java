package com.sas.social.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user_community")
public class UserCommunity {

	@Column(name="user_role", nullable = false)
	private String userRole;
	
	// Note: Multiple id annotations Hibernate compatible only 
	
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;		// Member of the community
    
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)	
    private Community community;  

	// Constructors
	public UserCommunity() {}

	public UserCommunity(String userRole) {
		this.userRole = userRole;
	}
	
	// getters and setters
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	
}
