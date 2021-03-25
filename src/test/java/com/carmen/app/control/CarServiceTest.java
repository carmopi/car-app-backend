package com.carmen.app.control;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;
import com.google.common.base.Verify;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@InjectMocks
	private CarService carService;

	@Mock
	private PersistenceService<Car, String> persistenceService;

	private Car car;

	private String id = "dff6b9ba-8579-11eb-8dcd-0242ac130003";

	@Before
	public void setUp() throws Exception {
		car = new Car();
		car.setId(id);
		car.setBrand("Ford");
		car.setCountry("USA");
		car.setRegistration(LocalDateTime.now());
	}

	@Test
	public void testCarCreated() {
		doNothing().when(this.persistenceService).createNew(car);
		assertEquals(car, carService.createCar(car));
		verify(this.persistenceService).createNew(car);
	}

	@Test
	public void testGetCars() {
		when(this.persistenceService.getAll("Car.FindCars", Car.class)).thenReturn(new ArrayList<Car>());
		List<Car> expectedCars = new ArrayList<Car>();
		List<Car> cars = this.carService.getCars();
		assertEquals(expectedCars, cars);

	}

	@Test
	public void testGetACar() throws CarNotFoundException {
		when(this.persistenceService.getById(Car.class, id)).thenReturn(car);
		assertEquals(car, carService.getCar(id));
		verify(this.persistenceService).getById(Car.class, id);
	}

	@Test
	public void testUpdateCar() throws CarNotFoundException {
		when(this.persistenceService.getById(Car.class, id)).thenReturn(car);
		when(this.persistenceService.updateOne(car)).thenReturn(car);
		assertEquals(car, carService.updateCar(car));
		verify(this.persistenceService).updateOne(car);
	}

	@Test
	public void testDeleteCar() throws CarNotFoundException {
		when(this.persistenceService.getById(Car.class, id)).thenReturn(car);
		doNothing().when(this.persistenceService).deleteOne(car);
		carService.deleteCar(id);
		verify(this.persistenceService).deleteOne(car);

	}

	@Test(expected = CarNotFoundException.class)
	public void testGetWrongCar() throws CarNotFoundException {
		// String carId = "f5b4afda-870b-11eb-8dcd-0242ac130003";
		when(this.persistenceService.getById(Car.class, id)).thenReturn(null);
		carService.getCar(id);
		verify(this.persistenceService).getById(Car.class, id);

	}

	@Test(expected = CarNotFoundException.class)
	public void testUpdateWrongCar() throws CarNotFoundException {
		when(this.persistenceService.getById(Car.class, id)).thenReturn(null);
		carService.updateCar(car);
		verify(this.persistenceService.getById(Car.class, id));

	}

	@Test(expected = CarNotFoundException.class)
	public void testDeleteWrongCar() throws CarNotFoundException {
		when(this.persistenceService.getById(Car.class, id)).thenReturn(null);
		carService.deleteCar(id);
		verify(this.persistenceService.getById(Car.class, id));
		verify(this.persistenceService).deleteOne(car);
	}

}
