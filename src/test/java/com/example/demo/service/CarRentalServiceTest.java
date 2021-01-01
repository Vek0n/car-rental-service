package com.example.demo.service;

import com.example.demo.domain.car.*;
import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import com.example.demo.repository.CarRentalRepository;
import com.example.demo.service.Exceptions.CarNotFoundException;
import com.example.demo.service.Exceptions.CarRentedByAnotherUserException;
import com.example.demo.service.Exceptions.ClientNotFoundException;
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
    void shouldAddCar() {
        Car testCar = new CarBuilder().defaultCar().build();
        given(carRentalRepository.save(testCar)).willReturn(testCar);

        Car result = testService.addCar(testCar);

        assertEquals(result, testCar);
    }

    @Test
    void shouldGetCar() throws CarNotFoundException {
        Car testCar = new CarBuilder().defaultCar().build();
        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));

        Car result = testService.getCar(testCar.getCarId());

        assertEquals(result, testCar);
    }

    @Test
    void shouldUpdateCar() throws ClientNotFoundException {
//        Client client = new ClientBuilder().defaultClient().build();
//
//        CarDTO carToUpdate = new CarDTOBuilder()
//                .defaultCar()
//                .withClient(client.getId())
//                .withStatus(CarStatus.RENTED)
//                .build();
//
//        Car testCar = new CarBuilder()
//                .defaultCar()
//                .withClient(client)
//                .withStatus(CarStatus.RENTED)
//                .build();
//
//        given(clientService.getClient(client.getId())).willReturn(client);
//        given(carRentalRepository.save(testCar)).willReturn(testCar);
////        Mockito.lenient().when(carRentalRepository.save(testCar)).thenReturn(testCar);
//
//        Car result = testService.updateCar(carToUpdate, carToUpdate.getClientId());
//
//        assertEquals(result, testCar);
    }

    @Test
    void deleteCar() throws CarNotFoundException {
        Car testCar = new CarBuilder().defaultCar().build();
        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));

        boolean result = testService.deleteCar(testCar.getCarId());

        assertTrue(result);
    }

    @Test
    void getAvailableCars() {
        //Given
        Client  client = new ClientBuilder().defaultClient().build();
        List<Car> givenCars = List.of(new CarBuilder().defaultCar().build(),
                                      new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build());

        List<Car> availableCars = List.of(givenCars.get(0));
        given(carRentalRepository.findByStatus(CarStatus.AVAILABLE)).willReturn(availableCars);

        //When
        List<Car> result = testService.getAvailableCars();

        //Then
        assertEquals(result, availableCars);
    }

    @Test
    void shouldRentCar() throws CarNotFoundException, CarRentedByAnotherUserException, ClientNotFoundException {
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
    void shouldThrowDuringRenting() throws CarNotFoundException, CarRentedByAnotherUserException {
        //Given
        Client testClient = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder()
                .defaultCar()
                .withStatus(CarStatus.RENTED)
                .withClient(testClient)
                .build();

        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));

        //Then
        assertThrows(CarRentedByAnotherUserException.class, () -> {
             testService.rentCar(testCar.getCarId(), testClient.getId());
         });

    }

    @Test
    void getRentedCars() {
        //Given
        Client  client = new ClientBuilder().defaultClient().build();
        List<Car> givenCars = List.of(new CarBuilder().defaultCar().build(),
                                      new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build());

        List<Car> rentedCars = List.of(givenCars.get(1));
        given(carRentalRepository.findByStatus(CarStatus.RENTED)).willReturn(rentedCars);

        //When
        List<Car> result = testService.getRentedCars();

        //Then
        assertEquals(result, rentedCars);
    }

    @Test
    void shouldReturnCar() throws CarNotFoundException, CarRentedByAnotherUserException {
        //given
        Client client = new ClientBuilder().defaultClient().build();
        Car testCar = new CarBuilder().defaultCar().withStatus(CarStatus.RENTED).withClient(client).build();
        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));
        given(carRentalRepository.save(testCar)).willReturn(testCar);

        //When
        Car result = testService.returnCar(testCar.getCarId(), client.getId());

        //Then
        assertEquals(result.getStatus(), CarStatus.AVAILABLE);
        assertEquals(result.getClient(), null);
        assertTrue(result.isAvailable());
    }

    @Test
    void shouldThrowCarRentedByAnotherUserDuringReturning() {
        //Given
        Client client1 = new ClientBuilder().defaultClient().build();
        Client client2 = new ClientBuilder().defaultClient().withClientId(2).build();

        Car testCar = new CarBuilder()
                .defaultCar()
                .withStatus(CarStatus.RENTED)
                .withClient(client1)
                .build();

        given(carRentalRepository.findById(testCar.getCarId())).willReturn(Optional.of(testCar));
//        given(carRentalRepository.save(testCar)).willReturn(testCar);

        //Then
        Exception exception = assertThrows(CarRentedByAnotherUserException.class, () -> {
            testService.returnCar(testCar.getCarId(), client2.getId());
        });

    }
}