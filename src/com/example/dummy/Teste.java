package com.example.dummy;

import com.example.dummy.adapters.MyRecyclerViewAdapter;
import com.exemplo.dummy.views.FloatingActionButton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

public class Teste extends Activity implements View.OnClickListener {
	protected static final String TAG = "Dummy";
	// Testing recyclerview
	private RecyclerView mRecyclerView;
	private MyRecyclerViewAdapter mAdapter;
	private/* RecyclerView.LayoutManager */LinearLayoutManager mLayoutManager;

	// Testing Reveal
	private View mView;
	// private View mView2;
	boolean mRevealState = false;

	// Testing FAB
	private FloatingActionButton mFabButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		mView = findViewById(R.id.view1);
		// mView2 = findViewById(R.id.view2);
		//
		// mView.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		// });

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mLayoutManager.scrollToPosition(0);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		String[] data = { "Button1", "Button2", "Button3", "Button2",
				"Button3", "Button2", "Button3", "Button2", "Button3",
				"Button2", "Button3", "Button2", "Button3", "Button2",
				"Button3", "Button2", "Button3", "Button2", "Button3",
				"Button2", "Button3", "Button2", "Button3", "Button2",
				"Button3" };
		mAdapter = new MyRecyclerViewAdapter(data, this);
		mRecyclerView.setAdapter(mAdapter);

		mView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(Teste.this, "mView clicked", Toast.LENGTH_SHORT)
						.show();
				mLayoutManager.scrollToPosition(mLayoutManager.getItemCount() - 1);
			}
		});

		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				int fisrt = mLayoutManager.findFirstVisibleItemPosition();
				int fisrtC = mLayoutManager
						.findFirstCompletelyVisibleItemPosition();
				if (fisrt == fisrtC && fisrtC == 0) {
					Log.d(TAG, "[Test][onScrollStateChanged] fisrt == fisrtC && fisrtC == 0");
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				// TODO Auto-generated method stub
				int fisrt = mLayoutManager.findFirstVisibleItemPosition();
				int fisrtC = mLayoutManager
						.findFirstCompletelyVisibleItemPosition();
				if (fisrt == fisrtC && fisrtC == 0) {
					Log.d(TAG, "[Test][onScrolled] fisrt == fisrtC && fisrtC == 0");
				}
				super.onScrolled(recyclerView, dx, dy);
			}
		});

		// mItemDecoration = new DividerItemDecoration(this,
		// DividerItemDecoration.VERTICAL_LIST);
		// mRecyclerView.addItemDecoration(mItemDecoration);

		// this is the default;
		// this call is actually only necessary with custom ItemAnimators
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		/*
		 * mRecyclerView.addOnItemTouchListener(this); gesturedetector = new
		 * GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());
		 */

//		ViewTreeObserver vto = mView.getViewTreeObserver();
//		if (vto.isAlive()) {
//			vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//				@Override
//				public void onGlobalLayout() {
//					if (mView.getViewTreeObserver().isAlive()) {
//						mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//					}
//					mFabButton = new FloatingActionButton.Builder(Teste.this)
//							.withDrawable(
//									getResources().getDrawable(
//											android.R.drawable.ic_input_add))
//							.withButtonColor(
//									getResources().getColor(R.color.accent))
//							.withGravity(Gravity.TOP | Gravity.END)
//							.withMarginsInPixels(0, mView.getBottom(), 0, 0)
//							.create();
//					mFabButton.setOnClickListener(Teste.this);
//				}
//			});
//		}
		
		mView.post(new Runnable() {

	        @Override
	        public void run() {
	            // safe to get height and width here   
				mFabButton = new FloatingActionButton.Builder(Teste.this)
						.withDrawable(
								getResources().getDrawable(
										android.R.drawable.ic_input_add))
						.withButtonColor(
								getResources().getColor(R.color.accent))
						.withGravity(Gravity.TOP | Gravity.END)
						.withMarginsInPixels(0, mView.getBottom(), 0, 0)
						.create();
				mFabButton.setOnClickListener(Teste.this);
	        }

	    });
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "mRevealState: " + mRevealState);
		if (mRevealState) {
			// TODO Auto-generated method stub
			// get the center for the clipping circle
			int cx = (mView.getLeft() + mView.getRight()) / 2;
			int cy = (mView.getTop() + mView.getBottom()) / 2;
			Log.d(TAG, "[Teste] mView.getLeft(): " + mView.getLeft()
					+ " mView.getRight() " + mView.getRight());
			Log.d(TAG, "[Teste] mView.getTop(): " + mView.getTop()
					+ " mView.getBottom() " + mView.getBottom());
			Log.d(TAG, "[Teste] cx: " + cx + " cy " + cy);

			// get the final radius for the clipping circle
			int finalRadius = Math.max(mView.getWidth(), mView.getHeight());

			// create the animator for this view (the start radius is
			// zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx,
					cy, 0, finalRadius);

			// make the view visible and start the animation
			mView.setVisibility(View.VISIBLE);
			anim.start();
			mRevealState = false;
		} else {
			// get the center for the clipping circle
			int cx = (mView.getLeft() + mView.getRight()) / 2;
			int cy = (mView.getTop() + mView.getBottom()) / 2;
			Log.d(TAG, "[Teste] mView.getLeft(): " + mView.getLeft()
					+ " mView.getRight() " + mView.getRight());
			Log.d(TAG, "[Teste] mView.getTop(): " + mView.getTop()
					+ " mView.getBottom() " + mView.getBottom());
			Log.d(TAG, "[Teste] cx: " + cx + " cy " + cy);

			// get the initial radius for the clipping circle
			int initialRadius = mView.getWidth();

			// create the animation (the final radius is zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx,
					cy, initialRadius, 0);

			// make the view invisible when the animation is done
			anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					mView.setVisibility(View.GONE);
				}
			});

			// start the animation
			anim.start();
			mRevealState = true;
		}
	}
}
