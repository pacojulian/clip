# CODE CHALLENGE CLIP

This project contains the information how the API Works, how to setup and some golden opprtunities

# Set Up


# Endpoints
* `POST /transaction/`
* `GET /transaction?user=`
* `POST /disbursement/`
* `GET /disbursement/`
* `POST /authenticate/`

To obtain better detail of each endpoint visit:  `http://localhost:8080/swagger-ui.html`

# Golden opportunities:
* Better Database design.
* Improve JPA.
* Pagination: at some point a user can have more than 100 disbursements/transactions  so it will be better for performance paginate the results.  
* Endpoint authentication: TODO
* Encryption to certain information such as: `credit card, account number, etc`
* Implementation of Reactor: if the apis calls get too busy at some point it will be better to opt for moving to an asynchronous version.

 


