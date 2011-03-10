package com.viviquity.core.facebook;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.viviquity.core.image.ImageCreator;

public class FacebookImage implements ImageCreator {

    private BufferedImage bgImage;
    private Font font;

    public FacebookImage(String bgFile, String fontFile) throws IOException, FontFormatException {
	init(bgFile, fontFile);
    }

    private void init(String bgFile, String fontFile) throws IOException, FontFormatException {
	InputStream is = FacebookImage.class.getResourceAsStream(bgFile);
	this.bgImage = ImageIO.read(is);
	Font font = Font.createFont(Font.TRUETYPE_FONT, FacebookImage.class.getResourceAsStream(fontFile));
	this.font = font.deriveFont(31f);
    }

    @Override
    public File createImage(String text) throws IOException {
	Graphics2D gr = bgImage.createGraphics();
	gr.setFont(font);
	File file = File.createTempFile("fatbelly", ".png");
	FontMetrics fm = gr.getFontMetrics();

	int textwidth = fm.stringWidth(text);
	int picwidth = bgImage.getWidth();
	int picHeight = bgImage.getHeight();

	FontRenderContext frc = gr.getFontRenderContext();
	TextLayout textLayout = new TextLayout(text, font, frc);

	gr.setPaint(Color.WHITE);
	AffineTransform at = AffineTransform.getTranslateInstance((picwidth / 2) - (textwidth / 2), picHeight - 10);
	Shape outline = textLayout.getOutline(at);
	gr.fill(outline);
	gr.setPaint(Color.BLACK);
	gr.draw(outline);

	if (ImageIO.write(bgImage, "png", file)) {
	    return file;
	} else {
	    throw new IOException("Could not create new facebook image");
	}
    }
}
