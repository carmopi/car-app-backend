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

	public TypedQuery<T> getAll(Class<T> c, Map<String, String> filterMap, String orderBy) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(c);
		Root<T> r = cq.from(c);
		cq.select(r);
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (Map.Entry<String, String> entry : filterMap.entrySet()) {
			Expression<String> key = cb.lower(r.get(entry.getKey()).as(String.class));
			String value = String.format("%%%s%%", entry.getValue().toLowerCase());
			predicates.add(cb.like(key, value));
		}
		Predicate predicate = cb.or(predicates.toArray(new Predicate[0]));
		cq.where(predicate);
		if (orderBy != null && !orderBy.isEmpty()) {
			if (orderBy.charAt(0) == '-') {
				cq.orderBy(cb.desc(r.get(orderBy.substring(1))));
			} else {
				cq.orderBy(cb.asc(r.get(orderBy)));
			}
		}
		TypedQuery<T> query = this.em.createQuery(cq);
		return query;
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

}
