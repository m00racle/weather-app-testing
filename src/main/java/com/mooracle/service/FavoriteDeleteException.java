package com.mooracle.service;
/** Entry 29: Creating FavoriteService.java interface and its Exceptions
 *  1.  This class will throw exception when the app failed to find the favorite by id to delete or failed to delete
 *      even when it manages to find the favorite
 *  2.  Same as other Exception this will onlu needed in runtime thus it will only use RuntimeException platform
 * */

public class FavoriteDeleteException extends RuntimeException {
    public FavoriteDeleteException(){
        super("Failed to delete favorite.");
    }
}
