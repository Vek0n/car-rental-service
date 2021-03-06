package com.example.demo.controller;

import com.example.demo.domain.car.Car;
import com.example.demo.domain.car.CarBuilder;
import com.example.demo.domain.car.CarDTO;
import com.example.demo.domain.car.CarStatus;
import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import com.example.demo.service.car.CarRentalService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CarRentalController.class)
class CarRentalControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarRentalService carRentalServiceMock;

    @Test
    void addCar() throws Exception {
        Car givenCar = new CarBuilder().defaultCar().build();

        given(carRentalServiceMock.addCar(any(CarDTO.class))).willReturn(givenCar);

        mvc.perform(post("/cars/")
                .content("{\"brandName\":\"Skoda\"," +
                        " \"modelName\":\"Fabia\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(givenCar.getCarId()))
                .andExpect(jsonPath("$.brandName").value(givenCar.getBrandName()))
                .andExpect(jsonPath("$.modelName").value(givenCar.getModelName()))
                .andExpect(jsonPath("$.status").value(givenCar.getStatus().toString()))
                .andExpect(jsonPath("$.client").value(givenCar.getClient()));
    }

    @Test
    void getAllCars() throws Exception {
        //Given
        Car testCar1 = new CarBuilder()
                .defaultCar()
                .build();
        Car testCar2 = new CarBuilder()
                .defaultCar()
                .withCarId(2)
                .withBrandName("Audi")
                .withModelName("A4")
                .build();

        List<Car> givenCars = List.of(testCar1, testCar2);

        given(carRentalServiceMock.getAllCars()).willReturn(givenCars);

        //Then
        mvc.perform(get("/cars").contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].carId").value(testCar1.getCarId()))
                .andExpect(jsonPath("$[0].brandName").value(testCar1.getBrandName()))
                .andExpect(jsonPath("$[0].modelName").value(testCar1.getModelName()))
                .andExpect(jsonPath("$[0].status").value(testCar1.getStatus().toString()))
                .andExpect(jsonPath("$[0].client").value(testCar1.getClient()))
                .andExpect(jsonPath("$[1].carId").value(testCar2.getCarId()))
                .andExpect(jsonPath("$[1].brandName").value(testCar2.getBrandName()))
                .andExpect(jsonPath("$[1].modelName").value(testCar2.getModelName()))
                .andExpect(jsonPath("$[1].status").value(testCar2.getStatus().toString()))
                .andExpect(jsonPath("$[1].client").value(testCar2.getClient()));
    }

    @Test
    void getCar() throws Exception {
        //Given
        Car givenCar = new CarBuilder().defaultCar().build();
        given(carRentalServiceMock.getCar(givenCar.getCarId())).willReturn(givenCar);
        //Then
        mvc.perform(get("/cars/" + givenCar.getCarId())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(givenCar.getCarId()))
                .andExpect(jsonPath("$.brandName").value(givenCar.getBrandName()))
                .andExpect(jsonPath("$.modelName").value(givenCar.getModelName()))
                .andExpect(jsonPath("$.status").value(givenCar.getStatus().toString()))
                .andExpect(jsonPath("$.client").value(givenCar.getClient()));
    }

    @Test
    void updateCar() throws Exception {
        Client client = new ClientBuilder().defaultClient().build();

        Car givenCar = new CarBuilder()
                .defaultCar()
                .build();

        Car updatedCar = new CarBuilder()
                .defaultCar()
                .withBrandName("Audi")
                .withModelName("A4")
                .withClient(client)
                .withStatus(CarStatus.RENTED)
                .build();

        given(carRentalServiceMock.updateCar(any(CarDTO.class), any(Long.TYPE))).willReturn(updatedCar);

        mvc.perform(put("/cars/" + givenCar.getCarId())
                .content("{\"brandName\":\"Audi\", \"modelName\":\"A4\", \"status\":\"RENTED\", \"clientId\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(updatedCar.getCarId()))
                .andExpect(jsonPath("$.brandName").value(updatedCar.getBrandName()))
                .andExpect(jsonPath("$.modelName").value(updatedCar.getModelName()))
                .andExpect(jsonPath("$.status").value(updatedCar.getStatus().toString()))
                .andExpect(jsonPath("$.client.id").value(updatedCar.getClient().getId()));
    }

    @Test
    void deleteCar() throws Exception {
        Car givenCar = new CarBuilder().defaultCar().build();
        given(carRentalServiceMock.deleteCar(givenCar.getCarId())).willReturn(true);

        mvc.perform(delete("/cars/" + givenCar.getCarId())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }


    @Test
    void getAvailableCars() throws Exception {
        //Given
        Car testCar = new CarBuilder()
                .defaultCar()
                .build();
        List<Car> givenCars = Collections.singletonList(testCar);

        given(carRentalServiceMock.getAvailableCars()).willReturn(givenCars);

        //Then
        mvc.perform(get("/cars/rent").contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].carId").value(testCar.getCarId()))
                .andExpect(jsonPath("$[0].brandName").value(testCar.getBrandName()))
                .andExpect(jsonPath("$[0].modelName").value(testCar.getModelName()))
                .andExpect(jsonPath("$[0].status").value(testCar.getStatus().toString()))
                .andExpect(jsonPath("$[0].client").value(testCar.getClient()));
    }


    @Test
    void getRentedCars() throws Exception {
        //Given
        Car testCar = new CarBuilder()
                        .defaultCar()
                        .withCarId(2)
                        .withBrandName("Audi")
                        .withModelName("A4")
                        .withStatus(CarStatus.RENTED)
                        .build();

        List<Car> givenCars = Collections.singletonList(testCar);

        given(carRentalServiceMock.getRentedCars()).willReturn(givenCars);

        //Then
        mvc.perform(get("/cars/return").contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].carId").value(testCar.getCarId()))
                .andExpect(jsonPath("$[0].brandName").value(testCar.getBrandName()))
                .andExpect(jsonPath("$[0].modelName").value(testCar.getModelName()))
                .andExpect(jsonPath("$[0].status").value(testCar.getStatus().toString()))
                .andExpect(jsonPath("$[0].client").value(testCar.getClient()));
    }


    @Test
    void rentCar() throws Exception {
        //Given
        Car givenCar = new CarBuilder()
                .defaultCar()
                .build();

        Client givenClient = new ClientBuilder()
                .defaultClient()
                .build();

        Car rentedTestCar = new CarBuilder()
                .defaultCar()
                .withStatus(CarStatus.RENTED)
                .withClient(givenClient)
                .build();

        given(carRentalServiceMock.rentCar(givenCar.getCarId(), givenClient.getId())).willReturn(rentedTestCar);

        mvc.perform(post("/cars/rent/" + givenCar.getCarId())
                .param("clientId", Long.toString(givenClient.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(rentedTestCar.getCarId()))
                .andExpect(jsonPath("$.brandName").value(rentedTestCar.getBrandName()))
                .andExpect(jsonPath("$.modelName").value(rentedTestCar.getModelName()))
                .andExpect(jsonPath("$.status").value(rentedTestCar.getStatus().toString()))
                .andExpect(jsonPath("$.client.id").value(rentedTestCar.getClient().getId()));
    }

    @Test
    void returnCar() throws Exception {

        Client givenClient = new ClientBuilder()
                .defaultClient()
                .build();

        Car givenCar = new CarBuilder()
                .defaultCar()
                .withStatus(CarStatus.RENTED)
                .withClient(givenClient)
                .build();

        Car returnedTestCar = new CarBuilder()
                .defaultCar()
                .build();

        given(carRentalServiceMock.returnCar(givenCar.getCarId(), givenClient.getId())).willReturn(returnedTestCar);

        mvc.perform(post("/cars/return/" + returnedTestCar.getCarId())
                .param("clientId", Long.toString(givenClient.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(returnedTestCar.getCarId()))
                .andExpect(jsonPath("$.brandName").value(returnedTestCar.getBrandName()))
                .andExpect(jsonPath("$.modelName").value(returnedTestCar.getModelName()))
                .andExpect(jsonPath("$.status").value(returnedTestCar.getStatus().toString()))
                .andExpect(jsonPath("$.client").value(returnedTestCar.getClient()));
    }
}