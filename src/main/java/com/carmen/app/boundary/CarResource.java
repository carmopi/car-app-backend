package com.carmen.app.boundary;



import java.util.List;
//import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.EJB;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;
import com.carmen.app.dto.CarDto;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.jms.Publisher;
import com.carmen.app.utils.Logged;
import com.carmen.app.utils.Secured;


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

	
	@Context
	SecurityContext securityContext;
	
	@Inject
	private Publisher publisher;
	
	@GET
	@Secured
	
	public Response getCars(@DefaultValue("0") @QueryParam(value = "page") int page,
			@DefaultValue("20") @QueryParam(value = "size") int size,
		 @QueryParam(value = "filterBy") String filterBy,
			@QueryParam(value = "orderBy") String orderBy,
			@QueryParam(value= "sort") @DefaultValue("asc") String sort) {
		
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
			List<CarDto> cars = this.carService.getCars(page, size, filterBy, sort, orderBy).stream().map(car -> car.convertToDto()).collect(Collectors.toList());
		
		return Response.status(Status.OK).entity(cars).build();
		}
		else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}

	@GET
	@Path("/{id}")
	@Secured
	public Response getCar(@PathParam("id") String id) {
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
		try {
			CarDto carDto = this.carService.getCar(id).convertToDto();
			return Response.status(Status.OK).entity(carDto).build();
			

		} catch (EntityNotFoundException ex) {
			return Response.status(Status.NOT_FOUND).build();

			
		}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@PUT
	@Path("/{id}")
	@Secured
	public Response updateCar(@PathParam("id") String id, CarDto carDto) {
		if(securityContext.isUserInRole("admin")) {
			
		
		try {

			carDto.setId(id);
			
			Response response = Response.status(Status.OK).entity(this.carService.updateCar(carDto.convertToEntity())).build();
			
			publisher.send(carDto.getId());
			return response;

		} catch (EntityNotFoundException ex) {
			Response response = Response.status(Status.NOT_FOUND).build();

			return response;
}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@POST
	@Secured
	public Response createdCar(CarDto carDto) {

		if(securityContext.isUserInRole("admin")) {
			
		
		this.carService.createCar(carDto.convertToEntity()).convertToDto();
		Response response = Response.status(Status.CREATED).entity(carDto).build();

		return response;
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Secured
	@Path("/{id}")

	public Response deleteCar(@PathParam("id") String id) {
		if(securityContext.isUserInRole("admin")) {
		try {
			//this.carService.deleteCar(id);
			this.carService.softDelete(id);
			return Response.status(Status.NO_CONTENT).build();
			
		} catch (EntityNotFoundException ex) {

			return Response.status(Status.NOT_FOUND).build();

		}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	

}
