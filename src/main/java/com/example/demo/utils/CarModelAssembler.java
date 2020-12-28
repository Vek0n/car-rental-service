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
		return carModel;
	}	
}

