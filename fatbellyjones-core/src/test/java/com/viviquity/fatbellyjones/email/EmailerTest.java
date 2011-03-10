package com.viviquity.fatbellyjones.email;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.viviquity.core.email.Emailer;
import com.viviquity.core.email.impl.EmailerImpl;
import com.viviquity.core.model.Event;
import com.viviquity.core.model.User;

public class EmailerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSendConfirmEmail() throws URISyntaxException {
	Emailer emailer = new EmailerImpl("mail.semantico.net", "declan", "dhoberaps", 25, "logo.gif");
	User user = new User();
	user.setFirstName("Declan");
	user.setSurname("Newman");
	user.setEmail("googlydec@gmail.com");

	User creator = new User();
	creator.setFirstName("Declan");
	creator.setSurname("Newman");

	Event event = new Event();
	event.setStart(new Date());
	event.setLocation("Twatsville");
	event.setUser(creator);
	boolean sent = emailer.sendConfirmEmail(new URI("http://www.google.com/"), user, event);
	assertTrue(sent);
    }
}
