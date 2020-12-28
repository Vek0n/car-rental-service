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
	private long client_id;
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
		return client_id;
	}


	public void setClient_id(long client_id) {
		this.client_id = client_id;
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


	@Override
	public String toString() {
		return "Car [id=" + id + ", client_id=" + client_id + ", brandName=" + brandName + ", modelName=" + modelName
				+ ", status=" + status + "]";
	}



	
	
}
