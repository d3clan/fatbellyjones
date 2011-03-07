package com.viviquity.readmy.controllers;

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
public class HomeController {

	private Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private EventManager eventManager;

	@RequestMapping(value = { "/home", "/index" })
	public ModelAndView homeHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Hitting home controller");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "Welcome to the Fat Belly Jones website");
		List<Event> nextGig = eventManager.findNext(1);
		model.put("gigs", nextGig);
		return new ModelAndView("home", model);
	}

}
