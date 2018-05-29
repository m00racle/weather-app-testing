package com.mooracle.service;
/** Entry 29: Creating FavoriteService.java interface and its Exceptions
 *  1.  This exception is thrown when the app fails to save a new created Favorite
 *  2.  It was runs in the runtime thus it will specific extends RuntimeException, please update
 *  3.  It will use RuntimeException platform to send error message when it happens
 * */

public class FavoriteSaveException extends RuntimeException {
    public FavoriteSaveException(){
        super("Failed to save favorite.");
    }
}
