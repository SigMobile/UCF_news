package com.sigmobile.ucf_news;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class FeedFragment extends ListFragment {
	private static final String TAG = "FeedFragment";

	private static final String URL_JSON = "http://knightnews.com/api/get_recent_posts/";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_URL = "url";
	private static final String TAG_TITLE = "title";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_DATE = "date";
	private static final String TAG_IMAGE = "image";

	private RequestQueue mQueue;
	private ArrayList<StoryItem> mItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// retains the fragment so it will stay alive during the activity lifeS
		// cycle.
		setRetainInstance(true);

		mItems = new ArrayList<StoryItem>();

		mQueue = Volley.newRequestQueue(getActivity());

		JsonObjectRequest request = new JsonObjectRequest(URL_JSON, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							VolleyLog.v("Response:%n %s", response.toString(4));
							Log.i(TAG, "Response: " + response.toString());

							parseJSON(response);
							setUpAdapter();

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
					}
				});

		setUpAdapter();

		request.setTag(this);
		mQueue.add(request);

		// Execute the AsyncTask to go and DL our RSS.
		// new FetchItemsTask().execute();
	}

	private void setUpAdapter() {
		// null checks because we are using asynchtasks we need to make sure we
		// have a hosting activity.
		if (getActivity() == null)
			return;

		if (mItems != null) {
			// set up the adapter
			setListAdapter(new StoryAdapter(mItems));
		} else {
			setListAdapter(null);
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// take user to article
		//
		// Get the story from the adapter using the position.

		StoryItem story = mItems.get(position);
		//
		// Uri storyUrl = Uri.parse(story.getUrl());
		//
		// Intent i = new Intent(Intent.ACTION_VIEW, storyUrl);
		//
		// startActivity(i);
		Intent i = new Intent(getActivity(), ReaderActivity.class);
		i.putExtra(ReaderFragment.KEY_STORY, story);
		startActivity(i);
	}

	// This is the AsynchTask
	//
	// private inner class to do all of the background networking work.
	private class FetchItemsTask extends
			AsyncTask<Void, Void, ArrayList<StoryItem>> {

		@Override
		protected ArrayList<StoryItem> doInBackground(Void... arg0) {
			// make sure we have an instance of the activity
			Activity act = getActivity();
			if (act == null)
				return new ArrayList<StoryItem>();

			// return the method we wrote to download the xml
			return new NewsFetcher().downloadStoryItems();
		}

		// we cannot update the UI in the background, which is why we use this
		// method. It will be called when the thread has finished and we have
		// abuncha story items
		@Override
		protected void onPostExecute(ArrayList<StoryItem> result) {
			// get the result and put it in our list of stories
			mItems = result;

			// then we need to fill up the adapter to the list view
			setUpAdapter();
		}
	}

	// This is the adapter class.
	// An adapter is the middle man between a view (i.e. ListFragment) and the
	// data. It is what get's the data that will fill the list view.
	private class StoryAdapter extends ArrayAdapter<StoryItem> {

		// constuctor, as of right now we are going to use the default simple
		// list item. Later we will create a newer nicer looking, more
		// informative
		public StoryAdapter(ArrayList<StoryItem> stories) {
			super(getActivity(), android.R.layout.simple_list_item_1, stories);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_cell_story, null);
			}

			StoryItem s = getItem(position);

			ImageView image = (ImageView) convertView
					.findViewById(R.id.story_item_image);
			image.setImageResource(R.drawable.ic_launcher);

			TextView title = (TextView) convertView
					.findViewById(R.id.story_item_title_text);
			title.setText(s.getTitle());

			TextView date = (TextView) convertView
					.findViewById(R.id.story_item_date_published_text);
			date.setText(s.getDate());

			return convertView;

		}

	}

	private void parseJSON(JSONObject response) {
		if (response == null)
			return;

		try {
			JSONArray posts = response.getJSONArray(TAG_POSTS);

			for (int i = 0; i < posts.length(); i++) {
				JSONObject p = posts.getJSONObject(i);

				JSONObject customFields = p.getJSONObject("custom_fields");
				String img = customFields.getString(TAG_IMAGE);

				String title = p.getString(TAG_TITLE);
				String url = p.getString(TAG_URL);
				String content = p.getString(TAG_CONTENT);
				String date = p.getString(TAG_DATE);

				StoryItem item = new StoryItem();
				item.setTitle(title);
				item.setDate(date);
				item.setContent(content);
				item.setUrl(url);
				item.setPictureUrl(img);

				mItems.add(item);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mQueue.cancelAll(this);
	}

}
