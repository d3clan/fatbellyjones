package com.viviquity.db.manager;

import java.util.List;

import com.viviquity.core.model.Event;

public interface EventManager extends JpaManager<Long, Event> {

    public List<Event> findByDate(String start, String end);

}
