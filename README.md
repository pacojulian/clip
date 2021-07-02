# CODE CHALLENGE CLIP

This project contains the information how the API Works, how to setup and some golden opprtunities

# Requirements
* Java JDK Version.
* Maven .

## Tools
 * Spring Boot
 * Gradle
 * H2 database
 * Swagger
 
## Building and runing the application

* Download the source code from github: `https://github.com/pacojulian/clip.git`
* To build the application execute the command: **mvn install**
* To run the application execute the command: **mvn spring-boot:run** 

This Application use Swagger for the api documentation you can visit it at: **localhost:8080/swagger-ui.html**<br/>
# Endpoints
* `POST /transaction/`
* `GET /transaction?user=`
* `POST /disbursement/`
* `GET /disbursement/`
* `POST /authenticate/`

# Golden opportunities:
* Improve Database design.
* Pagination: at some point a user can have more than 100 disbursements/transactions  so it will be better for performance paginate the results.  
* Endpoint authentication: Not everyone can access this type of resources.
* Encryption to certain information such as: `credit card, account number, etc`
* Implementation of Reactor: if the apis calls get too busy at some point it will be better to opt for moving to an asynchronous call.

 


