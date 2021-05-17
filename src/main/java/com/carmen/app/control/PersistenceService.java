package com.carmen.app.control;


import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.carmen.app.utils.Logged;

/**
 * 
 * Service that generate a CRUD to be used by any resource
 * 
 * @author Carmen Piñera Moreno
 *
 * @param <T> refers to a type
 * @param <K> refers to a key
 */

@Stateless
@Logged
public class PersistenceService<T, K> {

	@PersistenceContext(unitName = "carP")
	private EntityManager em;

	public List<T> getAll(Class<T> c, String sort, int size, int page, String filterBy, String orderBy) {
		
		if(sort == null || sort.trim().isEmpty()) sort="asc";	
		if(orderBy == null || orderBy.trim().isEmpty()) orderBy = "id";
		if(page <= 0) page = 0; 
		if(size <= 0 || size >= 20) size = 10;
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(c);
		
		Root<T> r = cq.from(c);
		
		if(filterBy != null) {
			Predicate brandPredicate = cb.like(r.get("brand").get("name"), filterBy);
			Predicate countryPredicate = cb.like(r.get("country").get("name"), filterBy);
			Predicate merge = cb.or(brandPredicate, countryPredicate);

			cq.where(merge);
		}
		
		if(sort == "asc")
			cq.orderBy(cb.asc(r.get(orderBy)));
		else
			cq.orderBy(cb.desc(r.get(orderBy)));
		
	
		TypedQuery<T> query = this.em.createQuery(cq);
		
		return query.getResultList();
	}

	public T getById(Class<T> c, K id) {
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
	
	public void executeNamedQuery(String query) {
		Query namedQuery = this.em.createNamedQuery(query);
		namedQuery.executeUpdate();
	}

}
