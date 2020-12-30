package com.example.demo.model;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client {
	@Id 
	@GeneratedValue
	private long  clientId;
	private String firstName;
	private String secondName;
	
	@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
	private Set<Car> car;
	
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
