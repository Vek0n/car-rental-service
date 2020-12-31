package com.example.demo.domain.client;
import com.example.demo.domain.car.Car;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Client {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long  clientId;
	private String firstName;
	private String secondName;
	
	@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
	private List<Car> cars;

	Client(long clientId, String firstName, String secondName, List<Car> cars) {
		this.clientId = clientId;
		this.firstName = firstName;
		this.secondName = secondName;
		this.cars = cars;
	}

	public Client(){}

	public long getId() {
		return clientId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
}
