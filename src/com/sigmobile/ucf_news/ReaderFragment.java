package com.sigmobile.ucf_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReaderFragment extends Fragment {

	public static final String KEY_STORY = "com.sigmobile.ucf_news.KEY_STORY";
	private TextView mTitleTextView, mDateTextView, mContentTextView;
	private String mTitle, mDate, mContent;
	private StoryItem mStory;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// get the fragments arguments
		Bundle args = getArguments();
		if (args != null) {
			mStory = (StoryItem) args.getSerializable(KEY_STORY);
		}

		View v = inflater.inflate(R.layout.fragment_reader, container, false);

		mTitleTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_title);
		mTitleTextView.setText(mStory.getTitle());

		mDateTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_date);
		mDateTextView.setText(mStory.getDate());

		mContentTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_content);
		mContentTextView.setText(mStory.getContent());

		return v;
	}

	// empty constructor
	public ReaderFragment() {
	}

	// new instance method that will be used to instantiate the fragment and get
	// fragment arguments.
	public static ReaderFragment newInstance(StoryItem story) {
		ReaderFragment rf = new ReaderFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_STORY, story);
		rf.setArguments(args);
		return rf;
	}

}
