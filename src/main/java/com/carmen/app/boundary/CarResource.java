package com.carmen.app.boundary;


import java.util.LinkedHashMap;
import java.util.List;
//import java.util.UUID;
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
 * @author Carmen Pi�era Moreno
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
	public Response getCars(@DefaultValue("1") @QueryParam(value = "page") int page,
			@DefaultValue("200") @QueryParam(value = "size") int size,
			@DefaultValue("") @QueryParam(value = "filterBy") String filterBy,
			@QueryParam(value = "orderBy") String orderBy) {
		Response response = null;
		if (page < 1 || size < 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<CarDto> list = this.carService.getCars(page, size, filterBy, orderBy).stream().map(car -> car.convertToDto())
				.collect(Collectors.toList());

		int perPage = list.size();
		int total = this.carService.getCount(filterBy);
		int pageCount = 1;
		if (perPage < total && perPage > 0) {
			pageCount = (total / perPage) + 1;
		}
		if (page > pageCount) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		LinkedHashMap<String, Object> carsMap = new LinkedHashMap<String, Object>();
		carsMap.put("page", page);
		carsMap.put("per_page", perPage);
		carsMap.put("page_count", pageCount);
		carsMap.put("total_count", total);
		carsMap.put("cars", list);
		response = Response.ok(carsMap).build();
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
