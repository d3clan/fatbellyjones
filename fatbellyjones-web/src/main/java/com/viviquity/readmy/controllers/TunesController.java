package com.viviquity.readmy.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.Event;
import com.viviquity.core.model.Tune;
import com.viviquity.db.manager.EventManager;
import com.viviquity.db.manager.PlaylistManager;
import com.viviquity.db.manager.TuneManager;
import com.viviquity.db.manager.UserManager;
import com.viviquity.readmy.bean.PlaylistBean;
import com.viviquity.readmy.bean.TuneBean;
import com.viviquity.readmy.validation.TuneBeanValidator;

@Controller
public class TunesController extends BaseController {

    private Logger logger = Logger.getLogger(TunesController.class);

    private static final String ERROR_RESULT_ATTRIBUTE = "errorResult";
    private static final String TUNE_BEAN_ATTRIBUTE = "tuneBean";

    private static final String[] STATUS = { Tune.INITIAL_STATUS, Tune.PRACTICE_STATUS, Tune.AGREED_STATUS,
	    Tune.PLAYING_STATUS, Tune.BREAK_STATUS };

    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PlaylistManager playlistManager;

    @Autowired
    private TuneManager tuneManager;

    @Autowired
    private TuneBeanValidator tuneBeanValidator;

    @RequestMapping("/admin/protected/tunes")
    public ModelAndView tuneHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	List<Tune> tunes = tuneManager.findAll();
	model.put("title", "Fat Belly Tunes");
	model.put("status", STATUS);
	model.put("tunes", tunes);

	logger.info("Hitting tune controller");
	// addtuneform
	return new ModelAndView("tunes", model);
    }

    @RequestMapping(value = "/admin/protected/tune/add", method = { RequestMethod.POST })
    public ModelAndView addTuneForm(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute(TUNE_BEAN_ATTRIBUTE) @Valid TuneBean eventBean, BindingResult result) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	tuneBeanValidator.validate(eventBean, result);
	if (!result.hasErrors()) {
	    Tune tune = new Tune();
	    tune.setTitle(eventBean.getTitle());
	    tune.setArtist(eventBean.getArtist());
	    tune = tuneManager.merge(tune);
	}
	model.put("status", STATUS);
	model.put(ERROR_RESULT_ATTRIBUTE, result != null ? result : null);
	logger.info("Hitting the ajax call for add to tune");
	return new ModelAndView("addtuneform", model);
    }

    @RequestMapping(value = "/admin/protected/tune/edit/{id}")
    public ModelAndView editTuneForm(HttpServletRequest request, HttpServletResponse response,
	    @PathVariable("id") Long id) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	Tune tune = tuneManager.findById(id);
	model.put("title", "Edit " + tune.getTitle());
	logger.info("Hitting edit tune");
	model.put("tune", tune);
	model.put("status", STATUS);
	return new ModelAndView("edittuneform", model);
    }

    @RequestMapping(value = "/admin/protected/tune/edit", method = { RequestMethod.POST })
    public String saveForm(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute(TUNE_BEAN_ATTRIBUTE) Tune tune) throws Exception {
	tune = tuneManager.merge(tune);
	logger.info("Hitting edit tune");
	return "redirect:/admin/protected/tunes.html";
    }

    @RequestMapping("/admin/protected/addtuneform")
    public ModelAndView addTuneFormCall(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute(TUNE_BEAN_ATTRIBUTE) @Valid TuneBean eventBean, BindingResult result) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("tuneBean", new TuneBean());
	return new ModelAndView("addtuneform");
    }

    @RequestMapping("/admin/protected/json/tunes/list")
    public @ResponseBody
    List<Tune> jsonGetTunes() throws Exception {
	return tuneManager.findAll();
    }

    @RequestMapping("/admin/protected/json/tunes/remaining")
    public @ResponseBody
    List<Tune> jsonGetTunesNotInPlaylist(
	    @ModelAttribute(PlaylistController.PLAYLIST_BEAN_ATTRIBUTE) PlaylistBean playlistBean) throws Exception {
	Event event = eventManager.findById(playlistBean.getEventId());
	if (event != null) {
	    return tuneManager.findAllNotInPlaylist(event.getPlaylist());
	} else {
	    return new ArrayList<Tune>();
	}
    }

    /**
     * @param tuneBeanValidator
     *            the tuneBeanValidator to set
     */
    public void setTuneBeanValidator(TuneBeanValidator tuneBeanValidator) {
	this.tuneBeanValidator = tuneBeanValidator;
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

}
