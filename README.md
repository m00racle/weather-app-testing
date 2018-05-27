# weather App Prior Notes:
This project is used in the Treehouse workshop on Unit Testing a Spring App. The application is a Spring web app that 
uses two Google APIs and the forecast.io API.

## Getting Your API Keys
In order to use this application, you'll need to get a couple API keys. Here's how.
### Forecast.io
NOTE: forecast.io is now under the name darksky.net. It is still can be accessed into API developer page using the link 
below but beware for terms and conditions. 

Go to [Forecast.io Developer page](https://developer.forecast.io/register) 
and register with your email address. After registering, you'll see a page 
with an example URL that you can use to GET JSON data at an arbitrary latitude and longitude. Clicking that link will 
show you what the data looks like that our application will receive.

At the bottom of the page, you'll see your unique API key displayed. Copy and paste this into *api.properties* as the 
value for the property named `weather.api.key`.
### Google
While logged in to a Google account, navigate to [Google Developer Console](https://console.developers.google.com). 
From the menu, choose **Credentials**, click **Create credentials**, and select **API key**. When asked, choose 
**Server key**. At this point you'll see your key, which you should copy. Paste this key into *api.properties* as the 
values for the `geocode.api.key` and `places.api.key` properties.

At this point you need to enable the Geocoding and Places API, so that your API key grants you access to those APIs. 
Under the menu item **Overview**, you should see a categorized list of Google APIs. Under the **Google Maps** category, 
enable the following APIs:
 
  - Google Maps Geocoding API, and
  - Google Places API Web Service

# Weather App Project
Use this [Guthub Repo](https://github.com/treehouse-projects/spring-unit-test-weather) as reference

Also read this for the [REST API for Spring](https://spring.io/guides/gs/consuming-rest)

## Entry 1: Dependencies and Plugins settings
To do this we need to go to **build.gradle** to add dependencies and plugins. Please note that plugins will use the most
recent method of creating it. 

## Building the Resources 
Here we need to build the resources needed for this project. Since this is outside the scope of the project we need to 
just download or copy most of its contents except the properties files.

## Entry 2: Adding properties file for setting the database and hibernate
In this entry we already built **com.mooracle.domain** package where we will put our @Entity object later on thus it can
be put into the designated model package inside the applocation properties which later will be called from config.

The other thing is regarding the database location it will use the TCP based server with local host will be located in
the newly built **data** directory in the root. The mv database file will be named as weather. 

**HOWEVER** to include a test method for this case it will use memory based database so that it will not burden the 
testing process. Remember test must be **FAST** in order to be feasible to do. Thus by using the memory based the CRUD
based process in the database will be much faster.

## Entry 3: adding external API to be integrated in the app using a properties file
Because we need some access to utilize the external API thus we need to go to each API web page and follow instructions
in the [Weather App Prior Notes](#weather-app-prior-notes:).

API key for Google Geocoding : AIzaSyDjDl4nhne6nmlpT0EivknNy4gjIj9fSgA
API key for Google Places web : AIzaSyDjDl4nhne6nmlpT0EivknNy4gjIj9fSgA


## Entry 4: Creating the Application.java class
this is standard Spring Application Boot Run to initialize other Components.  

## Entry 5: Creating Role @Entity
go to **com.mooracle.domain.Role** to make Role entity

## Entry 6: Creating User @Entity
go to **com.mooracle.domain.User** to make User entity class

## Entry 7: Creating Favorite @Entity
go to **com.mooracle.domain.Favorite** to make Favorite entity class

## Notes : After creating Entity models
By looking at the development process so far it is clear that the right path is by creating the model first.
This approach is used since model will be used through all the development phase especially in the controller and service
layer. The next component or better we called it as layer need to be build is the DAO layer. This approach from front and
back end of the app is practical since DAO component will also be used extensivelly in other components and layers.

Moreover, in this app development DAO mostly consist of interfaces. Ideally it will have its own implementations but in
this case it uses external classes that already internal implementations. 

## Entry 8: Creating UserDao Interface
Go to **com.mooracle.dao.UserDao** to build UserDao interface 

## Entry 9: Creating RoleDao interface
Go to **com.mooracle.dao.RoleDao** to create Role DAO interface

## Entry 10: Creating FavoriteDao interface
Go to **com.mooracle.dao.FavoriteDao** to build the FavoriteDao and add @Query to it

## Notes: After Creating DAO layer
After this we need to build the Service Layer since it will be required by Controllers and also Util package. The
service layer is more complex here since many business logic need to be implemented here. Moreover, in this layer we
also need to prepare Exception handlers for many scenarios. Then we also need to prepare the REST template to handle
the external API communication. 

## Entry 11: Preparing Dto Package and Creating Dto.java
This part of the Service layer is the most widely needed by other services, thus we must prepare it first. Meanwhile to
make it easier for package tree management it will be better to set the package for resttemplate also. Both resttemplate
and dto package has similar sub packages fo geocoding and weather. After all set go to **com.mooracle.service.dto.Dto**

## Entry 12: Creating Condition.java service
Go to **com.mooracle.service.dto.weather.Condition** to fecth all JSON data from Dark Sky API

## Entry 13: Creating ForecastData.java service 
Go to **com.mooracle.service.dto.weather.ForecastData**

## Entry 14: Creating Weather.java service
Go to **com.mooracle.service.dto.weather.Weather** to create current weather service

## Entry 15: Creating Geocoding AddressComponent.java class
Go to **com.mooracle.service.dto.geocoding.AddressComponent** to make design view on how an address should be written
[more on @JsonProperty annotation](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)

## Entry 16: Creating Geocoding Location.java class
Go to **com.mooracle.service.dto.geocoding.Location** the link to @JsonProperty is the same as Entry 15

## Entry 17: Creating Geocoding Geometry.java class
Go to **com.mooracle.service.dto.geocoding.Geometry** 

## Entry 18: Creating Geocoding PlacesResult.java class (patched)
Go to **com.mooracle.service.dto.geocoding.PlacesResult**

## Entry 19: Creating Geocoding PlacesResponse.java class
Go to **com.mooracle.service.dto.geocoding.PlacesResponse**

## Entry 20: Creating Geocoding service of GeocodingResult.java
Go to **com.mooracle.service.dto.geocoding.GeocodingResult**

## Entry 21: Creating Geocoding service GeocodingResponse.java
Go to **com.mooracle.service.dto.geocoding.GeocodingResponse**

## After finishing the Data Transfer Object (DTO) - prepare to merge branches
[More about DTO](https://en.wikipedia.org/wiki/Data_transfer_object). There also a debate on whether using DTO is good
or bad practice in [this forum](https://stackoverflow.com/questions/36174516/rest-api-dtos-or-not). This debate from my
point of view is also required on understanding what does DTO function is. However, for now the main question is whether
we merge the branch now and focus only on DTO or we wait until the REST template service is developed. In the name of 
practicality I think the development of DTO service layer is also a development itself and indeed if the fucntion is
tested to deem functioning it should be integrated in the properties-settings branch. This will also denotes the
importance of understanding what DTO really stands for. DTOs are simple objects that should not contain any business 
logic but may contain serialization and deserialization mechanisms for transferring data over the wire.

After this we will focus on the REST template functioning class thus we will create new GitHUb branch called 
rest-template-service. Here we will develop all necessary classes and interfaces to build full functioning REST API

## Entry 22: Creating REST PlacesService.java interface
Go to **com.mooracle.service.resttemplate.PlacesService**

## Entry 23: Creating REST RestApiService.java abstract class
Go to **com.mooracle.service.resttemplate.RestApiService** which will introduce RestTemplate which is part of Spring
framework web client package [here is the docs](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
This class simplifies communication with HTTP servers, and enforces RESTful principles.
[Find out more on Builder Design Pattern](https://www.google.co.id/search?q=java+builder+design+pattern&oq=java+builde&aqs=chrome.2.69i57j0l5.5224j1j7&sourceid=chrome&ie=UTF-8)
also it is best to [look back at abstract class](http://www.javacoffeebreak.com/faq/faq0084.html).
### Note:
In this entry we found out that this abstract class required to have call a class from **com.mooracle.util** package
thus we must take a detour by making a new branch to provide the necessary class for the abstract class to perform.
## Entry 23a: Creating WebUtils.java class to support RestApiService.java 
Go to **com.mooracle.util.WebUtils** after this finish we can back to Entry 23 to build RestApiService.java. 
[more on URL Encoder](https://docs.oracle.com/javase/7/docs/api/java/net/URLEncoder.html)
