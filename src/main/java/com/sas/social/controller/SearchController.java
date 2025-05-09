package com.sas.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sas.social.dto.SearchResultDto;
import com.sas.social.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
    
    @Autowired
    private SearchService searchService;
    
    @GetMapping
    public ResponseEntity<Page<SearchResultDto>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<SearchResultDto> results = searchService.search(query, pageable);
        
        return ResponseEntity.ok(results);
    }
}