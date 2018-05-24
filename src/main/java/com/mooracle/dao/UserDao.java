package com.mooracle.dao;

import com.mooracle.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Entry 8: Creating UserDao interface
 *  1.  This is the interface which managing the user database schema which requires @Repository
 *  2.  This interface extends CrudRepository which already has implementation in the Spring data repository
 *  3.  The implementation of the CrudRepository only add one other method findByUsername
 *  */

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
