package com.carmen.app.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import com.carmen.app.entities.Country;
import com.carmen.app.exceptions.EntityNotFoundException;
import com.carmen.app.utils.Logged;

/**
 * 
 * @author Carmen Piñera Moreno 
 *
 */

@Stateless
@Logged
public class CountryService {

	
	@EJB
	private PersistenceService<Country, String> persistenceService;
	
	public List<Country> getCountries(int page, int size, String filterBy,String sort, String orderBy){
		return this.persistenceService.getAll(Country.class, sort, size, page, filterBy, orderBy);
		
	}
	
	/**
	 * 
	 * Obtain a country by the id
	 * 
	 * @param id of the country 
	 * @return the country corresponding to the given id 
	 * @throws EntityNotFoundException if the id does not match with any {@link country} of the database
	 */
	
	public Country getCountry(String id) throws EntityNotFoundException{
	
		Country country = this.persistenceService.getById(Country.class, id);
		if(country == null){
			throw new EntityNotFoundException("country with id " + id + "not found");
		}
		
		return country;
	}
	 
	/**
	 * 
	 * @param country  {@link country} entity to be attached
	 * @return {@link country} entity created
	 */
	
	public Country createCountry(Country country) {
		this.persistenceService.createNew(country);
		return country;
	}
	
	/**
	 * Update a country with new data
	 * 
	 * @param country {@link Country} entity to be updated
	 * @return country {@link Country} entity to be updated
	 * @throws EntityNotFoundException if the id does not match with any {@link country}
	 *                              entity of the database
	 */
	
	public Country updateCountry(Country country) throws EntityNotFoundException {
		Country countryToUpdate = getCountry(country.getId());
		if(countryToUpdate == null) {
			throw new EntityNotFoundException("country with id " + country.getId() + " not found");
		}
		country = this.persistenceService.updateOne(country);
		
		return country;

	}
	
	/**
	 * 
	 * Deletes a country form the database by its id
	 * 
	 * @param id of the {@link country} entity to be delete
	 * @throws EntityNotFoundException if the id does not match with any {@link country}
	 *                              entity of the database
	 */

	public void deleteCountry(String id) throws EntityNotFoundException {
		Country country = this.getCountry(id);
		this.persistenceService.deleteOne(country);
	}

	
	
}
