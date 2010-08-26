package com.viviquity.pdf;

public interface PdfProcessor {
	
	public void splitPdf(String pdfFilename);
	
	public void stripPdf(String pdfFilename);
	
	public void createPageImages(String pdfFilename);
}
