package com.example.demo.service.car;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends Exception {

    CarNotFoundException(long id) {
        super("Car with id: " + id + " not found");
    }
}