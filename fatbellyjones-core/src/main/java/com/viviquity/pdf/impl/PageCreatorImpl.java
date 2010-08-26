package com.viviquity.pdf.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;
import org.jpedal.color.ColorSpaces;
import org.jpedal.constants.JPedalSettings;
import org.jpedal.io.ColorSpaceConvertor;
import org.jpedal.io.JAIHelper;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codecimpl.JPEGCodec;
import com.sun.media.jai.codecimpl.JPEGImageEncoder;
import com.viviquity.pdf.PageCreator;

public class PageCreatorImpl implements PageCreator {

	private final static String separator = System
			.getProperty("file.separator");

	private static final Logger logger = Logger
			.getLogger(PageCreatorImpl.class);
	
	private static final float jpegCompression= -1f;

	public void generatePages(String pdfFile) {
		long startTime = System.currentTimeMillis();

		String outputPath = pdfFile.substring(0, pdfFile.toLowerCase().indexOf(".pdf")) + separator;
		File outputPathFile = new File(outputPath);
		if (!outputPathFile.exists() || !outputPathFile.isDirectory()) {
			if (!outputPathFile.mkdirs()) {
				logger.debug("Can't create directory " + outputPath);
			}
		}

		final PdfDecoder decoder = new PdfDecoder(true);

		// mappings for non-embedded fonts to use
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


		 // used to debug issue 
		 mapValues.clear();
		 mapValues.put(JPedalSettings.EXTRACT_AT_PAGE_SIZE,new
		 String[]{"2000","1600"});
		 mapValues.put(JPedalSettings.IMAGE_HIRES,Boolean.TRUE);
		  
		  mapValues.put(JPedalSettings.IGNORE_FORMS_ON_PRINT,new int[]{1,2});
		 
		PdfDecoder.modifyJPedalParameters(mapValues);

			System.out.println("pdf : " + pdfFile);

		try {
			int start = 1, end = decoder.getPageCount();

			BufferedImage[] multiPages = new BufferedImage[1 + (end - start)];

			String multiPageFlag = System
					.getProperty("org.jpedal.multipage_tiff");
			boolean isSingleOutputFile = multiPageFlag != null
					&& multiPageFlag.toLowerCase().equals("true");

			// allow user to specify value
			String rawJPEGComp = System
					.getProperty("org.jpedal.compression_jpeg");
			if (rawJPEGComp != null) {
				try {
					jpegCompression = Float.parseFloat(rawJPEGComp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (jpegCompression < 0 || jpegCompression > 1)
					throw new RuntimeException(
							"Invalid value for JPEG compression - must be between 0 and 1");

			}

			String tiffFlag = System.getProperty("org.jpedal.compress_tiff");
			String jpgFlag = System.getProperty("org.jpedal.jpeg_dpi");
			boolean compressTiffs = tiffFlag != null
					&& tiffFlag.toLowerCase().equals("true");

			for (int pageNo = start; pageNo < end + 1; pageNo++) {
				logger.debug("page : " + pageNo);

				/**
				 * If you are using decoder.getPageAsHiRes() after passing
				 * additional parameters into JPedal using the static method
				 * PdfDecoder.modifyJPedalParameters(), then getPageAsHiRes()
				 * wont necessarily be thread safe. If you want to use
				 * getPageAsHiRes() and pass in additional parameters, in a
				 * thread safe mannor, please use the method getPageAsHiRes(int
				 * pageIndex, Map params) or getPageAsHiRes(int pageIndex, Map
				 * params, boolean isTransparent) and pass the additional
				 * parameters in directly to the getPageAsHiRes() method without
				 * calling PdfDecoder.modifyJPedalParameters() first.
				 * 
				 * Please see http://www.jpedal.org/support_egHiRes.php for more
				 * details on how to use HiResThumbnailExtraction.
				 */
				BufferedImage imageToSave = decoder.getPageAsHiRes(pageNo);

				// show status flag

				decoder.flushObjectValues(true);

				// System.out.println("w="+imageToSave.getWidth()+" h="+imageToSave.getHeight());
				// image needs to be sRGB for JPEG
				if (fileType.equals("jpg"))
					imageToSave = ColorSpaceConvertor.convertToRGB(imageToSave);

				String outputFileName = "";
				if (isSingleOutputFile)
					outputFileName = outputPath + "allPages." + fileType;
				else
					outputFileName = outputPath + "page" + pageNo + "."
							+ fileType;

				File file = new File(outputFileName);

				// if just gray we can reduce memory usage by converting image
				// to Grayscale

				/**
				 * see what Colorspaces used and reduce image if appropriate
				 * (only does Gray at present)
				 * 
				 * Can return null value if not sure
				 */
				Iterator colorspacesUsed = decoder.getPageInfo(PageInfo.COLORSPACES);

				int nextID;
				boolean isGrayOnly = colorspacesUsed != null; // assume true and
																// disprove

				while (colorspacesUsed != null && colorspacesUsed.hasNext()) {
					nextID = ((Integer) (colorspacesUsed.next())).intValue();

					if (nextID != ColorSpaces.DeviceGray
							&& nextID != ColorSpaces.CalGray)
						isGrayOnly = false;
				}

				// draw onto GRAY image to reduce colour depth
				// (converts ARGB to gray)
				if (isGrayOnly) {
					BufferedImage image_to_save2 = new BufferedImage(
							imageToSave.getWidth(), imageToSave.getHeight(),
							BufferedImage.TYPE_BYTE_GRAY);
					image_to_save2.getGraphics().drawImage(imageToSave, 0, 0,
							null);
					imageToSave = image_to_save2;
				}

				// put image in array if multi-images
				if (isSingleOutputFile)
					multiPages[pageNo - start] = imageToSave;

				if (imageToSave != null) {

					/**
					 * BufferedImage does not support any dpi concept. A higher
					 * dpi can be created using JAI to convert to a higher dpi
					 * image
					 */

					// shrink the page to 50% with graphics2D transformation
					// - add your own parameters as needed
					// you may want to replace null with a hints object if you
					// want to fine tune quality.

					/**
					 * example 1 biliniear scaling AffineTransform scale = new
					 * AffineTransform(); scale.scale(.5, .5); //50% as a
					 * decimal AffineTransformOp scalingOp =new
					 * AffineTransformOp(scale, null); image_to_save
					 * =scalingOp.filter(image_to_save, null);
					 */

					if (JAIHelper.isJAIused())
						JAIHelper.confirmJAIOnClasspath();


					if (isSingleOutputFile && pageNo == end) {
							OutputStream out = new FileOutputStream(
									outputFileName);
							ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", out, params);
							Vector vector = new Vector();
							vector.addAll(Arrays.asList(multiPages).subList(1,
									multiPages.length));

							params.setExtraImages(vector.iterator());

							encoder.encode(multiPages[0]);
							out.close();
						}
					} else if (isSingleOutputFile) {
						// non-JAI
					} else if ((jpgFlag != null || rawJPEGComp != null)
							&& fileType.startsWith("jp")
							&& JAIHelper.isJAIused()) {

						JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new java.io.FileOutputStream(
										outputFileName));
						com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam = jpegEncoder
								.getDefaultJPEGEncodeParam(imageToSave);

						if (jpgFlag != null) {

							int dpi = 96;

							try {
								dpi = Integer.parseInt(jpgFlag);
							} catch (Exception e) {
								e.printStackTrace();
							}

							jpegEncodeParam
									.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
							jpegEncodeParam.setXDensity(dpi);
							jpegEncodeParam.setYDensity(dpi);

						}

						if (jpegCompression >= 0 && jpegCompression <= 1f) {
							jpegEncodeParam.setQuality(jpegCompression, false);
							System.out.println("xx");
						}

						jpegEncoder.encode(imageToSave, jpegEncodeParam);
					} else {

						ImageIO.write(imageToSave, fileType, file);
					}
					// if you just want to save the image, use something like
					// javax.imageio.ImageIO.write((java.awt.image.RenderedImage)image_to_save,"png",new
					// java.io.FileOutputStream(output_dir + page +
					// image_name+".png"));

				}

				imageToSave.flush();
				imageToSave = null;
				logger.debug("Created : " + outputFileName);
			}
		} finally {

			decoder.closePdfFile();
		}

		System.out.println("time=" + (System.currentTimeMillis() - startTime)
				/ 1000);
	}
}
