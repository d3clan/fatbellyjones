package com.viviquity.db.dao;

import java.util.List;

import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;

public interface TuneDao extends JpaDao<Long, Tune> {

    public List<Tune> findAllNotInList(List<Long> tuneIds);

    public List<Tune> findAllByStatus(final String status);

    public List<Tune> findAllNotInPlaylist(List<PlaylistEntry> playlistEntries);

}
