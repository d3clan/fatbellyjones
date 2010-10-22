package com.viviquity.readmy.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.viviquity.core.model.Event;
import com.viviquity.readmy.controllers.ListEventsController;

public class EventBean {

    private static final Logger logger = Logger.getLogger(EventBean.class);

    public static final DecimalFormat MONEY_FORMAT = new DecimalFormat("######0.00");

    private Long id;
    private String title;
    private String startHour;
    private String startMin;
    private String endHour;
    private String endMin;
    private Long start;
    private String formattedStart;
    private Long end;
    private String formattedEnd;
    private Long user;
    private boolean allDay;
    private String link;
    private String className;
    private String host;
    private String location;
    private String city;
    private boolean isGig;
    private String street;
    private String phone;
    private String email;
    private String description;
    private String tagline;
    private String charge;
    private String chargePerPerson;

    public EventBean(Event event) {
	Calendar cal = Calendar.getInstance();
	this.id = event.getId();
	this.title = getTitle(event);
	this.allDay = event.isAllDay();
	this.start = event.getStart().getTime() / 1000L;
	cal.setTime(event.getStart());
	this.startHour = getStringTime(cal.get(Calendar.HOUR_OF_DAY));
	this.startMin = getStringTime(cal.get(Calendar.MINUTE));
	cal.setTime(event.getEndDate());
	this.end = event.getEndDate().getTime() / 1000L;
	this.endHour = getStringTime(cal.get(Calendar.HOUR_OF_DAY));
	this.endMin = getStringTime(cal.get(Calendar.MINUTE));
	this.link = event.getLink();
	this.formattedStart = ListEventsController.DATE_FORMATTER.format(event.getStart().getTime());
	this.formattedEnd = ListEventsController.DATE_FORMATTER.format(event.getEndDate().getTime());
	this.user = event.getUser() != null ? event.getUser().getId() : null;
	this.className = getClassName(event);
	this.host = event.getHost();
	this.location = event.getLocation();
	this.city = event.getCity();
	this.isGig = event.getIsGig();
	this.street = event.getStreet();
	this.phone = event.getPhone();
	this.email = event.getEmail();
	this.description = event.getDescription();
	this.tagline = event.getTagline();
	this.charge = getCharge(event.getCharge());
    }

    public Event getEvent(Event event) throws ParseException {
	if (event == null || event.getId() == null || event.getId() == 1L) {
	    event = new Event();
	}
	event.setId(id);
	event.setTitle(title);
	event.setAllDay(allDay);
	Date st = ListEventsController.DATE_FORMATTER.parse(formattedStart);
	st = setTime(st, startHour, startMin);
	event.setStart(st);
	Date et = ListEventsController.DATE_FORMATTER.parse(formattedEnd);
	et = setTime(et, endHour, endMin);
	event.setEndDate(et);
	event.setLink(link);
	event.setHost(host);
	event.setLocation(location);
	event.setCity(city);
	event.setIsGig(isGig);
	event.setStreet(street);
	event.setPhone(phone);
	event.setEmail(email);
	event.setDescription(description);
	event.setTagline(tagline);
	event.setCharge(parseCharge(charge));
	return event;
    }

    public EventBean() {
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the startHour
     */
    public String getStartHour() {
	return startHour;
    }

    /**
     * @param startHour
     *            the startHour to set
     */
    public void setStartHour(String startHour) {
	this.startHour = startHour;
    }

    /**
     * @return the startMin
     */
    public String getStartMin() {
	return startMin;
    }

    /**
     * @param startMin
     *            the startMin to set
     */
    public void setStartMin(String startMin) {
	this.startMin = startMin;
    }

    /**
     * @return the endHour
     */
    public String getEndHour() {
	return endHour;
    }

    /**
     * @param endHour
     *            the endHour to set
     */
    public void setEndHour(String endHour) {
	this.endHour = endHour;
    }

    /**
     * @return the endMin
     */
    public String getEndMin() {
	return endMin;
    }

    /**
     * @param endMin
     *            the endMin to set
     */
    public void setEndMin(String endMin) {
	this.endMin = endMin;
    }

    /**
     * @return the start
     */
    public Long getStart() {
	return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(Long start) {
	this.start = start / 1000;
    }

    /**
     * @return the formattedStart
     */
    public String getFormattedStart() {
	return formattedStart;
    }

    /**
     * @param formattedStart
     *            the formattedStart to set
     */
    public void setFormattedStart(String formattedStart) {
	this.formattedStart = formattedStart;
    }

    /**
     * @return the formattedEnd
     */
    public String getFormattedEnd() {
	return formattedEnd;
    }

    /**
     * @param formattedEnd
     *            the formattedEnd to set
     */
    public void setFormattedEnd(String formattedEnd) {
	this.formattedEnd = formattedEnd;
    }

    /**
     * @return the end
     */
    public Long getEnd() {
	return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(Long end) {
	this.end = end;
    }

    /**
     * @return the user
     */
    public Long getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(Long user) {
	this.user = user;
    }

    /**
     * @return the allDay
     */
    public boolean isAllDay() {
	return allDay;
    }

    /**
     * @param allDay
     *            the allDay to set
     */
    public void setAllDay(boolean allDay) {
	this.allDay = allDay;
    }

    /**
     * @return the link
     */
    public String getLink() {
	return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(String link) {
	this.link = link;
    }

    /**
     * @return the className
     */
    public String getClassName() {
	return className;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
	this.className = className;
    }

    /**
     * @return the host
     */
    public String getHost() {
	return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
	this.host = host;
    }

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the city
     */
    public String getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    /**
     * @return the isGig
     */
    public boolean getIsGig() {
	return isGig;
    }

    /**
     * @param isGig
     *            the isGig to set
     */
    public void setIsGig(boolean isGig) {
	this.isGig = isGig;
    }

    /**
     * @return the street
     */
    public String getStreet() {
	return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
	this.street = street;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
	return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
	this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the tagline
     */
    public String getTagline() {
	return tagline;
    }

    /**
     * @param tagline
     *            the tagline to set
     */
    public void setTagline(String tagline) {
	this.tagline = tagline;
    }

    /**
     * @return the charge
     */
    public String getCharge() {
	return charge;
    }

    /**
     * @param charge
     *            the charge to set
     */
    public void setCharge(String charge) {
	this.charge = charge;
    }

    /**
     * @return the chargePerPerson
     */
    public String getChargePerPerson() {
	return chargePerPerson;
    }

    /**
     * @param chargePerPerson
     *            the chargePerPerson to set
     */
    public void setChargePerPerson(String chargePerPerson) {
	this.chargePerPerson = chargePerPerson;
    }

    private BigDecimal parseCharge(String charge) {
	if (StringUtils.isNotBlank(charge)) {
	    try {
		return new BigDecimal(MONEY_FORMAT.parse(charge).doubleValue());
	    } catch (ParseException e) {
		logger.warn("Cannot parse the charge value" + charge, e);
	    } catch (NumberFormatException e) {
		logger.warn("Cannot parse the charge value" + charge, e);
	    }
	}
	return new BigDecimal(0.00);
    }

    private String getCharge(BigDecimal charge) {
	if (charge != null) {
	    return MONEY_FORMAT.format(charge.doubleValue());
	} else {
	    return MONEY_FORMAT.format(00.00);
	}
    }

    private Date setTime(Date date, String startHour, String startMin) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.set(Calendar.HOUR, getInteger(startHour));
	cal.set(Calendar.MINUTE, getInteger(startMin));
	return cal.getTime();
    }

    private Integer getInteger(String value) {
	int retRes = 0;
	if (StringUtils.isNotBlank(value)) {
	    try {
		retRes = Integer.parseInt(value);
	    } catch (NumberFormatException e) {
		logger.warn("Cannot parse value: " + value);
	    }
	}
	return retRes;
    }

    private String getClassName(Event event) {
	if (event.getIsGig()) {
	    return "gig";
	} else {
	    return event.getUser() != null ? event.getUser().getClassName() : null;
	}
    }

    private String getTitle(Event event) {
	if (event.getIsGig()) {
	    return event.getTitle();
	} else {
	    return (event.getUser() != null ? event.getUser().getFirstName() + " - " : "") + event.getTitle();
	}
    }

    private String getStringTime(Integer time) {
	if (time < 10) {
	    return "0" + Integer.toString(time);
	} else {
	    return Integer.toString(time);
	}
    }
}
