package com.viviquity.db.manager;

import java.util.List;

import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;

public interface TuneManager extends JpaManager<Long, Tune> {

    public List<Tune> findAllNotInList(List<Long> tuneIds);

    public List<Tune> findAllNotInPlaylist(List<PlaylistEntry> playlistEntries);

    public List<Tune> findAllByStatus(final String status);

}
