package com.example.demo.controller;

import com.example.demo.domain.car.Car;
import com.example.demo.domain.car.CarBuilder;
import com.example.demo.service.CarRentalService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}