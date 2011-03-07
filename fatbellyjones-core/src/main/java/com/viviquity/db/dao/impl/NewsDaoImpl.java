package com.viviquity.db.dao.impl;

import java.util.Date;
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

import com.viviquity.core.model.News;
import com.viviquity.db.dao.JpaDaoImpl;
import com.viviquity.db.dao.NewsDao;

@Repository("newsDao")
public class NewsDaoImpl extends JpaDaoImpl<Long, News> implements NewsDao {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
	super.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    public List<News> find(final int start, final int end) {
	final Date now = new Date();
	return getJpaTemplate().execute(new JpaCallback<List<News>>() {

	    @Override
	    public List<News> doInJpa(EntityManager em) throws PersistenceException {
		Query query = em.createQuery("from News where dateCreated < :now order by dateCreated desc");
		query.setFirstResult(start);
		query.setMaxResults(end);
		query.setParameter("now", now);
		return query.getResultList();
	    }

	});
    }
}
