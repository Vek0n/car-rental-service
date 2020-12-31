package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.car.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.CarRentalRepository;
import com.example.demo.domain.car.Car;
import com.example.demo.domain.client.Client;
import com.example.demo.domain.car.CarStatus;

@Service
public class CarRentalService {
	

	private CarRentalRepository carRepository;
	private ClientService clientService;
	
	@Autowired
	public CarRentalService(CarRentalRepository carRepository, ClientService clientService) {
		this.carRepository = carRepository;
		this.clientService = clientService;
	}
	
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}
	
	
	public Car addCar(Car newCar) {
		newCar.initiateCar();
		return carRepository.save(newCar);
	}
	
	
	public Car getCar(long id) throws CarNotFoundException {
		return carRepository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
	}
	
	
	public Car updateCar(CarDTO carToUpdate, long carId) throws ClientNotFoundException {
		if (carToUpdate.getClientId()==0){
			return carRepository.save(new Car(carId, carToUpdate, null));
		}else {
			return carRepository.save(new Car(carId,
					carToUpdate,
					clientService.getClient(carToUpdate.getClientId())));
		}
	}
	
	
	public boolean deleteCar(long id) throws CarNotFoundException {
		Car car = carRepository
				.findById(id)
				.orElseThrow(() -> new CarNotFoundException(id));
		carRepository.delete(car);
		return true;
	}
	
	
	public List<Car> getAvailableCars() {
		return carRepository.findByStatus(CarStatus.AVAILABLE);
	}
	
	
	public Car rentCar(long carId, long clientId) throws CarNotFoundException, CarHasBeenAlreadyRentedException, ClientNotFoundException {
		Car car = carRepository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.isAvailable()) {
			Client client = clientService.getClient(clientId);
			car.rentCar(client);
			return carRepository.save(car);
		} else {
			throw new CarHasBeenAlreadyRentedException(carId);
		}
	}
	
	
	public List<Car> getRentedCars() {
		return carRepository.findByStatus(CarStatus.RENTED);
	}
	
	
	
	public Car returnCar(long carId, long clientId) throws CarNotFoundException, CarHasBeenAlreadyRentedException {
		Car car = carRepository
				.findById(carId)
				.orElseThrow(() -> new CarNotFoundException(carId));

		if (car.getClient() !=null && clientId == car.getClient().getId()) {
			car.returnCar();
			carRepository.save(car);
			return carRepository.save(car);
		} else {
			throw new CarHasBeenAlreadyRentedException(carId);
		}
	}
}



