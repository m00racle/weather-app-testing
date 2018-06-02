package com.mooracle.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/** Entry 40: com.mooracle.config.DataConfig
 *  1.  This class configure Data container for entities, Datasource which properties in application.properties
 *  2.  This class also configure the Hibernate and Java Persistence API (JPA) transaction manager
 *  3.  In order to fetch all properties we need to instantiate Environmern interface field from Spring core env
 *  4.  In order to load JPA propertise we need to enable JPA repositories
 *  5.  All of the methods in this class will be contained as Java @Bean thus can be instantiated for each method as one
 *      object consist of all objects intantiated inside it.
 *  6.  The final objective is to wrap all methods to form transaction manager that manage CRUD operation. Thus all
 *      methods needed have to be a @Bean to goin additional method defined inside the class such as getObject().
 *  Note: This is the function of Java @Bean to add some method to use like a POJO in this case such as getters and
 *      setters
 * */

@Configuration
@EnableJpaRepositories(basePackages = "com.mooracle.dao")
@PropertySource("application.properties")
public class DataConfig {
    @Autowired
    private Environment environment;

    /** Notes:
     * This method we need to config the location of the entitites. Please refer to the properties name that mentioned
     * in application.properties. The entity domain is in weather.domain.package.
     *
     * this entity must be configured to use specific JPA properties., in this case we use Hibernate as JPA for the
     * entities. Therefore we need to instantiate first vendor JPA adaptor from hibernate.
     *
     * patch: refactor to rename the method name to entityManagerFactory from entityManagerFactoryBean since the name
     * is mandatory for com.mooracle.service.UserServiceImpl class
     * */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        // define the Hibernate JPA adapter

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        // settings for the Entity JPA Hibernate
        factoryBean.setDataSource(dataSource()); //<- crete the dataSource method @Bean after this
        factoryBean.setJpaVendorAdapter(vendorAdapter);


        // settings for entity location (domain)
        factoryBean.setPackagesToScan(environment.getProperty("weather.domain.package"));

        factoryBean.setJpaProperties(getHibernateProperties());//<- make the method getHibernateProperties after this

        return factoryBean;
    }

    /** Notes:
     * This class defines all properties for Hibernate. This class put all properties rather than set it up.
     * Also this class is not being contained into Java @Bean because when we put we add into one all object.
     * The content if this method is what used to be formed in the form of hibernate.cfg.xml thus we don't need to make
     * it into a @Bean
     * */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        // put all necessary properties

        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.implicit_naming_strategy",
                environment.getProperty("hibernate.implicit_naming_strategy"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

    /** Notes
     * this is the dataSource() merthod that is called by entityManagerFactory method. This method is a @Bean object
     * consist all properties of the dataSource management. This config method basically settings for database:
     *
     * - database JPA driver
     * - url settings that determine what kind of database is used (see more on the application.properties)
     * - username and password
     *
     * patches: changes the private declaration to public since this is mandatory for a @Bean method to be public
     *
     * */
    @Bean
    public DataSource dataSource() {// <-patched
        BasicDataSource dataSource = new BasicDataSource();

        // set database driver:

        dataSource.setDriverClassName(environment.getProperty("weather.db.driver"));

        // set url to the database:

        dataSource.setUrl(environment.getProperty("weather.db.url"));

        // set username and password

        dataSource.setUsername(environment.getProperty("weather.db.username"));
        dataSource.setPassword(environment.getProperty("weather.db.password"));

        return dataSource;
    }

    /** Notes:
     * All necessary methods have been declared and now it's time to wrap all up. The main objective is to form
     * transaction manager to handle the CRUD operation to database. In this case we use JPA as transaction manager
     *
     * patch: add @Bean to mmake this JpaTransactionManager as a Java Bean
     * */
    @Bean //<-patched
    public JpaTransactionManager transactionManager(){
        // instantiate the transaction manager

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        // wrap the entity manager bean

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        // wrap the data source bean

        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }
}
