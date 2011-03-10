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

import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;
import com.viviquity.db.dao.JpaDaoImpl;
import com.viviquity.db.dao.TuneDao;

@Repository("playlistDao")
public class TuneDaoImpl extends JpaDaoImpl<Long, Tune> implements TuneDao {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
	super.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tune> findAll() {
	Object res = getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h order by title asc");
		return q.getResultList();
	    }

	});

	return (List<Tune>) res;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tune> findAllByStatus(final String status) {
	Object res = getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		Query q = em.createQuery("SELECT h FROM " + entityClass.getName()
			+ " h where status = :status order by title asc");
		q.setParameter("status", status);
		return q.getResultList();
	    }

	});

	return (List<Tune>) res;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tune> findAllNotInList(final List<Long> tuneIds) {
	Object res = getJpaTemplate().execute(new JpaCallback() {

	    public Object doInJpa(EntityManager em) throws PersistenceException {
		if (tuneIds != null && tuneIds.size() > 0) {
		    Query q = em
			    .createQuery("from "
				    + entityClass.getName()
				    + " where id not in (:ids) and (status = 'playing' or status = 'break') order by status asc, title asc");
		    q.setParameter("ids", tuneIds);
		    return q.getResultList();
		} else {
		    return findAllByStatus("playing");
		}

	    }

	});

	return (List<Tune>) res;
    }

    @Override
    public List<Tune> findAllNotInPlaylist(final List<PlaylistEntry> playlistEntries) {

	return (List<Tune>) findAllNotInList(getTuneIds(playlistEntries));
    }

    private List<Long> getTuneIds(List<PlaylistEntry> playlistEntries) {
	List<Long> ids = new ArrayList<Long>();
	if (playlistEntries != null && playlistEntries.size() > 0) {
	    for (PlaylistEntry entry : playlistEntries) {
		ids.add(entry.getTune().getId());
	    }
	}
	return ids;
    }
}
