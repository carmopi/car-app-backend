package com.carmen.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "cars")
public class Car implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "BRAND_CAR")
	private String brand;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Registration date")
	private Date registration;
	
	@Column(name = "Country")
	private String country;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created")
	private Date created_at;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Last Update")
	private Date last_update;
	
	
	public Car () {	
		
	}
	
	public Car(UUID id, String brand, Date registration, String country, Date created_at, Date last_update) {
		this.brand = brand;
		this.registration = new Date(); 
		this.country = country;
		this.created_at = new Date();
		this.last_update = new Date();
		
	
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
	
	
	public Date getRegistration() {
		return registration;
	}
	
	
	public void setRegistration(Date registration) {
		this.registration = registration;
	}
	
	
	public String getCountry() {
		return country;
	}
	
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	public Date getCreated_at() {
		return created_at;
	}
	
	
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	
	public Date getLast_update() {
		return last_update;
	}
	
	
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", registration=" + registration + ", country=" + country
				+ ", created_at=" + created_at + ", last_update=" + last_update + "]";
	}
	
	
	
	
}
