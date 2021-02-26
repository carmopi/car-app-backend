package com.carmen.app.services;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.carmen.app.entities.Car;

@Stateless
public class CarService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("carP");
	private EntityManager em = emf.createEntityManager(); 
	
	
	public List<Car> getCars(){
		TypedQuery <Car>  query = em.createNamedQuery("FindAllCars", Car.class);
		return query.getResultList();
		
	}
	
	public Car getCar(UUID id) {
		Car car =  em.find(Car.class, id);
		if(car != null) {
			return car;
		}
	return null;
	}
	
	
	public void createCar(Car car) {
		em.persist(car);
	
	}
	
	public void updateCar(Car car) {
		getCar(car.getId());
		em.merge(car);
		
	}
	
	public void deleteCar(UUID id) {
		Car car = em.find(Car.class, id);
		em.remove(car);
	}
	
	
}
