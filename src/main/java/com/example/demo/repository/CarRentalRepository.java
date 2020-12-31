package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.car.Car;
import com.example.demo.domain.car.CarStatus;

public interface CarRentalRepository extends JpaRepository<Car, Long> {

	List<Car> findByStatus(CarStatus status);
	
}