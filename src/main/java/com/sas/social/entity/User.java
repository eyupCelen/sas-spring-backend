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
   
    //TODO: FOLLOW, BLOCK
    
}
