package com.carmen.app.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.carmen.app.entities.Brand;
import com.carmen.app.entities.Car;
import com.carmen.app.entities.Country;



/**
 * Data Transfer Object 
 * 
 * @author Carmen Piñera Moreno
 *
 */


public class CarDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private Brand brand;
	private LocalDateTime registration;
	private Country country;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdate;
	
	public CarDto() {
		
	}
	
	public CarDto(Brand brand, LocalDateTime registration, Country country) {
		this.brand = brand;
		this.registration = registration;
		this.country = country;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public LocalDateTime getRegistration() {
		return registration;
	}
	public void setRegistration(LocalDateTime registration) {
		this.registration = registration;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Car convertToEntity() {
		ModelMapper modelMapper = new ModelMapper();
		Car car = modelMapper.map(this, Car.class);
		return car;
	}

}
