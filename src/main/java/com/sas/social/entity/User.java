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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="name_surname", nullable = false, unique = true)
    private String nameSurname;
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name ="user_password", nullable = false)
    private String password;

    @Column(name="profile_photo_url")
    private String profilePhotoUrl;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    
    @ManyToMany(mappedBy = "likingUsers")
    private List<Post> likedPosts = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserCommunity> participatedCommunities = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
      name = "user_interest", 
      joinColumns = @JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> userCategories = new HashSet<>();
   
    @ManyToMany
    @JoinTable(
      name = "user_follow", 
      joinColumns = @JoinColumn(name = "follower_id"), 
      inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> followers = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
      name = "user_follow", 
      joinColumns = @JoinColumn(name = "followed_id"), 
      inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> follows = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
      name = "user_block", 
      joinColumns = @JoinColumn(name = "blocker_id"), 
      inverseJoinColumns = @JoinColumn(name = "blocked_id")
    )
    private Set<User> blockedUsers = new HashSet<>();
    
    // Constructor
    public User() {}

    
    // getters and setters
	public String getNameSurname() {
		return nameSurname;
	}

	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}

	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public List<Post> getLikedPosts() {
		return likedPosts;
	}

	public Set<UserCommunity> getParticipatedCommunities() {
		return participatedCommunities;
	}

	public Set<Category> getUserCategories() {
		return userCategories;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public Set<User> getBlockedUsers() {
		return blockedUsers;
	}
          
}
