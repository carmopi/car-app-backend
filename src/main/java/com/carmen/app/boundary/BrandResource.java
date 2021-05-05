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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.carmen.app.control.BrandService;
import com.carmen.app.dto.BrandDto;

import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;

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
	
	@GET
	public Response getBrands(@DefaultValue("0") @QueryParam(value = "page") int page,
			@DefaultValue("20") @QueryParam(value = "size") int size,
		 @QueryParam(value = "filterBy") String filterBy,
			@QueryParam(value = "orderBy") String orderBy,
			@QueryParam(value= "sort") @DefaultValue("asc") String sort) {
		
		List<BrandDto> brands = this.brandService.getBrands(page, size, filterBy, sort, orderBy).stream().map(brand -> brand.convertToDto()).collect(Collectors.toList());
		
		return Response.status(Status.OK).entity(brands).build();
		
	}
	
	@GET
	@Path("/{id}")
	public Response getBrand(@PathParam("id") String id) {
		try {
			BrandDto brandDto = this.brandService.getBrand(id).convertToDto();
			return Response.status(Status.OK).entity(brandDto).build();
			
		}catch (EntityNotFoundException ex) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	

	@PUT
	@Path("/{id}")

	public Response updateBrand(@PathParam("id") String id, BrandDto brandDto) {

		try {

			brandDto.setId(id);
			
			return Response.status(Status.OK).entity(this.brandService.updateBrand(brandDto.convertToEntity())).build();
			

		} catch (EntityNotFoundException ex) {
		return Response.status(Status.NOT_FOUND).build();

		

		}

	}
	
	@POST

	public Response createdBrand(BrandDto brandDto) {

		this.brandService.createBrand(brandDto.convertToEntity()).convertToDto();
		Response response = Response.status(Status.CREATED).entity(brandDto).build();

		return response;
	}

	@DELETE
	@Path("/{id}")

	public Response deleteBrand(@PathParam("id") String id) {

		try {
			this.brandService.deleteBrand(id);
			return Response.status(Status.NO_CONTENT).build();
		
		} catch (EntityNotFoundException ex) {

			return Response.status(Status.NOT_FOUND).build();

		}

	}

	
	
	
}
