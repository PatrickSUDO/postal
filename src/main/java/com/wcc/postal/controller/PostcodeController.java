package com.wcc.postal.controller;

import com.wcc.postal.dto.DistanceResponse;
import com.wcc.postal.dto.PostcodeUpdateRequest;
import com.wcc.postal.exception.PostcodeNotFoundException;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.service.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public ResponseEntity<DistanceResponse> getDistance(@RequestParam String postcode1, @RequestParam String postcode2) {
        if (!isValidPostcode(postcode1) || !isValidPostcode(postcode2)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Calculating distance between {} and {}", postcode1, postcode2);
        double distance = postcodeService.calculateDistance(postcode1, postcode2);
        Postcode p1 = postcodeService.getPostcode(postcode1);
        Postcode p2 = postcodeService.getPostcode(postcode2);

        return new ResponseEntity<>(new DistanceResponse(p1, p2, distance), HttpStatus.OK);
    }

    @PutMapping("/postcode")
    @PreAuthorize("hasRole('ADMIN')") // Only authenticated users with role 'ADMIN' can access this endpoint
    public ResponseEntity<Postcode> updatePostcode(@RequestBody PostcodeUpdateRequest updateRequest) {
        if (!isValidPostcode(updateRequest.getPostcode())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Updating coordinates for {}", updateRequest.getPostcode());
        Postcode updatedPostcode = postcodeService.updatePostcode(updateRequest.getPostcode(), updateRequest.getLatitude(), updateRequest.getLongitude());
        return new ResponseEntity<>(updatedPostcode, HttpStatus.OK);
    }

    private boolean isValidPostcode(String postcode) {
        // Add your postcode validation logic here. This is a simple example.
        String postcodeRegex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        return postcode.matches(postcodeRegex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        log.error("Parameter '{}' is missing", name);
        // Return a friendly error message
        return String.format("Parameter '%s' is missing", name);
    }

    @ExceptionHandler(PostcodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePostcodeNotFound(PostcodeNotFoundException ex) {
        return ex.getMessage();
    }

}
