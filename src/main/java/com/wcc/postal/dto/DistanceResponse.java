package com.wcc.postal.dto;

import com.wcc.postal.model.Postcode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistanceResponse {
    private String postcode1;
    private String postcode2;
    private double latitude1;
    private double latitude2;
    private double longitude1;
    private double longitude2;
    private double distance;
    private final String unit = "km";

    public DistanceResponse(Postcode postcode1, Postcode postcode2, double distance) {
        this.postcode1 = postcode1.getPostcode();
        this.latitude1 = postcode1.getLatitude();
        this.longitude1 = postcode1.getLongitude();
        this.postcode2 = postcode2.getPostcode();
        this.latitude2 = postcode2.getLatitude();
        this.longitude2 = postcode2.getLongitude();
        this.distance = distance;
    }
}

