package com.viviquity.readmy.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class ViewDelegate {

	private final Log log = LogFactory.getLog(getClass());

	public ModelAndView unspecified(HttpServletRequest req, HttpServletResponse resp) {
		log.info("unspecified");
		return indexHandler(req, resp);
	}

	public ModelAndView indexHandler(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", "Helo World");
		return new ModelAndView("mainlayout", model);
	}

	public ModelAndView missingHandler(HttpServletRequest req, HttpServletResponse resp) {
		log.warn("Page is missing:");
		return new ModelAndView("core/missing");
	}

	public ModelAndView accessHandler(HttpServletRequest req, HttpServletResponse resp) {
		log.warn("No access");
		return new ModelAndView("core/access");
	}

	public ModelAndView errorHandler(HttpServletRequest req, HttpServletResponse resp) {
		return new ModelAndView("core/error");
	}
}
