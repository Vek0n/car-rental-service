package com.example.demo.service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CarRentedByAnotherUserException extends Exception {
    public CarRentedByAnotherUserException(long carId) {
    }
}
