package com.viviquity.db.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.viviquity.core.model.User;
import com.viviquity.db.dao.JpaDaoImpl;
import com.viviquity.db.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends JpaDaoImpl<Long, User> implements UserDao {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
	super.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
	Object res = getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("from User u order by u.surname");
		return q.getResultList();
	    }

	});

	return (List<User>) res;
    }

    public User findByUsername(final String username) {
	return getJpaTemplate().execute(new JpaCallback<User>() {

	    @Override
	    public User doInJpa(EntityManager em) throws PersistenceException {
		Query query = em.createQuery("from User where username = :username");
		query.setParameter("username", username);
		return (User) query.getSingleResult();
	    }

	});
    }
}
