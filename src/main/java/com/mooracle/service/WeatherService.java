package com.mooracle.service;

import com.mooracle.service.dto.geocoding.Location;
import com.mooracle.service.dto.weather.Weather;

/** Entry 25: Creating WeatherService.java interface
 *  1.  This interface only specifies the relation between weather in specified Location
 *  2.  This will require Dto objects Weather and Location
 *  3.  This interface will be used in the Weather service package (implemented)
 * */
public interface WeatherService {
    Weather findByLocation(Location location);
}
