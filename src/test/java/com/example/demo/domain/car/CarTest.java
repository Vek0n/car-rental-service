package com.example.demo.domain.car;

import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void shouldReturnIsAvailable() {
        Client client = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build();

        assertFalse(testCar.isAvailable());
    }

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
    void shouldReturnCar() {
        Client client = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build();

        testCar.returnCar();

        assertEquals(testCar.getStatus(), CarStatus.AVAILABLE);
        assertNull(testCar.getClient());
        assertTrue(testCar.isAvailable());
    }

    @Test
    void shouldInitiateCar() {
        Client client = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build();

        testCar.initiateCar();

        assertEquals(testCar.getStatus(), CarStatus.AVAILABLE);
        assertNull(testCar.getClient());
        assertTrue(testCar.isAvailable());
    }
}