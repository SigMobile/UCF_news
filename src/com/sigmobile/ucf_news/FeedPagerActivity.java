package com.sigmobile.ucf_news;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class FeedPagerActivity extends FragmentActivity {
	private static final String TAG = "FeedPagerActivity";

	private static final String URL_JSON = "http://knightnews.com/api/get_recent_posts/";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_URL = "url";
	private static final String TAG_TITLE = "title";
	private static final String TAG_TITLE_PLAIN = "title_plain";
	private static final String TAG_EXCERPT = "excerpt";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_DATE = "date";
	private static final String TAG_IMAGE = "image";
	private static final String TAG_AUTHOR = "author";
	private static final String TAG_NAME = "name";

	private ViewPager mPager;
	private RequestQueue mQueue;
	private ArrayList<StoryItem> mItems;
	private JsonObjectRequest mRequest;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mQueue = Volley.newRequestQueue(this);

		mPager = new ViewPager(this);
		mPager.setId(R.id.viewPager);
		setContentView(mPager);
		
		fm = getSupportFragmentManager();
//		setUpAdapter();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mItems = new ArrayList<StoryItem>();
		fetchNewsItems();
		mRequest.setTag(this);
		mQueue.add(mRequest);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mQueue.cancelAll(this);
	}

	private void setUpAdapter() {
		if (this == null) {
			return;
		}
		if (mItems != null) {
			mPager.setAdapter(new PagerAdapter(fm));
		} else {
			mPager.setAdapter(null);
		}
	}

	private void fetchNewsItems() {
		mRequest = new JsonObjectRequest(URL_JSON, null,
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

				String title = p.getString(TAG_TITLE_PLAIN);
				String url = p.getString(TAG_URL);
				String content = p.getString(TAG_CONTENT);
				String description = p.getString(TAG_EXCERPT);
				String date = p.getString(TAG_DATE);

				JSONObject author = p.getJSONObject(TAG_AUTHOR);
				String name = author.getString(TAG_NAME);

				StoryItem item = new StoryItem();
				item.setTitle(title);
				item.setDate(date);
				item.setContent(content);
				item.setDescription(description);
				item.setUrl(url);
				item.setPictureUrl(img);
				item.setAuthor(name);

				mItems.add(item);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class PagerAdapter extends FragmentStatePagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			StoryItem abridgedStory = mItems.get(pos);
			return AbridgedStoryFragment.newInstance(abridgedStory);
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

	}
}
