package com.sigmobile.ucf_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ReaderFragment extends Fragment {
	private static final String TAG = "ReaderFragment";

	public static final String KEY_STORY = "com.sigmobile.ucf_news.KEY_STORY";
	private TextView mTitleTextView, mDateTextView;
	private WebView mContentWebView;
	private StoryItem mStory;

	public ReaderFragment() {
	}

	public static ReaderFragment newInstance(StoryItem story) {
		ReaderFragment rf = new ReaderFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_STORY, story);
		rf.setArguments(args);
		return rf;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		((ActionBarActivity) getActivity()).getSupportActionBar()
				.setDisplayHomeAsUpEnabled(true);

		// get the fragments arguments
		Bundle args = getArguments();
		if (args != null) {
			mStory = (StoryItem) args.getSerializable(KEY_STORY);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(getActivity());
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_reader, container, false);

		mTitleTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_title);
		mTitleTextView.setText(mStory.getTitle());

		mDateTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_date);
		mDateTextView.setText(mStory.getAuthor());

		mContentWebView = (WebView) v
				.findViewById(R.id.fragment_reader_story_content);

		mContentWebView.loadData(mStory.getUnparsedContent(),
				"text/html; charset=utf-8", "UTF-8");

		return v;
	}
}
