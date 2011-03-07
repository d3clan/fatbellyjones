package com.viviquity.readmy.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.viviquity.readmy.bean.TuneBean;

public class TuneBeanValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
	return TuneBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	TuneBean tuneBean = (TuneBean) target;
	if (tuneBean != null) {
	    if (StringUtils.isBlank(tuneBean.getArtist())) {
		errors.rejectValue("artist", "required", "Please supply an artist name.");
	    }
	    if (StringUtils.isBlank(tuneBean.getTitle())) {
		errors.rejectValue("title", "required", "Please supply an title name.");
	    }
	}
    }

}
