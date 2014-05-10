package com.sigmobile.ucf_news;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class EventsActivity extends ListActivity {
	private static final String TAG = "EventsActivity";

	private static final String URL_EVENTS = "http://knightnews.com/events.xml";

	private RequestQueue mQueue;
	private ArrayList<EventItem> mItems;
	private StringRequest mRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mQueue = Volley.newRequestQueue(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mItems = new ArrayList<EventItem>();
		fetchEventItems();
		mRequest.setTag(this);
		mQueue.add(mRequest);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mQueue.cancelAll(this);
	}

	private void setUpAdapter() {
		if (this == null)
			return;
		if (mItems != null) {
			setListAdapter(new EventsAdapter(mItems));
		} else {
			setListAdapter(null);
		}

	}

	private void fetchEventItems() {
		mRequest = new StringRequest(URL_EVENTS,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.i(TAG, response);
						XmlPullParserFactory factory;
						try {
							factory = XmlPullParserFactory.newInstance();
							XmlPullParser parser = factory.newPullParser();
							parser.setInput(new StringReader(response));
							try {

								parseItems(parser);
								setUpAdapter();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
					}
				});

	}

	private void parseItems(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		// read
		int eventType = parser.next();
		EventItem story = null;

		// while we aren't at the end of the document...
		while (eventType != XmlPullParser.END_DOCUMENT) {
			// This is where we will add the parsing logic to get the XML
			// attributes and add them to out StoryItem data model class
			if (eventType == XmlPullParser.START_TAG) {

				if (parser.getName().equalsIgnoreCase("events")) {

				}

				if (parser.getName().equalsIgnoreCase("event_name")) {
					story = new EventItem();
					story.setEventName(parser.nextText());

				}

				if (parser.getName().equalsIgnoreCase("event_date")) {
					story.setEventDate(parser.nextText());

				}

				if (parser.getName().equalsIgnoreCase("event_desc")) {
					story.setEventDesc(parser.nextText());
					mItems.add(story);
				}

			}
			eventType = parser.next();
		}
	}

	private class EventsAdapter extends ArrayAdapter<EventItem> {
		public EventsAdapter(ArrayList<EventItem> stories) {
			super(getApplicationContext(), android.R.layout.simple_list_item_1,
					stories);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.list_item_event, null);
			}

			EventItem s = getItem(position);

			TextView title = (TextView) convertView
					.findViewById(R.id.eventList_title_textView);
			title.setText(s.getEventName());

			TextView date = (TextView) convertView
					.findViewById(R.id.eventList_date_textView);
			date.setText(s.getEventDate());

			TextView desc = (TextView) convertView
					.findViewById(R.id.eventList_desc_textView);
			desc.setText(s.getEventDesc());

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.size();
		}

	}
}
