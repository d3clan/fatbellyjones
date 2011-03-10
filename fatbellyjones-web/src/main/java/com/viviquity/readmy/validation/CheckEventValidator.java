package com.viviquity.readmy.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.viviquity.readmy.bean.EventBean;
import com.viviquity.readmy.controllers.ListEventsController;

public class CheckEventValidator implements Validator {

    private static final Logger logger = Logger.getLogger(CheckEventValidator.class);

    private static final String EMAIL_PATTERN = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @Override
    public boolean supports(Class<?> clazz) {
	return EventBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	EventBean eventBean = (EventBean) target;
	if (eventBean != null) {
	    if (eventBean.getIsGig()) {
		validateGig(eventBean, errors);
	    } else {
		validateEvent(eventBean, errors);
	    }
	}
    }

    private void validateEvent(EventBean eventBean, Errors errors) {
	validateCommon(eventBean, errors);
	Date start = new Date();
	Date end = new Date();

	try {
	    start = ListEventsController.DATE_FORMATTER.parse(eventBean.getFormattedStart());
	    end = ListEventsController.DATE_FORMATTER.parse(eventBean.getFormattedEnd());
	} catch (ParseException e) {
	    logger.error("Cannot parse the end or start date");
	}

	if (start.after(end)) {
	    errors.rejectValue("start", "validation.negative", "Start date must be before end date");
	    errors.rejectValue("end", "validation.negative", "End date must be after start date");
	}
    }

    private void validateGig(EventBean eventBean, Errors errors) {
	validateCommon(eventBean, errors);

	try {
	    ListEventsController.DATE_FORMATTER.parse(eventBean.getFormattedStart());
	    ListEventsController.DATE_FORMATTER.parse(eventBean.getFormattedStart());
	} catch (ParseException e) {
	    logger.error("Cannot parse the end or start date");
	    errors.rejectValue("start", "required", "Invalid date format");
	}

	eventBean.setFormattedEnd(eventBean.getFormattedStart());

	checkField(errors, "host", eventBean.getTitle(), "Please enter a host.");
	checkField(errors, "location", eventBean.getTitle(), "Please enter a location.");
	checkField(errors, "city", eventBean.getTitle(), "Please enter a town.");
	checkField(errors, "street", eventBean.getTitle(), "Please enter a street address.");
	checkField(errors, "phone", eventBean.getTitle(), "Please enter a phone number.");
	checkField(errors, "description", eventBean.getTitle(), "Please enter a decsription.");
	checkField(errors, "tagline", eventBean.getTitle(), "Please enter a tagline.");

	if (!isValidEmail(eventBean.getEmail())) {
	    errors.rejectValue("email", "required", "Please enter a valid email.");
	} else {
	    checkField(errors, "email", eventBean.getEmail(), "Please enter an email address.");
	}

    }

    private void validateCommon(EventBean eventBean, Errors errors) {
	checkField(errors, "title", eventBean.getTitle(), "Please enter a title.");
	checkField(errors, "start", eventBean.getFormattedStart(), "Please enter a 'start' date.");
	checkField(errors, "end", eventBean.getFormattedEnd(), "Please enter a 'end' date.");
    }

    private void checkField(Errors errors, String fieldName, String value, String message) {
	if (StringUtils.isBlank(value)) {
	    errors.rejectValue(fieldName, "required", message);
	}
    }

    private boolean isValidEmail(String email) {
	if (email != null) {
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	} else {
	    return false;
	}
    }

}
