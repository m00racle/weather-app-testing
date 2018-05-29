package com.mooracle.service;

import com.mooracle.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/** Entry 27: Creating UserService.java interface
 *  1.  This interface specify to find User entity object by submittig username as key word.
 *  2.  This interface will be implemented in the same package as the interface
 *  3.  This interface extends UserDetailsService class from Spring Security
 * */
public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
