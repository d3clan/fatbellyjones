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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.News;
import com.viviquity.db.manager.NewsManager;

@Controller
public class LatestNewsController {

    private Logger logger = Logger.getLogger(LatestNewsController.class);

    @Autowired
    private NewsManager newsManager;

    @RequestMapping(value = { "/news/home", "/news/index" })
    public ModelAndView homeHandler(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "max", required = false) Integer max) throws Exception {
	if (page == null) {
	    page = 1;
	}
	if (max == null) {
	    max = 10;
	}
	logger.info("Hitting latest news");
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Fat Belly Jones &lt;&lt;Latest news&gt;&gt;");
	List<News> lastTen = newsManager.find((page - 1) * max, max);
	model.put("news", lastTen);

	return new ModelAndView("latest-news", model);
    }

    /**
     * @param newsManager
     *            the newsManager to set
     */
    public void setNewsManager(NewsManager newsManager) {
	this.newsManager = newsManager;
    }

}
