package com.mooracle.service;

import com.mooracle.config.AppConfig;
import com.mooracle.service.resttemplate.weather.WeatherServiceImpl;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

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

    /** make the configuration class inside the Test class to be use by @ContextConfiguration*
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
}