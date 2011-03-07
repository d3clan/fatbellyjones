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

import com.viviquity.core.model.User;
import com.viviquity.db.manager.UserManager;

@Controller
public class AboutUsController {

    private Logger logger = Logger.getLogger(AboutUsController.class);

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = { "/about/home", "/about/index" })
    public ModelAndView homeHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Fat Belly Jones &lt;&lt;About us&gt;&gt;");
	List<User> users = userManager.findAll();
	model.put("users", users);
	logger.info("Hitting about us controller");
	return new ModelAndView("about-us", model);
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

}
