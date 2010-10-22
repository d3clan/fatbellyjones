package com.viviquity.core.email.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import com.viviquity.core.email.Emailer;
import com.viviquity.core.model.Event;
import com.viviquity.core.model.User;

public class EmailerImpl implements Emailer {

    private static final String NEWLINE = "\r\n";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
    private static final Logger logger = Logger.getLogger(EmailerImpl.class);

    private String smtpServer;
    private String username;
    private String password;
    private int portNumber;
    private URL logo;

    public EmailerImpl(String smtpServer, String username, String password, int portNumber, String logo) {
	this.smtpServer = smtpServer;
	this.username = username;
	this.password = password;
	this.portNumber = portNumber;
	this.logo = getLogoURL(logo);
    }

    @Override
    public boolean sendConfirmEmail(URI eventLink, User user, Event event) {
	try {
	    HtmlEmail email = new HtmlEmail();
	    email.setHostName(smtpServer);
	    email.setAuthentication(username, password);
	    email.setSmtpPort(portNumber);

	    email.addTo(user.getEmail(), user.getFirstName() + " " + user.getSurname());

	    email.setFrom("no-reply@fatbellyjones.net", "Fat Belly Jones Admin");
	    email.setSubject("Please confirm your availablity on " + event.getStart().toString());

	    String cid = email.embed(logo, "Apache logo");

	    String html = getConfirmationHtml(eventLink, user, event, cid);

	    // set the html message
	    email.setHtmlMsg(html);

	    // set the alternative message
	    email.setTextMsg("Sorry, your email client does not support HTML messages.");

	    // send the email
	    email.send();
	    return true;
	} catch (EmailException e) {
	    logger.warn("Could not send email.", e);
	    return false;
	} catch (MalformedURLException e) {
	    logger.warn("Could not send email.", e);
	    return false;
	}

    }

    private String getConfirmationHtml(URI eventLink, User user, Event event, String cid) throws MalformedURLException {
	StringBuilder sb = new StringBuilder("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + NEWLINE);
	sb.append("<body>" + NEWLINE);
	sb.append("<img src=\"cid:" + cid + "\" title=\"Fat Belly Jones\"/>" + NEWLINE);
	sb.append("<p>Hello " + user.getFirstName() + ",</p>" + NEWLINE);
	sb.append("<p>" + event.getUser().getFirstName() + " has created a new gig in the calendar on" + NEWLINE);
	sb.append(sdf.format(event.getStart()) + " at " + event.getLocation() + ".</p>" + NEWLINE);
	sb.append("<p>To confirm your availablity, please <a href=\"" + eventLink.toURL().toString()
		+ "\">visit the admin site</a></p>" + NEWLINE);
	sb.append("<p>Or copy the following link, and paste it into the address bar of your browser.</p>" + NEWLINE);
	sb.append("<p>" + eventLink.toURL().toString() + "</p>" + NEWLINE);
	sb.append("</body>" + NEWLINE);
	sb.append("</html>" + NEWLINE);
	return sb.toString();
    }

    private URL getLogoURL(String logo) {
	return EmailerImpl.class.getResource(logo);
    }
}
