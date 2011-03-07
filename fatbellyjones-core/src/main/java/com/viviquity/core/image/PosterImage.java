package com.viviquity.core.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class PosterImage {

    private File baseDir;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String[] amPm = { "am", "pm" };
    private String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
    private String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" };
    private BufferedImage bgImage;
    private Font topTextFont;
    private Font dateFont;

    public PosterImage(File baseDir) throws FontFormatException, IOException {
	topTextFont = Font.createFont(Font.TRUETYPE_FONT, PosterImage.class
		.getResourceAsStream("/META-INF/WORLDOFW.TTF"));
	dateFont = Font.createFont(Font.TRUETYPE_FONT, PosterImage.class.getResourceAsStream("/META-INF/Franks.ttf"));
	bgImage = ImageIO.read(PosterImage.class.getResource("/META-INF/poster-template.jpg"));
	this.baseDir = baseDir;
    }

    public File createImage(String venue, Date date) throws IOException, DocumentException {
	RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	String dateString = getFormattedDate(cal);

	Graphics2D gr = bgImage.createGraphics();
	gr.addRenderingHints(hints);

	String filename = getFilename(venue, date);

	File file = File.createTempFile(filename, ".png");
	if (!file.exists()) {
	    if (!file.createNewFile()) {
		throw new RuntimeException("Cannot create new file");
	    }
	}

	dateFont = dateFont.deriveFont(300f);

	gr.setFont(dateFont);

	FontMetrics fm = gr.getFontMetrics();

	int textwidth = fm.stringWidth(dateString);
	int picwidth = bgImage.getWidth();

	FontRenderContext frc = gr.getFontRenderContext();
	TextLayout textLayout = new TextLayout(dateString, dateFont, frc);

	gr.setPaint(Color.WHITE);
	AffineTransform at = AffineTransform.getTranslateInstance((picwidth / 2) - (textwidth / 2), 960);
	Shape outline = textLayout.getOutline(at);
	gr.fill(outline);
	gr.setPaint(Color.BLACK);
	gr.draw(outline);

	dateFont = dateFont.deriveFont(210f);

	gr.setFont(dateFont);

	fm = gr.getFontMetrics();

	String timeString = getFormattedTime(cal);
	textwidth = fm.stringWidth(timeString);

	textLayout = new TextLayout(timeString, dateFont, frc);

	gr.setPaint(Color.WHITE);
	at = AffineTransform.getTranslateInstance((picwidth / 2) - (textwidth / 2), 1200);
	outline = textLayout.getOutline(at);
	gr.fill(outline);
	gr.setPaint(Color.BLACK);
	gr.draw(outline);

	String topText = venue + ", presents...";

	topTextFont = topTextFont.deriveFont(120f);
	gr.setFont(topTextFont);
	fm = gr.getFontMetrics();
	frc = gr.getFontRenderContext();
	textLayout = new TextLayout(topText, topTextFont, frc);

	gr.setPaint(Color.WHITE);
	textwidth = fm.stringWidth(topText);
	at = AffineTransform.getTranslateInstance((picwidth - (textwidth + 100)), 600);
	outline = textLayout.getOutline(at);
	gr.fill(outline);
	gr.setPaint(Color.BLACK);
	gr.draw(outline);

	if (ImageIO.write(bgImage, "png", file)) {
	    return createPdf(file, venue, date);
	} else {
	    throw new IOException("Could not create new facebook image");
	}

    }

    private String getFilename(String venue, Date date) {
	if (StringUtils.isNotBlank(venue)) {
	    return venue.replaceAll("[^a-zA-Z0-9]", "") + "-" + sdf.format(date);
	} else {
	    return null;
	}
    }

    private File createPdf(File image, String venue, Date date) throws MalformedURLException, IOException,
	    DocumentException {
	String filename = getFilename(venue, date);
	File file = File.createTempFile(filename, ".pdf");

	Document document = new Document();
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
	document.setMargins(0, 0, 0, 0);
	addMetadata(document, venue, date);
	document.open();
	Image poster = Image.getInstance(image.getAbsolutePath());

	Rectangle area = document.getPageSize();
	poster.scaleToFit(area.getWidth(), area.getHeight());
	document.add(poster);
	document.close();
	writer.close();
	return file;
    }

    private static void addMetadata(Document document, String venue, Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	document.addTitle("Fat Belly Jones - " + venue + " - " + sdf.format(date));
	document.addSubject("Poster for " + venue);
	document.addKeywords("Fat Belly Jones," + venue);
	document.addAuthor("Fat Belly Jones");
	document.addCreator("Declan Newman");
    }

    private String getFormattedDate(Calendar cal) {
	String day = daysOfWeek[cal.get(Calendar.DAY_OF_WEEK) - 1];
	int date = cal.get(Calendar.DAY_OF_MONTH);
	String suffix = getSuffix(date);
	String month = months[cal.get(Calendar.MONTH)];

	StringBuilder sb = new StringBuilder();
	sb.append(day);
	sb.append(" ");
	sb.append(date + suffix);
	sb.append(" ");
	sb.append(month);
	return sb.toString();
    }

    private String getFormattedTime(Calendar cal) {
	int hour = cal.get(Calendar.HOUR);
	int min = cal.get(Calendar.MINUTE);
	StringBuilder hourMin = new StringBuilder(Integer.toString(hour));

	if (min != 0) {
	    hourMin.append(":" + min);
	}
	hourMin.append(amPm[cal.get(Calendar.AM_PM)]);
	return hourMin.toString();
    }

    private static String getSuffix(int i) {
	switch (i) {
	case 1:
	case 21:
	case 31:
	    return "st";
	case 2:
	case 22:
	    return "nd";
	case 3:
	case 23:
	    return "rd";
	default:
	    return "th";
	}
    }

}
