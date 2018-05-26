package com.mooracle.service.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mooracle.service.dto.Dto;

/** Entry 18: Creating Geocoding PlaceResult.java class
 *  1.  This class extending the Dto class
 *  2.  This class is intended to fetch the place id data from external Google place API using JSON data type
 *  3.  To find out more about @JsonProperty refer to Entry 15 ind the README
 *  4.  This is just standard getters and setters class with the field which fetch a JSON property data
 *  5.  This class will be the backbone of the PlaceResponse class which looks like will handle the controller requests
 *
 * */
public class PlaceResult extends Dto {
    // field declarations:

    @JsonProperty("place_id")
    private String placeId;

    private Geometry geometry;

    // getters and setters

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
