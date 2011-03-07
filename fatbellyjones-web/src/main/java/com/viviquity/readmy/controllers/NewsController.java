package com.viviquity.readmy.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.db.manager.NewsManager;

@Controller
public class NewsController {

    private Logger logger = Logger.getLogger(NewsController.class);

    private NewsManager newsManager;

    @Autowired
    public NewsController(NewsManager newsManager) {
	this.newsManager = newsManager;
    }

    @RequestMapping("/admin/protected/news")
    public ModelAndView homeHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	logger.info("Hitting news controller");
	model.put("newsItems", newsManager.findAll());
	model.put("title", "Fat Belly News");
	return new ModelAndView("messages", model);
    }

}
