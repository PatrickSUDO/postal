package com.wcc.postal.service;

import com.wcc.postal.model.Postcode;
import com.wcc.postal.repository.PostcodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PostcodeServiceTest {

    @InjectMocks
    private PostcodeService postcodeService;

    @Mock
    private PostcodeRepository postcodeRepository;

    @Test
    public void testGetPostcode() {
        Postcode postcode = new Postcode(); // Add appropriate constructors
        Mockito.when(postcodeRepository.findByPostcode(any(String.class))).thenReturn(postcode);

        Postcode result = postcodeService.getPostcode("AB12 3CD");
        assertEquals(postcode, result);
    }

    @Test
    public void testCalculateDistanceWithNonExistingPostcode1() {
        Mockito.when(postcodeRepository.findByPostcode("NON_EXISTING")).thenReturn(null);
        Mockito.when(postcodeRepository.findByPostcode("EXISTING")).thenReturn(new Postcode());

        assertThrows(IllegalArgumentException.class, () -> postcodeService.calculateDistance("NON_EXISTING", "EXISTING"));
    }

    @Test
    public void testCalculateDistanceWithNonExistingPostcode2() {
        Mockito.when(postcodeRepository.findByPostcode("EXISTING")).thenReturn(new Postcode());
        Mockito.when(postcodeRepository.findByPostcode("NON_EXISTING")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> postcodeService.calculateDistance("EXISTING", "NON_EXISTING"));
    }

    @Test
    public void testGetPostcodeWithNonExistingPostcode() {
        Mockito.when(postcodeRepository.findByPostcode("NON_EXISTING")).thenReturn(null);

        Postcode result = postcodeService.getPostcode("NON_EXISTING");
        assertNull(result);
    }

}

