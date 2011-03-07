package com.viviquity.readmy.controllers;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.viviquity.core.model.User;
import com.viviquity.core.storage.FatbelliesIOException;
import com.viviquity.core.storage.StorageManager;
import com.viviquity.core.utils.ImageUtils;
import com.viviquity.db.manager.UserManager;
import com.viviquity.readmy.bean.UserBean;

@Controller
public class ProfileController extends BaseController {

    private Logger logger = Logger.getLogger(ProfileController.class);

    private StorageManager storageManager;
    private UserManager userManager;

    @Autowired
    public ProfileController(StorageManager storageManager, UserManager userManager) {
	this.storageManager = storageManager;
	this.userManager = userManager;
    }

    @RequestMapping("/admin/protected/profile")
    public ModelAndView homeHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, Object> model = new HashMap<String, Object>();
	User user = getCurrentUser();
	model.put("title", "Edit your profile (" + user.getFirstName() + " " + user.getSurname() + ")");
	logger.info("Edit your profile");
	UserBean userBean = new UserBean();
	userBean.setEmail(user.getEmail());
	userBean.setInstrument(user.getInstrument());
	userBean.setFirstName(user.getFirstName());
	userBean.setId(user.getId());
	userBean.setMobile(user.getMobile());
	userBean.setSurname(user.getSurname());
	userBean.setTown(user.getCity());
	userBean.setPhoto(user.getPhoto());
	model.put("userBean", userBean);
	return new ModelAndView("profile", model);
    }

    @RequestMapping(value = "/admin/protected/profile/save", method = RequestMethod.POST)
    public ModelAndView saveProfile(HttpServletRequest request, @ModelAttribute("userBean") UserBean userBean)
	    throws FatbelliesIOException {
	User user = userManager.findById(userBean.getId());
	if (user != null) {
	    if (userBean.getImage() != null && userBean.getImage().getSize() > 0) {
		URL image = saveFile(userBean.getFirstName(), userBean.getImage());
		user.setPhoto(image.toString());
	    }
	    user.setEmail(userBean.getEmail());
	    user.setInstrument(userBean.getInstrument());
	    user.setCity(userBean.getTown());
	    user.setFirstName(userBean.getFirstName());
	    user.setMobile(userBean.getMobile());
	    user.setSurname(userBean.getSurname());
	    userManager.merge(user);
	}
	return new ModelAndView("redirect:/admin/protected/profile.html");
    }

    private URL saveFile(String fname, MultipartFile multipartFile) throws FatbelliesIOException {
	String filename = multipartFile.getOriginalFilename();
	FileOutputStream fis = null;
	File f;
	try {
	    f = File.createTempFile(fname, filename.substring(filename.lastIndexOf('.'), filename.length()));
	    fis = new FileOutputStream(f);
	} catch (IOException e) {
	    throw new FatbelliesIOException("Cannot create and save image", e);
	}

	InputStream is = null;
	try {
	    is = multipartFile.getInputStream();
	    byte[] data = new byte[1024];
	    int in = 0;
	    while ((in = is.read(data)) > 0) {
		fis.write(data);
	    }
	} catch (FileNotFoundException e) {
	    throw new FatbelliesIOException("Cannot create and save image", e);
	} catch (IOException e) {
	    throw new FatbelliesIOException("Cannot create and save image", e);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (IOException e) {
		    logger.warn("Cannot close stream", e);
		}
	    }
	    if (is != null) {
		try {
		    is.close();
		} catch (IOException e) {
		    logger.warn("Cannot close stream", e);
		}
	    }
	}
	if (f != null) {
	    return convertAndCleanUp(f);
	} else {
	    return null;
	}
    }

    private URL convertAndCleanUp(File f) {
	File converted = null;
	URL url = null;
	try {
	    converted = convertImage(f);
	    url = storageManager.save(converted);
	    return url;
	} catch (Exception e) {
	    logger
		    .error(
			    "There is a problem with connecting, saving and uploading the image. Please see stack trace for more details.",
			    e);
	    return null;
	} finally {
	    deleteFile(f);
	    deleteFile(converted);
	}
    }

    private void deleteFile(File f) {
	if (!f.delete()) {
	    logger.warn("Cannot delete file: " + f);
	}
    }

    private File convertImage(File image) {
	InputStream imageIs = null;
	InputStream convertedIs = null;
	OutputStream fos = null;
	try {
	    imageIs = new FileInputStream(image);
	    convertedIs = ImageUtils.scaleImage(imageIs, 200, 300);
	    File thumb = File.createTempFile("thumb", ".jpg");
	    fos = new FileOutputStream(thumb);
	    IOUtils.copy(convertedIs, fos);
	    return thumb;
	} catch (FileNotFoundException e) {
	    logger.error("Cannot find image for conversion.", e);
	    return null;
	} catch (IOException e) {
	    logger.error("Cannot create input stream.", e);
	    return null;
	} finally {
	    closeStream(imageIs);
	    closeStream(convertedIs);
	    closeStream(fos);
	}
    }

    private void closeStream(Closeable is) {
	try {
	    if (is != null) {
		is.close();
	    }
	} catch (IOException e) {
	    logger.warn("Cannot close stream", e);
	}
    }

}
