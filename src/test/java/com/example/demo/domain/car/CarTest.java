package com.example.demo.domain.car;

import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void shouldRentCar() {
        //Given
        Car testCar = new CarBuilder().defaultCar().build();
        Client client = new ClientBuilder().defaultClient().build();

        //When
        testCar.rentCar(client);

        //Then
        assertEquals(testCar.getStatus(), CarStatus.RENTED);
        assertEquals(testCar.getClient(), client);
        assertTrue(!testCar.isAvailable());
    }

    @Test
    void returnCar() {
    }

    @Test
    void initiateCar() {
    }

    @Test
    void isAvaillable() {
    }
}