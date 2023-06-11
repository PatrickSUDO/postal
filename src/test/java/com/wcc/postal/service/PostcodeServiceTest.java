package com.wcc.postal.service;

import com.wcc.postal.PostcodeFixture;
import com.wcc.postal.exception.PostcodeNotFoundException;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.repository.PostcodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@MockitoSettings(strictness = Strictness.LENIENT)
public class PostcodeServiceTest {

    @Mock
    private PostcodeRepository postcodeRepository;

    @InjectMocks
    private PostcodeService postcodeService;

    private Postcode postcode1;
    private Postcode postcode2;

    @BeforeEach
    public void setUp() {
        postcode1 = PostcodeFixture.getPostcode1();
        postcode2 = PostcodeFixture.getPostcode2();

        given(postcodeRepository.findByPostcode("AB10 1XG")).willReturn(postcode1);
        given(postcodeRepository.findByPostcode("AB53 4PA")).willReturn(postcode2);
    }

    @Test
    public void testGetPostcode() {
        Postcode result = postcodeService.getPostcode("AB10 1XG");
        assertEquals(postcode1, result);

        result = postcodeService.getPostcode("AB53 4PA");
        assertEquals(postcode2, result);

        given(postcodeRepository.findByPostcode("UNKNOWN")).willReturn(null);
        assertThrows(PostcodeNotFoundException.class, () -> {
            postcodeService.getPostcode("UNKNOWN"); // Unknown postcode
        });
    }

    @Test
    public void testCalculateDistance() {
        double result = postcodeService.calculateDistance("AB10 1XG", "AB53 4PA");
        // Expected distance here. You can calculate it manually using the coordinates and the Haversine formula.
        double expectedDistance = 48.81671253752173;
        assertEquals(expectedDistance, result, 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            postcodeService.calculateDistance("AB10 1XG", "UNKNOWN"); // Unknown postcode
        });
    }

    @Test
    public void testUpdatePostcode() {
        given(postcodeRepository.save(any(Postcode.class))).willAnswer(invocation -> invocation.getArgument(0));

        Postcode result = postcodeService.updatePostcode("AB10 1XG", 58.0f, -3.0f);
        assertEquals("AB10 1XG", result.getPostcode());
        assertEquals(58.0f, result.getLatitude());
        assertEquals(-3.0f, result.getLongitude());
    }
}
