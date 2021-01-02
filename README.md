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
    "carId": 2,
    "brandName": "Ford",
    "modelName": "Focus",
    "status": "AVAILABLE",
    "client": null
  }
  ```
  
#### Get all cars
- **Request** : **`GET /cars`**
- **Response** :
  ```
	]
	  {
	    "carId": 1,
	    "brandName": "Audi",
	    "modelName": "TT",
	    "status": "RENTED",
	    "client": {
	      "firstName": "Szymon",
	      "secondName": "Kaczmarek",
	      "id": 2
	    }
	  },
	  {
	    "carId": 2,
	    "brandName": "Ford",
	    "modelName": "Focus",
	    "status": "AVAILABLE",
	    "client": null
	  }
	]
  ```

  
#### Get a car
- **Request** : **`GET /cars/{id}`**
- **Response** :
  ```
	  {
	    "carId": 2,
	    "brandName": "Ford",
	    "modelName": "Focus",
	    "status": "AVAILABLE",
	    "client": null
	  }
  ```
  
  
#### Delete a car
- **Request** : **`DELETE /cars/{id}`**
- **Response** :
  ```
  200 OK
  true
  ```
  
#### Edit a car
- **Request** : **`PUT /cars/{id}`**
  ```
	{
		"brandName":"Ford",
		"modelName":"Fiesta",
		"status":"AVAILABLE"
	}
  ```
- **Response** :
  ```
	{
	  "carId": 2,
	  "brandName": "Ford",
	  "modelName": "Fiesta",
	  "status": "AVAILABLE",
	  "client": null
	}
  ```

#### List of all available cars
- **Request** : **`GET /cars/rent`**

#### Rent a car
- **Request** : **`POST /cars/rent/{carId}?clientId={clientId}`**
- **Response** :
  ```
	{
	  "carId": 2,
	  "brandName": "Ford",
	  "modelName": "Fiesta",
	  "status": "RENTED",
	  "client": {
	    "firstName": "Jan",
	    "secondName": "Kowalski",
	    "id": 1
	  }
	}
  ```
  
#### Rent a car (in case of conflict)
- **Request** : **`POST /cars/rent/{carId}?clientId={clientId}`**
- **Response** :
  ```
  409 Conflict
  Car already rented
  ```
  
#### List of all rented cars
- **Request** : **`GET /cars/return`**
- **Response** :

  
#### Return a car
- **Request** : **`POST /cars/return/{carId}?clientId={clientId}`**
- **Response** :
  ```
	{
	  "carId": 2,
	  "brandName": "Ford",
	  "modelName": "Fiesta",
	  "status": "AVAILABLE",
	  "client": null
	}
  ```
 
 
#### Return a car (in case of conflict)
- **Request** : **`POST /cars/return/{carId}?clientId={clientId}`**
- **Response** :
  ```
  409 Conflict
  Car not rented
  ```
  
#### Pre-populated database with clients :
  ```
insert into client (FIRST_NAME, SECOND_NAME) values ('Jan', 'Kowalski'), ('Szymon', 'Kaczmarek')

  ```
  
