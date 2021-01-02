package com.example.demo.domain.car;

public class CarDTO {
    private String brandName;
    private String modelName;
    private CarStatus status;
    private long clientId;

    CarDTO(String brandName, String modelName, CarStatus status, long clientId) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.status = status;
        this.clientId = clientId;
    }

    String getBrandName() {
        return brandName;
    }
    String getModelName() {
        return modelName;
    }
    CarStatus getStatus() {
        return status;
    }

    public long getClientId() {
        return clientId;
    }
}
