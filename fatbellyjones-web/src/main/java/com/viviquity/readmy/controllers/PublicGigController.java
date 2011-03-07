package com.viviquity.readmy.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
