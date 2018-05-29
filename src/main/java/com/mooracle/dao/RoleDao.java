package com.mooracle.dao;

import com.mooracle.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Entry 9: Creating RoleDao interface
 *  1.  This interface manages the database schema for Role
 *  2.  As we know from the domain Role @Entity model, the Role object does not have any column on its own
 *  3.  The Role object also does not have the functionality to be called independently
 *  4.  This interface does not need to add any method to the standard CrudRepository class
 *  */

@Repository
public interface RoleDao extends CrudRepository <Role, Long> {
}
