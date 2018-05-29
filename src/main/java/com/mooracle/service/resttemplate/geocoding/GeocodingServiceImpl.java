package com.mooracle.service.resttemplate.geocoding;

import com.mooracle.service.GeocodingService;
import com.mooracle.service.dto.geocoding.GeocodingResponse;
import com.mooracle.service.dto.geocoding.GeocodingResult;
import com.mooracle.service.resttemplate.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/** Entry 32: Creating GeocodingServiceImpl class extends RestApiService and implement GeocodingService interface
 *  1.  This class assemble request for search of geocoding
 *  2.  Using search terms (q) as input it will fetch the response that was composed in GeocodingResponse class in Dto
 *  3.  This class use the same principle as the PlaceServiceImpl java will also use @Value annotation (more in Entry 31)
 *  4.  This class will aslo fetch values from api.properties file
 * */

@Service
@PropertySource("api.properties")
public class GeocodingServiceImpl extends RestApiService<GeocodingResponse> implements GeocodingService {
    // field declaration
    private String name;
    private String key;
    private String host;
    private String region;

    //constructor
    @Autowired
    public GeocodingServiceImpl(@Value("geocode.api.name") String name,
                                @Value("geocode.api.key") String key,
                                @Value("geocode.api.host") String host,
                                @Value("geocode.api.region") String region) {
        super();
        this.name = name;
        this.key = key;
        this.host = host;
        this.region = region;
    }

    @Override
    public GeocodingResult findBySearchTerm(String q) {
        GeocodingResponse response = get("maps/api/geocode/json?address={q}&sensor=false&key={key}")
                .param("q", q) // the parameter passed in the request
                .execute();
        return response.getResults().get(0); // return the top result
    }

    // getter for the name only since it has no method in the extended RestApiService abstract class
    public String getName() {
        return name;
    }

    /** Note:
     *  As mentioned above the RestApiService only has three methods: host, key and getDtoClass
     *  this will determine the sequence of data from the DTO package.
     * */
    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getApiKey() {
        return key;
    }

    @Override
    public Class<GeocodingResponse> getDtoClass() {
        return GeocodingResponse.class;
    }
}
