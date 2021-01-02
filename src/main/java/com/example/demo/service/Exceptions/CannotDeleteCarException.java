package com.example.demo.service.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CannotDeleteCarException extends Exception {

    public CannotDeleteCarException(long id) {
        super("Car with id: " + id + " cannot be deleted");
    }
}