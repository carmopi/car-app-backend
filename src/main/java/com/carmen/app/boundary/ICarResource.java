package com.carmen.app.boundary;

import java.util.List;
//import java.util.UUID;

import javax.ws.rs.core.Response;

import com.carmen.app.entities.Car;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ICarResource {

	@Operation(summary = "Get all cars", responses = {
			@ApiResponse(responseCode = "200", description = "Get all cars", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))) })
	public List<Car> getCars();

	@Operation(summary = "Get car by id", responses = {
			@ApiResponse(responseCode = "200", description = "Get car by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "404", description = "There is no car with this id") })
	public Response getCar(@Parameter(description = "Car id", required = true) String id);

	@Operation(summary = "Update a car", responses = {
			@ApiResponse(responseCode = "201", description = "Car update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "400", description = "Car not valid"),
			@ApiResponse(responseCode = "404", description = "There is no car with this id") })
	public Response updateCar(@Parameter(description = "Car that needs to be updated", required = true) String id);

	@Operation(summary = "Create a new Car", responses = {
			@ApiResponse(responseCode = "201", description = "Car created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "400", description = "Car not valid")

	})
	public Response createdCar(
			@RequestBody(description = "Created a new Car", required = true, content = @Content(schema = @Schema(implementation = Car.class))) Car car);

	@Operation(summary = "Delete a Car", responses = {
			@ApiResponse(responseCode = "204", description = "The car has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "404", description = "There is no car with this id"),
			@ApiResponse(responseCode = "400", description = "Car not valid") })
	public Response deleteCar(@Parameter(description = "Car that need to be deleted", required = true) String id);

} 
