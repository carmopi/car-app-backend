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

import com.carmen.app.control.BrandService;
import com.carmen.app.dto.BrandDto;

import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;
import com.carmen.app.utils.Secured;

/**
 * 
 * @author Carmen Piñera Moreno
 *
 */


@Path("/brands")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Logged
public class BrandResource implements IBrandResource {

	@EJB 
	private BrandService brandService;
	
	
	@Context
	SecurityContext securityContext;
	
	@GET
	@Secured
	public Response getBrands(@DefaultValue("0") @QueryParam(value = "page") int page,
			@DefaultValue("20") @QueryParam(value = "size") int size,
		 @QueryParam(value = "filterBy") String filterBy,
			@QueryParam(value = "orderBy") String orderBy,
			@QueryParam(value= "sort") @DefaultValue("asc") String sort) {
		
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
		List<BrandDto> brands = this.brandService.getBrands(page, size, filterBy, sort, orderBy).stream().map(brand -> brand.convertToDto()).collect(Collectors.toList());
		
		return Response.status(Status.OK).entity(brands).build();
		}
		else {
			return Response.status(Status.UNAUTHORIZED).build();

		}
	}
	
	@GET
	@Path("/{id}")
	@Secured
	public Response getBrand(@PathParam("id") String id) {
		if(securityContext.isUserInRole("username") || securityContext.isUserInRole("admin")) {
		try {
			BrandDto brandDto = this.brandService.getBrand(id).convertToDto();
			return Response.status(Status.OK).entity(brandDto).build();
			
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
	public Response updateBrand(@PathParam("id") String id, BrandDto brandDto) {
		if(securityContext.isUserInRole("admin")) {

		try {

			brandDto.setId(id);
			
			return Response.status(Status.OK).entity(this.brandService.updateBrand(brandDto.convertToEntity())).build();
			

		} catch (EntityNotFoundException ex) {
		return Response.status(Status.NOT_FOUND).build();

		
		}
		
		}else {
			return Response.status(Status.UNAUTHORIZED).build();

		}
		
	}
	
	@POST
	@Secured
	public Response createdBrand(BrandDto brandDto) {
		if(securityContext.isUserInRole("admin")) {

		this.brandService.createBrand(brandDto.convertToEntity()).convertToDto();
		Response response = Response.status(Status.CREATED).entity(brandDto).build();

		return response;
		}
		else {
			return Response.status(Status.UNAUTHORIZED).build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Secured
	public Response deleteBrand(@PathParam("id") String id) {
		if(securityContext.isUserInRole("admin")) {

		try {
			this.brandService.deleteBrand(id);
			return Response.status(Status.NO_CONTENT).build();
		
		} catch (EntityNotFoundException ex) {

			return Response.status(Status.NOT_FOUND).build();

		}
		
		}else {
			return Response.status(Status.UNAUTHORIZED).build();

		}

	}

	
	
	
}
