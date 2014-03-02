package com.sigmobile.ucf_news;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/**
 * Networking Class.
 * 
 * NewsFetcher is the class that makes a connections with the URL of the news
 * feed and then fetches the XML and parses it into our StoryObjects.
 * 
 */
public class NewsFetcher {
	private static final String TAG = "NewsFetcher";

	private static byte[] getUrlBytes() throws IOException {
		// create a URL object pointing to a web Address
		// I think this is the URL to the RSS feed.
		URL url = new URL("http://feeds.feedburner.com/KnightNews");

		// open a http connection on the URL
		//
		// HttpURLConnection connection =

		try {
			// We are going to grab a byte array full of the RSS(XML) data.
			// We're in luck because there's a class called
			// "ByteArrayOutputStream" We should prob use that.

			// Create an input stream connected to the HTTPURLConnection we made

			// make sure we're actually connected
			// Check the connections response code against the STATIC
			// httpurlconnection variable that means we're connected and
			// everything is ok.
			//
			// If things are'nt ok return null from here.
			//
			// if () {
			// return null;
			// }

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			// Use a while loop to read the contents of the inputStream.
			// while () {
			// //write to the out stream.
			// }
			// close everything up

			// and then return the outputStream in the form of a byte array.
			return null;
		} finally {
			// always disconnect the http connection.

		}
	}

	// returns the bites fetched from the URL into a string
	private static String getUrl() throws IOException {
		return new String(getUrlBytes());
	}

	public ArrayList<StoryItem> downloadStoryItems() {

		// list to hold all of our parsed xml
		ArrayList<StoryItem> items = new ArrayList<StoryItem>();
		try {

			// connect to the url
			String xmlString = getUrl();

			Log.i(TAG, "Received xml: " + xmlString);

			// make a xml parser, must create an instance of the
			// XMLPullParserFactory first.
			//
			// XmlPullParserFactory factory =
			//
			// now create the parser

			//
			// set the parser input to the string we just pulled down. (use the
			// StringReader class tho)
			//

			// parse the items using our partItems method (need to write the
			// logic for that.

		} catch (IOException e) {
			Log.e(TAG, "Failed to fetch items", e);
		} //catch (XmlPullParserException e) {
			//Log.e(TAG, "Failed to parse items", e);
	//	}
		return items;
	}

	// method to parse our XML photos
	// The XMLPullParser is actually used internally by Android OS to to inflate
	// our layout files.
	void parseItems(ArrayList<StoryItem> items, XmlPullParser parser)
			throws XmlPullParserException, IOException {

		// read
		int eventType = parser.next();

		// while we aren't at the end of the document...
		while (eventType != XmlPullParser.END_DOCUMENT) {
			// This is where we will add the parsing logic to get the XML
			// attributes and add them to out StoryItem data model class
		}
	}

}
