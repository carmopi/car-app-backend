package com.carmen.app.boundary;

import javax.ws.rs.core.Response;

import com.carmen.app.dto.BrandDto;

import com.carmen.app.entities.Brand;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface IBrandResource {

	/**
	 * 
	 * Get a list of brands existing in the database
	 * 
	 * @param page   number of the pagination
	 * @param size   number of brands
	 * @param filterBy field to filter
	 * @param sort   field to sort
	 * @param orderBy field to order
	 * 
	 * @return response that contains a list with all brands values
	 */
	@Operation(summary = "Get all brands", responses = {
			@ApiResponse(responseCode = "200", description = "Get all brans", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))) })
	public Response getBrands(@Parameter(description = "pages", required = false) int page,
			@Parameter(description = "size of data in one page", required = false) int size,
			@Parameter(description = "value to be searched", required = false) String filterBy,
			@Parameter(description = "value to be order", required = false) String orderBy,
			@Parameter(description = "how data will be sorted", required = false) String sort);

	/**
	 * Get a brand with a given id
	 * 
	 * @param id that belongs to a {@link BrandDto}
	 * @return response that contains either the brand or code 404 if the id does not
	 *         match with any brand in the database
	 */

	@Operation(summary = "Get brand by id", responses = {
			@ApiResponse(responseCode = "200", description = "Get Brand by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))),
			@ApiResponse(responseCode = "404", description = "There is no brand with this id") })
	public Response getBrand(@Parameter(description = "Brand id", required = true) String id);

	/**
	 * Updated a {@link BrandDto} entity by its given id and the request body
	 * 
	 * @param id  of the brand
	 * @param brand {@link BrandDto} to be updated
	 * @return response that contains the update of {@link BrandDto} and the code 200,
	 *         if there is no brand with the given id the response contains the code
	 *         404 and if the request body contains errors, the response contains
	 *         the status code 400
	 */
	@Operation(summary = "Update a brnad", responses = {
			@ApiResponse(responseCode = "201", description = "Brand update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))),
			@ApiResponse(responseCode = "400", description = "Brand not valid"),
			@ApiResponse(responseCode = "404", description = "There is no brand with this id") })
	public Response updateBrand(@Parameter(description = "Brand that needs to be updated", required = true) String id,
			@RequestBody(description = "Updated a Brand", required = true, content = @Content(schema = @Schema(implementation = BrandDto.class))) BrandDto brandDto);

	/**
	 * Create a {@link BrandDto} form the request body
	 * 
	 * @param brand {@link BrandDto} to create
	 * @return response that contains either the created brand and status code 200 of
	 *         there are errors in the request body the response contains status
	 *         code 400
	 */
	@Operation(summary = "Create a new Brand", responses = {
			@ApiResponse(responseCode = "201", description = "Brand created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class))),
			@ApiResponse(responseCode = "400", description = "Brand not valid")

	})
	public Response createdBrand(
			@RequestBody(description = "Created a new Brand", required = true, content = @Content(schema = @Schema(implementation = BrandDto.class))) BrandDto brandDto);

	/**
	 * Delete a {@link BrandDto} from the database
	 * 
	 * @param id of the brand to delete
	 * @return response that contains status code 204 if there is a brand that matches
	 *         with the given id, if the id does not match with any brand in the data
	 *         base, response contains status code 404.
	 */
	@Operation(summary = "Delete a Brand", responses = {
			@ApiResponse(responseCode = "204", description = "The brand has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class))),
			@ApiResponse(responseCode = "404", description = "There is no brand with this id"),
			@ApiResponse(responseCode = "400", description = "Brand not valid") })
	public Response deleteBrand(@Parameter(description = "Brand that need to be deleted", required = true) String id);

}
