package com.carmen.app.services;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import com.carmen.app.entities.Car;

public class CarService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("carP");
	private EntityManager em = emf.createEntityManager(); 
	
	private Car car = new Car();
	
	public Car getCar(UUID id) {
	 em.find(Car.class, id);
		if(car != null) {
			return car;
		}
	return null;
	}
	
	
	public void createCar(Car car) {
		em.persist(car);
	
	}
	
	public void updateCar(UUID id) {
	em.find(Car.class, id);
		em.merge(car);
	}
	
	public void deleteCar(UUID id) {
		em.find(Car.class, id);
		em.remove(car);
	}
	
	
}
