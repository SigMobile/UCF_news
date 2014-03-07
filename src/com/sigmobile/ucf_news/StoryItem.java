package com.sigmobile.ucf_news;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;

public class StoryItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2274504092267548833L;
	// We will at least need a title and a URL to the story. I'm not sure what
	// other xml attributes are in the the feed, we can add more later, like a
	// url to a picture or something
	private String mTitle;
	private String mUrl;
	private String mContent, mUnparsedContent;
	private String mDate;
	private String mPictureUrl;

	@Override
	public String toString() {
		// Make the default toString() method for a story return the title.
		return mTitle;
	}

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

	public String getContent() {
		return mContent;
	}

	/**
	 * 
	 * @param content
	 *            - the story content.
	 * 
	 *            This method sets to variables, it sets the unparsed Contents
	 *            to a variable and also sets the parsed out content to a
	 *            variable.
	 */
	public void setContent(String content) {

		setUnparsedContent(content);

		mContent = parseHTML(content);
	}

	public String getUnparsedContent() {
		return mUnparsedContent;
	}

	public void setUnparsedContent(String unparsedContent) {
		mUnparsedContent = unparsedContent;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public String getPictureUrl() {
		return mPictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		mPictureUrl = pictureUrl;
	}

	private String parseHTML(String contents) {
		return android.text.Html.fromHtml(contents, new ImageGetter() {
			
			@Override
			public Drawable getDrawable(String source) {
				
				return null;
			}
		} ,null).toString();
	}

	
}
