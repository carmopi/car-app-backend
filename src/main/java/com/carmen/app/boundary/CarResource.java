package com.carmen.app.boundary;

import java.util.List;
import java.util.UUID;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.CarNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
@Path("/cars")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource{
	
	private static final Logger log = LogManager.getLogger(CarResource.class);
	
	private CarService carService = new CarService();

	@GET
	@Operation(summary = "Get all cars",
					responses = {
							@ApiResponse(responseCode = "200",
									description = "Get all cars",
									content =  @Content(mediaType = "application/json",
									schema = @Schema(implementation = Car.class)))
					})
	public List<Car> getCars() {
		log.info("Connecting with getCars");
		return this.carService.getCars();

	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Get car by id",
				responses = {
						@ApiResponse(responseCode = "200",
								description = "Get car by id",
								content = @Content(mediaType = "application/json",
								schema = @Schema(implementation = Car.class))),
						@ApiResponse(responseCode = "404",
									description = "There is no car with this id")
				})
	public Response getCar(@Parameter(description = "Car id", required = true) UUID id) {
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
	@Operation(summary = "Update a car",
				responses = {
						@ApiResponse(responseCode = "201",
								description = "Car update", 
								content = @Content(mediaType = "application/json",
								schema = @Schema(implementation = Car.class))),
							@ApiResponse(responseCode = "400",
								description = "Car not valid"),
							@ApiResponse(responseCode = "404",
										description = "There is no car with this id")	
				})
	public Response updateCar(@Parameter(description = "Car that needs to be updated", required = true) UUID id) {
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
	@Operation(summary = "Create a new Car",      
				responses = {
						@ApiResponse(responseCode = "201",
								description = "Car created",
								content= @Content(mediaType = "application/json",
								schema = @Schema(implementation = Car.class))),
						@ApiResponse(responseCode = "400",    
									description = "Car not valid")
						
								
								
	})
	public Response createdCar(@RequestBody(description = "Created a new Car", required = true,
									content = @Content(schema = @Schema(implementation = Car.class))) Car car) {
		log.info("Connecting with createdCar");
		this.carService.createCar(car);
		Response response = Response.status(Status.CREATED).build();

		return response;
	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Delete a Car",
				responses =  {
						@ApiResponse(responseCode = "204", 
								description = "The car has been deleted", 
								content = @Content(mediaType = "application/json", 
								schema = @Schema(implementation = Car.class))),
						@ApiResponse(responseCode = "404", 
								description = "There is no car with this id"),	
						@ApiResponse(responseCode = "400",    
								description = "Car not valid")
				})
	public Response deleteCar(@Parameter(description = "Car that need to be deleted", required = true) UUID id) {
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
