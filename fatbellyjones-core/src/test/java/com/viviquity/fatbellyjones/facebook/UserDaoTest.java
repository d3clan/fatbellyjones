package com.viviquity.fatbellyjones.facebook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.Role;
import com.viviquity.core.model.User;
import com.viviquity.db.dao.EventDao;
import com.viviquity.db.dao.RoleDao;
import com.viviquity.db.dao.UserDao;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserDaoTest {

    @Autowired
    @Resource(name = "userDao")
    private UserDao userDao;

    @Autowired
    @Resource(name = "eventDao")
    private EventDao eventDao;

    @Autowired
    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Before
    public void setup() {
	userDao.removeAll();
	eventDao.removeAll();
	roleDao.removeAll();
    }

    @Test
    public void testGetAll() {

	List<Event> events = eventDao.findAll();

	assertEquals(0, events.size());

	assertNotNull(userDao);
	User user = new User();
	user.setCity("Lewes");
	user.setCountry("GB");
	user.setEmail("email");
	user.setFirstName("Declan");
	user.setRole(getRole());
	user.setEvents(getEvents());
	userDao.persist(user);

	List<User> all = userDao.findAll();

	assertEquals(1, all.size());
    }

    private Role getRole() {
	Role role = new Role();
	role.setName("ADMIN");
	return role;
    }

    private List<Event> getEvents() {
	List<Event> events = new ArrayList<Event>();
	for (int i = 0; i < 10; i++) {
	    Event event = new Event();
	    event.setAllDay(true);
	    event.setEndDate(new Date());
	    event.setLink("http://www.google.com/");
	    event.setStart(new Date());
	    event.setTitle("A title");
	    events.add(event);
	}
	return events;
    }

}
