package com.carmen.app.boundary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.carmen.app.control.CarService;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CarResourceTest {

	@InjectMocks
	private CarResource carResource;

	@Mock
	private CarService carService;

	private Car car;

	private String carId = "dff6b9ba-8579-11eb-8dcd-0242ac130003";

	@Before
	public void setUp() throws Exception {
		car = new Car();
		car.setId(carId);
		car.setBrand("Ford");
		car.setCountry("USA");
		car.setRegistration(LocalDateTime.now());
		

	}

	@Test
	public void testCreatedCar_ShouldReturnCreated() {
		when(this.carService.createCar(car)).thenReturn(car);
		Response response = this.carResource.createdCar(car);
		assertEquals(car, response.getEntity());
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

	}

	@Test
	public void testGetAllCars_ShouldReturnOK() {
		when(this.carService.getCars()).thenReturn(new ArrayList<Car>());
		List<Car> carsExpected = new ArrayList<Car>();
		Response response = this.carResource.getCars();
		assertEquals(carsExpected, response.getEntity());
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void testupdateCar_ShouldReturnOk() throws CarNotFoundException {
		car.setId(carId);

		when(this.carService.updateCar(car)).thenReturn(car);
		Response response = this.carResource.updateCar(carId, car);
		assertEquals(car, response.getEntity());
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void testDeleteACar_ShouldReturnNoContent() throws CarNotFoundException {

		doNothing().when(this.carService).deleteCar(carId);
		Response response = this.carResource.deleteCar(carId);
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

	}

	@Test
	public void testGetACarWithValidId_ShouldReturnOK() throws CarNotFoundException {

		when(this.carService.getCar(car.getId())).thenReturn(car);
		Response response = this.carResource.getCar(car.getId());
		assertEquals(car, response.getEntity());
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void testGetCarWithNoValidId_ShouldReturnBadRequest() throws CarNotFoundException {
		String id = "377ccbea-864a-11eb-8dcd-0242ac130003";
		when(this.carService.getCar(id)).thenThrow(CarNotFoundException.class);
		Response response = this.carResource.getCar(id);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

	}

	@Test
	public void testDeleteWrongCar_ShouldReturnBadRequest() throws CarNotFoundException {
		String id = "377ccbea-864a-11eb-8dcd-0242ac130003";
		doThrow(CarNotFoundException.class).when(this.carService).deleteCar(id);
		Response response = this.carResource.deleteCar(id);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testUpdateWrongCar_ShouldReturnBadRequest() throws CarNotFoundException {
		String id = "377ccbea-864a-11eb-8dcd-0242ac130003";
		when(this.carService.getCar(id)).thenReturn(car);
		doThrow(CarNotFoundException.class).when(this.carService).updateCar(car);
		Response response = this.carResource.updateCar(id, car);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
