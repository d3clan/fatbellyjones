package com.viviquity.fatbellyjones.facebook;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.schema.EventsGetResponse;
import com.google.code.facebookapi.schema.Photo;

public class FacebookEventTest {

    private SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    private String FB_APP_API_KEY = new String("148552345165361");
    private String FB_APP_SECRET = new String("4321cc7f84f988ea94749d182b8ef320");
    private String FB_SESSION_KEY = new String("484b45136deddbd3aad6a006-596617631");

    @Before
    public void setUp() throws Exception {
    }

    /*
     * name category subcategory host location city start_time (seconds since
     * epoch) end_time (seconds since epoch)
     * 
     * Optionally, you can pass the following parameters in the event_info
     * array:
     * 
     * street phone email page_id description privacy_type tagline
     */
    @Test
    public void testFacebookEventRead() throws FacebookException {

	dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	// FacebookXmlRestClient startSession = new
	// FacebookXmlRestClient("facebook",
	// "4321cc7f84f988ea94749d182b8ef320");

	FacebookJaxbRestClient client = new FacebookJaxbRestClient(FB_APP_API_KEY, FB_APP_SECRET, FB_SESSION_KEY);

	// String token = client.auth_createToken();
	// String result = client.auth_getSession(token);
	Calendar start = Calendar.getInstance();
	start.set(Calendar.YEAR, 2010);
	start.set(Calendar.MONTH, Calendar.DECEMBER);
	start.set(Calendar.DAY_OF_MONTH, 31);
	start.set(Calendar.HOUR_OF_DAY, 21);
	start.set(Calendar.MINUTE, 45);

	EventsGetResponse ob = (EventsGetResponse) client.events_get(130085527037539L, null, null, null);
	assertNotNull(ob);

	Long userId = client.users_getLoggedInUser();
	// Map<String, String> eventInfo = new HashMap<String, String>();
	// eventInfo.put("name", "Sample Event created from code");
	// eventInfo.put("category", "Test category");
	// eventInfo.put("subcategory", "Test subcategory");
	// eventInfo.put("host", "Test host");
	// eventInfo.put("location", "A Location");
	// eventInfo.put("city", "Lewes");
	// eventInfo.put("location", "A Location");
	// eventInfo.put("start_time", Long.toString(start.getTimeInMillis()));
	// start.add(Calendar.HOUR_OF_DAY, 3);
	// eventInfo.put("end_time", Long.toString(start.getTimeInMillis()));
	//
	// // Optional
	// eventInfo.put("street", "Street address");
	// eventInfo.put("phone", "01273 47889892");
	// eventInfo.put("email", "declan@declans.net");
	// eventInfo.put("page_id", "130085527037539");
	// eventInfo.put("description", "Long description");
	// // eventInfo.put("event_info-city", "Lewes");
	// // eventInfo.put("privacy_type", "Long description");
	// eventInfo.put("tagline", "Marvelous!");

	URL url = ClassLoader.getSystemClassLoader().getResource("test-image.gif");
	Photo photo = client.photos_upload(client.getCacheUserId(), new File(url.getPath()));

	Map<String, String> event = new HashMap<String, String>();
	event.put("start_time", dateFormatGmt.format(start.getTime()));
	event.put("name", "And one more new Event");
	event.put("location", "Somewhere over the rainbow");
	event.put("page_id", "130085527037539");
	event.put("pic", photo.getLink());
	event.put("privacy_type", "OPEN");
	event.put("description", "Oh yes!!");

	Long response = client.events_create(event);

	assertNotNull(response);
    }

    @After
    public void tearDown() throws Exception {
    }

}
