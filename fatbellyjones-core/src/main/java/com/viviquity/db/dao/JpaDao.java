package com.viviquity.db.dao;

import java.util.List;

public interface JpaDao<K, E> {

    public void persist(E entity);

    public E merge(E entity);

    public void refresh(E entity);

    public E findById(K id);

    public E flush(E entity);

    public List<E> findAll();

    public Integer removeAll();

    void remove(E entity);

}
