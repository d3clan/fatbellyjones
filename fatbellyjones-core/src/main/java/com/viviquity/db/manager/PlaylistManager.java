package com.viviquity.db.manager;

import java.util.List;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;

public interface PlaylistManager extends JpaManager<Long, PlaylistEntry> {

    public List<PlaylistEntry> findByEvent(Event event);

    public List<PlaylistEntry> findLastPlaylist();

}
