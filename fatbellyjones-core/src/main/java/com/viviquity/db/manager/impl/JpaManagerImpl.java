package com.viviquity.db.manager.impl;

import java.util.List;

import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.manager.JpaManager;

public class JpaManagerImpl<K, E> implements JpaManager<K, E> {

    protected JpaDao<K, E> dao;

    public JpaManagerImpl(JpaDao<K, E> dao) {
	this.dao = dao;
    }

    @Override
    public List<E> findAll() {
	return dao.findAll();
    }

    @Override
    public E findById(K id) {
	return dao.findById(id);
    }

    @Override
    public E flush(E entity) {
	return dao.flush(entity);
    }

    @Override
    public E merge(E entity) {
	return dao.merge(entity);
    }

    @Override
    public void persist(E entity) {
	dao.persist(entity);
    }

    @Override
    public void refresh(E entity) {
	dao.refresh(entity);
    }

    @Override
    public void remove(E entity) {
	dao.remove(entity);
    }

    @Override
    public Integer removeAll() {
	return dao.removeAll();
    }

}
