package com.carmen.app.control;

import java.util.List;
//import java.util.UUID;

//import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;
import com.carmen.app.utils.Logged;

/**
 * 
 * Service that generate a CRUD to be used by {@link CarResource}
 * 
 * @author Carmen Piñera Moreno
 *
 */
@Stateless
@Logged
public class CarService {

	@PersistenceContext(unitName = "carP")
	private EntityManager em;

	/**
	 * Obtain every car that exist in the system
	 * 
	 * @return a List of cars {@link Car} using a namendQuery
	 */

	public List<Car> getCars() {
		TypedQuery<Car> query = this.em.createNamedQuery("Car.FindCars", Car.class);
		return query.getResultList();

	}

	/**
	 * Obtain a car by its id
	 * 
	 * @param id of the car in order to search it on the system
	 * @return car Return the car corresponding to a given id {@link Car} entity of
	 *         the database, if found
	 * @throws CarNotFoundException if the id does not match with any {@link Car}
	 *                              entity of the database
	 */

	public Car getCar(String id) throws CarNotFoundException {

		Car car = this.em.find(Car.class, id);
		if (car == null) {
			throw new CarNotFoundException("Car with id " + id + " not found");

		}
		return car;

	}

	/**
	 * 
	 * Attach a new car in the system
	 * 
	 * @param car {@link Car} entity to be attached
	 * @return car {@link Car} entity created
	 */

	public Car createCar(Car car) {
		this.em.persist(car);
		return car;

	}

	/**
	 * Update a car with new data
	 * 
	 * @param car {@link Car} entity to be updated
	 * @return car {@link Car} entity to be updated
	 * @throws CarNotFoundException if the id does not match with any {@link Car}
	 *                              entity of the database
	 */

	public Car updateCar(Car car) throws CarNotFoundException {
		getCar(car.getId());

		car = this.em.merge(car);
		this.em.flush();
		return car;

	}
	
	/**
	 * 
	 * Deletes a car form the database by its id
	 * 
	 * @param id of the {@link Car} entity to be delete
	 * @throws CarNotFoundException if the id does not match with any {@link Car}
	 *                              entity of the database
	 */

	public void deleteCar(String id) throws CarNotFoundException {
		Car car = this.getCar(id);
		this.em.remove(car);
	}

}
