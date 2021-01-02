package com.example.demo.domain.car;

import com.example.demo.domain.client.Client;

public class CarBuilder {

    private long carId;
    private String brandName;
    private String modelName;
    private CarStatus status;
    private Client client;

    public CarBuilder() {
    }

    public CarBuilder defaultCar() {
        this.carId = 1;
        this.brandName = "Skoda";
        this.modelName = "Fabia";
        this.status = CarStatus.AVAILABLE;
        this.client = null;
        return this;
    }

    public CarBuilder withCarId(long carId) {
        this.carId = carId;
        return this;
    }

    public CarBuilder withBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public CarBuilder withModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public CarBuilder withStatus(CarStatus status) {
        this.status = status;
        return this;
    }

    public CarBuilder withClient(Client client) {
        this.client = client;
        return this;
    }

    public Car build() {
        return new Car(carId, brandName, modelName, status, client);
    }
}