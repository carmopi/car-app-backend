package com.carmen.app.boundary;

import javax.ws.rs.core.Response;

import com.carmen.app.dto.CountryDto;

import com.carmen.app.entities.Country;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ICountryResource {

	/**
	 * 
	 * Get a list of Countries existing in the database
	 * 
	 * @param page   number of the pagination
	 * @param size   number of Countries
	 * @param filterBy field to filter
	 * @param sort   field to sort
	 * @param orderBy field to order
	 * 
	 * @return response that contains a list with all Countries values
	 */
	@Operation(summary = "Get all Countries", responses = {
			@ApiResponse(responseCode = "200", description = "Get all countries", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))) })
	public Response getCountries(@Parameter(description = "pages", required = false) int page,
			@Parameter(description = "size of data in one page", required = false) int size,
			@Parameter(description = "value to be searched", required = false) String filterBy,
			@Parameter(description = "value to be order", required = false) String orderBy,
			@Parameter(description = "how data will be sorted", required = false) String sort);

	/**
	 * Get a Country with a given id
	 * 
	 * @param id that belongs to a {@link CountryDto}
	 * @return response that contains either the Country or code 404 if the id does not
	 *         match with any Country in the database
	 */

	@Operation(summary = "Get Country by id", responses = {
			@ApiResponse(responseCode = "200", description = "Get Country by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
			@ApiResponse(responseCode = "404", description = "There is no Country with this id") })
	public Response getCountry(@Parameter(description = "Country id", required = true) String id);

	/**
	 * Updated a {@link CountryDto} entity by its given id and the request body
	 * 
	 * @param id  of the Country
	 * @param Country {@link CountryDto} to be updated
	 * @return response that contains the update of {@link CountryDto} and the code 200,
	 *         if there is no Country with the given id the response contains the code
	 *         404 and if the request body contains errors, the response contains
	 *         the status code 400
	 */
	@Operation(summary = "Update a brnad", responses = {
			@ApiResponse(responseCode = "201", description = "Country update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
			@ApiResponse(responseCode = "400", description = "Country not valid"),
			@ApiResponse(responseCode = "404", description = "There is no Country with this id") })
	public Response updateCountry(@Parameter(description = "Country that needs to be updated", required = true) String id,
			@RequestBody(description = "Updated a Country", required = true, content = @Content(schema = @Schema(implementation = CountryDto.class))) CountryDto CountryDto);

	/**
	 * Create a {@link CountryDto} form the request body
	 * 
	 * @param Country {@link CountryDto} to create
	 * @return response that contains either the created Country and status code 200 of
	 *         there are errors in the request body the response contains status
	 *         code 400
	 */
	@Operation(summary = "Create a new Country", responses = {
			@ApiResponse(responseCode = "201", description = "Country created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDto.class))),
			@ApiResponse(responseCode = "400", description = "Country not valid")

	})
	public Response createdCountry(
			@RequestBody(description = "Created a new Country", required = true, content = @Content(schema = @Schema(implementation = CountryDto.class))) CountryDto CountryDto);

	/**
	 * Delete a {@link CountryDto} from the database
	 * 
	 * @param id of the Country to delete
	 * @return response that contains status code 204 if there is a Country that matches
	 *         with the given id, if the id does not match with any Country in the data
	 *         base, response contains status code 404.
	 */
	@Operation(summary = "Delete a Country", responses = {
			@ApiResponse(responseCode = "204", description = "The Country has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDto.class))),
			@ApiResponse(responseCode = "404", description = "There is no Country with this id"),
			@ApiResponse(responseCode = "400", description = "Country not valid") })
	public Response deleteCountry(@Parameter(description = "Country that need to be deleted", required = true) String id);

}
