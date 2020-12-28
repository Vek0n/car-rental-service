package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Car;

public interface CarRentalRepository extends JpaRepository<Car, Long> {

}
