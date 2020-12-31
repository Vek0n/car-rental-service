package com.example.demo.domain.client;

import com.example.demo.domain.car.Car;

import java.util.List;
import java.util.Set;

public class ClientBuilder {
    private long clientId;
    private String firstName;
    private String secondName;
    private List<Car> cars;

    public ClientBuilder() {
    }

    public ClientBuilder defaultClient(){
        this.clientId = 1;
        this.firstName = "Adam";
        this.secondName = "Adam";
        this.cars = null;
        return this;
    }
    public ClientBuilder withClientId(long clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientBuilder withfirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ClientBuilder withSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public ClientBuilder withcars(List<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Client build() {
        return new Client(clientId, firstName, secondName, cars);
    }
}
