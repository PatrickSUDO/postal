package com.wcc.postal.controller;

import com.wcc.postal.model.Postcode;
import com.wcc.postal.service.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PostcodeController {

    private final PostcodeService postcodeService; // This service will implement the logic for your controller

    @Autowired
    public PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping("/{postcodeId}")
    @PreAuthorize("hasRole('USER')") // Only authenticated users with role 'USER' can access this endpoint
    public ResponseEntity<?> getPostcode(@PathVariable Long postcodeId) {
        Postcode postcode = postcodeService.getPostcode(postcodeId);
        if (postcode == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postcode);
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')") // Only authenticated users with role 'ADMIN' can access this endpoint
    public ResponseEntity<?> createPostcode(@RequestBody Postcode newPostcode) {
        // Implement the logic of creating a new postcode using the postcodeService here
        // For example:
        Postcode postcode = postcodeService.createPostcode(newPostcode);
        if (postcode == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(postcode);
        }
    }
}
