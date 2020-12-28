package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;

import com.example.demo.model.Car;
import com.example.demo.utils.CarStatus;

public interface CarRentalRepository extends JpaRepository<Car, Long> {

	List<Car> findByStatus(CarStatus status);
	
}