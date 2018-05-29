package com.mooracle.service.resttemplate;

import com.mooracle.service.dto.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

//we need to manually import static the uriEncode since it was used but not initialized
import static com.mooracle.util.WebUtils.uriEncode;

/** Entry 23: Creating REST RestApiService.java abstract class
 *  1.  This service will enforce RESTful principles in communicating with the external API.
 *  2.  Here we will use RestTemplate class which docs can be seen in this Entry README.
 *  3.  In this class we will use another model class inside class to have RequestBuilder
 *  4.  There are many new concept in code written inside the RequestBuilder inner class but basically it produce URI
 *  5.  The specification of the URI is what this class is intended to build
 *  6.  remember this is an abstract class meaning it needs other class to extends it
 *  7.  In the abstract class declaration there is generic used. This is still unclear what it does exactly find out more
 *  8.  We need to build com.teamtreehouse.util.WebUtils for uriEncode using static calls since when it is static
 *      uriEncode will be initialized when this class is compiled.
 *  9. The RestApiService generic extends Dto thus limit the Generic as objects in Dto package only
 * */

public abstract class RestApiService<T extends Dto> {
    //generics extends in the abstract class declaration

    @Autowired
    private RestTemplate restTemplate;

    // list of another abstract methods
    public abstract String getHost();
    public abstract String getApiKey();

    // another abstract class of the Generic T declared in the abstract class declaration above
    public abstract Class<T> getDtoClass();// <- patched

    // buiding the RequestBuilder, then choose alt+enter create inner class and change it into public for test
    protected RequestBuilder get(String uriTemplate) {
        return new RequestBuilder(uriTemplate);
    }

    /** This is the inner class to build the RequestBuilder method (Find out more on Java Builder Design Pattern)
     *  The inner class is intended to build a Request starts with the URI and added with parameters which mapped into
     *  a Map of String as key to String as value.
     * */
    public class RequestBuilder {

        // field declaration uriTemplate is a String
        private String uriTemplate;

        // field Map string to string or parameter name and its parameter value
        private Map<String, String> params = new HashMap<>();

        private boolean secure = true;

        // constructor
        protected RequestBuilder(String uriTemplate) {
            this.uriTemplate = uriTemplate;
        }

        // we start typical builder inner class
        public RequestBuilder param(String key, Object value){
            params.put(key, uriEncode("" + value));
            return this;
        }

        // for HTTPS the secure status will be true
        public RequestBuilder withHttps(){
            secure = true;
            return this;
        }

        // for the HTTP the secure must be off to ensure access
        public RequestBuilder withHttp(){
            secure = false;
            return this;
        }

        // execute the T generic
        public T execute(){
            params.put("key", getApiKey());

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(secure ? "https" : "http")
                    .host(getHost())
                    .path(uriTemplate)
                    .build()
                    .expand(params);
            String url = uriComponents.toUriString();
            return restTemplate.getForObject(url, getDtoClass());
        }
    }
}
