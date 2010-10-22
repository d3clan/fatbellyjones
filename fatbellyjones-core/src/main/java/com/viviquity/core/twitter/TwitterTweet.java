package com.viviquity.core.twitter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.Authorization;
import twitter4j.http.OAuthAuthorization;

import com.viviquity.core.model.Event;
import com.viviquity.core.storage.FatbelliesIOException;

public class TwitterTweet {

    private static final String prefix = "... ";
    public static final int MAX_CHARACTERS = 140;

    private Twitter twitter;
    private HttpClient client;
    private URL tinyUrl;

    public TwitterTweet(AccessToken accessToken, String key, String secret, HttpClient client, URL tinyUrl) {
	this.twitter = initialiseTwitter(accessToken, key, secret);
	this.client = client;
	this.tinyUrl = tinyUrl;
    }

    public Status tweet(URL link, Event event) throws FatbelliesIOException {
	try {
	    return twitter.updateStatus(getStatusUpdate(link, event));
	} catch (TwitterException e) {
	    throw new FatbelliesIOException("Cannot update Twitter", e);
	}
    }

    private StatusUpdate getStatusUpdate(URL link, Event event) throws FatbelliesIOException {
	String res = null;
	URL tinyUrl = getTinyUrl(link);
	String d = event.getDescription();
	if (StringUtils.isNotBlank(d)) {
	    int count = d.length() + tinyUrl.toString().length() + prefix.length();
	    if (count > MAX_CHARACTERS) {
		res = d.substring(0, d.length() - tinyUrl.toString().length() + prefix.length()) + prefix + tinyUrl;
	    } else {
		res = d + prefix.length() + prefix + tinyUrl;
	    }
	} else {
	    throw new FatbelliesIOException("There must be description to post to Twitter");
	}
	return new StatusUpdate(res);
    }

    private URL getTinyUrl(URL url) throws FatbelliesIOException {
	HttpMethod getMethod = null;
	try {
	    getMethod = new GetMethod(tinyUrl.toString());
	    NameValuePair[] query = new NameValuePair[] { new NameValuePair("url", url.toString()) };
	    getMethod.setQueryString(query);
	    client.executeMethod(getMethod);
	    return new URL(getMethod.getResponseBodyAsString());
	} catch (HttpException e) {
	    throw new FatbelliesIOException("Cannot talk to Twitter", e);
	} catch (MalformedURLException e) {
	    throw new FatbelliesIOException("Cannot talk to Twitter", e);
	} catch (IOException e) {
	    throw new FatbelliesIOException("Cannot talk to Twitter", e);
	} finally {
	    if (getMethod != null) {
		getMethod.releaseConnection();
	    }

	}
    }

    private Twitter initialiseTwitter(AccessToken accessToken, String key, String secret) {
	Configuration config = new ConfigurationBuilder().build();
	Authorization auth = new OAuthAuthorization(config, key, secret, accessToken);
	Twitter twitter = new TwitterFactory().getInstance(auth);
	return twitter;
    }
    /*
     * public static void main(String args[]) throws Exception { // The factory
     * instance is re-useable and thread safe. Twitter twitter = new
     * TwitterFactory().getInstance();
     * 
     * twitter.setOAuthConsumer("YlqJrDegFx3h1A0ptdI9VQ",
     * "unXb1LHqC3rrcycWKBu0gILlnMbknXoFYTRsG2FQw"); RequestToken requestToken =
     * twitter.getOAuthRequestToken(); AccessToken accessToken = null;
     * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     * while (null == accessToken) {
     * System.out.println("Open the following URL and grant access to your account:"
     * ); System.out.println(requestToken.getAuthorizationURL());
     * System.out.print
     * ("Enter the PIN(if aviailable) or just hit enter.[PIN]:"); String pin =
     * br.readLine(); try { if (pin.length() > 0) { accessToken =
     * twitter.getOAuthAccessToken(requestToken, pin); } else { accessToken =
     * twitter.getOAuthAccessToken(); } } catch (TwitterException te) { if (401
     * == te.getStatusCode()) {
     * System.out.println("Unable to get the access token."); } else {
     * te.printStackTrace(); } } } // persist to the accessToken for future
     * reference. storeAccessToken(twitter.verifyCredentials().getId(),
     * accessToken); Status status = twitter.updateStatus(args[0]);
     * System.out.println("Successfully updated the status to [" +
     * status.getText() + "]."); System.exit(0); }
     * 
     * private static void storeAccessToken(int useId, AccessToken accessToken)
     * { System.out.println("Token " + accessToken.getToken());
     * System.out.println("Secret " + accessToken.getTokenSecret()); }
     */

}
