package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.service.CarRentalService;
import com.example.demo.utils.CarNotFoundException;


@RestController
public class CarRentalController {

	private CarRentalService carRentalService;
	
	@Autowired
	public CarRentalController(CarRentalService carRentalService) {
		this.carRentalService = carRentalService;
	}
	

	@PostMapping(path = "/cars")
	public Car addCar(@RequestBody Car newCar) {
		return carRentalService.addCar(newCar);
	}

	@GetMapping(path = "/cars")
	public List<Car> getAllCars() {
		return carRentalService.getAllCars();
	}


	@GetMapping(path = "/cars/{id}")
	public Car getCar(@PathVariable long id) throws CarNotFoundException {
		return carRentalService.getCar(id);
	}

	
	@PutMapping(path = "/cars/{id}")
	public Car updateCar(@RequestBody Car car, @PathVariable long id) throws CarNotFoundException {
		return carRentalService.updateCar(car, id);
	}

	@DeleteMapping("/cars/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable long id) throws CarNotFoundException {
		return carRentalService.deleteCar(id);
	}

	@GetMapping(path = "/cars/rent")
	public List<Car>  getAvailableCars() {
		return carRentalService.getAvailableCars();
	}

	@PostMapping(path = "/cars/rent/{carId}")
	public ResponseEntity<?> rentCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		return carRentalService.rentCar(carId, client);
	}
	
	@GetMapping(path = "/cars/return")
	public List<Car>  getRentedCars() {
		return carRentalService.getRentedCars();
	}

	@PostMapping(path = "/cars/return/{carId}")
	public ResponseEntity<?> returnCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		return carRentalService.returnCar(carId, client);
	}
}
