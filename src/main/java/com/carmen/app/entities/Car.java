package com.carmen.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "cars")
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
	private LocalDateTime created_at;
	
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	@UpdateTimestamp
	private LocalDateTime last_update;
	
	
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
		return created_at;
	}
	
	
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	
	
	public LocalDateTime getLast_update() {
		return last_update;
	}
	
	
	public void setLast_update(LocalDateTime last_update) {
		this.last_update = last_update;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", registration=" + registration + ", country=" + country
				+ ", created_at=" + created_at + ", last_update=" + last_update + "]";
	}
	
	
	
	
}
