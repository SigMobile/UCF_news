package com.sigmobile.ucf_news;

import android.support.v4.app.Fragment;

public class ReaderActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ReaderFragment();
	}

}
