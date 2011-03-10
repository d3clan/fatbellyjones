package com.viviquity.db.dao;

import java.util.List;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;

public interface PlaylistDao extends JpaDao<Long, PlaylistEntry> {

    public List<PlaylistEntry> findByEvent(Event event);

    public List<PlaylistEntry> findLastPlaylist();

}
