package com.mooracle.service;

import com.mooracle.dao.FavoriteDao;
import com.mooracle.domain.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Entry 30: Creating FavoriteServiceImpl implements FavoriteService Interface
 *  1.  This class implements FavoriteService interface please implement all methods
 *  2.  Please mind the exceptions
 *  3.  It the same as UserServiceImpl basically manages database concerning Favorite Entity object
 *  4.  Thus we need to @Autowired FavoriteDao
 * */

@Service
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public List<Favorite> findAll() {
        return favoriteDao.findAll();
    }

    /** Patched:
     * This method was patched for structure on throwing FAvoriteNotFoundException()
     * */
    @Override
    public Favorite findById(Long id)  {
        Favorite fave = favoriteDao.findOne(id);
        if(fave == null){
            throw new FavoriteNotFoundException();
        }
        return fave; // <-changed
    }

    @Override
    public void save(Favorite favorite) {

        // only save when there is really a User logged in and really has access to favorite
        if(favoriteDao.saveForCurrentUser(favorite) == 1){
            // we will change it with new location to be choses as new favorite
            Favorite newFavorite = favoriteDao.findByPlaceId(favorite.getPlaceId());
            // update the new place favorite by setting its new placeId
            favorite.setId(newFavorite.getId());
        } else {
            throw new FavoriteSaveException();
        }
    }

    @Override
    public void delete(Long id) {

        // if the intended favorite is not present or user does not have access
        if(favoriteDao.deleteForCurrentUser(id) < 1){
            throw new FavoriteDeleteException();
        }
    }
}
