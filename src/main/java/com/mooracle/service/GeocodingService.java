package com.mooracle.service;

import com.mooracle.service.dto.geocoding.GeocodingResult;

/** Entry 26: Creating GeocodingService.java interface
 *  1.  This interface will specify the Geocoding object search result by the String input
 *  2.  This has the same principle as search but specific for Geocoding data
 *  3.  This interface will be implemented in the service.resttemplate.geooding package
 * */
public interface GeocodingService {
    GeocodingResult findBySearchTerm(String q);
}
