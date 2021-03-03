package com.carmen.app.control;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;

@Stateless
public class CarService {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("carP");
	private EntityManager em = emf.createEntityManager(); 
	
	
	public List<Car> getCars(){
		TypedQuery <Car>  query = this.em.createNamedQuery("Car.FindCars", Car.class);
		return query.getResultList();
		
	}
	
	public Car getCar(UUID id) throws CarNotFoundException{
		Car car =  this.em.find(Car.class, id);
		if(car == null) {
			throw new CarNotFoundException("Car with id " + id + " not found");
		}
		return car;
		
	}
	
	
	public void createCar(Car car) {
		this.em.persist(car);
	
	}
	
	public void updateCar(Car car) throws CarNotFoundException {
		Car carUpdate = getCar(car.getId());
		this.em.merge(carUpdate);

	}
	
	public void deleteCar(UUID id) throws CarNotFoundException{
		Car car = this.getCar(id);
		this.em.remove(car);
	}
	
	
}
