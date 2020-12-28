package com.example.demo.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.controller.CarRentalController;
import com.example.demo.repository.CarRentalRepository;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.utils.CarModelAssembler;
import com.example.demo.utils.CarNotFoundException;
import com.example.demo.utils.CarStatus;

@Component
public class CarRentalService {
	

	private CarRentalRepository repository;
	private CarModelAssembler assembler;
	
	@Autowired
	public CarRentalService(CarRentalRepository repository,  CarModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	public CollectionModel<EntityModel<Car>> getAllCars() {

		List<EntityModel<Car>> cars = repository
				.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(cars, linkTo(methodOn(CarRentalService.class)
				.getAllCars())
				.withSelfRel());
	}
	
	
	public Car addCar(@RequestBody Car newCar) {
		newCar.initiateCar();
		return repository.save(newCar);
	}
	
	
	public EntityModel<Car> getCar(@PathVariable long id) throws CarNotFoundException {
		Car car = repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		
		return assembler.toModel(car);
	}
	
	
	public Car updateCar(@RequestBody Car carToUpdate, @PathVariable long id) throws CarNotFoundException {
		Car currentCar = repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		
		carToUpdate.setId(id);
		carToUpdate.setStatus(
				currentCar.getStatus());
		repository.save(carToUpdate);
		
		return carToUpdate;
	}
	
	
	public ResponseEntity<?> deleteCar(@PathVariable long id) throws CarNotFoundException {
		Car car = repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		repository.delete(car);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	
	public CollectionModel<EntityModel<Car>> getAvailableCars() {
		
		List<EntityModel<Car>> cars = repository
				.findByStatus(CarStatus.AVAILABLE)
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(cars, linkTo(methodOn(CarRentalController.class)
				.getAllCars())
				.withSelfRel());
	}
	
	
	public ResponseEntity<Car> rentCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		Car car = repository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.getStatus() == CarStatus.AVAILABLE) {
			car.rentCar(client.getId());
			repository.save(car);
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} else {
			return new ResponseEntity<Car>(car, HttpStatus.CONFLICT);
		}
	}
	
	
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
	
	
	public ResponseEntity<Car> returnCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		Car car = repository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.getStatus() == CarStatus.RENTED && client.getId() == car.getClient_id()) {
			car.returnCar();
			repository.save(car);
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} else {
			return new ResponseEntity<Car>(car, HttpStatus.CONFLICT);
		}
	}
}



