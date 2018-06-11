package com.mooracle.service;

import com.mooracle.config.AppConfig;
import com.mooracle.service.dto.geocoding.Location;
import com.mooracle.service.dto.weather.Weather;
import com.mooracle.service.resttemplate.weather.WeatherServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
/** Entry 46: Setting Up a Test Context com.mooracle.service.WeatherServiceTest
 *  This class will demonstrate the use of minimal test ApplicationContext in order to test the Weather Service. What
 *  we are testing is the interface rather than the implementation.
 *
 *  To do this and access some od the Spring functionality during the test we need to declare that this test runs with
 *  Spring JUnit 4 Runner.
 *
 *  Next in order to trigger the loading of a test context, we'll @ContextConfiguration. By default without specifying a
 *  class of to location as an annotation element, Spring will look for classes annotated with @Configuration in this
 *  class (Current class). Thus we can just set a @Configuration static class inside the test class.
 * */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WeatherServiceTest {

    // Entry 47: Autowired a service

    @Autowired
    private WeatherService service;

    // Entry 47: setting a @Before for Location

    private Location loc;
    private Weather weather;

    // Entry 47: making static final Error field

    private static final double ERROR_GEO = 0.0000001;
    private static final double ERROR_TIME = 5000;// in milliseconds

    @Before
    public void setup(){
        loc = new Location(41.9403795, -87.652180499999);
        weather = service.findByLocation(loc);
    }

    /** Entry 46:make the configuration class inside the Test class to be use by @ContextConfiguration*
     * In here is the same as the other config class, since we want to use data properies from api.properties we add
     * a @PropertySource to this inner class.
     *
     * Next as in the config classes we need to initialize an Environment variable to gain the properties. also as in
     * the RetsTemplate method inside the app config I need to make a @Bean of restTemplate. This is also tricky since
     * the option to copy paste the code of the same restTemplate @Bean on app config class is not practical.
     *
     * The most practical way is to modify the restTemplate method in the app config to produce a static method that can
     * be instantly used to make a @Bean by this test class and also api config class.
     *
     * After modifying the restTemplate and build a defaultRestTemplate we now can just call that method to create a
     * RestTemplate @Bean
     *
     * Next we also need to build a @Bean of WeatherService since in the actual run defined in com.mooracle.Application
     * the weather service was scanned by its @Service to then instatiated as service with all of its properties. Thus
     * since this case we cannot use the Application run to do this we need to define our own WeatherService for the test
     * as like implemented in Wether test implementation. Note that all properties name is the same as in the
     * WeatherServiceImpl parameters since it is initializing it!!
     *
     * There is the preparation prior to the real test! Next we will write the code for the @Test
     */

    @Configuration
    @PropertySource("api.properties")
    public static class TestConfig{
        @Autowired
        private Environment env;

        @Bean
        public RestTemplate restTemplate(){
            return AppConfig.defaultRestTemplate();
        }

        @Bean
        public WeatherService weatherService() {
            WeatherService service = new WeatherServiceImpl(
                    env.getProperty("weather.api.name"),
                    env.getProperty("weather.api.key"),
                    env.getProperty("weather.api.host")
            );
            return service;
        }
    }

    /** Entry 47: Testing With Our Test Context com.mooracle.service.WeatherServiceTest
     * Now that we already set the Test Context (in this case TestConfig) we are ready to begin our test. NOTE This tests
     * is not to determine if the API connected to this service is indeed working. It is to test that the handling of
     * data to and from that (simulated) API indeed working!!
     *
     * First we need to @Autowired the Service in the field declaration in this test we need to use WeatherService
     *
     * Then for the first test we need to see if the findByLocation method will returns the same coordinates. This is
     * the only method WeatherService interface has. It will need Location as parameter and Weather as returned.
     * However, in this test we will just want to make sure in the data fetched from API indeed has the same coordinate
     * as the Location parameter.
     *
     * In order to do this (mind you we do not use any internet connection to the API) we need to set the coordinates in
     * an object Location. Thus we need to set a @Before for that.
     *
     * We also need to setup @Before for the Weather object that will be used as return of this unit test.
     *
     * In the @Test we will asseert two things (latitude and longitude. In the com.mooracle.service.dto.weather.Weather
     * we can see we can get those latitude and longitudes. We will test if the latitude and longitude from Weather is
     * the same with latitude and longitude from Location loc using closeTo method that requires ERROR margin to work.
     * Thus we need to determine how close is the ERROR-GEO margin for this test in a static final field declaration!
     *
     * This ERROR_GEO will be used in the closeTo part of the expected Hemcrest assertThat. This will be a static method
     * imported FROM : matchers.closeTo
     * */

    @Test
    public void findByLocation_ShuouldReturnSameCoords() throws Exception{
        assertThat(weather.getLatitude(), closeTo(loc.getLatitude(),ERROR_GEO));
        assertThat(weather.getLongitude(), closeTo(loc.getLongitude(), ERROR_GEO));
    }

    /** Entry 47: Testing With Our Test Context com.mooracle.service.WeatherServiceTest
     * Next we need to test that the findByLocation call will return 8days of forecast data.
     *
     * In this @Test we will use another Hemcrest feature called hasSize. In this case we will assert that a data from
     * getDaily com.mooracle.service.dto.weather.ForecastData object has getData() method that returns a List of
     * forecast. Thus we can assert the size it should be. Remember Hemcrest put the should be after the actual, thus
     * that hasSize can be read better like getData should hasSize of 8.
     * */

    @Test
    public void findByLocation_ShouldReturn8DaysForecastData() throws Exception{
        assertThat(weather.getDaily().getData(),hasSize(8));
    }

    /** Entry 47: Testing With Our Test Context com.mooracle.service.WeatherServiceTest
     * Next we should test if the findByLocation will return the Current Conditions.
     *
     * In this test we need to see that the result weather data has the time stamp closeTo the current time stamp.
     * Once again we use closeTo meaning we need to set another static final ERROR this time in milliseconds unit.
     *
     * To test this first we need the current time. This can be achieved by grabbing an Instant object of time called
     * Instant.now();
     *
     * Then we will calculate the difference between now and the timestamp of the weather data. But in this case the
     * difference is called Duration (the sama as Instant it's a java.time object). Please NOTE we need to convert it
     * to milliseconds (in this code is toMillis) since the Duration calculate in Epoc unit.
     *
     * Lastly we will assertThat the Duration is closeTo zero with margin of error called ERROR_TIME
     * */

    @Test
    public void findByLocation_ShouldReturnCurrentConditions() throws Exception{
        Instant now = Instant.now();

        double duration = Duration.between(now, weather.getCurrently().getTime()).toMillis();

        assertThat(duration, closeTo(0,ERROR_TIME));
    }

}