package com.example.demo.domain.car;

import javax.persistence.*;

import com.example.demo.domain.client.Client;
import net.minidev.json.annotate.JsonIgnore;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    private String brandName;
    private String modelName;
    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", nullable = true)
    private Client client;

    private Car() {
    }

    public Car(long id, CarDTO car, Client client){
        this.carId = id;
        this.brandName = car.getBrandName();
        this.modelName = car.getModelName();
        this.status = car.getStatus();
        this.client = client;
    }

    Car(long carId, String brandName, String modelName, CarStatus status, Client client) {
        this.carId = carId;
        this.brandName = brandName;
        this.modelName = modelName;
        this.status = status;
        this.client = client;
    }

    public long getCarId() {
        return carId;
    }

    public Client getClient() {
        return client;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void rentCar(Client client) {
        this.status = CarStatus.RENTED;
        this.client = client;
    }

    public void returnCar() {
        this.initiateCar();
    }

    public void initiateCar() {
        this.status = CarStatus.AVAILABLE;
        this.client = null;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return this.client == null;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", brandName='" + brandName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", status=" + status +
                ", client=" + client +
                '}';
    }
}
