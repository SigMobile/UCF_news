package com.sigmobile.ucf_news;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sigmobile.ucf_news.URLImageParser.ImageGetterAsyncTask;

import android.R.anim;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ReaderFragment extends Fragment {

	public static final String KEY_STORY = "com.sigmobile.ucf_news.KEY_STORY";
	private TextView mTitleTextView, mDateTextView;
	private WebView mContentWebView;
	private StoryItem mStory;

	@Override
	public View onCreateView(LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {

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

		mContentWebView = (WebView) v
				.findViewById(R.id.fragment_reader_story_content);
		mContentWebView.loadData(mStory.getUnparsedContent(), "text/html; charset=utf-8",
				"UTF-8");

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

	private String parseHTML(String contents) {
		return android.text.Html.fromHtml(contents, new ImageGetter() {

			@Override
			public Drawable getDrawable(String source) {
				Drawable d;
				try {
					d = Drawable.createFromStream(
							new URL(mStory.getPictureUrl()).openStream(),
							"src name");
					d.setBounds(0, 0, d.getIntrinsicWidth(),
							d.getIntrinsicHeight());

					// setImageDrawable(d);

					return d;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}, null).toString();
	}

}
