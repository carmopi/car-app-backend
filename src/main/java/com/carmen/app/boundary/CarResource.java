package com.carmen.app.boundary;

import java.util.List;
import java.util.UUID;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;

@Path("/cars")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource{
	
	private static final Logger log = LogManager.getLogger(CarResource.class);
	
	private CarService carService = new CarService();

	@GET
	public List<Car> getCars() {
		log.info("Connecting with getCars");
		return this.carService.getCars();

	}

	@GET
	@Path("/{id}")
	
	public Response getCar(@PathParam("id") UUID id) {
		log.info("Connecting with getCar");
		try {
			Car car = this.carService.getCar(id);
			Response response = Response.status(Status.OK).entity(car).build();
			return response;

		} catch (CarNotFoundException ex) {
			Response response = Response.status(Status.NOT_FOUND).build();
			log.error("Error: Car Not Found");
			return response;
		}

	}

	@PUT
	@Path("/{id}")
	
	public Response updateCar(@PathParam("id") UUID id) {
		log.info("Connecting with updatedCar");
		try {
			Car newCar = this.carService.getCar(id);
			//newCar.setBrand(car.getBrand());
			//newCar.setCountry(car.getCountry());
			//newCar.setRegistration(car.getRegistration());

			this.carService.updateCar(newCar);

			Response response = Response.status(Status.OK).entity(newCar).build();
			return response;

		} catch (CarNotFoundException ex) {
			Response response = Response.status(Status.NOT_FOUND).build();
			
			log.error("Error: Car Not Found");
			return response;

		}

	}

	@POST
	
	public Response createdCar(Car car) {
		log.info("Connecting with createdCar");
		this.carService.createCar(car);
		Response response = Response.status(Status.CREATED).build();

		return response;
	}

	@DELETE
	@Path("/{id}")
	
	public Response deleteCar(@PathParam("id") UUID id) {
		log.info("Connnecting with deleteCar");
		try {
			this.carService.deleteCar(id);
			Response response = Response.status(Status.NO_CONTENT).build();
			return response;
		} catch (CarNotFoundException ex) {

			Response response = Response.status(Status.NOT_FOUND).build();
			log.error("Error: Car Not Found");
			return response;
		}

	}

}
