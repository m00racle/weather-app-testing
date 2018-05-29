package com.mooracle.service.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mooracle.service.dto.Dto;

import java.util.List;

/** Entry 15: Creating Geocoding AddressComponent.java class
 *  1.  This class is meant to provide the abstract of the address format and also prepare a place for JSON external data
 *  2.  more about @JsonProperty can be found the link in the README
 *  3.  The class itslef still extends abstract class Dto so it can access other classes which extends Dto class
 *  4.  This class is required by other classes inside the geocoding package thus it must created first.
 *  5.  The JSON data name sounds like mandatory naming thus the name in field declaration have the same name as JSON
 *  6.  In our case the name in the field is different with the JSON name thus in the @JsonProperty we need to provide
 *      ("value") which denotes the JSON name of the external data.
 *
 * */

public class AddressComponent extends Dto {
    //field and Json data declaration:

    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    private List<String> types;

    //getters and setters:

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
