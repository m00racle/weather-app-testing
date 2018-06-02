package com.mooracle.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mooracle.service.TimestampDeserializer;
import com.mooracle.web.ReferrerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; //<-learn more

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/** Entry 39: Create com.mooracle.config.AppConfig
 *  1.  This class configure the Application as a whole. The app itsel consist mainly on external API management
 *  2.  Thus it is normal to have REST templated configured here,
 *  3.  Next this is web app thus it will need to configure MVC (model, view, controller) platform for this web app
 *  4.  The most intriguin is the TimeStampDeserializer which is part of REST template.
 *  5.  This configuration class will also @Override interceptors using the ReferrerInterceptor in the web package.
 *  6.  The properties used in this class as expected comes from api.properties since we deal mostly on external API
 *  7.  The WebMvcConfigurerAdapter is stated as deprecated by the Spring docs but it is not in IDEA so we still use it
 * */

@Configuration
@PropertySource("api.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
    // field declaration (please note the one wih @Value)

    @Value("${api.timeout}")
    private int timeout;

    // to resolve the ${} used in many @Value annotations

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    /** Notes:
     * After this we will configure REST template. In this method we will need to separate another object into a private
     * method that will be called by the restTemplate method in the process. Here we will be introduced to a
     * ObjectMapper that the link to learn more is in the README. At the moment the intention of this class is not yet
     * fully understand but for now we can say ObjectMapper provides functionality for reading and writing JSON. The
     * writing and reading processes are both from and to POJO also from and to JSON Tree Model.
     *
     * Here we also use SimpleModule class object to initialize module called timestampModule using
     * com.mooracle.service.TimestampDeserializer class. What it does is defining the time using Epoch and then
     * translate it into more readable format. Time is important in the forecast data which changes over time.
     * */
    @Bean
    public RestTemplate restTemplate(){
        // initialize Rest Template
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

        // set the Object Mapper

        ObjectMapper jacksonObjectMapper = new ObjectMapper(); //<- learn more

        // creating the timestampModule

        SimpleModule timestampModule = new SimpleModule(
                "TimestampModule",
                new Version(1,0,0,null,null,null)
        )
                .addDeserializer(
                        Instant.class,
                        new TimestampDeserializer()
                );

        // configure the Object Mapper

        jacksonObjectMapper.registerModule(timestampModule);
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        // Mapping the json message converter

        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(jacksonObjectMapper);

        // set the message converter in restTemplate object
        restTemplate.setMessageConverters(Arrays.asList(jsonMessageConverter));
        return restTemplate; //<-patched: change to restTemplate field not the method
    }

    /** Notes:
     * This method is created from the restTemplate method above. This method is intended to provide
     * ClientHttpRequestFactory object.
     *
     * The Factory will provide the timeout for connection and reading the Http request
     * */
    private ClientHttpRequestFactory clientHttpRequestFactory() {
        // initialize the Component Facotry

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // set the factory timeouts

        factory.setReadTimeout(timeout);
        factory.setConnectTimeout(timeout);

        return factory;
    }

    /** Notes:
     * This class is summoned using alt+insert and choose @Override methods.
     *
     * This method looks like put com/mooracle/web/ReferrerInterceptor.java as interceptor for Http servlet.
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReferrerInterceptor());
    }
}
