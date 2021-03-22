package com.carmen.app.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.carmen.app.utils.Logged;

@Stateless
@Logged
public class PersistenceService<T,K>{

	
	@PersistenceContext(unitName = "carP")
	private EntityManager em;
	
	public List<T> getEntities(String NameQuery, Class<T> c){
		TypedQuery<T> query = this.em.createNamedQuery(NameQuery, c);
		return query.getResultList();
	}
	
	public T getEntity (Class<T> c, K id) {
		return this.em.find(c, id);
		
	}
	
	public T createEntity(T entity) {
		 this.em.persist(entity);
		 return entity;
	}
	
	public T updateEntity(T entity) {
		this.em.merge(entity);
		return entity;
	}
	
	public void deleteEntity(T entity) {
		this.em.remove(entity);
	}
	
}
