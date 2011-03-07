package com.viviquity.core.facebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.viviquity.core.model.Event;
import com.viviquity.core.storage.FatbelliesIOException;

public class FacebookEvent {

    private SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    private FacebookJaxbRestClient client;
    private String groupId;

    public FacebookEvent(FacebookJaxbRestClient client, String groupId) {
	super();
	this.client = client;
	this.groupId = groupId;
    }

    public Long createEvent(Event event, String fileName, URL image) throws FatbelliesIOException {
	Map<String, String> eventMap = new HashMap<String, String>();
	eventMap.put("start_time", dateFormatGmt.format(event.getStart()));
	eventMap.put("end_time", dateFormatGmt.format(event.getEndDate()));
	eventMap.put("name", event.getTitle());
	eventMap.put("location", event.getLocation());
	eventMap.put("page_id", groupId);
	eventMap.put("pic", image.toString());
	eventMap.put("privacy_type", "OPEN");
	eventMap.put("privacy", "OPEN");
	eventMap.put("tagline", event.getTagline());
	eventMap.put("description", event.getDescription());
	try {
	    return client.events_create(eventMap, "event.gif", image.openStream());
	} catch (Exception e) {
	    throw new FatbelliesIOException("Cannot create new Facebook event", e);
	}
    }

    public static byte[] getBytesFromFile(URL url) throws IOException {
	File file = new File(url.getFile());
	InputStream is = new FileInputStream(file);

	// Get the size of the file
	long length = file.length();

	if (length > Integer.MAX_VALUE) {
	    // File is too large
	}

	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];

	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}

	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	    throw new IOException("Could not completely read file " + file.getName());
	}

	// Close the input stream and return bytes
	is.close();
	return bytes;
    }
}
