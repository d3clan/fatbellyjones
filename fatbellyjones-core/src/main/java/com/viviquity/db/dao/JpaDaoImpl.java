package com.viviquity.db.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;


public class JpaDaoImpl<K, E> extends JpaDaoSupport implements JpaDao<K, E> {
    protected Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public JpaDaoImpl() {
	ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
	this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    @Transactional
    public void persist(E entity) {
	getJpaTemplate().persist(entity);
    }

    @Override
    @Transactional
    public void remove(E entity) {
	getJpaTemplate().remove(entity);
    }

    @Override
    @Transactional
    public E merge(E entity) {
	return getJpaTemplate().merge(entity);
    }

    @Override
    public void refresh(E entity) {
	getJpaTemplate().refresh(entity);
    }

    @Override
    public E findById(K id) {
	return getJpaTemplate().find(entityClass, id);
    }

    @Override
    @Transactional
    public E flush(E entity) {
	getJpaTemplate().flush();
	return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() {
	Object res = getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
		return q.getResultList();
	    }

	});

	return (List<E>) res;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Integer removeAll() {
	return (Integer) getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h");
		return q.executeUpdate();
	    }

	});
    }

}
