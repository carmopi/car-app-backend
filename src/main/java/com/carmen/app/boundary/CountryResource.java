package com.carmen.app.boundary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
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

import com.carmen.app.control.CountryService;
import com.carmen.app.dto.CountryDto;

import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;
import com.carmen.app.utils.Secured;

/**
 * 
 * @author Carmen Piñera Moreno
 *
 */


@Path("/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Logged
public class CountryResource implements ICountryResource {

	@EJB 
	private CountryService countryService;
	
	@Context
	SecurityContext securityContext;
	
	
	@GET
	@Secured
	public Response getCountries(@DefaultValue("0") @QueryParam(value = "page") int page,
			@DefaultValue("20") @QueryParam(value = "size") int size,
		 @QueryParam(value = "filterBy") String filterBy,
			@QueryParam(value = "orderBy") String orderBy,
			@QueryParam(value= "sort") @DefaultValue("asc") String sort) {
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
		List<CountryDto> countries = this.countryService.getCountries(page, size, filterBy, sort, orderBy).stream().map(country -> country.convertToDto()).collect(Collectors.toList());
		
		return Response.status(Status.OK).entity(countries).build();
		
	}
		else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		}
	
	
	@GET
	@Path("/{id}")
	@Secured
	public Response getCountry(@PathParam("id") String id) {
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
		try {
			CountryDto countryDto = this.countryService.getCountry(id).convertToDto();
			return Response.status(Status.OK).entity(countryDto).build();
			
		}catch (EntityNotFoundException ex) {
			return Response.status(Status.NOT_FOUND).build();
		}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	

	@PUT
	@Path("/{id}")
	@Secured
	public Response updateCountry(@PathParam("id") String id, CountryDto countryDto) {
		if(securityContext.isUserInRole("admin")) {
		try {

			countryDto.setId(id);
			
			return Response.status(Status.OK).entity(this.countryService.updateCountry(countryDto.convertToEntity())).build();
			

		} catch (EntityNotFoundException ex) {
		return Response.status(Status.NOT_FOUND).build();

		}

		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}
	
	@POST
	@Secured
	public Response createdCountry(CountryDto countryDto) {
		if(securityContext.isUserInRole("admin")) {
		this.countryService.createCountry(countryDto.convertToEntity()).convertToDto();
		Response response = Response.status(Status.CREATED).entity(countryDto).build();

		return response;
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Path("/{id}")

	public Response deleteCountry(@PathParam("id") String id) {
		if(securityContext.isUserInRole("admin")) {
		try {
			this.countryService.deleteCountry(id);
			return Response.status(Status.NO_CONTENT).build();
		
		} catch (EntityNotFoundException ex) {

			return Response.status(Status.NOT_FOUND).build();
		}
		
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	
	
	
}
