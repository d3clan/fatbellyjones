package com.viviquity.readmy.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.User;
import com.viviquity.db.manager.EventManager;
import com.viviquity.db.manager.UserManager;
import com.viviquity.readmy.bean.EventBean;
import com.viviquity.readmy.validation.CheckEventValidator;

@Controller
public class ListEventsController extends BaseController {

    private Logger logger = Logger.getLogger(ListEventsController.class);
    private static final String EVENT_BEAN_ATTRIBUTE = "eventBean";
    private static final String ERROR_RESULT_ATTRIBUTE = "errorResult";

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    private EventManager eventManager;
    private UserManager userManager;

    @Autowired
    private CheckEventValidator validator;

    public void setValidator(CheckEventValidator validator) {
	this.validator = validator;
    }

    @Autowired
    public ListEventsController(EventManager eventManager, UserManager userManager) {
	this.eventManager = eventManager;
	this.userManager = userManager;
    }

    @RequestMapping("/admin/protected/events/list")
    public ModelAndView listEvents(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "date", required = false) Long date) throws Exception {
	Calendar calendar = Calendar.getInstance();
	if (date != null && date != 0L) {
	    calendar.setTimeInMillis(date);
	}

	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Add event");
	model.put("events", eventManager.findAll());
	model.put("year", calendar.get(Calendar.YEAR) + "");
	model.put("month", calendar.get(Calendar.MONTH));
	model.put("day", calendar.get(Calendar.DAY_OF_MONTH));
	logger.info("Hitting add event controller");
	return new ModelAndView("list-events", model);
    }

    @RequestMapping("/admin/protected/events/edit/{id}")
    public ModelAndView editEvent(@PathVariable(value = "id") Long id) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Edit event");
	logger.info("Hitting add event controller");
	Event event = eventManager.findById(id);
	EventBean eventBean = new EventBean(event);
	model.put("hours", getHours());
	model.put("mins", getMins());
	model.put("eventBean", eventBean);
	return new ModelAndView("event-form", model);
    }

    @RequestMapping("/admin/protected/events/new")
    public ModelAndView newEvent(HttpServletRequest request, @RequestParam(value = "day", required = false) Long day,
	    @ModelAttribute(EVENT_BEAN_ATTRIBUTE) @Valid EventBean eventBean, BindingResult result) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();

	eventBean = (EventBean) request.getSession(true).getAttribute(EVENT_BEAN_ATTRIBUTE);
	if (eventBean != null) {
	    request.getSession(true).removeAttribute(EVENT_BEAN_ATTRIBUTE);
	}

	Calendar fromDate = Calendar.getInstance();
	Calendar toDate = Calendar.getInstance();

	if (day != null && day != 0L) {
	    fromDate.setTimeInMillis(day);
	    toDate.setTimeInMillis(day);
	}

	if (eventBean == null) {
	    eventBean = new EventBean();
	    eventBean.setFormattedStart(DATE_FORMATTER.format(fromDate.getTime()));
	    eventBean.setFormattedEnd(DATE_FORMATTER.format(toDate.getTime()));
	}

	validator.validate(eventBean, result);
	model.put("hours", getHours());
	model.put("mins", getMins());
	model.put("title", "New event");
	model.put(EVENT_BEAN_ATTRIBUTE, eventBean);
	model.put(ERROR_RESULT_ATTRIBUTE, result != null ? result : null);
	logger.info("Hitting add new event controller");
	return new ModelAndView("event-form", model);
    }

    @RequestMapping(value = "/admin/protected/events/add")
    public String addEvent(HttpServletRequest request, @RequestParam(value = "from", required = false) Long from,
	    @RequestParam(value = "to", required = false) Long to,
	    @ModelAttribute(EVENT_BEAN_ATTRIBUTE) @Valid EventBean eventBean, BindingResult result) throws Exception {

	if (!request.getMethod().equalsIgnoreCase("POST")) {
	    return "redirect:/admin/protected/events/new.html";
	}

	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "Create new event");
	Calendar fromDate = Calendar.getInstance();
	Calendar toDate = Calendar.getInstance();

	if (StringUtils.isNotBlank(eventBean.getFormattedStart())) {
	    Date start = DATE_FORMATTER.parse(eventBean.getFormattedStart());
	    fromDate.setTimeInMillis(start.getTime());
	}
	if (StringUtils.isNotBlank(eventBean.getFormattedEnd())) {
	    Date end = DATE_FORMATTER.parse(eventBean.getFormattedEnd());
	    toDate.setTimeInMillis(end.getTime());
	}
	model.put("from", DATE_FORMATTER.format(fromDate.getTime()));
	model.put("to", DATE_FORMATTER.format(toDate.getTime()));
	logger.info("Hitting add event controller");

	model.put("title", "New event");
	model.put("hours", getHours());
	model.put("mins", getMins());
	if (!eventBean.getIsGig()) {
	    validator.validate(eventBean, result);
	    if (isValidEvent(eventBean) && !result.hasErrors()) {
		saveCalendarEvent(eventBean);
		return "redirect:/admin/protected/events/list.html?date=" + fromDate.getTimeInMillis();
	    } else {
		request.getSession(true).setAttribute(EVENT_BEAN_ATTRIBUTE, eventBean);
		return "redirect:/admin/protected/events/new.html";
	    }
	} else {
	    validator.validate(eventBean, result);
	    if (!result.hasErrors()) {

		saveCalendarEvent(eventBean);
		return "redirect:/admin/protected/gig/confirm.html?id=" + eventBean.getId();
	    } else {
		request.getSession(true).setAttribute(EVENT_BEAN_ATTRIBUTE, eventBean);
		return "redirect:/admin/protected/events/new.html";
	    }
	}

    }

    @RequestMapping("/admin/protected/json/events/list")
    public @ResponseBody
    List<EventBean> jsonGetEvents(@RequestParam(value = "start", required = false) String start,
	    @RequestParam(value = "end", required = false) String end) throws Exception {
	List<Event> events;

	if (StringUtils.isNotBlank(start) || StringUtils.isNotBlank(end)) {
	    events = eventManager.findByDate(start, end);
	} else {
	    events = eventManager.findAll();
	}

	List<EventBean> beans = new ArrayList<EventBean>();
	for (Event event : events) {
	    beans.add(new EventBean(event));
	}
	return beans;
    }

    @RequestMapping("/admin/protected/json/events/update")
    public @ResponseBody
    Boolean jsonUpdateEvent(@RequestParam(value = "id", required = true) Long id,
	    @RequestParam(value = "delta", required = true) Integer delta) throws Exception {
	Event event = eventManager.findById(id);
	if (event != null) {
	    Date start = event.getStart();
	    Date end = event.getEndDate();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(start);
	    cal.add(Calendar.DAY_OF_YEAR, delta);
	    event.setStart(cal.getTime());
	    cal.setTime(end);
	    cal.add(Calendar.DAY_OF_YEAR, delta);
	    event.setEndDate(cal.getTime());
	    event = eventManager.merge(event);
	    return true;
	} else {
	    return false;
	}
    }

    @RequestMapping("/admin/protected/json/events/popup-event")
    public ModelAndView getPopup(@RequestParam(value = "start", required = false) String start,
	    @RequestParam(value = "end", required = false) String end) throws Exception {
	return new ModelAndView("popup-event", null);
    }

    public List<String> getHours() {
	List<String> hours = new ArrayList<String>();
	for (int i = 0; i < 24; i++) {
	    if (i < 10) {
		hours.add("0" + i);
	    } else {
		hours.add(Integer.toString(i));
	    }
	}
	return hours;
    }

    public List<String> getMins() {
	List<String> mins = new ArrayList<String>();
	mins.add("00");
	mins.add("15");
	mins.add("30");
	mins.add("45");
	return mins;
    }

    private boolean isValidEvent(EventBean eventBean) {
	if (StringUtils.isNotBlank(eventBean.getFormattedStart())
		&& StringUtils.isNotBlank(eventBean.getFormattedEnd())) {
	    try {
		DATE_FORMATTER.parse(eventBean.getFormattedStart());
		DATE_FORMATTER.parse(eventBean.getFormattedStart());
		return StringUtils.isNotBlank(eventBean.getTitle());
	    } catch (ParseException e) {
		return false;
	    }
	} else {
	    return false;
	}
    }

    private Event saveCalendarEvent(EventBean eventBean) throws ParseException {
	Event event = null;
	if (eventBean.getId() != null && eventBean.getId() > 0L) {
	    event = eventManager.findById(eventBean.getId());
	}
	event = eventBean.getEvent(event);
	event.setAllDay(!event.getIsGig());
	User user = getCurrentUser();
	event.setUser(user);
	event = eventManager.merge(event);
	eventBean.setId(event.getId());
	return event;
    }

    // private Map<String, String> getEventMap(EventBean eventBean) {
    // Map<String, String> eventInfo = new HashMap<String, String>();
    // eventInfo.put("name", eventBean.getTitle());
    // // eventInfo.put("category", eventBean.get);
    // // eventInfo.put("subcategory", "Test subcategory");
    // eventInfo.put("host", eventBean.getHost());
    // eventInfo.put("location", eventBean.getLocation());
    // eventInfo.put("city", eventBean.getCity());
    // eventInfo.put("location", eventBean.getLocation());
    // eventInfo.put("start_time", Long.toString(eventBean.getStart()));
    // eventInfo.put("end_time", Long.toString(eventBean.getEnd()));
    //
    // // Optional
    // eventInfo.put("street", eventBean.getStreet());
    // eventInfo.put("phone", eventBean.getPhone());
    // eventInfo.put("email", eventBean.getEmail());
    // eventInfo.put("page_id", "130085527037539");
    // eventInfo.put("description", eventBean.getDescription());
    // eventInfo.put("privacy", "OPEN");
    // eventInfo.put("tagline", eventBean.getTagline());
    // return eventInfo;
    // }
}
