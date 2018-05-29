package com.mooracle.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Entry 29: Creating FavoriteService.java interface and its Exceptions
 *  1.  This is the Exception for Not found Favorite case
 *  2.  This Exception will send also HTTP status message NOT_FOUND (I forget the code)
 *  3.  Since it will also send @ResponseStatus we need the annotations
 *  4.  Also it will send a error message in the runtime exception thus please update the extends
 * */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FavoriteNotFoundException extends RuntimeException {

    // method of using the RuntimeException (super) to send message
    public FavoriteNotFoundException(){
       super("Favorite is not found.");
    }
}
