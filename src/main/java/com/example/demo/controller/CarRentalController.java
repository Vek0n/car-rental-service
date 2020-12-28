package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.CarRentalRepository;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.utils.CarModelAssembler;
import com.example.demo.utils.CarStatus;

@RestController
public class CarRentalController {

	@Autowired
	CarRentalRepository repository;
	@Autowired
	CarModelAssembler assembler;

	@PostMapping(path = "/cars")
	public Car addCar(@RequestBody Car newCar) {
		newCar.setStatus(CarStatus.AVAILABLE);
		newCar.setClient_id(0);
		return repository.save(newCar);
	}

	@GetMapping(path = "/cars")
	public CollectionModel<EntityModel<Car>> getAllCars() {

		List<EntityModel<Car>> cars = repository
				.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(cars, linkTo(methodOn(CarRentalController.class)
				.getAllCars())
				.withSelfRel());
	}

	@GetMapping(path = "/cars/{id}")
	public EntityModel<Car> getCar(@PathVariable long id) {
		Car car = repository
				.findById(id)
				.orElseThrow();
		
		return assembler.toModel(car);
	}

	@PutMapping(path = "/cars/{id}")
	public ResponseEntity<?> updateCar(@RequestBody Car car, @PathVariable long id) {
		Car carToUpdate = car;
		Car currentCar = repository
				.findById(id)
				.orElseThrow();
		
		carToUpdate.setId(id);
		carToUpdate.setStatus(currentCar.getStatus());
		repository.save(carToUpdate);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/cars/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable long id) {
		Car car = repository.getOne(id);
		repository.delete(car);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/cars/rent")
	public CollectionModel<EntityModel<Car>> getAvailableCars() {
		List<EntityModel<Car>> cars = repository
				.findAll()
				.stream()
				.filter(car -> car.getStatus() == CarStatus.AVAILABLE)
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(cars, linkTo(methodOn(CarRentalController.class)
				.getAllCars())
				.withSelfRel());
	}

	@PostMapping(path = "/cars/rent/{carId}")
	public ResponseEntity<?> rentCar(@PathVariable long carId, @RequestBody Client client) {
		Car car = repository
				.findById(carId)
				.orElseThrow();

		if (car.getStatus() == CarStatus.AVAILABLE) {
			car.setClient_id(client.getId());
			car.setStatus(CarStatus.RENTED);
			repository.save(car);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Car already rented",HttpStatus.CONFLICT);
		}
	}

	@GetMapping(path = "/cars/return")
	public CollectionModel<EntityModel<Car>> getRentedCars() {
		List<EntityModel<Car>> cars = repository
				.findAll()
				.stream()
				.filter(car -> car.getStatus() == CarStatus.RENTED)
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(cars, 
				linkTo(methodOn(CarRentalController.class)
						.getAllCars())
						.withSelfRel());
	}

	@PostMapping(path = "/cars/return/{carId}")
	public ResponseEntity<?> returnCar(@PathVariable long carId, @RequestBody Client client) {
		Car car = repository
				.findById(carId)
				.orElseThrow();

		if (car.getStatus() == CarStatus.RENTED && client.getId() == car.getClient_id()) {
			car.setStatus(CarStatus.AVAILABLE);
			repository.save(car);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Car not rented",HttpStatus.CONFLICT);
		}
	}
}
