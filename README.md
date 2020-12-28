# car-rental-service
## Endpoints

#### Add a car
- **Request** : **`POST /cars`**
  ```
  {
    "brandName": "Ford",
    "modelName": "Focus"
  }
  ```
- **Response** :
  ```
  {
    "id": 1,
    "client_id": 0,
    "brandName": "Ford",
    "modelName": "Focus",
    "status": "AVAILABLE"
  }
  ```
  
#### Get all cars
- **Request** : **`GET /cars`**
- **Response** :
	```
	{
	  "_embedded": {
	    "cars": [
	      {
		"id": 1,
		"client_id": 0,
		"brandName": "Ford",
		"modelName": "Focus",
		"status": "AVAILABLE",
		"_links": {
		  "self": {
		    "href": "http://localhost:8080/cars/1"
		  },
		  "cars": {
		    "href": "http://localhost:8080/cars"
		  }
		}
	      }
	    ]
	  },
	  "_links": {
	    "self": {
	      "href": "http://localhost:8080/cars"
	    }
	  }
	}
	```
  
  
#### Delete a car
- **Request** : **`POST /cars/{id}`**
- **Response** :
  ```
  200 OK
  ```
  
#### Edit a car
- **Request** : **`PUT /cars/{id}`**
	```
	{
	  "brandName": "Ford",
	  "modelName": "Fiesta"
	}
	```
- **Response** :
  ```
  200 OK
  ```

#### List of all available cars
- **Request** : **`GET /cars/rent`**

#### Rent a car
- **Request** : **`POST /cars/rent/{car_id}`**
	```
	{
		"id":{client_id}
	}
	```
- **Response** :
  ```
  202 Accepted
  ```
  
#### Rent a car (in case of conflict)
- **Request** : **`POST /cars/rent/{id}`**
- **Response** :
  ```
  409 Conflict
  Car already rented
  ```
  
#### List of all rented cars
- **Request** : **`GET /cars/return/`**
- **Response** :
	```
	{
	  "_embedded": {
	    "cars": [
	      {
		"id": 1,
		"client_id": 1,
		"brandName": "Ford",
		"modelName": "Fiesta",
		"status": "RENTED",
		"_links": {
		  "self": {
		    "href": "http://localhost:8080/cars/1"
		  },
		  "cars": {
		    "href": "http://localhost:8080/cars"
		  }
		}
	      }
	    ]
	  },
	  "_links": {
	    "self": {
	      "href": "http://localhost:8080/cars"
	    }
	  }
	}
	```
  
#### Return a car
- **Request** : **`POST /cars/return/{id}`**
- **Response** :
  ```
  202 Accepted
  ```
 
 
#### Return a car (in case of conflict)
- **Request** : **`POST /cars/return{id}`**
- **Response** :
  ```
  409 Conflict
  Car not rented
  ```
  
#### Pre-populated clients database:
  ```
  insert into client values (1, 'Jan', 'Kowalski')
  insert into client values (2, 'Szymon', 'Kaczmarek')
  ```
