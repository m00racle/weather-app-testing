package com.mooracle.service.resttemplate.geocoding;

import com.mooracle.service.dto.geocoding.PlacesResponse;
import com.mooracle.service.dto.geocoding.PlacesResult;
import com.mooracle.service.resttemplate.PlacesService;
import com.mooracle.service.resttemplate.RestApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/** Entry 31: Creating PlaceServiceImpl implementing the PlaceService Interface
 *  1.  This class implements PlaceService interface and extends RestApiService class please implements all both methods
 *  2.  In extending the RestApiService we need to specify the generic as RestApiService
 *  3.  This is a @Service class make sure Spring knows this
 *  4.  Here we will meet with @Value annotation (link in the README).
 *  5.  In this case @Value is used for setting the default value expressions in the constructor using values from
 *      api.properties file content related to Places.
 *  6.  In this class we will use parameters that we defined before in api.properties file. Remember api.properties is
 *      already inside a root filepath (resources folder is included in file path) thus we only ned to define the name.
 *  7.
 * */

@Service
@PropertySource("api.properties")
public class PlaceServiceImpl extends RestApiService<PlacesResponse> implements PlacesService {
    // field declaration
    private String name;
    private String key;
    private String host;

    // constructor: here the passed arguments will use @Value
    public PlaceServiceImpl(@Value("${places.api.name}") String name,
                            @Value("${places.api.key}") String key,
                            @Value("${places.api.host}") String host) {
        //the name in the @Value sounds familiar? It should be, we coded them in the place section of api.properties file
        this.name = name;
        this.key = key;
        this.host = host;
    }

    /** Note:
     *  Looks like this part is responsible of requesting a get to an address specified in the get()
     *  Then it will store result in the response variable. In that response it need to return the getResult method
     *  */
    @Override
    public PlacesResult findByPlaceId(String placeId) {
        PlacesResponse response = get("/maps/api/place/details/json?placeid={placeId}&sensor=false&key={key}")
                .param("placeId", placeId)
                .execute();
        return response.getResult();
    }

    @Override
    public String getHost() {
        return host; // remember we already take this from api.properties using @Value
    }

    @Override
    public String getApiKey() {
        return key; // this also already taken from api.service using @Value
    }

    @Override
    public Class<PlacesResponse> getDotClass() {
        return PlacesResponse.class;
    }
}
