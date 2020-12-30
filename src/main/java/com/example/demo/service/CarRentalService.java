package com.example.demo.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.repository.CarRentalRepository;
import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.utils.CarNotFoundException;
import com.example.demo.utils.CarStatus;

@Service
public class CarRentalService {
	

	private CarRentalRepository repository;
	
	@Autowired
	public CarRentalService(CarRentalRepository repository) {
		this.repository = repository;
	}
	
	public List<Car> getAllCars() {
		return repository.findAll();
	}
	
	
	public Car addCar(@RequestBody Car newCar) {
		newCar.initiateCar();
		return repository.save(newCar);
	}
	
	
	public Car getCar(@PathVariable long id) throws CarNotFoundException {
		return repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
	}
	
	
	public Car updateCar(@RequestBody Car carToUpdate, @PathVariable long id) throws CarNotFoundException {
		Car car = repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		
		BeanUtils.copyProperties(carToUpdate, car, "carId"); //nie dziala
		repository.save(car);
		
		return carToUpdate;
	}
	
	
	public ResponseEntity<?> deleteCar(@PathVariable long id) throws CarNotFoundException {
		Car car = repository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		repository.delete(car);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	
	public List<Car> getAvailableCars() {
		return repository.findByStatus(CarStatus.AVAILABLE);
	}
	
	
	public ResponseEntity<Car> rentCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		Car car = repository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.getClient() == null) {
			car.rentCar(client);
			repository.save(car);
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} else {
			return new ResponseEntity<Car>(car, HttpStatus.CONFLICT);
		}
	}
	
	
	public List<Car> getRentedCars() {
		return repository.findByStatus(CarStatus.RENTED);
	}
	
	
	
	public ResponseEntity<Car> returnCar(@PathVariable long carId, @RequestBody Client client) throws CarNotFoundException {
		Car car = repository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.getClient() !=null) { //&& client.getId() == car.getClient().getId()){
			car.returnCar();
			repository.save(car);
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} else {
			return new ResponseEntity<Car>(car, HttpStatus.CONFLICT);
		}
	}
}



