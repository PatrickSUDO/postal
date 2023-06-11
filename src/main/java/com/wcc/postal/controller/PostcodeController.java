package com.wcc.postal.controller;

import com.wcc.postal.dto.DistanceResponse;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.service.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class PostcodeController {
    private static final Logger log = LoggerFactory.getLogger(PostcodeController.class);

    private final PostcodeService postcodeService; // This service will implement the logic for your controller

    @Autowired
    public PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping("/distance")
    @PreAuthorize("hasRole('USER')") // Only authenticated users with role 'USER' can access this endpoint
    public DistanceResponse getDistance(@RequestParam String postcode1, @RequestParam String postcode2) {
        log.info("Calculating distance between {} and {}", postcode1, postcode2);
        double distance = postcodeService.calculateDistance(postcode1, postcode2);
        Postcode p1 = postcodeService.getPostcode(postcode1);
        Postcode p2 = postcodeService.getPostcode(postcode2);

        return new DistanceResponse(p1, p2, distance);
    }

}
