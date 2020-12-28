package com.example.demo.utils;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controller.CarRentalController;
import com.example.demo.model.Car;

@Component
public class CarModelAssembler implements RepresentationModelAssembler<Car, EntityModel<Car>> {
	
	@Override
	public EntityModel<Car> toModel(Car car) {
		
		EntityModel<Car> carModel = EntityModel.of(car,
			linkTo(methodOn(CarRentalController.class).getCar(car.getId())).withSelfRel(),
			linkTo(methodOn(CarRentalController.class).getAllCars()).withRel("cars"));
		
//		if(car.getStatus() == CarStatus.AVAILABLE) {
//			carModel.add(linkTo(methodOn(CarRentalController.class).rentCar(car.getId())).withRel("rent"));
//		}else {
//			carModel.add(linkTo(methodOn(CarRentalController.class).returnCar(car.getId())).withRel("return"));
//		}
		return carModel;
	}
	
}

