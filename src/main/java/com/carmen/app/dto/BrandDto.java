package com.carmen.app.dto;

import java.io.Serializable;


import org.modelmapper.ModelMapper;

import com.carmen.app.entities.Brand;




/**
 * Data Transfer Object 
 * 
 * @author Carmen Piñera Moreno
 *
 */


public class BrandDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String name;
	
	public BrandDto() {
		
	}
	
	public BrandDto(String name) {
		this.name = name;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Brand convertToEntity() {
		ModelMapper modelMapper = new ModelMapper();
		Brand brand = modelMapper.map(this, Brand.class);
		return brand;
	}

}
