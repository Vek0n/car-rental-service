package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CarHasBeenAlreadyRentedException extends Exception {
    public CarHasBeenAlreadyRentedException(long carId) {
    }
}
