package com.carmen.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "cars")
@NamedQuery(name = "FindCars", query ="SELECT a FROM Car a")
public class Car implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "brand_car")
	private String brand;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name ="registration_date")
	private LocalDateTime registration;
	
	@Column(name = "country")
	private String country;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	@UpdateTimestamp
	private LocalDateTime lastUpdate;
	
	
	public Car () {	
		
	}
	
	public Car(String brand, LocalDateTime registration, String country) {
		this.brand = brand;
		this.registration = registration; 
		this.country = country;

	}
	
	public UUID getId() {
		return id;
	}
	
	
	public void setId(UUID id) {
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
	
	
	public LocalDateTime getCreated_at() {
		return createdAt;
	}
	
	
	public void setCreated_at(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	public LocalDateTime getLast_update() {
		return lastUpdate;
	}
	
	
	public void setLast_update(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", registration=" + registration + ", country=" + country
				+ ", created_at=" + createdAt + ", last_update=" + lastUpdate + "]";
	}
	
	
	
	
}
