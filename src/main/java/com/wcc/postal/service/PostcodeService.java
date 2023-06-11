package com.wcc.postal.service;
import com.wcc.postal.exception.PostcodeNotFoundException;
import com.wcc.postal.model.Postcode;
import com.wcc.postal.repository.PostcodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostcodeService {

    private static final Logger log = LoggerFactory.getLogger(PostcodeService.class);

    private final PostcodeRepository postcodeRepository;

    @Autowired
    public PostcodeService(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    @Transactional
    public Postcode updatePostcode(String postcode, Float latitude, Float longitude) {
        log.info("Updating coordinates for {}", postcode);
        Postcode existingPostcode = getPostcode(postcode);
        existingPostcode.setLatitude(latitude);
        existingPostcode.setLongitude(longitude);
        return postcodeRepository.save(existingPostcode);
    }


    public Postcode getPostcode(String postcode) {
        log.info("Fetching postcode information for {}", postcode);
        Postcode result = postcodeRepository.findByPostcode(postcode);
        if (result == null) {
            log.error("Postcode '{}' not found", postcode);
            throw new PostcodeNotFoundException("Postcode '" + postcode + "' not found");
        }
        return result;
    }

    public double calculateDistance(String postcode1, String postcode2) {
        log.info("Calculating distance between {} and {}", postcode1, postcode2);

        Postcode loc1 = postcodeRepository.findByPostcode(postcode1);
        Postcode loc2 = postcodeRepository.findByPostcode(postcode2);

        if(loc1 == null || loc2 == null) {
            log.error("Invalid postcode(s) provided.");
            throw new IllegalArgumentException("Invalid postcode(s) provided.");
        }

        Float lat1 = loc1.getLatitude();
        Float lon1 = loc1.getLongitude();
        Float lat2 = loc2.getLatitude();
        Float lon2 = loc2.getLongitude();

        if(lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            log.error("Missing latitude/longitude data.");
            throw new IllegalArgumentException("Missing latitude/longitude data.");
        }

        return calculateDistance(lat1, lon1, lat2, lon2);
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