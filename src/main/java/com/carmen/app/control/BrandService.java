package com.carmen.app.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.carmen.app.entities.Brand;

import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;

/**
 * 
 * @author Carmen Piñera Moreno 
 *
 */

@Stateless
@Logged
public class BrandService {

	
	@EJB
	private PersistenceService<Brand, String> persistenceService;
	
	public List<Brand> getBrands(int page, int size, String filterBy,String sort, String orderBy){
		return this.persistenceService.getAll(Brand.class, sort, size, page, filterBy, orderBy);
		
	}
	
	/**
	 * 
	 * Obtain a brand by the id
	 * 
	 * @param id of the brand 
	 * @return the brand corresponding to the given id 
	 * @throws EntityNotFoundException if the id does not match with any {@link Brand} of the database
	 */
	
	public Brand getBrand(String id) throws EntityNotFoundException{
		
		Brand brand = this.persistenceService.getById(Brand.class, id);
		if(brand == null){
			throw new EntityNotFoundException("Brand with id " + id + "not found");
		}
		
		return brand;
	}
	 
	/**
	 * 
	 * @param brand  {@link Brand} entity to be attached
	 * @return {@link Brand} entity created
	 */
	
	public Brand createBrand(Brand brand) {
		this.persistenceService.createNew(brand);
		return brand;
	}
	
	/**
	 * Update a brand with new data
	 * 
	 * @param brand {@link Brand} entity to be updated
	 * @return brand {@link Brand} entity to be updated
	 * @throws EntityNotFoundException if the id does not match with any {@link Brand}
	 *                              entity of the database
	 */
	
	public Brand updateBrand(Brand brand) throws EntityNotFoundException {
		Brand brandToUpdate = getBrand(brand.getId());
		if(brandToUpdate == null) {
			throw new EntityNotFoundException("Brand with id " + brand.getId() + " not found");
		}
		brand = this.persistenceService.updateOne(brand);
		
		return brand;

	}
	
	/**
	 * 
	 * Deletes a brand form the database by its id
	 * 
	 * @param id of the {@link Brand} entity to be delete
	 * @throws EntityNotFoundException if the id does not match with any {@link Brand}
	 *                              entity of the database
	 */

	public void deleteBrand(String id) throws EntityNotFoundException {
		Brand brand = this.getBrand(id);
		this.persistenceService.deleteOne(brand);
	}

	
	
}
