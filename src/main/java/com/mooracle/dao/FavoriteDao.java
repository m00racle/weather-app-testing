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
 *
 * Entry 48:
 *  Spring Data JPA Repository allows us to create an interface that extends other such as CrudRepository. When we fire
 *  up our app, Spring Data will generate the implementations of these repositories or known as DAO.
 *
 *  In this project, I have no explicit defined implementation class for this interface. This implementation will be
 *  provided by Spring Data when the app is booted.
 *
 *  When we checked the CrudRepository interface it has all the method we need here findAll, findOne, save, delete. But
 *  I can't find the annotation definition from it. To learn about those annotation there is a link in the README.
 *
 *  In testing we must remember that we don't have to test out our dependencies in this case CrudRepository interface.
 *  It was already done by their developers. However, we need to test all the methods we modified. In this case:
 *      1.  findAll()
 *      2.  findByPlaceId(String PlaceId)
 *      3.  saveForCurrentUser
 *      4.  deleteForCurrentUser
 *  All of which uses Spring JPA annotations such as @Query, @Modifying, and @Transactional (more about these annotations
 *  there is a link in the README)
 *
 *  Now let's create test for this interface!
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
