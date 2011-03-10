package com.viviquity.readmy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.viviquity.core.model.User;
import com.viviquity.db.manager.UserManager;

public class BaseController {

    @Autowired
    private UserManager userManager;

    protected User getCurrentUser() {
	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
		.getUsername();
	return userManager.findByUsername(username);
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

}
