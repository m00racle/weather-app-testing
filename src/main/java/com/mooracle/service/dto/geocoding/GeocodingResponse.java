package com.mooracle.service.dto.geocoding;

import com.mooracle.service.dto.Dto;

import java.util.List;

/** Entry 21: Creating Geocoding service GeocodingResponse.java
 *  1.  this class handles requests most likely from controllers or sort on geocoding data
 *  2.  this class extends Dto and basically just sending and setting the GeocodingResult class
 *  3.  Thus this is a standard getters and setters class with no constructors.
 *  4.  please note the data of GeocodingResult is in the form of a list thus it might not just one
 *
 * */
public class GeocodingResponse extends Dto {

    // field declarations
    private String status;
    private List<GeocodingResult> results;

    // getters and setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodingResult> getResults() {
        return results;
    }

    public void setResults(List<GeocodingResult> results) {
        this.results = results;
    }
}
