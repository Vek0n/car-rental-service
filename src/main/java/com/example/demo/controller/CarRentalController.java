package com.example.demo.controller;

import com.example.demo.domain.car.Car;
import com.example.demo.domain.car.CarDTO;
import com.example.demo.service.car.CarRentalService;
import com.example.demo.service.car.CannotDeleteCarException;
import com.example.demo.service.car.CarNotFoundException;
import com.example.demo.service.car.CarRentedByAnotherUserException;
import com.example.demo.service.client.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
public class CarRentalController {

    private CarRentalService carRentalService;

    @Autowired
    public CarRentalController(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }


    @PostMapping(path = "/cars")
    public Car addCar(@RequestBody CarDTO newCar) {
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
    public Car updateCar(@RequestBody CarDTO car, @PathVariable long id) throws ClientNotFoundException {
        return carRentalService.updateCar(car, id);
    }


    @DeleteMapping("/cars/{id}")
    public boolean deleteCar(@PathVariable long id) throws CarNotFoundException, CannotDeleteCarException {
        return carRentalService.deleteCar(id);
    }

    @GetMapping(path = "/cars/rent")
    public List<Car> getAvailableCars() {
        return carRentalService.getAvailableCars();
    }

    @GetMapping(path = "/cars/return")
    public List<Car> getRentedCars() {
        return carRentalService.getRentedCars();
    }

    @PostMapping(path = "/cars/rent/{carId}")
    public Car rentCar(@PathVariable long carId, @RequestParam long clientId) throws CarNotFoundException, CarRentedByAnotherUserException, ClientNotFoundException {
        return carRentalService.rentCar(carId, clientId);
    }

    @PostMapping(path = "/cars/return/{carId}")
    public Car returnCar(@PathVariable long carId, @RequestParam long clientId) throws CarNotFoundException, CarRentedByAnotherUserException {
        return carRentalService.returnCar(carId, clientId);
    }
}
