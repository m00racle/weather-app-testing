package com.mooracle.service.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mooracle.service.dto.Dto;

/** Entry 16: Creating Geocoding Location.java class
 *  1.  This class is intended to fetch the Latitude and Longitude data in JSON format
 *  2.  In the course the original Location class is not extending Dto but here we will do that since Dto is just empty
 *  3.  Location is needed by Geometry the class that needed most by other classes in geocoding package.
 *  4.  This class has two constructors one is default other is common constructor.
 *  5.  Other than that it is business as usual with getters and setters.
 *  6.  Once again all field will use @JsonProperty with naming different with the data source name.
 *  7.  Thus we need to defince @JsonProperty(value) the data source name for each field.
 *  8.  More on @JsonProperty in the README Entry 15 section
 *  */
public class Location extends Dto {
    // field and JSON data declaration:

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lng")
    private double longitude;

    //constructors

    public Location() {}

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // getters and setters:

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
