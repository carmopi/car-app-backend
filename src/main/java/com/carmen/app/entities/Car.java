package com.carmen.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.*;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.carmen.app.dto.CarDto;
import org.modelmapper.ModelMapper;

/**
 * Class that represent the Car entity
 * 
 * @author Carmen Pi�era Moreno
 *
 */
@Entity
@Table(name = "cars")
@NamedQuery(name = "Car.FindCars", query = "SELECT a FROM Car a")
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	
	private String id;

	
	@ManyToOne
	@NotNull(message = "Brand is required")
	private Brand brand;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration")
	@NotNull(message = "Registration date is required")
	private LocalDateTime registration;

	@ManyToOne
	@NotNull(message = "Country is required")
	private Country country;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	@CreationTimestamp
	private LocalDateTime createdAt;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastUpdate")
	@UpdateTimestamp
	private LocalDateTime lastUpdate;

	public Car() {

	}

	public Car(Brand brand, LocalDateTime registration, Country country) {
	
		this.brand = brand;
		this.registration = registration;
		this.country = country;
		
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

	@PrePersist
	private void autoGeneratedId() {
		this.id = UUID.randomUUID().toString();
	}
	
	public CarDto convertToDto() {
		ModelMapper modelMapper = new ModelMapper();
		CarDto carDto = modelMapper.map(this, CarDto.class);
		return carDto;
		
	}
}
