package com.example.demo.service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends Exception {

    public CarNotFoundException(long id) {
        super("Car with id: " + id + " not found");
    }
}