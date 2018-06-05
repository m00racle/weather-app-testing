package com.mooracle.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Entry 43: com.mooracle.web.controller.WeatherControllerTest
 *  1.  This test is generated from the class WeatherController page and goto > test as in the previous Unit Testing
 *      course
 *  2.  Please note that in the reference it was still using com.mooracle but this is inside the test directory not main
 *  3.  The controller is the first layer to test and we will moving on towards service and then dao layers.
 *  4.  We need to set mock environment to test what will happen when "/" is called (in the controller it only render
 *      weather/detail template)
 *  5.  We focus more on Unit testing not integration testing thus the likes of ApplicationContext test that loads the
 *      Bean definitions in the beginning of app initialization is not our concern
 *  6.  Thus for this test we will make a fake MVC object to do the process that usually takes the config package to do
 *  7.  Please take a minute to learn the import static packages with all * operators meaning all methods
 *  8.  We use it in the homeShouldRenderDetailView method to make get (which is the member of the MockMvcRequestBuilders
 *      package can be written as short as get) and also view() (part of the MockMvcResultMatchers package)
 *  9.  To run any test just open the page the test you want to run, focus on the class test and press ctrl+shift+F10
 *      or just right click the class name and choose run test
 *  Additional Notes available in README
 * */
public class WeatherControllerTest {
    // mock MVC Object
    private MockMvc mockMvc;
    private WeatherController controller;

    /** Notes:
     * Here in this @Before method we initialize the controller that will be tested and prepare mock MVC object to
     * by pass the ApplicationContext operation. To do this I need a MOckMvcBuilders and choose standaloneSetup.
     *
     * By using this mocked MVC object we can add some simulation on how will a HTTP request is performed full with its
     * attributes and then we can test the response matchers.
     * */

    @Before
    public void setup(){
        controller = new WeatherController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /** Notes:
     * we will test whether there is a request for "/" URL the controller will return the correct template String which
     * is the "weather/detail"
     *
     * plase note some code such as get and view() it is part of two import static packages listed above. This is the
     * way to shorten the code ie: from MockMvcRequestBuilders.get("/") to just get("/"). Same case applies for view()
     * */

    @Test
    public void homeShouldRenderDetailView() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(view().name("weather/detail"));
    }

    /** Notes:
     * This test that the calls to the URL "/search" will always carries a parameter called q. That q then will be part
     * of the redirecting URL as "/search/{q} in the unicode form.
     *
     * In this case if I search yogyakarta in the rendered page the request will carry yogyakarta as param named q which
     * has value "yogyakarta" stored in it. Then it will invoke redirection to "/search/yogyakarta"
     * */

    @Test
    public void searchShouldRedirectWithPathParam() throws Exception {
        mockMvc.perform(get("/search").param("q","yogyakarta"))
                .andExpect(redirectedUrl("/search/yogyakarta"));
    }
}