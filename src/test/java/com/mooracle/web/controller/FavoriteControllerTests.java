package com.mooracle.web.controller;

import com.mooracle.domain.Favorite;
import com.mooracle.service.FavoriteNotFoundException;
import com.mooracle.service.FavoriteService;
import com.mooracle.web.FlashMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.mooracle.domain.Favorite.FavoriteBuilder;
import static org.mockito.Mockito.*;


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

    /** Notes:
     * here we are going to test what happen if the call to URI /favorites does occur:
     *  1.  The response status should be 200 (OK)
     *  2.  The render view should be name favorite/index
     *  3.  The model has "favorites" list attribute
     * */

    @Test
    public void index_ShouldIncluidesTheFavoritesInTheModel() throws Exception{

        // Arrange the mock behavior

            // prepare the List to be fed into model. Remeber Favorite @Entity has builders method we should also import
        // static it to simplify the coding

        List<Favorite> favorites = Arrays.asList(
                new FavoriteBuilder(1L).withAddress("Chicago").withPlaceId("Chicago1").build(),
                new FavoriteBuilder(2L).withAddress("Singapore").withPlaceId("Singapore1").build()
        );

        // Then we call the FavoriteService object in this test called service to do its job returns the favorites list

        when(service.findAll()).thenReturn(favorites); //<- we already import static Mockito

        // Act (perform the MVC request) and Asserts results

        /* perform to get "/favorite" as defined in the @RequestMapping for index method and then it should
        * 1.    Return 200 (OK) status
        * 2.    Choose favorite/index as template
        * 3.    Has "favorites" as attribute in its model
        * */

        mockMvc.perform(get("/favorites"))
                .andExpect(status().isOk()) //<- status is 200
                .andExpect(view().name("favorite/index")) // view template name chosen favorite/index
                .andExpect(model().attribute("favorites",favorites));

        /* Notes:
        * That the last assertion checks only the favorites List is really inside the model not that the favorites is
        * from the service. We need to verify if the favorites really called from the service. Since Mockito is already
        * in the import static list thus we just going to code verify not Mockito.verify
        * */

        verify(service).findAll();
    }

    /** Notes:
     * In this test we wil see if when the add method (post "/favorite") is called these things happens:
     *  1.  The service.save method is called to save the new Favorite
     *  2.  It will send a flash message
     *  3.  It will redirect the result to /favorites/{id}
     *
     * In this method we will test a save method from the service. To mock a void method is a bit different compared to
     * the returnuing method like in the test method above. We will use Mockito.doAnswer method and inside it we use a
     * lambda to describe the save method from favoriteService to be called. This lambda is possible because doAnswer is
     * Mockito interface with a single method. see more on the mockito link in the Teacher's notes
     *
     * The doAnswer is using lambda to invoke save method from the FavoriteService interface which only has one
     * parameter passed into it which is a Favorite object. This is a little bit tricky to make since invocation will
     * require us to make a Favorite object casted into invovation object which has argument in the position 0. Then
     * since we want to test if the id is passed into the redirect we need to set the Favorite object id. Then lastly
     * we return null since the lambda require us to return something. Null will be accepted just fine by Mockito
     *
     * After finishing the lambda we need to set when the doAnswer is used. That is when the service object called save
     * method on any Favorite.class object
     *
     * As the Act and Assert part is a little bit complex since we will use POST method. Fisrt we define the POST and
     * the URI of the POST. Then we must also define the parameters of a favorite such as formattedAddress and placeId.
     * The Favorite Id itself is already set in the doAnswer part.
     *
     * As always it is best practice if we verify that service.save(any(Favorite.class)) does indeed called during the
     * test.
     * */

    @Test
    public void add_ShouldRedirectToNewFavorite() throws Exception{

        // Arrange the mock behavior

        doAnswer(invocation -> {
            // creating and casting Favorite object

            Favorite f = (Favorite)invocation.getArguments()[0]; //<- get argument at index 0

            //set the f (Favorite) id to be tested later

            f.setId(1L);//<- set the id as 1 the L is because the number format must be Long


            return null;
        }).when(service).save(any(Favorite.class));//<- do this when service.save is called for any Favorite class object

        // Act (perform the MVC request) and Asserts results

        mockMvc.perform(
                post("/favorites")
                    .param("formattedAddress", "chicago, il")//<- you can put anything here
                    .param("placeId","chicago1")
        ).andExpect(redirectedUrl("/favorites/1")//<- remember we set the id to 1L
        ).andExpect(flash().attributeExists("flash"));
        verify(service).save(any(Favorite.class));//<-verify if service save method is indeed called

    }

    /** Notes:
     * we normally use the JUnit expected exception test like in the basic unit testing. For this test we will expect that
     * the FavoriteNotFoundException (see the FavoriteController.java) will be invoked. However, there is one big
     * problem here.
     *
     * In the FavoriteController class we can see that the methods inside it do not explicitly has try catch block to
     * handle such FavoriteNotFoundException. This kind of try catch Exception is handled by Spring using annotation
     * in the method that handles this specific exception @Exceptionhandler. Thus the exception itself will only passed
     * in the Spring runtime and never made into the JUnit test run. Thus we cannot just simply simulate not found in the
     * test. Thus the expected attribute in @Test will not going to do any good.
     *
     * We can however, use the model that contain attribute "ex" that bears the exception.
     *
     * Thus we just ask the mock when service.findById(just fill any id) then throw FavoriteNotFoundException class.
     * Since we do not make any Favorite in the mock behavior it should throw that specific Exception.
     *
     * Next we just Act and Assert by performing Get request to "/favorites/1" and Asser by expecting view name will be
     * "error" (see in the FavoriteController) that is the name of the returned view from notFound method.
     * And then the model does contains attribute called "ex" which are (please Note) instanceOf(FavoriteNotFound
     * Exception class). This instaceOf method is part of the org.hamcrest.Matchers package not Mockito!
     *
     * Once again it will be a best practice to verify that the service.findById(1L) is indeed called during the test run
     * */

    @Test
    public void detail_ShouldErrorOnNotFound() throws Exception{

        // Arrange the mock behavior

        when(service.findById(1L)).thenThrow(FavoriteNotFoundException.class);

        // Act (perform the MVC request) and Asserts results

        mockMvc.perform(get("/favorites/1"))
                .andExpect(view().name("error"))
                .andExpect(model().attribute("ex",
                        org.hamcrest.Matchers.instanceOf(FavoriteNotFoundException.class)));

        // verify that service.findById was called
        verify(service).findById(1L);

    }

    /** Notes:
     * This @Test the remove method called by POST "/favorites/{id}/delete" will invoke calls to FavoriteService.delete
     * method. Then it will create flash attribute called "flash" and redirect to "/favorites"
     *
     * First we need to mock the service.delete(Long id) call. Here we will set it to call it for any Long type number
     * as id. This is to simplify the process. In the delete method there is void return thus nothing to set here since
     * basically designated favorite object is just missing. Just return null!
     *
     * Then all the rest is internal process to test including the flash attribute, and redirect. But we need to verify
     * that indeed a call to service.delete(Any(Long.TYPE number)) really did happen during the process.
     * */

    @Test
    public void remove_ShouldRedirectToFavoriteIndex() throws Exception{

        // Arrange the mock behavior (Basically no settings needed in delete right?) but still it void thus doAnswer

        doAnswer(invocation -> {
            return null;
        }).when(service).delete(any(Long.TYPE));

        // Act, Asserts
        mockMvc.perform(post("/favorites/2/delete"))
                .andExpect(redirectedUrl("/favorites"))
                .andExpect(flash().attributeExists("flash"));

        // verify
        verify(service).delete(any(Long.TYPE));
    }
}