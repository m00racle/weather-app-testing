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