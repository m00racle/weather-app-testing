package com.mooracle.service;

import com.mooracle.domain.Favorite;

import java.util.List;

/** Entry 29: Creating FavoriteService.java interface and its Exceptions
 *  1.  This interface will serve as database management for favorite click and choices
 *  2.  It will find the favorite by id from database or throw exception if it does not exist
 *  3.  It will save created favorite or throw exception if fails to do so
 *  4.  It will delete selected favorite (by id) or throw exception if fails to do so
 *  5.  Each exception will be created following the method declarations
 *  6.  Also it will list all available favorite objects
 *  */
public interface FavoriteService {
    List<Favorite> findAll();
    Favorite findById(Long id) throws FavoriteNotFoundException;
    void save(Favorite favorite) throws FavoriteSaveException;
    void delete(Long id) throws FavoriteDeleteException;
}
