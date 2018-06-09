package com.mooracle.service;

import com.mooracle.dao.FavoriteDao;
import com.mooracle.domain.Favorite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Entry 45: Testing Services com.mooracle.service.FavoriteServiceTest
 * This will test the FavoriteService interface service. In this class we need to use access to the database. We do not
 * want that. Thus we need to mock some DAOs.
 *
 * In this test class we will have 1 test for findAll() method and 2 for findById() method (one for the found and one
 * for not found Favorite). Thus in this class there will be at least 3 @Test.
 *
 * We still using Mockito for this JUnit test thus we need @RunWith(MockitoJUnitRunner.class). We need the Mockito to
 * do Mock some of these fields:
 * 1. FavoriteDao
 * 2. Inject the Dao into the service. Please note that FavoriteService is an interface we need to instantiate the
 *      inject into FavoriteServiceImpl()
 *  Unlike in the controller tests we do not need to setup anything prior any test thus we don't need @Before here
 *
 *  Let's test the first @Test the findAll() method. This test should return two when the findAll() methods are called
 *  see the Notes on that @Test doc.
 *
 *  The second @Test will be findById with supplied id should just return one Favorite. Look on the @Test doc
 *
 *  The third @Test is whether findById will throws FavoriteNotFoundException if the Dao findOne returns null
 * */

@RunWith(MockitoJUnitRunner.class)
public class FavoriteServiceTest {

    @Mock
    private FavoriteDao dao;

    @InjectMocks
    private FavoriteService service = new FavoriteServiceImpl();

    /** Notes:
     *  In Arrange section of this test we will create a List of two Favorite Entities which just been instantiated but
     *  not filled any value (Remember Favorite @Entity jas default constructor thus we can just create one without
     *  supplying any parameters).
     *
     *  In the Assertion part we will use the traditional JUnit assertEquals since this process of mocking the Dao
     *  should return a List (Array List to be exact) of Favorite thus we can find out about its size()
     *
     *  Verify that FavoriteDao in this case called dao is indeed called
     * */

    @Test
    public void findAll_ShouldReturnTwo() throws Exception{

        // Arrange the preparation:

        List<Favorite> favorites = Arrays.asList(
                new Favorite(),
                new Favorite()
        );

        when(dao.findAll()).thenReturn(favorites);

        // Asserts

        assertEquals("findAll should return two favorites",2,service.findAll().size());

        //Verify

        verify(dao).findAll();
    }

    /** Notes:
     * We start by some insight of the FavoriteDao which is consist of many Query which are useless to this test. Please
     * remember since we are not really accessing the Dao we cannot exactly use the @Query since it needs to be in
     * runtime of the Dao.
     *
     * We will simulate a call to a single Favorite object in this case we will not use a single method from
     * FavoriteDao interface but we use the extension on CrudRepository Interface which has the findOne() method.
     *
     * The doc of that method is Retrieves an entity by its id. We don't care about the implementations rigth now but one
     * thing we must note we wont going to access it in reality. We just going to Mock it. Thus when this method called
     * we need to return a Favorite. Just one not more. We then can just create a New Favorite and return it to service.
     *
     * Note that we only return a single Favorite class object using default constructor with no added parameters
     *
     * In the Assert part we will use the hemcrest function (link to learn more in the README), to Assert that if
     * the service findById is called it will return instance of a Favorite class entity.
     *
     * The instanceOf method is part of Hemcrest package we use particularly the org.hamcrest.Matchers.instanceOf;
     * This hemcrest assertion is more readable since it place the actual value (service.findById(1L)) before the
     * expected result instance of Favorite class
     * */

    @Test
    public void findById_ShouldReturnOne() throws Exception{

        // Arrange

        when(dao.findOne(1L)).thenReturn(new Favorite());

        // Assert

        assertThat(service.findById(1L), instanceOf(Favorite.class));

        // Verify

        verify(dao).findOne(1L);
    }

    /** Notes:
     * This will thst if the method findById will throw Favorite Not Found Exception if Dao returns null
     *
     * Thus we need to ensure the @Test expected = FavoriteNotFoundException.class
     *
     * The in Arrange part we need to make sure dao mocked when called the findOne method it will always returns null.
     * Then we need to actually call that method through service.findById() with the id is the same as mocked
     *
     * Assertion is already done by using the @Test expected = FavoriteNotFoundException, thus we can just continue to
     * verify that dao.findOne(id) is indeed called;
     * */

    @Test(expected = FavoriteNotFoundException.class)
    public void findById_ShouldThrowFavoriteNotFoundException() throws Exception{

        // Arrange

        when(dao.findOne(2L)).thenReturn(null);
        service.findById(2L);

        // verify

        verify(dao).findOne(2L);
    }
}