package com.sas.social.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="community")
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="community_id")
	private Integer communityId;

	@Column(name="community_name", unique = true, nullable = false)
	private String communityName;
	
	@Column(name="community_photo_url")
	private String photoUrl;
	
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityPost> communityPosts = new ArrayList<>();
    
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private Set<UserCommunity> communityUsers = new HashSet<>();

    public Community() {}

	public Community(Integer communityId, String communityName, String photoUrl) {
		this.communityId = communityId;
		this.communityName = communityName;
		this.photoUrl = photoUrl;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public List<CommunityPost> getCommunityPosts() {
		return communityPosts;
	}

	public Set<UserCommunity> getCommunityUsers() {
		return communityUsers;
	}
    
}
