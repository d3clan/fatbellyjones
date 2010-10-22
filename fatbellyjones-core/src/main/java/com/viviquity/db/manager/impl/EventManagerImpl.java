package com.viviquity.db.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.Event;
import com.viviquity.db.dao.EventDao;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.manager.EventManager;

@Service("eventManager")
public class EventManagerImpl extends JpaManagerImpl<Long, Event> implements EventManager {

    @Autowired
    public EventManagerImpl(JpaDao<Long, Event> eventDao) {
	super(eventDao);
    }

    public List<Event> findByDate(String start, String end) {
	try {
	    Long st = StringUtils.isNotBlank(start) ? Long.decode(start) : null;
	    Long en = StringUtils.isNotBlank(end) ? Long.decode(end) : null;

	    Calendar startCal = Calendar.getInstance();
	    startCal.add(Calendar.YEAR, -2000);

	    Calendar endCal = Calendar.getInstance();
	    endCal.add(Calendar.YEAR, 2000);

	    Date startDate = start != null ? new Date(st * 1000) : startCal.getTime();
	    Date endDate = start != null ? new Date(en * 1000) : endCal.getTime();

	    return ((EventDao) dao).findByDate(startDate, endDate);
	} catch (NumberFormatException e) {
	    return null;
	}
    }
}
