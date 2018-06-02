package com.mooracle.dao;

import com.mooracle.domain.Favorite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Entry 10: Create FavoriteDao interface
 *  1.  This interface is used to manage the Favorite database schema
 *  2.  Since Favorite can be utilized to many users it needs to add some functionality in the database ORM
 *  3.  Query and Modify are both functionality which need to be mapped first to user before relate it to Favorite object
 *  4.  Each addition of function have comments
 *  5.  In the case of modifying the Favorite of certain user it has to include @Transactional before saving
 *  6.  Please be careful in selecting the annotations especially @Transactional please refer to the import list
 *  7.  Some of the Querries use native SQL to control the database
 *  patch: fix the @Query for transactional Favorite f
 *  */

@Repository
public interface FavoriteDao extends CrudRepository<Favorite, Long> {
    //List all favorite places of one user entity currently logged in
    @Query("select f from Favorite f where f.user.id=:#{principal.id}")
    List<Favorite> findAll();

    //Finding the Favorite object based on the given placeId<- PATCHED
    @Query("select f from Favorite f where f.user.id=:#{principal.id} and f.placeId=:#{#placeId}")
    Favorite findByPlaceId(@Param("placeId") String placeId);

    //saving or updating the Favorite for current user logged in
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into favorite (user_id,formattedAddress,placeId) values (:#{principal.id},:#{#favorite.formattedAddress},:#{#favorite.placeId})")
    int saveForCurrentUser(@Param("favorite") Favorite favorite);

    //deleting existing favorite from logged in user
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from favorite where id=:#{#id} and user_id=:#{principal.id}")
    int deleteForCurrentUser(@Param("id") Long id);
}
