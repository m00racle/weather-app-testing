package com.mooracle.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mooracle.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;
/** Entry 48: Setting Up DBUnit for DAO Tests
 * These @Test will involve a bit more setup for couple of reasons:
 *  1.  Our DAO methods have custom queries that inject authenticated user data (where f.user.id=:#{principal.id})
 *      The principal is coming from Spring Security. Essentially this authenticate user's ID that's getting injected as
 *      a Query.
 *
 *      Example:    if I call FavoriteDao findAll() method I don't want it to return all entities that are saved in the
 *                  favorite table. Instead I want to select all favorites whose user is currently the authenticated user.
 *                  Remember in the com.mooracle.domain.Favorite we already stored the user_id column which containing
 *                  the com.mooracle.domain.User object.
 *                  Thus to get its User's credential the id is the best since it was Primary key meaning no other key
 *                  is the same. Thus we compared one condition that f(favorite table).user.id is the same as current
 *                  authenticated user called principal's .id
 *  2.  In order to make the authentication needed in point 1 we'll need that user datailed service as @Bean. Then we
 *      also need to make @Entity com.mooracle.domain.User object mapped by Hibernate. As well as
 *      com.mooracle.domain.Favorite also need to be mapped by Hibernate.
 *  3.  Since we use Spring Data we also need to generate those implementations as well.
 *
 *  All of those reasons above are the reasons we technically will make this an integration test by having the test to
 *  create an Application Context (learn more about this in README). By doing this we'll have our entire app @Bean
 *  available when we need them.
 *
 *  When using Spring data, this is often a reasonable approach since generating implementations for those repositories
 *  would be unreasonably time consuming.
 *
 *  Also it is part of this test to ensure those implementations are indeed generated correctly. But first we need to
 *  specify this test class to @RunWith Spring JUnit runner
 *
 *  To make an integraation test first we need to add @SpringApplicationConfiguration class which is already deprecated
 *  we use @SpringBootTest now (more on this there is a link in README) which by default should find all @Configuration
 *  to be implemented into this test
 *
 *  Next thing we'll have to tackle is how to start with a pre-existing set of data. There are vaious tools out there to
 *  help us with this but we will use DB unit. We'll use it to inject a set of starter data into our database so that
 *  we have some users, roles, and favorites ready to go when we want to test.
 *
 *  To do this we need to use DBUnit @DatabaseSetup and specify where the file that contains our test data will be
 *  resides. In this case it will be in the classpath:favorites.xml which we have not build yet.
 *
 *  This tells DB unit to inject the reference data into our in memory test database (which is an H2 database) before
 *  starting our tests. NOTE: we can apply the @DatabaseSetup to individual methods too, but applying it to the class
 *  will inject the reference data before each test method, kinda like a setup @Before method.
 *
 *  Next we add @TestExecutionListeners (description on README) which will provides a couple test execution listeners,
 *  DependencyInjectionTestExecutionListener and DBUnitTestExecutionListener.
 *
 *  According to Spring doc a test execution listener defines a listener API for acting to test execution of events. We
 *  need this DependencyInjectionListener so that our @Autowired dependencies can be injected from the loaded
 *  application context.In this case we need to inject @Autowired FavoriteDao as Dao under test.
 *
 *  As for the DbUnitTestExecutionListener we need it to allow us to inject data to our database.
 *
 *  Okay last setting before we start to write the code to our tests is to build the favorite.xml. This is resides in
 *  classpath meaning inside the resource directory. But we want it only available for test thus we need to build it
 *  not in /home/idegroup/IdeProject/weather-app-testing/src/main/resources but rather in
 *  /home/idegroup/IdeProject/weather-app-testing/src/test/resources or the one resource directory under test not main!
 *
 * */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DatabaseSetup("classpath:favorites.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class FavoriteDaoTest {
    @Autowired
    private FavoriteDao dao;
}