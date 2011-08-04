package com.viviquity.readmy.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.Event;
import com.viviquity.db.manager.EventManager;

@Controller
public class PublicGigController {
	
	public static final String[] MONTHS = {"January" , "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private Logger logger = Logger.getLogger(PublicGigController.class);

	@Autowired
	private EventManager eventManager;

	@RequestMapping(value = { "/gigs/home", "/gigs/index" })
	public ModelAndView homeHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Hitting home controller");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "Latest gigs");
		
		Map<String, List<Event>> eventsByMonth = getEventsByMonth();
		model.put("gigs", eventsByMonth);
		return new ModelAndView("public-gigs", model);
	}
	
	private Map<String, List<Event>> getEventsByMonth() {
		Map<String, List<Event>> events = new HashMap<String, List<Event>>();
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		Date start = now.getTime();
		now.add(Calendar.YEAR, 1);
		now.add(Calendar.DAY_OF_YEAR, 1);
		
		Date end = now.getTime();
		List<Event> nextGigs = eventManager.findGigsByDate(start, end);
		
		for (Event event : nextGigs) {
			if (event.getStart() != null) {
				Date startDate = event.getStart();
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				int month = cal.get(Calendar.MONTH);
				if (events.containsKey(month)) {
					events.get(month).add(event);
				} else {
					List<Event> evs = new ArrayList<Event>();
					evs.add(event);
					events.put(MONTHS[month], evs);
				}
			}
		}
		
		return events;
	}
	
	private Map<String, List<Event>> sortMap(Map<String, List<Event>> events) {
		Map<String, List<Event>> evts = new HashMap<String, List<Event>>();
		LinkedList<String> keys = new LinkedList<String>(events.keySet());
		Collections.sort(keys, new Comparator<String>() {

			@Override
			public int compare(String key1, String key2) {
				try {
					int k1 = getIndexOfMonth(key1);
					int k2 = getIndexOfMonth(key2);
					if (k1 == k2) {
						return 0;
					} else if (k1 < k2) {
						return -1;
					} else {
						return 1;
					}
				} catch (NotFoundException e) {
					logger.error("Cannot dort list", e);
				}
				return 0;
			}
			
		});
		
		for (String key : keys) {
			List<Event> monthEvents = events.get(key);
			evts.put(key, monthEvents);
		}
		return evts;
	}
	
	private int getIndexOfMonth(String month) throws NotFoundException {
		for (int i=0;i<MONTHS.length;i++) {
			String arrayMonth = MONTHS[i];
			if (arrayMonth.equals(month)) {
				return i;
			}
		}
		throw new NotFoundException("Cannot find month " + month);
	}

}
