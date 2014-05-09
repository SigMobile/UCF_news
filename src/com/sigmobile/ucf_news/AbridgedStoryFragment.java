package com.sigmobile.ucf_news;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AbridgedStoryFragment extends Fragment {
	private static final String TAG = "AbridgedStoryActivity";

	private static final String KEY_STORYITEM = "com.sigmobile.ucf_news.KEY_STORYITEM";
	private static final float SWIPE_MAX_OFF_PATH = 250;
	private static final float SWIPE_MIN_DISTANCE = 120;
	private static final float SWIPE_THRESHOLD_VELOCITY = 200;

	private ImageView mImageViewThumb;
	private TextView mTextViewHeadline, mTextViewAuthor, mTextViewDesc;
	private StoryItem mItem;

	public AbridgedStoryFragment() {
	};

	public static AbridgedStoryFragment newInstance(StoryItem abridgedStory) {
		AbridgedStoryFragment f = new AbridgedStoryFragment();
		Bundle bun = new Bundle();
		bun.putSerializable(KEY_STORYITEM, abridgedStory);
		f.setArguments(bun);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mItem = (StoryItem) getArguments().getSerializable(KEY_STORYITEM);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_abridged, container, false);

		LinearLayout root = (LinearLayout) v.findViewById(R.id.root);
		root.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
			@Override
			public void onSwipeLeft() {
				Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT)
						.show();

			}

			@Override
			public void onSwipeRight() {
				Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT)
						.show();
			}
		});

		mImageViewThumb = (ImageView) v
				.findViewById(R.id.abridged_imageView_thumbnail);
		Picasso.with(getActivity()).load(mItem.getPictureUrl()).fit().noFade()
				.into(mImageViewThumb);

		mTextViewHeadline = (TextView) v
				.findViewById(R.id.abridged_imageView_headline);
		mTextViewHeadline.setText(mItem.getTitle());

		mTextViewAuthor = (TextView) v
				.findViewById(R.id.abridged_imageView_author);
		mTextViewAuthor.setText(mItem.getAuthor());

		mTextViewDesc = (TextView) v
				.findViewById(R.id.abridged_imageView_description);
		mTextViewDesc.setText(mItem.getDescription());

		return v;
	}

	/**
	 * Detects left and right swipes across a view.
	 */
	public class OnSwipeTouchListener implements OnTouchListener {

		private final GestureDetector gestureDetector;

		public OnSwipeTouchListener(Context context) {
			gestureDetector = new GestureDetector(context,
					new GestureListener());
		}

		public void onSwipeLeft() {
		}

		public void onSwipeRight() {
		}

		public boolean onTouch(View v, MotionEvent event) {
			return gestureDetector.onTouchEvent(event);
		}

		private final class GestureListener extends SimpleOnGestureListener {

			private static final int SWIPE_DISTANCE_THRESHOLD = 100;
			private static final int SWIPE_VELOCITY_THRESHOLD = 100;

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				float distanceX = e2.getX() - e1.getX();
				float distanceY = e2.getY() - e1.getY();
				if (Math.abs(distanceX) > Math.abs(distanceY)
						&& Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD
						&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
					if (distanceX > 0)
						onSwipeRight();
					else
						onSwipeLeft();
					return true;
				}
				return false;
			}
		}
	}
}
