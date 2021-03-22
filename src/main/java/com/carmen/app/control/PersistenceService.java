package com.carmen.app.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.carmen.app.utils.Logged;
/**
 * 
 *Service that generate a CRUD to be used by any resource
 * @author Carmen Piñera Moreno
 *
 * @param <T> refers to a type
 * @param <K> refers to a key
 */
@Stateless
@Logged
public class PersistenceService<T,K>{

	
	@PersistenceContext(unitName = "carP")
	private EntityManager em;
	
	public List<T> getAll(String NameQuery, Class<T> c){
		TypedQuery<T> query = this.em.createNamedQuery(NameQuery, c);
		return query.getResultList();
	}
	
	public T getById (Class<T> c, K id) {
		return this.em.find(c, id);
		
	}
	
	public T createNew(T entity) {
		 this.em.persist(entity);
		 return entity;
	}
	
	public T updateOne(T entity) {
		this.em.merge(entity);
		return entity;
	}
	
	public void deleteOne(T entity) {
		this.em.remove(entity);
	}

	
	
}
