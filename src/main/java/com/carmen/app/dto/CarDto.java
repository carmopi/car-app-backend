package com.carmen.app.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.carmen.app.entities.Car;



/**
 * Data Transfer Object 
 * 
 * @author Carmen Piñera Moreno
 *
 */


public class CarDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String brand;
	private LocalDateTime registration;
	private String country;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public LocalDateTime getRegistration() {
		return registration;
	}
	public void setRegistration(LocalDateTime registration) {
		this.registration = registration;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Car convertToEntity() {
		ModelMapper modelMapper = new ModelMapper();
		Car car = modelMapper.map(this, Car.class);
		return car;
	}

}
