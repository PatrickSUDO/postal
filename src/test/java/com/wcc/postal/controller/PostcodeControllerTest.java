package com.wcc.postal.controller;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.service.PostcodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostcodeController.class)
@ExtendWith(MockitoExtension.class)
public class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostcodeService postcodeService;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetDistance() throws Exception {
        Postcode postcode1 = new Postcode();
        postcode1.setPostcode("AB10 1XG");
        postcode1.setLatitude(57.14415740966797f);
        postcode1.setLongitude(-2.1148641109466553f);

        Postcode postcode2 = new Postcode();
        postcode2.setPostcode("AB53 4PA");
        postcode2.setLatitude(57.54204177856445f);
        postcode2.setLongitude(-2.458717107772827f);

        when(postcodeService.getPostcode(anyString())).thenReturn(postcode1, postcode2);
        when(postcodeService.calculateDistance(anyString(), anyString())).thenReturn(5.202602188565955d);

        mockMvc.perform(MockMvcRequestBuilders.get("/distance")
                        .param("postcode1", "AB10 1XG")
                        .param("postcode2", "AB53 4PA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distance").value(5.202602188565955f));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetDistanceWithInvalidPostcode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/distance")
                        .param("postcode1", "AB10 1XG")
                        .param("postcode2", "")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }
}

