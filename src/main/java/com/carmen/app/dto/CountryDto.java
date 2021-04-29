package com.carmen.app.dto;

import java.io.Serializable;


import org.modelmapper.ModelMapper;


import com.carmen.app.entities.Country;



/**
 * Data Transfer Object 
 * 
 * @author Carmen Piñera Moreno
 *
 */


public class CountryDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String name;
	
	
	public CountryDto() {
		
	}
	
	public CountryDto(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Country convertToEntity() {
		ModelMapper modelMapper = new ModelMapper();
		Country country = modelMapper.map(this, Country.class);
		return country;
	}

}