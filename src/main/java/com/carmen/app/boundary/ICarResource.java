package com.carmen.app.boundary;

import java.util.List;
//import java.util.UUID;

import javax.ws.rs.core.Response;

import com.carmen.app.dto.CarDto;
import com.carmen.app.entities.Car;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ICarResource {

	/**
	 * 
	 * Get a list of cars existing in the database
	 * @param page number of the pagination
	 * @param size number of cars
	 * @param filter field to filter
	 * @param sort  field to order
	 * 
	 * @return response that contains a list with all cars values
	 */
	@Operation(summary = "Get all cars", responses = {
			@ApiResponse(responseCode = "200", description = "Get all cars", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))) })
	public Response getCars(int page, int size, String filter, String orderBy);

	/**
	 * Get a car with a given id
	 * 
	 * @param id that belongs to a {@link CarDto} 
	 * @return response that contains either the car or code 404 if the id does not
	 *         match with any car in the database
	 */

	@Operation(summary = "Get car by id", responses = {
			@ApiResponse(responseCode = "200", description = "Get car by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "404", description = "There is no car with this id") })
	public Response getCar(@Parameter(description = "Car id", required = true) String id);

	/**
	 * Updated a {@link CarDto} entity by its given id and the request body
	 * 
	 * @param id  of the car
	 * @param car {@link CarDto} to be updated
	 * @return response that contains the update of {@link CarDto} and the code 200, if
	 *         there is no car with the given id the response contains the code 404
	 *         and if the request body contains errors, the response contains the
	 *         status code 400
	 */
	@Operation(summary = "Update a car", responses = {
			@ApiResponse(responseCode = "201", description = "Car update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
			@ApiResponse(responseCode = "400", description = "Car not valid"),
			@ApiResponse(responseCode = "404", description = "There is no car with this id") })
	public Response updateCar(@Parameter(description = "Car that needs to be updated", required = true) String id,
			@RequestBody(description = "Updated a Car", required = true, content = @Content(schema = @Schema(implementation = CarDto.class))) CarDto carDto);

	/**
	 * Create a {@link CarDto} form the request body
	 * 
	 * @param car {@link CarDto}  to create
	 * @return response that contains either the created car and status code 200 of
	 *         there are errors in the request body the response contains status
	 *         code 400
	 */
	@Operation(summary = "Create a new Car", responses = {
			@ApiResponse(responseCode = "201", description = "Car created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDto.class))),
			@ApiResponse(responseCode = "400", description = "Car not valid")

	})
	public Response createdCar(
			@RequestBody(description = "Created a new Car", required = true, content = @Content(schema = @Schema(implementation = CarDto.class))) CarDto carDto);

	/**
	 * Delete a {@link CarDto} from the database
	 * 
	 * @param id of the car to delete
	 * @return response that contains status code 204 if there is a car that matches
	 *         with the given id, if the id does not match with any car in the data
	 *         base, response contains status code 404.
	 */
	@Operation(summary = "Delete a Car", responses = {
			@ApiResponse(responseCode = "204", description = "The car has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDto.class))),
			@ApiResponse(responseCode = "404", description = "There is no car with this id"),
			@ApiResponse(responseCode = "400", description = "Car not valid") })
	public Response deleteCar(@Parameter(description = "Car that need to be deleted", required = true) String id);

}
