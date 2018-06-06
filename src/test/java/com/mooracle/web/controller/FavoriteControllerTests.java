package com.mooracle.web.controller;

import com.mooracle.service.FavoriteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/** Entry 44: com.mooracle.web.controller.FavoriteControllerTest
 *  1.  This tests the FavoriteController class and the usage of mock procedures to isolate the unit test
 *  2.  The use of a Unit test need to focus the test only on the FavoriteController class others such as services must
 *      not be included in the test since it will have its' own integration tests.
 *  3.  Thus we need to include mocking some of the services components to do what a services layer should do.
 *  4.  To use Mockito to mock some of the components we need to add @RunWith in the class declaration
 *  5.  Choose MockitoJUnitRunner.class as Run with since we use Mockito for JUnit testing
 *  6.  More on how to set Mockito in the link in the README file
 *  7.  Note we already put some import static packages to assist with the simplification of the codes. Some already
 *      mentioned in previous WeatherControllerTest
 *  8.  In this test we also still using 3A principle in the basic JUnit testing course
 * */

@RunWith(MockitoJUnitRunner.class)
public class FavoriteControllerTests {
    // preparing the fields to be initialized

    private MockMvc mockMvc;

    @InjectMocks
    private FavoriteController controller; // we initialize it using Mockito since we want to inject service into it

    @Mock
    private FavoriteService service; // service will be injected to controller field

    /** NOtes:
     *  As always we need to prepare everything before the actual testing. In this @Before method we only need to
     *  prepare mockMvc since the controller field is already initialized by Mockito library. Please note that the way
     *  it initialize mockMvc is the same as in the WeatherControllerTest class. @Before method
     * */

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // tests:

    @Test
    public void index_ShouldIncluidesTheFavoritesInTheModel() throws Exception{

        // Arrange the mock behavior

        // Act (perform the MVC request) and Asserts results

    }

    @Test
    public void add_ShouldRedirectToNewFavorite() throws Exception{

        // Arrange the mock behavior

        // Act (perform the MVC request) and Asserts results

    }

    @Test
    public void detail_ShouldErrorOnNotFound() throws Exception{

        // Arrange the mock behavior

        // Act (perform the MVC request) and Asserts results

    }
}