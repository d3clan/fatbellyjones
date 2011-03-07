package com.viviquity.db.dao.impl;

import java.util.ArrayList;
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
import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.db.dao.JpaDaoImpl;
import com.viviquity.db.dao.PlaylistDao;

@Repository("playlistDao")
public class PlaylistDaoImpl extends JpaDaoImpl<Long, PlaylistEntry> implements PlaylistDao {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
	super.setEntityManagerFactory(entityManagerFactory);
    }

    @SuppressWarnings("unchecked")
    public List<PlaylistEntry> findByEvent(final Event event) {
	return getJpaTemplate().execute(new JpaCallback<List<PlaylistEntry>>() {

	    @Override
	    public List<PlaylistEntry> doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("from PlaylistEntry where event = :event");
		q.setParameter("event", event);
		return q.getResultList();
	    }

	});
    }

    @Override
    public List<PlaylistEntry> findLastPlaylist() {
	return getJpaTemplate().execute(new JpaCallback<List<PlaylistEntry>>() {

	    @SuppressWarnings("unchecked")
	    @Override
	    public List<PlaylistEntry> doInJpa(EntityManager em) throws PersistenceException {
		List<Event> events = em
			.createQuery(
				"from Event as ev where (select count(*) from PlaylistEntry as ple join ev.playlist as pl where ple.event.id = ev.id) > 0")
			.setMaxResults(1).getResultList();

		if (events != null && events.size() > 0) {
		    Event event = events.get(0);
		    Query q = em
			    .createQuery("from PlaylistEntry as pe inner join pe.event as ev where ev.isGig = true and ev.id = :eventId order by ev.start asc");
		    q.setParameter("eventId", event.getId());
		    return q.getResultList();
		} else {
		    return new ArrayList<PlaylistEntry>();
		}
	    }

	});
    }
}
