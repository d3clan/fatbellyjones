package com.viviquity.fatbellyjones.facebook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.viviquity.db.dao.EventDao;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class EventDaoTest {

    // @Autowired
    // @Resource(name = "userDao")
    // private UserDao userDao;

    @Autowired
    @Resource(name = "eventDao")
    private EventDao eventDao;

    @Before
    public void setup() {
	eventDao.removeAll();
    }

    @Test
    public void testGetAll() {
	assertNotNull(eventDao);
	Event event = new Event();
	event.setAllDay(false);
	event.setEndDate(new Date());
	event.setLink("link");
	event.setStart(new Date());
	event.setTitle("A Title");
	eventDao.persist(event);

	List<Event> all = eventDao.findAll();

	assertEquals(1, all.size());
    }

}
