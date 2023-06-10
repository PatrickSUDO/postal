package com.wcc.postal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "postcodes")
public class Postcode {
    @Id
    private String id;

    private String postcode;

    private Double latitude;

    private Double longitude;

    // getters and setters
}
