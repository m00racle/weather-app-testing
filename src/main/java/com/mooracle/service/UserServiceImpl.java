package com.mooracle.service;

import com.mooracle.dao.UserDao;
import com.mooracle.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Entry 28: Creating UserServiceImpl.java class implements UserService.java interface
 *  1.  This class implements UserService.java interface thus it must implements all of interface's methods
 *  2.  However, please remember that UserService.java extends the UserDetailsService interface from Spring security
 *  3.  The UserDetailsService interface will make this class also implements its methods.
 *  4.  This class basically just fetch user data from Dto package which in this case UserDao interface
 *  5.  As usual when @Autowired Spring will refer to the only one immediate implementation of the interface
 * */

@Service //patched
public class UserServiceImpl implements UserService{
    // instantiate Dto field for User data (UserDao interface)
    @Autowired
    private UserDao userDao;

    // Override added method of UserDetailsService which extended to UserService interface
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    // Override the method from UserDetailsService (please remember to throw exception when needed)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username); // bring out the selected User from method above
        if(user == null){
            throw new UsernameNotFoundException("User not available"); // when username is not available
        }
        return user; // load all user attributes and return it
    }
}
