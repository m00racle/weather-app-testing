package com.mooracle.service.dto.geocoding;

import com.mooracle.service.dto.Dto;

/** Entry 19: Creating Geocoding PlacesResponse.java class
 *  1.  This class will use the places resulted from the class PlacesResult and send the response of the request
 *  2.  The requests will most likely comes from the controllers thus this is the class that might be referred to handle
 *      such requests
 *  3.  This is also a standard getters and setters class with no initialization constructor
 *  4.  Also this class extends the Dto.java class
 *
 * */
public class PlacesResponse extends Dto {

    // field declarations:
    private PlacesResult result;
    private String status;

    // getters and setters

    public PlacesResult getResult() {
        return result;
    }

    public void setResult(PlacesResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
