package com.mooracle.web.controller;

import com.mooracle.domain.Favorite;
import com.mooracle.service.*;
import com.mooracle.service.dto.geocoding.PlacesResult;
import com.mooracle.service.dto.weather.Weather;
import com.mooracle.service.resttemplate.PlacesService;
import com.mooracle.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

// import static packages:
import javax.servlet.http.HttpServletRequest;

import static com.mooracle.web.FlashMessage.Status.FAILURE;
import static com.mooracle.web.FlashMessage.Status.SUCCESS;
import static com.mooracle.web.ReferrerInterceptor.redirect;

/** Entry 38: com.mooracle.web.controller.FavoriteController
 *  1.  This class controller handles Favorite management (indexing, details, add, remove)
 *  2.  This class also handles Exceptions which will be related to flash messages (SUCCESS, FIALURE, INFO)
 *  3.  Those flash messages are not initialized in this class and part of an enum inside a class thus we need to import
 *      those into static field
 *  4.  Also we need to import ReferrerInterceptor as static field to monitor the redirect syntax
 *  5.  We also learn about FlashMap and the link to learn more from the docs is in the README
 *  6.  Also comes with FlashMap there also RequestContextUtils link to learn more in README
 *  7.  The new concept here also on @ExceptionHandler which link to docs is in README
 * */

@Controller
public class FavoriteController {

    // initialize FavoriteService interface implementation

    @Autowired
    private FavoriteService favoriteService;

    //initialize PlacesService interface

    @Autowired
    private PlacesService placesService;

    //initialize WeatherService interface

    @Autowired
    private WeatherService weatherService;

    /** Notes:
     * This method will handles all request to index page of all favorites places for one particular user. To access it
     * the user must pass authentication to ensure that the current user has sufficient authorization before proceeds
     * to the /favorites page. To do that we need @PreAuthorize the user.
     *
     * If the user has sufficient authorization then app will call findAll() method to list all favorites owned by the
     * user. Then the app will render it to the view. The view template used is templates/favorite/index.html.
     *
     * For the new concept of @PreAuthorize can be followed in the README. The main hasRole method is only available in
     * Tomcat and Spring Security libraries.
     *
     * If we only look at the code the /favorites URI only index all favorites for current logged user with no weather
     * information whatsoever.
     * */
    @RequestMapping("/favorites")
    @PreAuthorize("hasRole('ROLE_USER')") // inspect the authorization
    public String index(Model model){
        model.addAttribute("favorites", favoriteService.findAll());
        return "favorite/index";
    }

    /** Notes:
     * This method handles request to /favorite/{id} with id is the id for the one favorite of logged user. That {id}
     * field in the URL will be fetched using @PathVariable to gain Long type object called id.
     *
     * Using the newly fetched Long id the app can get the correct favorite object. Please note up until now the id used
     * to pick this favorite is the favorite object id in the database not the id of the real place of that favorite
     * object. Therefore to get the weather data we need to determine the place of that favorite object using its
     * placeId.
     * */
    @RequestMapping("/favorites/{id}")
    public String detail(@PathVariable Long id,
                         Model model){
        Favorite favorite = favoriteService.findById(id);

        // finding the place of the current favorite:

        PlacesResult placesResult = placesService.findByPlaceId(favorite.getPlaceId());

        // get weather forecast for that particular location (place)

        Weather weather = weatherService.findByLocation(placesResult.getGeometry().getLocation());

        // put all necessary model to be rendered in the view template

        model.addAttribute("favorite", favorite);
        model.addAttribute("weather", weather);

        return "favorite/detail";
    }

    /** Notes:
     * This method handles the addition of a new favorite by authorized user. Thus this method handles POST request
     * intended for /favorites. Because this is a POST request it is meant to change the database, in this case Create
     * new Favorite object. Therefore in the end the rendering view will be redirected to newly created favorite object
     * detail page.
     *
     * The main process here is the use of favoriteService save method. Then there will be redirect attribute that will
     * be passed into the URL where this method redirect its return value.
     *
     * The name of the flashAttribute redirected is set to be "flash" as used in the view template
     * */
    @RequestMapping(path = "/favorites", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String add(Favorite favorite,
                      RedirectAttributes redirectAttributes){

        favoriteService.save(favorite);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Added to Favorites", SUCCESS));

        return "redirect:/favorites/" + favorite.getId();
    }

    /** Notes:
     * This class handles Delete POST request to particular Favorite object deignated by {id} URL path. Thus we need to
     * fetch that @PathVariable to be passed as Long id object. The remove method can be accessed only by authorized
     * user
     *
     * The main process of course the favoriteService delete method which require us to pass the id of the intended
     * Favorite object to be deleted. After that since the intended favorite is deleted then redirect the process back
     * to the /index web page.
     * */
    @RequestMapping(path = "/favorites/{id}/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String remove(@PathVariable Long id,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request){

        favoriteService.delete(id);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Favorite deleted", SUCCESS));

        return "redirect:/favorites";
    }

    /** Notes:
     * This method specific handles Exception invoked when Favorite is not found when the user search it. This specific
     * exception has templates/error.html template to render the view. It looks like only passing the "ex" attribute to
     * the model map to be rendered in view. Looks like this method will handle each time an exception happens from
     * any web page it was invoked since all web page should have search form.
     *
     * This method related to the com.mooracle.service.FavoriteNotFoundException thus all exception processed by that
     * class will end up here. This will simplify the detail method above to omit the try catch mechanism to handle
     * exception when the intended favorite object is not available.
     * */
    @ExceptionHandler(FavoriteNotFoundException.class)
    public String notFound(Model model,
                           Exception ex){

        model.addAttribute("ex", ex);

        return "error";
    }

    /** Notes:
     * This method handles all errors related to the CRUD operation into the database from this controller. In particular
     * there are two of them here the add and remove methods in this class to be precise. Both method supposed to include
     * try catch mechanism to handle exceptions of the operation but by using this @ExceptionHandler they can omit the
     * try catch block to simplify the code.
     *
     * Note we need to include both com.mooracle.service.FavoriteSaveException and
     * com.mooracle.service.FavoriteDeleteException in this method since this method can handle all exceptions from
     * both services.
     *
     * In this method we will also include flash message that indicate this FAILURE status. Thus in both add and remove
     * methods above there are no scenario where FAILURE status is used since it will be handled here. When the flashMap
     * is not null meaning it was an error in the database processing we will put the flash message with FAILURE
     * */
    @ExceptionHandler({FavoriteSaveException.class, FavoriteDeleteException.class})
    public String databaseError(Model model,
                                HttpServletRequest request,
                                Exception ex){

        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);

        // if there is an error in database processing
        if(flashMap != null){
            flashMap.put("flash",
                    new FlashMessage(ex.getMessage(), FAILURE));
        }

        return redirect(request);
    }
}
