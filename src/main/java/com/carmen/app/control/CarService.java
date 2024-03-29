package com.carmen.app.control;


import java.time.LocalDateTime;
import java.util.List;
//import java.util.UUID;


import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;

import jdk.internal.org.jline.utils.Log;

/**
 * 
 * Service that generate a CRUD to be used by the CarResource
 * 
 * @author Carmen Pi�era Moreno
 *
 */
@Stateless
@Logged
public class CarService {

	@EJB
	private PersistenceService<Car, String> persistenceService;

	
	
		
	/**
	 * Obtain every car that exist in the system
	* @param page     page number of the pagination
	 * @param size     number of cars.
	 * @param filterBy {@link Car} Field to be filtered by.
	 * @param orderBy  {@link Car} Field to be ordered by.
	 * @return cars List that contains all of the {@link Car} entities.
	 */
	public List<Car> getCars(int page, int size, String filterBy,String sort, String orderBy) {
		return this.persistenceService.getAll(Car.class, sort, size, page, filterBy, orderBy);
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

	public Car getCar(String id) throws EntityNotFoundException {

		Car car = this.persistenceService.getById(Car.class, id);
		if (car == null) {
			throw new EntityNotFoundException("Car with id " + id + " not found");

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

	public Car updateCar(Car car) throws EntityNotFoundException {
		Car carToUpdate = getCar(car.getId());
		if(carToUpdate == null) {
			throw new EntityNotFoundException("Car with id " + car.getId() + " not found");
		}
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

	public void deleteCar(String id) throws EntityNotFoundException {
		Car car = this.getCar(id);
		this.persistenceService.deleteOne(car);
	}
	
	
	@Transactional
	public Car softDelete(String id) throws EntityNotFoundException {
		Car carToDelete = this.persistenceService.getById(Car.class, id);
		if(carToDelete == null) {
			return null;
		}else {
			carToDelete.setToDelete(true);
			this.persistenceService.updateOne(carToDelete);
			return carToDelete;
		}
	}
	
	public void deleteMarkedCars() {
		this.persistenceService.executeNamedQuery("Car.deletedMarkedCars");
		}

}
