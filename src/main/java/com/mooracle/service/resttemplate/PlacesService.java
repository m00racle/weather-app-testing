package com.mooracle.service.resttemplate;

import com.mooracle.service.dto.geocoding.PlacesResult;

/** Entry 22: Creating REST PlacesService.java interface
 *  1.  This interface only has one method to find a place that uses PlacesResult class object to specify places that
 *      matches String placeId
 *  2.  This interface still has no implementation yet
 *
 * */
public interface PlacesService {
    PlacesResult findByPlaceId(String placeId);
}
