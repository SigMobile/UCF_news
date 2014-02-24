package com.sigmobile.ucf_news;

public class StoryItem {

	// We will at least need a title and a URL to the story. I'm not sure what
	// other xml attributes are in the the feed, we can add more later, like a
	// url to a picture or something
	private String mTitle;
	private String mUrl;

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

}
