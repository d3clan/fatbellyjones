package com.viviquity.readmy.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;
import com.viviquity.core.pdf.PlaylistPdf;
import com.viviquity.db.manager.EventManager;
import com.viviquity.db.manager.PlaylistManager;
import com.viviquity.db.manager.TuneManager;
import com.viviquity.db.manager.UserManager;
import com.viviquity.readmy.bean.PlaylistBean;
import com.viviquity.readmy.bean.TuneBean;

@Controller
public class PlaylistController extends BaseController {

    public static final String PLAYLIST_BEAN_ATTRIBUTE = "playlistBean";

    private static final Logger logger = Logger.getLogger(PlaylistController.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PlaylistManager playlistManager;

    @Autowired
    private TuneManager tuneManager;

    @RequestMapping("/admin/protected/playlists")
    public ModelAndView listAllPlaylists(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("title", "List all playlists");
	model.put("gigs", eventManager.findConfirmedGigs());
	return new ModelAndView("allplaylists", model);
    }

    @RequestMapping("/admin/protected/playlist/{eventId}")
    public ModelAndView homeHandler(HttpServletRequest request, HttpServletResponse response,
	    @PathVariable("eventId") Long eventId) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();

	Event event = eventManager.findById(eventId);
	List<PlaylistEntry> playlist = new ArrayList<PlaylistEntry>();

	if (event != null) {
	    playlist = event.getPlaylist();
	    if (playlist == null || playlist.size() == 0) {
		playlist = playlistManager.findLastPlaylist();
		event.setPlaylist(copyPlaylist(event, playlist));
		event = eventManager.merge(event);
	    }
	} else {
	    return new ModelAndView("error", model);
	}

	model.put("title", "Playlist for " + event.getTitle());
	model.put("event", event);

	logger.info("Hitting home controller");
	return new ModelAndView("playlists", model);
    }

    @RequestMapping("/admin/protected/pdf/playlist/{eventId}")
    public void getPdfPlaylist(HttpServletRequest request, HttpServletResponse response,
	    @PathVariable("eventId") Long eventId) throws Exception {

	Event event = eventManager.findById(eventId);

	response.setContentType("application/pdf");
	response.setHeader("content-disposition:", "attachment; " + sdf.format(new Date()) + ".pdf");
	logger.info("Creating PDF playlist");
	PlaylistPdf.getPlaylistPdf(event, response.getOutputStream());
    }

    @RequestMapping(value = "/admin/protected/playlisttunes", method = { RequestMethod.POST })
    public ModelAndView addTuneFormCall(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("id") Long id) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	Event event = eventManager.findById(id);
	List<Tune> tunes = null;
	if (event != null) {
	    tunes = tuneManager.findAllNotInPlaylist(event.getPlaylist());
	}
	model.put("event", event);
	model.put("tunes", tunes);
	return new ModelAndView("playlisttunes", model);
    }

    @RequestMapping("/admin/protected/json/playlist")
    public @ResponseBody
    List<TuneBean> jsonGetTunes(@ModelAttribute(PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean) throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    List<Tune> tunes = tuneManager.findAllNotInPlaylist(event.getPlaylist());
	    return getTuneBeanListFromTuneList(tunes);
	} else {
	    return null;
	}
    }

    @RequestMapping("/admin/protected/json/ordered")
    public @ResponseBody
    List<TuneBean> jsonGetPlaylist(@ModelAttribute(PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean) throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    return getPlaylistBeans(event.getPlaylist());
	} else {
	    return null;
	}
    }

    @RequestMapping("/admin/protected/json/playlist/order")
    public @ResponseBody
    List<TuneBean> jsonUpdateOrder(@ModelAttribute(PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean) throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    Set<Integer> breakPositions = new HashSet<Integer>();
	    Set<PlaylistEntry> breakEntries = new HashSet<PlaylistEntry>();

	    List<PlaylistEntry> entries = event.getPlaylist();
	    Map<Integer, Long> ids = playlistBean.getTuneIds();
	    Set<Integer> keySet = ids.keySet();

	    for (Integer key : keySet) {
		for (PlaylistEntry entry : entries) {
		    Long id = ids.get(key);
		    if (id.equals(entry.getTune().getId())) {
			entry.setOrder(key);
			break;
		    }
		}
	    }

	    Iterator<Integer> poses = breakPositions.iterator();
	    Iterator<PlaylistEntry> ents = breakEntries.iterator();
	    while (poses.hasNext()) {
		Integer pos = poses.next();
		PlaylistEntry ent = ents.next();
		logger.info("Adding breaks in at " + pos + " for " + ent.getId());
		ent.setOrder(pos);
	    }

	    event.setPlaylist(entries);
	    event = eventManager.merge(event);
	    entries = event.getPlaylist();
	    return getPlaylistBeans(entries);
	} else {
	    return null;
	}
    }

    @RequestMapping("/admin/protected/json/playlist/add")
    public @ResponseBody
    List<TuneBean> jsonAddToPlaylist(@ModelAttribute(PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean)
	    throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    Long tuneId = playlistBean.getTuneId();
	    List<PlaylistEntry> entries = event.getPlaylist();

	    PlaylistEntry pe = null;

	    if (entries.size() > 0) {
		pe = entries.get(entries.size() - 1);
	    }

	    Tune tune = tuneManager.findById(tuneId);
	    PlaylistEntry entry = new PlaylistEntry();
	    entry.setEvent(event);
	    entry.setTune(tune);
	    entry.setOrder((pe != null ? pe.getOrder() : 0) + 1);
	    entries.add(entry);
	    event.setPlaylist(entries);
	    event = eventManager.merge(event);
	    return getPlaylistBeans(event.getPlaylist());
	} else {
	    return null;
	}
    }

    @RequestMapping("/admin/protected/json/playlist/remove")
    public @ResponseBody
    List<TuneBean> jsonRemoveFromPlaylist(@ModelAttribute(PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean)
	    throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    Long position = new Long(playlistBean.getPosition());
	    List<PlaylistEntry> entries = event.getPlaylist();
	    PlaylistEntry toBeDeleted = null;

	    for (PlaylistEntry entry : entries) {
		if (position.equals(entry.getTune().getId())) {
		    toBeDeleted = entry;
		    break;
		}
	    }

	    if (toBeDeleted != null) {
		entries.remove(toBeDeleted);
	    }

	    event.setPlaylist(entries);
	    event = eventManager.merge(event);
	    return getPlaylistBeans(event.getPlaylist());
	} else {
	    return null;
	}
    }

    private List<TuneBean> getPlaylistBeans(List<PlaylistEntry> playlist) {
	List<TuneBean> playlistBeans = new ArrayList<TuneBean>();
	if (playlist != null && playlist.size() > 0) {
	    for (PlaylistEntry entry : playlist) {
		TuneBean bean = new TuneBean();
		bean.setId(entry.getTune().getId());
		bean.setArtist(entry.getTune().getArtist());
		bean.setTitle(entry.getTune().getTitle());
		bean.setClassName(entry.getTune().getStatus());
		playlistBeans.add(bean);
	    }
	}
	return playlistBeans;
    }

    private List<TuneBean> getTuneBeanListFromTuneList(List<Tune> tunes) {
	List<TuneBean> beans = new ArrayList<TuneBean>();
	for (Tune tune : tunes) {
	    TuneBean tb = new TuneBean();
	    tb.setArtist(tune.getArtist());
	    tb.setTitle(tune.getTitle());
	    tb.setClassName(tune.getStatus());
	    beans.add(tb);
	}
	return beans;
    }

    /**
     * @param eventManager
     *            the eventManager to set
     */
    public void setEventManager(EventManager eventManager) {
	this.eventManager = eventManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    /**
     * @param playlistManager
     *            the playlistManager to set
     */
    public void setPlaylistManager(PlaylistManager playlistManager) {
	this.playlistManager = playlistManager;
    }

    private List<PlaylistEntry> copyPlaylist(Event ev, List<PlaylistEntry> playlist) {
	List<PlaylistEntry> list = new ArrayList<PlaylistEntry>();
	for (PlaylistEntry entry : list) {
	    PlaylistEntry ple = new PlaylistEntry();
	    ple.setEvent(ev);
	    ple.setOrder(entry.getOrder());
	    ple.setSet(entry.getSet());
	    ple.setTune(entry.getTune());
	    list.add(entry);
	}
	return list;
    }

}
