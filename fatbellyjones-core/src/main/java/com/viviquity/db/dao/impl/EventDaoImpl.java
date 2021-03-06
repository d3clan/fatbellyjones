package com.viviquity.db.dao.impl;

import java.util.Calendar;
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

import com.viviquity.core.model.Event;
import com.viviquity.db.dao.EventDao;
import com.viviquity.db.dao.JpaDaoImpl;

@Repository("eventDao")
public class EventDaoImpl extends JpaDaoImpl<Long, Event> implements EventDao {

	@Autowired
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}

	@SuppressWarnings("unchecked")
	public List<Event> findByDate(final Date start, final Date end) {
		return getJpaTemplate().execute(new JpaCallback<List<Event>>() {

			@Override
			public List<Event> doInJpa(EntityManager em)
					throws PersistenceException {
				Query q = em
						.createQuery("from Event where start > :start and endDate < :end");
				q.setParameter("start", start);
				q.setParameter("end", end);
				return q.getResultList();
			}

		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findGigsByDate(final Date start, final Date end) {
		return getJpaTemplate().execute(new JpaCallback<List<Event>>() {

			@Override
			public List<Event> doInJpa(EntityManager em)
					throws PersistenceException {
				Query q = em
						.createQuery("from Event where isGig = :isGig and confirmed = :confirmed and start > :start and endDate < :end order by start asc");
				q.setParameter("isGig", true);
				q.setParameter("confirmed", true);
				q.setParameter("start", start);
				q.setParameter("end", end);
				return q.getResultList();
			}

		});
	}

	@SuppressWarnings("unchecked")
	public List<Event> findConfirmedGigs() {
		return getJpaTemplate().execute(new JpaCallback<List<Event>>() {

			@Override
			public List<Event> doInJpa(EntityManager em)
					throws PersistenceException {
				Query q = em
						.createQuery("from Event where isGig = :isGig and confirmed = :confirmed order by start desc");
				q.setParameter("isGig", true);
				q.setParameter("confirmed", true);
				return q.getResultList();
			}

		});
	}

	@Override
	public List<Event> findNext(int count) {
		return getJpaTemplate().execute(new JpaCallback<List<Event>>() {

			@Override
			public List<Event> doInJpa(EntityManager em)
					throws PersistenceException {
				Calendar now = Calendar.getInstance();
				now.set(Calendar.HOUR_OF_DAY, 0);
				now.set(Calendar.MINUTE, 0);
				now.set(Calendar.SECOND, 0);
				now.set(Calendar.MILLISECOND, 0);
				Date start = now.getTime();
				Query q = em
						.createQuery("from Event where isGig = :isGig and confirmed = :confirmed and start >= :start order by start desc");
				q.setParameter("isGig", true);
				q.setParameter("confirmed", true);
				q.setParameter("start", start);
				return q.getResultList();
			}

		});
	}
	
	
}
