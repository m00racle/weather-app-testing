package com.mooracle.service.dto.geocoding;

import com.mooracle.service.dto.Dto;

/** Entry 17: Creating Geocoding Geometry.java class
 *  1.  This class is intended just to set and get geocoding Location object
 *  2.  I am not sure why this class is needed, maybe in the controller there is a call for making Location as a whole
 *  3.  This is maybe why the Location.java class is not exactly part of Dto extended class
 *  4.  But for now let's just keep it that way.
 * */
public class Geometry extends Dto {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
