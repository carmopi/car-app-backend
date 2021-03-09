package com.carmen.app.control;

import java.util.List;
//import java.util.UUID;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;
import com.carmen.app.utils.LoggInterceptor;

@Stateless
@Interceptors(LoggInterceptor.class)
public class CarService {
	
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("carP");
	private EntityManager em = emf.createEntityManager(); 
	
	
	public List<Car> getCars(){
		TypedQuery <Car>  query = this.em.createNamedQuery("Car.FindCars", Car.class);
		return query.getResultList();
		
	}
	
	public Car getCar(String id) throws CarNotFoundException{
		
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
	
	public void deleteCar(String id) throws CarNotFoundException{
		Car car = this.getCar(id);
		this.em.remove(car);
	}
	
	
}
