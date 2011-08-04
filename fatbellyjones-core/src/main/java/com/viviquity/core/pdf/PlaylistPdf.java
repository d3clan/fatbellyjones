package com.viviquity.core.pdf;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.viviquity.core.model.Event;
import com.viviquity.core.model.PlaylistEntry;
import com.viviquity.core.model.Tune;

public class PlaylistPdf {

    /** A font used in our PDF file */
    public static final Font NORMAL = new Font(FontFamily.HELVETICA, 52, Font.NORMAL);
    /** A font used in our PDF file */
    public static final Font BOLD = new Font(FontFamily.HELVETICA, 52, Font.BOLD);
    /** A font used in our PDF file */
    public static final Font ITALIC = new Font(FontFamily.HELVETICA, 52, Font.ITALIC);
    /** A font used in our PDF file */
    public static final Font BOLDITALIC = new Font(FontFamily.HELVETICA, 52, Font.BOLDITALIC);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void getPlaylistPdf(Event event, OutputStream out) throws DocumentException {
	List<PlaylistEntry> playlist = event.getPlaylist();
	Document document = new Document(PageSize.A4);

	PdfWriter.getInstance(document, out);
	document.addTitle("Playlist for Fat Belly Jones - " + event.getTitle());
	document.addSubject("Playlist for Fat Belly Jones - " + event.getTitle());
	document.addKeywords("Fat Belly Jones," + event.getDescription());
	document.addAuthor("Fat Belly Jones Website");
	document.addCreator("Declan Newman");

	document.open();
	Paragraph titlePara = new Paragraph(event.getTitle() + " " + sdf.format(event.getStart()));
	titlePara.setFont(BOLDITALIC);
	document.add(titlePara);
	PdfPTable table = new PdfPTable(4);

	int count = 0;
	for (PlaylistEntry entry : playlist) {
	    if (!Tune.BREAK_STATUS.equals(entry.getTune().getStatus())) {
		Phrase ph = new Phrase((count++) + "");
		ph.setFont(BOLDITALIC);
		PdfPCell countCell = new PdfPCell(ph);
		countCell.setBorderWidth(0);
		table.addCell(countCell);

		Phrase tle = new Phrase(entry.getTune().getTitle());
		tle.setFont(BOLD);
		PdfPCell title = new PdfPCell(tle);
		title.setBorderWidth(0);
		table.addCell(title);

		Phrase countInP = new Phrase(entry.getTune().getCountIn());
		countInP.setFont(BOLD);
		PdfPCell countIn = new PdfPCell(countInP);
		countIn.setBorderWidth(0);
		table.addCell(countIn);

		Phrase keyP = new Phrase(entry.getTune().getKey());
		keyP.setFont(BOLD);
		PdfPCell key = new PdfPCell(keyP);
		key.setBorderWidth(0);
		table.addCell(key);
	    } else {
		PdfPCell blank = new PdfPCell(new Phrase());
		blank.setBorderWidth(0);
		table.addCell(blank);
		PdfPCell title = new PdfPCell(new Phrase(entry.getTune().getTitle()));
		title.setBorderWidth(0);
		table.addCell(title);
		table.addCell(blank);
		table.addCell(blank);
	    }

	}

	document.add(table);
	document.close();
    }
}
