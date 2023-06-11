package com.wcc.postal;

import com.wcc.postal.model.Postcode;

public class PostcodeFixture {

    public static Postcode getPostcode1() {
        Postcode postcode1 = new Postcode();
        postcode1.setPostcode("AB10 1XG");
        postcode1.setLatitude(57.14415740966797f);
        postcode1.setLongitude(-2.1148641109466553f);
        return postcode1;
    }

    public static Postcode getPostcode2() {
        Postcode postcode2 = new Postcode();
        postcode2.setPostcode("AB53 4PA");
        postcode2.setLatitude(57.54204177856445f);
        postcode2.setLongitude(-2.458717107772827f);
        return postcode2;
    }
}
