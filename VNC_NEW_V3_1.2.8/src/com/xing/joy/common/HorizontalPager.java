package com.xing.joy.common;

/*
 * Copyright (C) 2010 Deez Apps!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Scroller;

/**
 * Based on apps/Launcher/src/com/android/launcher/Workspace.java
 */
public class HorizontalPager extends ViewGroup {
	public static final String TAG = "com.xing.joy.common.HorizontalPager";

	private static final int INVALID_SCREEN = -1;

	/**
	 * The velocity at which a fling gesture will cause us to snap to the next
	 * screen
	 */
	private static final int SNAP_VELOCITY = 1000;

	private boolean mFirstLayout = true;

	private int mCurrentScreen;
	private int mNextScreen = INVALID_SCREEN;

	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private float mLastMotionX;
	private float mLastMotionY;

	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;

	private int mTouchState = TOUCH_STATE_REST;

	private boolean mAllowLongPress;

	private Set<OnScrollListener> mListeners = new HashSet<OnScrollListener>();

	/**
	 * Used to inflate the Workspace from XML.
	 * 
	 * @param context
	 *            The application's context.
	 * @param attrs
	 *            The attribtues set containing the Workspace's customization
	 *            values.
	 */
	public HorizontalPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Used to inflate the Workspace from XML.
	 * 
	 * @param context
	 *            The application's context.
	 * @param attrs
	 *            The attribtues set containing the Workspace's customization
	 *            values.
	 * @param defStyle
	 *            Unused.
	 */
	public HorizontalPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		/*
		 * TypedArray a = context.obtainStyledAttributes(attrs,
		 * R.styleable.Workspace, defStyle, 0); a.recycle();
		 */

		initWorkspace();
	}

	/**
	 * Initializes various states for this workspace.
	 */
	private void initWorkspace() {
		mScroller = new Scroller(getContext());
		mCurrentScreen = 0;
	}

	/**
	 * Returns the index of the currently displayed screen.
	 * 
	 * @return The index of the currently displayed screen.
	 */
	int getCurrentScreen() {
		return mCurrentScreen;
	}

	/**
	 * Sets the current screen.
	 * 
	 * @param currentScreen
	 */
	public void setCurrentScreen(int currentScreen, final boolean animate) {
		mCurrentScreen = Math.max(0, Math.min(currentScreen, getChildCount()));
		if (animate) {
			snapToScreen(currentScreen);
		} else {
			scrollTo(mCurrentScreen * getWidth(), 0);
		}
		invalidate();
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		} else if (mNextScreen != INVALID_SCREEN) {
			mCurrentScreen = mNextScreen;
			mNextScreen = INVALID_SCREEN;
			clearChildrenCache();
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		// ViewGroup.dispatchDraw() supports many features we don't need:
		// clip to padding, layout animation, animation listener, disappearing
		// children, etc. The following implementation attempts to fast-track
		// the drawing dispatch by drawing only what we know needs to be drawn.
		try {

			boolean fastDraw = mTouchState != TOUCH_STATE_SCROLLING && mNextScreen == INVALID_SCREEN;
			// If we are not scrolling or flinging, draw only the current screen
			if (fastDraw) {
				drawChild(canvas, getChildAt(mCurrentScreen), getDrawingTime());
			} else {
				final long drawingTime = getDrawingTime();
				// If we are flinging, draw only the current screen and the
				// target screen
				if (mNextScreen >= 0 && mNextScreen < getChildCount() && Math.abs(mCurrentScreen - mNextScreen) == 1) {
					drawChild(canvas, getChildAt(mCurrentScreen), drawingTime);
					drawChild(canvas, getChildAt(mNextScreen), drawingTime);
				} else {
					// If we are scrolling, draw all of our children
					final int count = getChildCount();
					for (int i = 0; i < count; i++) {
						drawChild(canvas, getChildAt(i), drawingTime);
					}
				}
			}

			for (OnScrollListener mListener : mListeners) {
				mListener.onScroll(getScrollX());
				if (getScrollX() % getWidth() == 0) {
					mListener.onViewScrollFinished(getScrollX() / getWidth());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);

		// The children are given the same width and height as the workspace
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		if (mFirstLayout) {
			scrollTo(mCurrentScreen * width, 0);
			mFirstLayout = false;
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int childLeft = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
		int screen = indexOfChild(child);
		if (screen != mCurrentScreen || !mScroller.isFinished()) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
		try {

			int focusableScreen;
			if (mNextScreen != INVALID_SCREEN) {
				focusableScreen = mNextScreen;
			} else {
				focusableScreen = mCurrentScreen;
			}
			getChildAt(focusableScreen).requestFocus(direction, previouslyFocusedRect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		if (direction == View.FOCUS_LEFT) {
			if (getCurrentScreen() > 0) {
				snapToScreen(getCurrentScreen() - 1);
				return true;
			}
		} else if (direction == View.FOCUS_RIGHT) {
			if (getCurrentScreen() < getChildCount() - 1) {
				snapToScreen(getCurrentScreen() + 1);
				return true;
			}
		}
		return super.dispatchUnhandledMove(focused, direction);
	}

	@Override
	public void addFocusables(ArrayList<View> views, int direction) {
		getChildAt(mCurrentScreen).addFocusables(views, direction);
		if (direction == View.FOCUS_LEFT) {
			if (mCurrentScreen > 0) {
				getChildAt(mCurrentScreen - 1).addFocusables(views, direction);
			}
		} else if (direction == View.FOCUS_RIGHT) {
			if (mCurrentScreen < getChildCount() - 1) {
				getChildAt(mCurrentScreen + 1).addFocusables(views, direction);
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// Log.d(TAG, "onInterceptTouchEvent::action=" + ev.getAction());

		/*
		 * This method JUST determines whether we want to intercept the motion.
		 * If we return true, onTouchEvent will be called and we do the actual
		 * scrolling there.
		 */

		/*
		 * Shortcut the most recurring case: the user is in the dragging state
		 * and he is moving his finger. We want to intercept this motion.
		 */
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			Log.d(TAG, "onInterceptTouchEvent::shortcut=true");
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			/*
			 * mIsBeingDragged == false, otherwise the shortcut would have
			 * caught it. Check whether the user has moved far enough from his
			 * original down touch.
			 */
			if (mTouchState == TOUCH_STATE_REST) {
				checkStartScroll(x, y);
			}

			break;

		case MotionEvent.ACTION_DOWN:
			// Remember location of down touch
			mLastMotionX = x;
			mLastMotionY = y;
			mAllowLongPress = true;

			/*
			 * If being flinged and user touches the screen, initiate drag;
			 * otherwise don't. mScroller.isFinished should be false when being
			 * flinged.
			 */
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// Release the drag
			clearChildrenCache();
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		/*
		 * The only time we want to intercept motion events is if we are in the
		 * drag mode.
		 */
		return mTouchState != TOUCH_STATE_REST;
	}

	private void checkStartScroll(float x, float y) {
		/*
		 * Locally do absolute value. mLastMotionX is set to the y value of the
		 * down event.
		 */
		try {
			final int xDiff = (int) Math.abs(x - mLastMotionX);
			final int yDiff = (int) Math.abs(y - mLastMotionY);
			final int touchSlop = ViewConfiguration.getTouchSlop();

			boolean xMoved = xDiff > touchSlop;
			boolean yMoved = yDiff > touchSlop;

			if (xMoved || yMoved) {

				if (xMoved) {
					// Scroll if the user moved far enough along the X axis
					mTouchState = TOUCH_STATE_SCROLLING;
					enableChildrenCache();
				}
				// Either way, cancel any pending longpress
				if (mAllowLongPress) {
					mAllowLongPress = false;
					// Try canceling the long press. It could also have been
					// scheduled
					// by a distant descendant, so use the mAllowLongPress flag
					// to
					// block
					// everything
					final View currentScreen = getChildAt(mCurrentScreen);
					currentScreen.cancelLongPress();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void enableChildrenCache() {
		setChildrenDrawingCacheEnabled(true);
		setChildrenDrawnWithCacheEnabled(true);
	}

	void clearChildrenCache() {
		setChildrenDrawnWithCacheEnabled(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Log.d(TAG, "onTouchEvent::action=" + ev.getAction());

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);

		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			/*
			 * If being flinged and user touches, stop the fling. isFinished
			 * will be false if being flinged.
			 */
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}

			// Remember where the motion event started
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mTouchState == TOUCH_STATE_REST) {
				checkStartScroll(x, y);
			} else if (mTouchState == TOUCH_STATE_SCROLLING) {
				// Scroll to follow the motion event
				int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				final int scrollX = getScrollX();
				if (deltaX < 0) {
					if (scrollX > 0) {
						scrollBy(Math.max(-scrollX, deltaX), 0);
					}
				} else if (deltaX > 0) {
					if (getChildAt(getChildCount() - 1) != null) {
						final int availableToScroll = getChildAt(getChildCount() - 1).getRight() - scrollX - getWidth();

						if (availableToScroll > 0) {
							scrollBy(Math.min(availableToScroll, deltaX), 0);
						}
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mTouchState == TOUCH_STATE_SCROLLING) {
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);
				int velocityX = (int) velocityTracker.getXVelocity();

				if (velocityX > SNAP_VELOCITY && mCurrentScreen > 0) {
					// Fling hard enough to move left
					snapToScreen(mCurrentScreen - 1);
				} else if (velocityX < -SNAP_VELOCITY && mCurrentScreen < getChildCount() - 1) {
					// Fling hard enough to move right
					snapToScreen(mCurrentScreen + 1);
				} else {
					snapToDestination();
				}

				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
			}
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
		}

		return true;
	}

	private void snapToDestination() {
		final int screenWidth = getWidth();
		final int startX = mCurrentScreen * screenWidth;
		int whichScreen = mCurrentScreen;
		if (getScrollX() < startX - screenWidth / 8) {
			whichScreen = Math.max(0, whichScreen - 1);
		} else if (getScrollX() > startX + screenWidth / 8) {
			whichScreen = Math.min(getChildCount() - 1, whichScreen + 1);
		}

		snapToScreen(whichScreen);
	}

	void snapToScreen(int whichScreen) {
		enableChildrenCache();

		boolean changingScreens = whichScreen != mCurrentScreen;

		mNextScreen = whichScreen;

		View focusedChild = getFocusedChild();
		if (focusedChild != null && changingScreens && focusedChild == getChildAt(mCurrentScreen)) {
			focusedChild.clearFocus();
		}

		final int newX = whichScreen * getWidth();
		final int delta = newX - getScrollX();
		mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
		invalidate();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final SavedState state = new SavedState(super.onSaveInstanceState());
		state.currentScreen = mCurrentScreen;
		// Log.d(TAG, "onSaveInstanceState::mCurrentScreen=" + mCurrentScreen);
		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		if (savedState.currentScreen != -1) {
			mCurrentScreen = savedState.currentScreen;
		}
		// Log.d(TAG, "onRestoreInstanceState::mCurrentScreen=" +
		// mCurrentScreen);
	}

	public void scrollLeft() {
		if (mNextScreen == INVALID_SCREEN && mCurrentScreen > 0 && mScroller.isFinished()) {
			snapToScreen(mCurrentScreen - 1);
		}
	}

	public void scrollRight() {
		if (mNextScreen == INVALID_SCREEN && mCurrentScreen < getChildCount() - 1 && mScroller.isFinished()) {
			snapToScreen(mCurrentScreen + 1);
		}
	}

	public int getScreenForView(View v) {
		int result = -1;
		if (v != null) {
			ViewParent vp = v.getParent();
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				if (vp == getChildAt(i)) {
					return i;
				}
			}
		}
		return result;
	}

	/**
	 * @return True is long presses are still allowed for the current touch
	 */
	public boolean allowLongPress() {
		return mAllowLongPress;
	}

	public static class SavedState extends BaseSavedState {
		int currentScreen = -1;

		SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentScreen = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(currentScreen);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public void addOnScrollListener(OnScrollListener listener) {
		mListeners.add(listener);
	}

	public void removeOnScrollListener(OnScrollListener listener) {
		mListeners.remove(listener);
	}

	public static interface OnScrollListener {
		void onScroll(int scrollX);

		void onViewScrollFinished(int viewIndex);
	}
}