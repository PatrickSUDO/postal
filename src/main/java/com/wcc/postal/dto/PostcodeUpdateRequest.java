package com.wcc.postal.dto;

import com.wcc.postal.model.Postcode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostcodeUpdateRequest {
    private String postcode;
    private Float latitude;
    private Float longitude;
}
