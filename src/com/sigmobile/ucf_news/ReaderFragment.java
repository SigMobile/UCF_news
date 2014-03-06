package com.sigmobile.ucf_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReaderFragment extends Fragment {

	public static final String KEY_TITLE = "com.sigmobile.ucf_news.KEY_TITLE";
	public static final String KEY_DATE = "com.sigmobile.ucf_news.KEY_DATE";
	public static final String KEY_CONTENT = "com.sigmobile.ucf_news.KEY_CONTENT";
	private TextView mTitleTextView, mDateTextView, mContentTextView;
	private String mTitle, mDate, mContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//get the fragments arguments
		Bundle args = getArguments();
		if (args != null) {
			mTitle = args.getString(KEY_TITLE);
			mDate = args.getString(KEY_DATE);
			mContent = args.getString(KEY_CONTENT);
		}

		View v = inflater.inflate(R.layout.fragment_reader, container, false);

		mTitleTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_title);
		mTitleTextView.setText(mTitle);

		mDateTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_date);
		mDateTextView.setText(mDate);

		mContentTextView = (TextView) v
				.findViewById(R.id.fragment_reader_story_content);
		mContentTextView.setText(mContent);

		return v;
	}

	// empty constructor
	public ReaderFragment() {
	}

	// new instance method that will be used to instantiate the fragment and get
	// fragment arguments.
	public static ReaderFragment newInstance(String title, String date,
			String content) {
		ReaderFragment rf = new ReaderFragment();
		Bundle args = new Bundle();
		args.putString(KEY_TITLE, title);
		args.putString(KEY_DATE, date);
		args.putString(KEY_CONTENT, content);
		rf.setArguments(args);
		return rf;
	}

}
