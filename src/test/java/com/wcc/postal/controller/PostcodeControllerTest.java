package com.wcc.postal.controller;

import com.wcc.postal.dto.DistanceResponse;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PostcodeControllerTest {

    @InjectMocks
    private PostcodeController postcodeController;

    @Mock
    private PostcodeService postcodeService;

    @Test
    public void testGetDistance() {
        Postcode postcode1 = new Postcode(); // Add appropriate constructors
        Postcode postcode2 = new Postcode(); // Add appropriate constructors
        Mockito.when(postcodeService.getPostcode(any(String.class))).thenReturn(postcode1);
        Mockito.when(postcodeService.calculateDistance(any(String.class), any(String.class))).thenReturn(123.45);

        ResponseEntity<DistanceResponse> response = postcodeController.getDistance("AB12 3CD", "EF45 6GH");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(123.45, response.getBody().getDistance());
    }

    @Test
    public void testGetDistanceWithInvalidPostcode1() {
        ResponseEntity<DistanceResponse> response = postcodeController.getDistance("INVALID", "EF45 6GH");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetDistanceWithInvalidPostcode2() {
        ResponseEntity<DistanceResponse> response = postcodeController.getDistance("AB12 3CD", "INVALID");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetDistanceWithNullPostcode1() {
        ResponseEntity<DistanceResponse> response = postcodeController.getDistance(null, "EF45 6GH");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetDistanceWithNullPostcode2() {
        ResponseEntity<DistanceResponse> response = postcodeController.getDistance("AB12 3CD", null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}

