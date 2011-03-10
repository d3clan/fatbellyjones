package com.viviquity.readmy.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.email.Emailer;
import com.viviquity.core.model.ConfirmedUser;
import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.User;
import com.viviquity.db.manager.EventManager;
import com.viviquity.db.manager.UserManager;
import com.viviquity.readmy.bean.EventBean;

@Controller
public class GigController extends BaseController {

    private Logger logger = Logger.getLogger(GigController.class);

    @Autowired
    private EventManager eventManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private Emailer emailer;

    @RequestMapping( { "/admin/protected/gig/confirm/{id}", "/admin/protected/gig/{id}" })
    public ModelAndView gigConfirm(@PathVariable(value = "id") Long id) throws Exception {
	logger.info("Hitting gig controller");
	User currentUser = getCurrentUser();
	Event event = eventManager.findById(id);
	Map<String, Object> model = new HashMap<String, Object>();

	if (event != null) {

	    List<PlaylistEntry> playlist = event.getPlaylist();

	    if (playlist == null || playlist.size() == 0) {
		playlist = new ArrayList<PlaylistEntry>();
		event.setPlaylist(playlist);
		eventManager.merge(event);
	    }

	    List<User> allUsers = userManager.findAll();
	    List<ConfirmedUser> confirmed = event.getConfirmedUsers();
	    model.put("title", "Confirm gig for " + event.getTitle());
	    model.put("allUsers", allUsers);
	    model.put("currentUser", currentUser);
	    model.put("event", event);
	    model.put("perPerson", getPerPerson(event.getCharge(), allUsers));
	    model.put("functions", this);
	    model.put("confirmedUsers", getConfirmedUsers(confirmed, currentUser, event));
	} else {
	    model.put("title", "Cannot find event");
	}
	return new ModelAndView("gig", model);
    }

    @RequestMapping("/admin/protected/gig/finish")
    public ModelAndView gigHandler(HttpServletRequest request, @RequestParam(value = "id") Long id) throws Exception {
	logger.info("Hitting finish gig");
	User currentUser = getCurrentUser();
	Event event = eventManager.findById(id);
	saveConfirmed(request, event);

	Map<String, Object> model = new HashMap<String, Object>();
	List<User> allUsers = userManager.findAll();
	List<ConfirmedUser> confirmed = event.getConfirmedUsers();
	List<User> confirmedUsers = getConfirmedUsers(confirmed, currentUser, event);

	model.put("currentUser", currentUser);
	model.put("event", event);
	model.put("functions", this);

	if (areAllConfirmed(allUsers, confirmed)) {
	    if (allArtifactsCreated(event)) {
		model.put("title", "Confirmed gig");
		return new ModelAndView("generate", model);
	    }
	    model.put("title", "Generating gig artifacts");
	    return new ModelAndView("generate", model);
	} else {
	    URI eventLink = getEventLink(request, event);
	    for (User user : allUsers) {
		if (!isConfirmed(user, confirmedUsers)) {
		    emailer.sendConfirmEmail(eventLink, user, event);
		}
	    }
	    model.put("title", "Confirm gig for " + event.getTitle());
	    return new ModelAndView("pending", model);
	}
    }

    private boolean allArtifactsCreated(Event event) {
	return (event != null && event.getTweet() != null && event.getFacebookId() != null && event.getFacebookImage() != null);
    }

    private boolean areAllConfirmed(List<User> allUsers, List<ConfirmedUser> confirmed) {
	for (User user : allUsers) {
	    if (!isAlreadyConfirmed(user.getId(), confirmed)) {
		return false;
	    }
	}
	return true;
    }

    public boolean isConfirmed(User user, List<User> users) {
	if (users != null && users.size() > 0) {
	    for (User confirmedUser : users) {
		if (user.getId() == confirmedUser.getId()) {
		    return true;
		}
	    }
	}
	return false;
    }

    @SuppressWarnings("unchecked")
    private void saveConfirmed(HttpServletRequest request, Event event) {
	Enumeration<String> paramNames = request.getParameterNames();
	List<ConfirmedUser> confirmedUsers = event.getConfirmedUsers();

	while (paramNames.hasMoreElements()) {
	    String name = paramNames.nextElement();
	    if (name.startsWith("user-confirm:")) {
		String[] parts = name.split(":");
		if (parts != null && parts.length == 2) {
		    try {
			Long id = Long.parseLong(parts[1]);
			if (!isAlreadyConfirmed(id, confirmedUsers)) {
			    User confirmed = userManager.findById(id);
			    ConfirmedUser confirmedUser = new ConfirmedUser();
			    confirmedUser.setEvent(event);
			    confirmedUser.setUser(confirmed);
			    confirmedUsers.add(confirmedUser);
			}

		    } catch (NumberFormatException e) {
			continue;
		    }
		}
	    }
	}
	eventManager.merge(event);
    }

    private boolean isAlreadyConfirmed(Long userId, List<ConfirmedUser> confirmedUsers) {
	for (ConfirmedUser user : confirmedUsers) {
	    User confirmed = user.getUser();
	    if (userId.equals(confirmed.getId())) {
		return true;
	    }
	}
	return false;
    }

    private List<User> getConfirmedUsers(List<ConfirmedUser> confirmed, User currentUser, Event event) {
	List<User> users = new ArrayList<User>();
	if (confirmed != null && confirmed.size() > 0) {
	    for (ConfirmedUser conf : confirmed) {
		User user = conf.getUser();
		logger.debug("The following user is confirmed: " + user);
		users.add(user);
	    }
	} else {
	    logger.debug("Adding the current user: " + currentUser + " to the confirmed list");
	    confirmed = new ArrayList<ConfirmedUser>();
	    ConfirmedUser current = new ConfirmedUser();
	    current.setEvent(event);
	    current.setUser(currentUser);
	    confirmed.add(current);
	    eventManager.merge(event);
	    if (!users.contains(currentUser)) {
		users.add(currentUser);
	    }
	}
	return users;
    }

    private String getPerPerson(BigDecimal charge, List<User> allUsers) {
	int memberCount = 0;
	BigDecimal retVal = new BigDecimal(0.00);
	if (allUsers != null && allUsers.size() > 0) {
	    memberCount = allUsers.size();
	}
	if (charge != null) {
	    retVal = charge.divide(new BigDecimal(memberCount), BigDecimal.ROUND_HALF_DOWN);
	}
	return EventBean.MONEY_FORMAT.format(retVal.doubleValue());
    }

    private URI getEventLink(HttpServletRequest request, Event event) {
	StringBuilder sb = new StringBuilder(request.getProtocol());
	sb.append("://");
	sb.append(request.getServerName());
	sb.append(request.getServerPort() != 80 ? request.getServerPort() : "");
	sb.append(request.getContextPath());
	sb.append("admin/protected/gig/confirm.html?id=" + event.getId());
	try {
	    return new URI(sb.toString());
	} catch (URISyntaxException e) {
	    logger.warn("Cannot create link to page");
	    return null;
	}
    }

    /**
     * @return the eventManager
     */
    public EventManager getEventManager() {
	return eventManager;
    }

    /**
     * @param eventManager
     *            the eventManager to set
     */
    public void setEventManager(EventManager eventManager) {
	this.eventManager = eventManager;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager() {
	return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    /**
     * @param emailer
     *            the emailer to set
     */
    public void setEmailer(Emailer emailer) {
	this.emailer = emailer;
    }
}
