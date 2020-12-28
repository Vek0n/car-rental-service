package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.demo.utils.CarStatus;

@Entity
public class Car {
	@Id 
	@GeneratedValue
	private long  id;
	private long clientId;
	private String brandName;
	private String modelName;
	private CarStatus status;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClient_id() {
		return clientId;
	}

	public void setClient_id(long client_id) {
		this.clientId = client_id;
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
	
	public void rentCar(long clientId) {
		this.status = CarStatus.RENTED;
		this.clientId = clientId;
		
	}

	public void returnCar() {
		this.initiateCar();
	}
	
	public void initiateCar() {
		this.status = CarStatus.AVAILABLE;
		this.clientId = 0;
	}
	
	@Override
	public String toString() {
		return "Car [id=" + id + ", client_id=" + clientId + ", brandName=" + brandName + ", modelName=" + modelName
				+ ", status=" + status + "]";
	}	
}
