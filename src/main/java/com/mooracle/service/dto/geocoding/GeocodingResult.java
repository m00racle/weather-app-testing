package com.mooracle.service.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mooracle.service.dto.Dto;

import java.util.List;

/** Entry 20: Creating Geocoding service of GeocodingResult.java
 *  1.  This class will serve the call from GeocodingResponse.java
 *  2.  This class will compile all data from Address components and Geometry
 *  3.  This class is standard getters and setters with no apaprent constructor
 *  4.  In this class there is still calls to external JSON data from Google API but the data is already defined
 *  5.  The definition of the data comes from AddressComponent.java class but here we still need to fetch it from JSON
 *  6.  Looks like in the Google API it also available of List compiled on Address Components thus we can just fetch it
 *
 * */
public class GeocodingResult extends Dto {
    // field declaration

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    @JsonProperty("place_id")
    private String placeId;

    // getters and setters

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
