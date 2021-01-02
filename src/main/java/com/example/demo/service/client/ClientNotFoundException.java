package com.example.demo.service.client;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends Exception {

    ClientNotFoundException(long id) {
        super("Client with id: " + id + " not found");
    }
}