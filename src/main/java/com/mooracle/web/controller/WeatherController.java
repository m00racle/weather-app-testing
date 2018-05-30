package com.mooracle.web.controller;


import com.mooracle.service.GeocodingService;
import com.mooracle.service.WeatherService;
import com.mooracle.service.dto.geocoding.GeocodingResult;
import com.mooracle.service.dto.geocoding.Location;
import com.mooracle.service.dto.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import the static methods uriEncode

import static com.mooracle.util.WebUtils.uriEncode;

/** Entry 37: com.mooracle.web.controller.WeatherController
 *  1.  This controller class controls the main body of the apps
 *  2.  It spans from controlling the geocoding up to weather data
 *  3.  The controls utilize many HTTP servlet methods which I am honestly not really familiar with
 *  4.  This class also use com.mooracle.util.WebUtils.uriEncode method which is not initialized in this class
 *  5.  Thus this method must be imported as static package that will ensure initialization in compiler
 * */

@Controller
public class WeatherController {
    // field declaration and initialization

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private GeocodingService geocodingService;

    /** Notes:
     *  this is the rerquest for home webpage
     *
     *  However, here there are no model added inside the controller. Meaning there will be no weather object in the
     *  template. In other word $weather == null in the weather/detail.html template. Therefore it will only posed the
     *  header, footer and header 2 and one line paragraph.
     * */
    @RequestMapping("/")
    public String home() {
        return "weather/detail";
    }

    /** Notes:
     * This method will response to the request to /search web page which is activated from search form button with
     * GET method. Please NOTE this search form is not part of weather/details but par of layout template appended
     * using th:replace key into the weather/details template.
     *
     * The only thing it does is to append a request parameter (which comes with the Http request to /serach) called
     * q which is a string. After that redirect the controller to other method /search/q with q is the appended request
     * parameter.
     * */
    @RequestMapping("/search")
    public String search(@RequestParam String q){
        return String.format("redirect:/search/%s", uriEncode(q));
    }

    /** Notes:
     * This method accept the redirect from the /search request by search method.
     *
     * As the q already fetched from the request parameter and put directly into the path then in this method we will
     * use @PathVariable to retrieve the String object q as input for the search terms. Then using that search terms q
     * we can call many Geocoding and Weather object using WeatherService and GeocodingService implementation in service
     * layer's packages
     *
     * In this method we also learn that DTO consist of Entities that is not from internal of the app rather it will be
     * fetched from external API.
     *
     * In the process we will use Geocoding Service to determine the location from the search term. This step is
     * important to understand that the Geocoding location determination is important since the weather data relies
     * heavily on this geocoding data. Therefore, if we put search term q to weather Service it will prone to error.
     * */
    @RequestMapping("/search/{q}")
    public String getWeatherForSearchTerm(@PathVariable String q, Model model){
        GeocodingResult geocodingResult = geocodingService.findBySearchTerm(q); // find the geocoding from search term

        // we use the location getter of the Geocoding result to determine the weather in that location.

        Weather weather = weatherService.findByLocation(geocodingResult.getGeometry().getLocation());

        // put it all into the model map

        model.addAttribute("geocodingResult", geocodingResult);
        model.addAttribute("weather", weather);

        return "weather/detail";
    }

    /** Notes:
     * This method is intended to get the weather forecast for given location. The location was formed using longitude
     * (double lng) and latitude (double lat) which is a request parameter in the URI http.
     *
     * However, this method controller is not yet implemented in any templates. So far no other controller method has
     * this controller request mapping as redirect.
     * */
    @RequestMapping("/geo")
    public String getWeatherForLocation(@RequestParam double lat,
                                        @RequestParam double lng,
                                        Model model){
        // define the location using longitude and latitude

        Location location = new Location(lat, lng);

        // get weather forecast data based on the location

        Weather weather = weatherService.findByLocation(location);

        //add it into model map to be rendered in view templates

        model.addAttribute("weather", weather);

        return "weather/detail";
    }
}
