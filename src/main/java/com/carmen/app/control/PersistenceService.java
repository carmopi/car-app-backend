package com.carmen.app.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	public List<T> getAll(Class<T> c, int page, int size, String filter, String sort){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(c);
		Root<T> root = query.from(c);
		query.select(root);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.like(root.get("brand"), "%s"));
		predicates.add(builder.like(root.get("country"), "%s"));
		
		query.where(builder.or(predicates.toArray(new Predicate[0]))).distinct(true);
		
		if (sort != null && !sort.isEmpty()) {
			
				query.orderBy(builder.desc(root.get(sort)));
			}else {
				query.orderBy(builder.asc(root.get(sort)));
			}
		
		
		TypedQuery<T> q = this.em.createQuery(query);
		return q.getResultList();
	
		
		
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
