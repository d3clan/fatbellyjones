package com.viviquity.core.email;

import java.net.URI;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.User;

public interface Emailer {

    public boolean sendConfirmEmail(URI eventLink, User user, Event event);

}
