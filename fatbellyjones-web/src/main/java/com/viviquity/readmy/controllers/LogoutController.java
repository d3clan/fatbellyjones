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
public class LogoutController {

    private Logger logger = Logger.getLogger(LogoutController.class);

    @RequestMapping("/admin/logout")
    public ModelAndView loginHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Hello World!!");
	logger.info("Hitting logout controller");
	return new ModelAndView("logout", model);
    }

}
