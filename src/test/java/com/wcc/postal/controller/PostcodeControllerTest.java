package com.wcc.postal.controller;
import com.wcc.postal.PostcodeFixture;
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

    private Postcode postcode1;
    private Postcode postcode2;
    @Test
    @WithMockUser(roles = "USER")
    public void testGetDistance() throws Exception {
        postcode1 = PostcodeFixture.getPostcode1();
        postcode2 = PostcodeFixture.getPostcode2();

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

