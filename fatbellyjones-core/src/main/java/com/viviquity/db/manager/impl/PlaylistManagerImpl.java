package com.viviquity.db.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.dao.PlaylistDao;
import com.viviquity.db.manager.PlaylistManager;

@Service("eventManager")
public class PlaylistManagerImpl extends JpaManagerImpl<Long, PlaylistEntry> implements PlaylistManager {

    @Autowired
    public PlaylistManagerImpl(JpaDao<Long, PlaylistEntry> playlistDao) {
	super(playlistDao);
    }

    public List<PlaylistEntry> findByEvent(Event event) {
	return ((PlaylistDao) dao).findByEvent(event);
    }

    public List<PlaylistEntry> findLastPlaylist() {
	List<PlaylistEntry> playlist = ((PlaylistDao) dao).findLastPlaylist();
	return playlist;
    }

}
