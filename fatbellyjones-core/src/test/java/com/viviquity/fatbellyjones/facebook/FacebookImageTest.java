package com.viviquity.fatbellyjones.facebook;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;
import com.viviquity.core.facebook.FacebookImage;
import com.viviquity.core.image.PosterImage;

public class FacebookImageTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateImage() throws IOException, FontFormatException {
	FacebookImage facebookImage = new FacebookImage("/META-INF/facebook-bg.gif", "/META-INF/Franks.ttf");
	File file = facebookImage.createImage("The Royal Oak");
	System.out.println(file.getAbsolutePath());
    }

    @Test
    public void testPosterImage() throws FontFormatException, IOException, DocumentException {
	PosterImage image = new PosterImage(new File("/Users/declan/Desktop"));
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.MONTH, Calendar.JANUARY);
	cal.set(Calendar.DAY_OF_MONTH, 12);
	cal.set(Calendar.HOUR_OF_DAY, 21);
	cal.set(Calendar.MINUTE, 0);
	File file = image.createImage("The Gatwick Arms, Horley", cal.getTime());
	System.out.println(file.getAbsolutePath());
    }

}
