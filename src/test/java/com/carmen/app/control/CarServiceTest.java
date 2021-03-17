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
	private EntityManager em;

	@Mock
	TypedQuery<Car> query;

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
		doNothing().when(this.em).persist(Mockito.any());
		assertEquals(car, carService.createCar(car));
		verify(em).persist(Mockito.any());
	}

	@Test
	public void testGetCars() {
		List<Car> cars = new ArrayList<>();

		when(em.createNamedQuery("Car.FindCars", Car.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(cars);
		assertEquals(cars, carService.getCars());
		verify(em).createNamedQuery("Car.FindCars", Car.class);
		verify(query).getResultList();

	}

	@Test
	public void testGetACar() throws CarNotFoundException {
		when(em.find(Car.class, id)).thenReturn(car);
		assertEquals(car, carService.getCar(id));
		verify(em).find(Car.class, id);
	}

	@Test
	public void testUpdateCar() throws CarNotFoundException {
		when(em.find(Car.class, id)).thenReturn(car);
		when(em.merge(car)).thenReturn(car);
		assertEquals(car, carService.updateCar(car));
		verify(em).merge(car);
	}

	@Test
	public void testDeleteCar() throws CarNotFoundException {
		when(em.find(Car.class, id)).thenReturn(car);
		doNothing().when(em).remove(car);
		carService.deleteCar(id);
		verify(em).remove(car);

	}

	@Test(expected = CarNotFoundException.class)
	public void testGetWrongCar() throws CarNotFoundException {
		// String carId = "f5b4afda-870b-11eb-8dcd-0242ac130003";
		when(em.find(Car.class, id)).thenReturn(null);
		carService.getCar(id);
		verify(em).persist(car);

	}

	@Test(expected = CarNotFoundException.class)
	public void testUpdateWrongCar() throws CarNotFoundException {
		when(em.find(Car.class, id)).thenReturn(null);
		carService.updateCar(car);
		verify(em.find(Car.class, id));

	}

	@Test(expected = CarNotFoundException.class)
	public void testDeleteWrongCar() throws CarNotFoundException {
		when(em.find(Car.class, id)).thenReturn(null);
		carService.deleteCar(id);
		verify(em.find(Car.class, id));
		verify(em).remove(car);
	}

}
