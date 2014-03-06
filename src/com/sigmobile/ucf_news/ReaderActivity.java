package com.sigmobile.ucf_news;

import android.support.v4.app.Fragment;

public class ReaderActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		StoryItem story = (StoryItem) getIntent().getSerializableExtra(ReaderFragment.KEY_STORY);

		return ReaderFragment.newInstance(story);
	}

}
