package com.viviquity.readmy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.User;
import com.viviquity.db.manager.UserManager;

@Controller
public class MemberController {

    private Logger logger = Logger.getLogger(MemberController.class);

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = { "/about/member/{id}" })
    public ModelAndView memberHandler(@PathVariable(value = "id") Long id) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	User user = userManager.findById(id);
	model.put("title", "Fat Belly Jones &lt;&lt;" + user.getFirstName() + " &ndash;" + user.getInstrument()
		+ "&gt;&gt;");
	model.put("user", user);
	logger.info("Hitting member controller");
	return new ModelAndView("member", model);
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

}
