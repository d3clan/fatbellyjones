package com.viviquity.readmy.db.utils.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.viviquity.readmy.db.utils.GenericDao;

public class GenericDaoJpa<T, PK extends Serializable> extends JpaDaoSupport
		implements GenericDao<T, PK> {

	private Class<?> persistentClass;

	/**
	 * Constructor that takes in a class to see which type of entity to persist
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoJpa(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@SuppressWarnings("unchecked")
	public boolean exists(Serializable id) {
		T entity = (T) get(id);
		return entity != null;
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		T entity = (T) getJpaTemplate().find(persistentClass, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getJpaTemplate().find("from " + persistentClass.getSimpleName());
	}

	@Transactional
	public void remove(Serializable id) {
		getJpaTemplate().remove(this.get(id));
	}

	@Transactional
	public void remove(T entity) {
		getJpaTemplate().remove(entity);
	}

	@Transactional
	public void remove(Collection<T> entities) {
		for (T entity : entities) {
			getJpaTemplate().remove(entity);
		}
	}

	@Transactional
	public void removeAll() {
		remove(getAll());
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public T save(Object object) {
		return (T) getJpaTemplate().merge(object);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public T findByExternalId(final Serializable externalId) {
		List<T> results = getJpaTemplate().find(
				"from " + persistentClass.getSimpleName()
						+ " where externalId=?", externalId);
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

}
