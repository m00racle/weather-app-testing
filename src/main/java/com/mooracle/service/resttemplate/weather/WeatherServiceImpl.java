package com.mooracle.service.resttemplate.weather;

import com.mooracle.service.WeatherService;
import com.mooracle.service.dto.geocoding.Location;
import com.mooracle.service.dto.weather.Weather;
import com.mooracle.service.resttemplate.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/** Entry 33: Create WeatherServiceImpl extends RestApiService and implements WeatherService interface
 *  1.  This class is used to fetch weather data from the Dark Sky API.
 *  2.  Unlike Google Geocoding and Google places the data comes in direct package of data
 *  3.  Thus we, do not need to fetch and sort the response results.
 *  4.  This class also still use properties listed in the api.properties
 * */

@Service
@PropertySource("api.properties")
public class WeatherServiceImpl extends RestApiService<Weather> implements WeatherService {
    // field declaration
    private String name;
    private String key;
    private String host;

    //constructor
    @Autowired
    public WeatherServiceImpl(@Value("${weather.api.name}") String name,
                              @Value("${weather.api.key}") String key,
                              @Value("${weather.api.host}") String host) {
        super(); // it calls the constructor of the RestApiService (extended class)
        this.name = name;
        this.key = key;
        this.host = host;
    }

    /** Notes:
     *  As mentioned above the response of the request uri to the API in the Dark Sky weather app API is just data
     *  it does not need further processing just come as it is. Thus we can just return the response of the request
     *  */
    @Override
    public Weather findByLocation(Location location) {
        return get("/forecast/{key}/{lat},{lng}")
                .param("lat", location.getLatitude())
                .param("lng", location.getLongitude())
                .execute();
    }

    /** Notes:
     * name is the only field not mentioned in RestApiService thus need to be given its own getters to safely accessed
     * from outside the package.
     * */
    public String getName() {
        return name;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getApiKey() {
        return key;
    }

    @Override
    public Class<Weather> getDtoClass() {
        return Weather.class;
    }
}
