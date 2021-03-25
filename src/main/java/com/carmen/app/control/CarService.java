package com.carmen.app.control;

import java.util.List;
//import java.util.UUID;

import javax.ejb.EJB;
//import javax.ejb.EJB;
import javax.ejb.Stateless;


//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;
import com.carmen.app.utils.Logged;

/**
 * 
 * Service that generate a CRUD to be used by the CarResource
 * 
 * @author Carmen Piñera Moreno
 *
 */
@Stateless
@Logged
public class CarService {

	@EJB
	private PersistenceService<Car, String> persistenceService;

	/**
	 * Obtain every car that exist in the system
	 * 
	 * @return a List of cars {@link Car} using a namendQuery
	 */

	public List<Car> getCars() {
		
		return this.persistenceService.getAll("Car.FindCars", Car.class);

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

		Car car = this.persistenceService.getById(Car.class, id);
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
		this.persistenceService.createNew(car);
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

		car = this.persistenceService.updateOne(car);
		
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
		this.persistenceService.deleteOne(car);
	}

}
