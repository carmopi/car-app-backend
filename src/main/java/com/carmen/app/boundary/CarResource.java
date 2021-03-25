package com.carmen.app.boundary;

import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.EJB;
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

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;
import com.carmen.app.dto.CarDto;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;

import com.carmen.app.utils.Logged;

/**
 * 
 * Resource that maps {@link Car} API to a method
 * 
 * @author Carmen Piñera Moreno
 *
 */
@Path("/cars")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Logged
public class CarResource implements ICarResource {

	@EJB
	private CarService carService;

	@GET
	public Response getCars() {
		List<CarDto> cars = this.carService.getCars().stream().map(car -> car.convertToDto()).collect(Collectors.toList());
		Response response = Response.status(Status.OK).entity(cars).build();
		return response;

	}

	@GET
	@Path("/{id}")

	public Response getCar(@PathParam("id") String id) {

		try {
			CarDto carDto = this.carService.getCar(id).convertToDto();
			Response response = Response.status(Status.OK).entity(carDto).build();
			return response;

		} catch (CarNotFoundException ex) {
			Response response = Response.status(Status.NOT_FOUND).build();

			return response;
		}

	}

	@PUT
	@Path("/{id}")

	public Response updateCar(@PathParam("id") String id, CarDto carDto) {

		try {

			carDto.setId(id);
			Car updateCarDto = carDto.convertToEntity();
			Response response = Response.status(Status.OK).entity(this.carService.updateCar(updateCarDto)).build();
			return response;

		} catch (CarNotFoundException ex) {
			Response response = Response.status(Status.NOT_FOUND).build();

			return response;

		}

	}

	@POST

	public Response createdCar(CarDto carDto) {

		this.carService.createCar(carDto.convertToEntity()).convertToDto();
		Response response = Response.status(Status.CREATED).entity(carDto).build();

		return response;
	}

	@DELETE
	@Path("/{id}")

	public Response deleteCar(@PathParam("id") String id) {

		try {
			this.carService.deleteCar(id);
			Response response = Response.status(Status.NO_CONTENT).build();
			return response;
		} catch (CarNotFoundException ex) {

			Response response = Response.status(Status.NOT_FOUND).build();

			return response;
		}

	}


}
