package com.sigmobile.ucf_news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HomeActivity extends Activity {

	private ImageButton mImageButtonNews, mImageButtonText, mImageButtonMap,
			mImageButtonEvents, mImageButtonSports;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mImageButtonNews = (ImageButton) findViewById(R.id.home_imageButton_one);
		mImageButtonNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						FeedActivity.class);
				startActivity(i);
			}
		});

		mImageButtonText = (ImageButton) findViewById(R.id.home_imageButton_two);
		mImageButtonText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mImageButtonEvents = (ImageButton) findViewById(R.id.home_imageButton_three);
		mImageButtonEvents.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mImageButtonMap = (ImageButton) findViewById(R.id.home_imageButton_four);
		mImageButtonMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mImageButtonSports = (ImageButton) findViewById(R.id.home_imageButton_five);
		mImageButtonSports.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

}
