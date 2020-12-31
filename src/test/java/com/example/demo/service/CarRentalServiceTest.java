package com.example.demo.service;

import com.example.demo.domain.car.Car;
import com.example.demo.domain.car.CarBuilder;
import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import com.example.demo.repository.CarRentalRepository;
import com.example.demo.domain.car.CarStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CarRentalServiceTest {

    @Mock
    CarRentalRepository carRentalRepository;

    @Mock
    ClientService clientService;

    @InjectMocks
    CarRentalService testService;

    @Test
    void shouldGiveAllCars() {
        //Given
        List<Car> givenCars = Collections.singletonList(new CarBuilder().defaultCar().build());
        given(carRentalRepository.findAll()).willReturn(givenCars);
        //When
        List<Car> result = testService.getAllCars();
        //Then
        assertEquals(result, givenCars);
    }

    @Test
    void addCar() {
    }

    @Test
    void getCar() {
    }

    @Test
    void updateCar() {
    }

    @Test
    void deleteCar() {
    }

    @Test
    void getAvailableCars() {
    }

    @Test
    void shouldRentCar() throws CarNotFoundException, CarHasBeenAlreadyRentedException, ClientNotFoundException {
        //Given
        Car testCar = new CarBuilder().defaultCar().build();
        Client testClient = new ClientBuilder().defaultClient().build();

        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));
        given(carRentalRepository.save(testCar)).willReturn(testCar);
        given(clientService.getClient(testClient.getId())).willReturn(testClient);
        //When
        Car resault = testService.rentCar(testCar.getCarId(), testClient.getId());

        //Then
        assertEquals(resault.getStatus(), CarStatus.RENTED);
        assertEquals(testCar.getClient(), testClient);
        assertTrue(!testCar.isAvailable());
    }

    @Test
    void shouldThrowDuringRenting() throws CarNotFoundException, CarHasBeenAlreadyRentedException {
        //Given
        Client testClient = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder()
                .defaultCar()
                .withStatus(CarStatus.RENTED)
                .withClient(testClient)
                .build();

        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));

        //Then
         Exception exception = assertThrows(CarHasBeenAlreadyRentedException.class, () -> {
             testService.rentCar(testCar.getCarId(), testClient.getId());
         });

    }

    @Test
    void getRentedCars() {
    }

    @Test
    void returnCar() {
    }
}