package com.wcc.postal.service;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.repository.PostcodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostcodeService {

    private static final Logger log = LoggerFactory.getLogger(PostcodeService.class);

    private final PostcodeRepository postcodeRepository;

    @Autowired
    public PostcodeService(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    public Postcode getPostcode(String postcode) {
        log.info("Fetching postcode information for {}", postcode);
        return postcodeRepository.findByPostcode(postcode);
    }

    public double calculateDistance(String postcode1, String postcode2) {
        log.info("Calculating distance between {} and {}", postcode1, postcode2);

        Postcode loc1 = postcodeRepository.findByPostcode(postcode1);
        Postcode loc2 = postcodeRepository.findByPostcode(postcode2);

        if(loc1 == null || loc2 == null) {
            log.error("Invalid postcode(s) provided.");
            throw new IllegalArgumentException("Invalid postcode(s) provided.");
        }

        return calculateDistance(
                loc1.getLatitude(),
                loc1.getLongitude(),
                loc2.getLatitude(),
                loc2.getLongitude());
    }

    private static final double EARTH_RADIUS = 6371; // radius in kilometers

    private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }
}