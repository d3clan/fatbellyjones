package com.viviquity.db.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;
import com.viviquity.db.dao.JpaDao;
import com.viviquity.db.dao.TuneDao;
import com.viviquity.db.manager.TuneManager;

@Service("tuneManager")
public class TuneManagerImpl extends JpaManagerImpl<Long, Tune> implements TuneManager {

    @Autowired
    public TuneManagerImpl(JpaDao<Long, Tune> tuneDao) {
	super(tuneDao);
    }

    public List<Tune> findAllNotInList(List<Long> tuneIds) {
	return ((TuneDao) dao).findAllNotInList(tuneIds);
    }

    public List<Tune> findAllByStatus(final String status) {
	return ((TuneDao) dao).findAllByStatus(status);
    }

    @Override
    public List<Tune> findAllNotInPlaylist(List<PlaylistEntry> playlistEntries) {
	return ((TuneDao) dao).findAllNotInPlaylist(playlistEntries);
    }

}
