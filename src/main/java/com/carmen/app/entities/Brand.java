package com.carmen.app.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "brand")
public class Brand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3114253996370404722L;

	@Id

	private String id;

	@NotNull
	
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonbTransient
	private Set<Car> cars = new HashSet<Car>();

	public Brand() {
		
	}
	
	public Brand(String id, String name) {
	this.id = id;
	this.name = name;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	
	@PrePersist
	private void autoGeneratedId() {
		this.id = UUID.randomUUID().toString();
	}
	
	

	
}