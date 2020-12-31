package com.example.demo.domain.car;

public class CarDTO {
    String brandName;
    String modelName;
    CarStatus status;
    long clientId;

    public String getBrandName() {
        return brandName;
    }
    public String getModelName() {
        return modelName;
    }
    public CarStatus getStatus() {
        return status;
    }

    public long getClientId() {
        return clientId;
    }
}
