package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.utils.CarStatus;

@Entity
public class Car {
	@Id 
	@GeneratedValue
	private long  carId;
	private String brandName;
	private String modelName;
	private CarStatus status;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="clientId", nullable=true)
	private Client client;

	
	public long getId() {
		return carId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus available) {
		this.status = available;
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

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", brandName=" + brandName + ", modelName=" + modelName + ", status=" + status
				+ ", client=" + client + "]";
	}
	

}
