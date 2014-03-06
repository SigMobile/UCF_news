package com.sigmobile.ucf_news;

import android.support.v4.app.Fragment;

public class ReaderActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		String title = (String) getIntent().getStringExtra(
				ReaderFragment.KEY_TITLE);
		String date = (String) getIntent().getStringExtra(
				ReaderFragment.KEY_DATE);
		String content = (String) getIntent().getStringExtra(
				ReaderFragment.KEY_CONTENT);

		return ReaderFragment.newInstance(title, date, content);
	}

}
