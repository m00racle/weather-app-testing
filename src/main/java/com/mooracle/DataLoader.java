package com.mooracle;

import com.mooracle.dao.RoleDao;
import com.mooracle.dao.UserDao;
import com.mooracle.domain.Role;
import com.mooracle.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** Entry 43: com.mooracle.DataLoader
 * 1.   This class required to input initial User object to be loaded to activate the /login
 * 2.   This class has only required to create a Role
 * 3.   Then put that role into user named "user" and has password of "password"
 * 4.   Then set the role and save the user to database
 * 5.   To do all of this this class must implements ApplicationRunner interface.
 * 6.   That interface have only one method to be implemented and @Override
 * */

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /** Notes
     * In this method we defined the name "ROLE_USER" then save it onto Role database to be then used by the newly
     * created user object.
     * */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        // create new role and save it into database

        Role role = new Role("ROLE_USER");
        roleDao.save(role);

        // set the role into a user

        User user = new User("user", "password");
        user.setRole(role);
        userDao.save(user);
    }
}
