package com.viviquity.readmy.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.Status;

import com.viviquity.core.facebook.FacebookEvent;
import com.viviquity.core.facebook.FacebookImage;
import com.viviquity.core.image.PosterImage;
import com.viviquity.core.model.Event;
import com.viviquity.core.storage.FatbelliesIOException;
import com.viviquity.core.storage.StorageManager;
import com.viviquity.core.twitter.TwitterTweet;
import com.viviquity.db.manager.EventManager;
import com.viviquity.readmy.bean.PosterResponse;

@Controller
public class ArtifactJsonController {

    private Logger logger = Logger.getLogger(ArtifactJsonController.class);

    @Autowired
    private EventManager eventManager;

    @Autowired
    private FacebookImage facebookImage;

    @Autowired
    private FacebookEvent facebookEvent;

    @Autowired
    private PosterImage posterImage;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private TwitterTweet twitterTweet;

    @RequestMapping(value = "/admin/protected/json/artifact/poster/create", method = { RequestMethod.POST })
    public @ResponseBody
    PosterResponse jsonCreatePoster(PosterResponse poster,
	    @RequestParam(value = "force", required = false, defaultValue = "false") String force) throws Exception {
	Event event = eventManager.findById(poster.getEventId());
	Boolean forceCreate = Boolean.parseBoolean(force);
	try {
	    if (event != null) {
		URL image = null;
		if (event.getPoster() == null || forceCreate) {
		    File f = posterImage.createImage(event.getLocation(), event.getStart());
		    image = storageManager.save(f);
		    event.setPoster(image.toString());
		    eventManager.merge(event);
		} else {
		    image = new URL(event.getPoster());
		}
		poster.setResult("success");
		poster.setLink(image);
		poster.setMessage("Image created successfully");
	    } else {
		poster.setResult("error");
		poster.setMessage("Cannot find event");
	    }
	} catch (FatbelliesIOException e) {
	    poster.setResult("error");
	    poster.setMessage("Could not create and save poster");
	}
	return poster;
    }

    @RequestMapping(value = { "/admin/protected/json/artifact/facebook-image/create" }, method = { RequestMethod.POST })
    public @ResponseBody
    PosterResponse jsonCreateFacebookImage(PosterResponse poster,
	    @RequestParam(value = "force", required = false, defaultValue = "false") String force) throws Exception {
	Event event = eventManager.findById(poster.getEventId());
	Boolean forceCreate = Boolean.parseBoolean(force);
	try {
	    if (event != null) {
		URL image = null;
		if (event.getFacebookImage() == null || forceCreate) {
		    File f = facebookImage.createImage(event.getLocation());
		    image = storageManager.save(f);
		    event.setFacebookImage(image.toString());
		    eventManager.merge(event);
		} else {
		    image = new URL(event.getFacebookImage());
		}
		poster.setResult("success");
		poster.setLink(image);
		poster.setMessage("Image created successfully");
	    } else {
		poster.setResult("error");
		poster.setMessage("Cannot find event");
	    }
	} catch (FatbelliesIOException e) {
	    poster.setResult("error");
	    poster.setMessage("Could not create and save poster");
	}
	return poster;
    }

    @RequestMapping(value = { "/admin/protected/json/artifact/facebook-event/create" }, method = { RequestMethod.POST })
    public @ResponseBody
    PosterResponse jsonCreateFacebookEvent(PosterResponse poster,
	    @RequestParam(value = "force", required = false, defaultValue = "false") String force) throws Exception {
	Event event = eventManager.findById(poster.getEventId());
	Boolean forceCreate = Boolean.parseBoolean(force);
	try {
	    if (event != null) {
		Long eventId = null;
		if (event.getFacebookId() == null || forceCreate) {
		    URL image = new URL(poster.getImage());
		    eventId = facebookEvent.createEvent(event, Long.toString(event.getId()), image);
		    event.setFacebookId(eventId);
		    event.setFacebookEvent("http://www.facebook.com/event.php?eid=" + eventId);
		    event = eventManager.merge(event);
		} else {
		    eventId = event.getFacebookId();
		}
		poster.setResult("success");
		poster.setLink(new URL("http://www.facebook.com/event.php?eid=" + eventId));
		poster.setMessage("Event created successfully");
	    } else {
		poster.setResult("error");
		poster.setMessage("Cannot find event");
	    }
	} catch (FatbelliesIOException e) {
	    poster.setResult("error");
	    poster.setMessage("Could not create and save poster");
	}
	return poster;
    }

    @RequestMapping(value = { "/admin/protected/json/artifact/twitter-tweet/create" }, method = { RequestMethod.POST })
    public @ResponseBody
    PosterResponse jsonCreateTwitterTweet(HttpServletRequest request, PosterResponse poster,
	    @RequestParam(value = "force", required = false, defaultValue = "false") String force) throws Exception {
	Event event = eventManager.findById(poster.getEventId());
	Boolean forceCreate = Boolean.parseBoolean(force);
	try {
	    if (event != null) {
		String tweetId = null;
		if (event.getTweet() == null || forceCreate) {
		    Status status = twitterTweet.tweet(getEventUrl(request, event), event);
		    tweetId = Long.toString(status.getId());
		    event.setTweet(tweetId);
		    event = eventManager.merge(event);
		} else {
		    tweetId = event.getTweet();
		}

		poster.setResult("success");
		poster.setLink(new URL("http://twitter.com/#!/fatbellies/status/" + tweetId));
		poster.setMessage("Twitter updated, id " + tweetId);
	    } else {
		poster.setResult("error");
		poster.setMessage("Cannot find event");
	    }
	} catch (FatbelliesIOException e) {
	    poster.setResult("error");
	    poster.setMessage("Could not update Twitter");
	}
	return poster;
    }

    @RequestMapping(value = { "/admin/protected/json/event/confirm" }, method = { RequestMethod.POST })
    public @ResponseBody
    PosterResponse jsonConfirmEvent(HttpServletRequest request, PosterResponse poster) throws Exception {
	Event event = eventManager.findById(poster.getEventId());
	if (event != null) {
	    event.setConfirmed(Boolean.TRUE);
	    event.setTentative(Boolean.FALSE);
	    eventManager.merge(event);
	    poster.setMessage("Event is confirmed");
	    poster.setResult("success");
	} else {
	    poster.setMessage("Could not confirm event");
	    poster.setResult("error");
	}

	return poster;
    }

    /**
     * @param eventManager
     *            the eventManager to set
     */
    public void setEventManager(EventManager eventManager) {
	this.eventManager = eventManager;
    }

    /**
     * @param facebookImage
     *            the facebookImage to set
     */
    public void setFacebookImage(FacebookImage facebookImage) {
	this.facebookImage = facebookImage;
    }

    /**
     * @param posterImage
     *            the posterImage to set
     */
    public void setPosterImage(PosterImage posterImage) {
	this.posterImage = posterImage;
    }

    /**
     * @param facebookEvent
     *            the facebookEvent to set
     */
    public void setFacebookEvent(FacebookEvent facebookEvent) {
	this.facebookEvent = facebookEvent;
    }

    /**
     * @param storageManager
     *            the storageManager to set
     */
    public void setStorageManager(StorageManager storageManager) {
	this.storageManager = storageManager;
    }

    /**
     * @param twitterTweet
     *            the twitterTweet to set
     */
    public void setTwitterTweet(TwitterTweet twitterTweet) {
	this.twitterTweet = twitterTweet;
    }

    private URL getEventUrl(HttpServletRequest request, Event event) throws MalformedURLException {
	String port = request.getServerPort() != 80 ? ":" + Integer.toString(request.getServerPort()) : "";
	return new URL(request.getScheme() + "://" + request.getServerName() + port + request.getContextPath()
		+ "/gigs.html?id=" + event.getId());
    }
}
