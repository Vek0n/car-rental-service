package com.example.demo.domain.car;


public class CarDTOBuilder {

    private String brandName;
    private String modelName;
    private CarStatus status;
    private long clientId;

    public CarDTOBuilder(){}

    public CarDTOBuilder defaultCar(){
        this.brandName = "Skoda";
        this.modelName = "Fabia";
        this.status = CarStatus.AVAILABLE;
        this.clientId = 0;
        return this;
    }

    public CarDTOBuilder withBrandName(String brandName){
        this.brandName = brandName;
        return this;
    }

    public  CarDTOBuilder withModelName(String modelName){
        this.modelName = modelName;
        return this;
    }

    public CarDTOBuilder withStatus(CarStatus status){
        this.status = status;
        return this;
    }

    public CarDTOBuilder withClient(long clientId){
        this.clientId = clientId;
        return this;
    }

    public CarDTO build(){
        return new CarDTO(brandName, modelName, status, clientId);
    }
}