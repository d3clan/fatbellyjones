package com.viviquity.db.dao;

import java.util.Date;
import java.util.List;

import com.viviquity.core.model.Event;

public interface EventDao extends JpaDao<Long, Event> {

    public List<Event> findByDate(Date start, Date end);
    
    public List<Event> findGigsByDate(Date start, Date end);

    public List<Event> findConfirmedGigs();

	public List<Event> findNext(int count);

}
