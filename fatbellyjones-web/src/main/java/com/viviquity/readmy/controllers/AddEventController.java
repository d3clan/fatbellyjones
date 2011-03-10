package com.viviquity.readmy.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddEventController {

    private Logger logger = Logger.getLogger(AddEventController.class);

    @RequestMapping("/admin/protected/event/add")
    public ModelAndView loginHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Add event");
	logger.info("Hitting add event controller");
	return new ModelAndView("add-event", model);
    }

}
