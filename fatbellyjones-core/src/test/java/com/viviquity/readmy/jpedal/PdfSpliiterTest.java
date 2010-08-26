package com.viviquity.readmy.jpedal;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;
import org.jpedal.constants.JPedalSettings;
import org.jpedal.exception.PdfException;
import org.junit.Test;

public class PdfSpliiterTest {

	private static final Logger logger = Logger.getLogger(PdfSpliiterTest.class);

	@Test
	public void testDecode() throws PdfException {
		logger.info("Home directory is " + ClassLoader.getSystemResource("."));
		URL url = ClassLoader.getSystemResource("test.pdf");

		String pdfFile = url.getFile();

		PdfDecoder decoder = new PdfDecoder(true);
		PdfDecoder.setFontReplacements(decoder);
		decoder.openPdfFile(pdfFile);

		Map<Object, Object> mapValues = new HashMap<Object, Object>();

		/** USEFUL OPTIONS */
		// do not scale above this figure
		mapValues.put(JPedalSettings.EXTRACT_AT_BEST_QUALITY_MAXSCALING,
				new Integer(2));

		// alternatively specify a page size (aspect ratio preserved so will do
		// best fit)
		// set a page size (JPedal will put best fit to this)
		mapValues.put(JPedalSettings.EXTRACT_AT_PAGE_SIZE, new String[] {
				"2000", "1600" });

		// which takes priority (default is false)
		mapValues.put(JPedalSettings.PAGE_SIZE_OVERRIDES_IMAGE, Boolean.TRUE);

		int start = 1, end = decoder.getPageCount();

		BufferedImage[] multiPages = new BufferedImage[1 + (end - start)];

	}

}
