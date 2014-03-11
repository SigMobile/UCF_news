package com.sigmobile.ucf_news;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FeedFragment extends ListFragment {
	private static final String TAG = "FeedFragment";

	private ListView mListView;
	ArrayList<StoryItem> mItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// retains the fragment so it will stay alive during the activity life
		// cycle.
		setRetainInstance(true);

		// Execute the AsyncTask to go and DL our RSS.
		new  FetchItemsTask().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get a View instance that we can use to inflate our layout file.

		// set up the list view

		setUpAdapter();

		// return the view
		return null;
	}

	private void setUpAdapter() {
		// null checks
		if (getActivity() == null || mListView == null)
			return;

		if (mItems != null) {
			// set up the adapter
			mListView.setAdapter(new StoryAdapter(mItems));
		} else {
			mListView.setAdapter(null);
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// take user to article
		//
		// Get the story from the adapter using the position.
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
			// This method will be used once we have a more complex
			// TableViewCell.
			return super.getView(position, convertView, parent);
		}

	}

}
