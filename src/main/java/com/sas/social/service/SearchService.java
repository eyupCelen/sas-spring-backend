package com.sas.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sas.social.dto.SearchResultDto;
import com.sas.social.entity.Community;
import com.sas.social.entity.Post;
import com.sas.social.entity.User;
import com.sas.social.mapper.CommunityMapper;
import com.sas.social.mapper.PostMapper;
import com.sas.social.mapper.UserMapper;
import com.sas.social.repository.CommunityRepository;
import com.sas.social.repository.PostRepository;
import com.sas.social.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private CommunityMapper communityMapper;
    
    @Autowired
    private PostMapper postMapper;
    
    /**
     * Search for users, communities, and posts based on the search term
     * 
     * @param searchTerm Text to search for
     * @param pageable Pagination information
     * @return Page of search results with type information
     */
    public Page<SearchResultDto> search(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return Page.empty(pageable);
        }
        
        String term = "%" + searchTerm.toLowerCase() + "%";
        
        // Find matching users by name
        List<User> users = userRepository.findByNameContainingIgnoreCase(term);
        
        // Find matching communities by name
        List<Community> communities = communityRepository.findByNameContainingIgnoreCase(term);
        
        // Find matching posts by description
        List<Post> posts = postRepository.findByDescriptionContainingIgnoreCase(term);
        
        // Combine all results and sort them
        List<SearchResultDto> allResults = new ArrayList<>();
        
        // Map users to SearchResultDto
        users.forEach(user -> {
            SearchResultDto dto = new SearchResultDto();
            dto.setType("USER");
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setData(userMapper.toDto(user));
            allResults.add(dto);
        });
        
        // Map communities to SearchResultDto
        communities.forEach(community -> {
            SearchResultDto dto = new SearchResultDto();
            dto.setType("COMMUNITY");
            dto.setId(community.getId());
            dto.setName(community.getName());
            dto.setData(communityMapper.toDto(community));
            allResults.add(dto);
        });
        
        // Map posts to SearchResultDto
        posts.forEach(post -> {
            SearchResultDto dto = new SearchResultDto();
            dto.setType("POST");
            dto.setId(post.getId());
            dto.setName(post.getTitle()); // Assuming posts have a title, adjust if needed
            dto.setDescription(post.getDescription());
            dto.setData(postMapper.toResponseDto(post));
            allResults.add(dto);
        });
        
        // Sort results by relevance (exact matches first, then partial matches)
        List<SearchResultDto> sortedResults = allResults.stream()
            .sorted(getSearchResultComparator(searchTerm))
            .collect(Collectors.toList());
        
        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedResults.size());
        
        if (start > sortedResults.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, sortedResults.size());
        }
        
        return new PageImpl<>(
            sortedResults.subList(start, end),
            pageable,
            sortedResults.size()
        );
    }
    
    /**
     * Creates a comparator for search results that prioritizes:
     * 1. Exact matches
     * 2. Starts with the search term
     * 3. Contains the search term
     */
    private Comparator<SearchResultDto> getSearchResultComparator(String searchTerm) {
        String term = searchTerm.toLowerCase();
        
        return (result1, result2) -> {
            String name1 = result1.getName().toLowerCase();
            String name2 = result2.getName().toLowerCase();
            
            // Exact matches first
            if (name1.equals(term) && !name2.equals(term)) {
                return -1;
            }
            if (!name1.equals(term) && name2.equals(term)) {
                return 1;
            }
            
            // Then matches that start with the term
            if (name1.startsWith(term) && !name2.startsWith(term)) {
                return -1;
            }
            if (!name1.startsWith(term) && name2.startsWith(term)) {
                return 1;
            }
            
            // Finally, alphabetical order
            return name1.compareTo(name2);
        };
    }
}