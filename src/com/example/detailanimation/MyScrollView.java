package com.example.detailanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 不管对scrollView还是对内部的ViewPager或者listView作判断在往下滑动的时间都 不可避免的要用知道
 * listview是否滑动到顶部。
 * @author xiaojian1
 *
 */
public class MyScrollView extends ScrollView {
	private static final String TAG = "MyScrollView";

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private int mVerticalScrollOffset;

	@Override
	protected int computeVerticalScrollOffset() {
		mVerticalScrollOffset = super.computeVerticalScrollOffset();
		Log.i(TAG, "computeVerticalScrollOffset "+mVerticalScrollOffset);
		return mVerticalScrollOffset;
	}
	
	@Override
	protected int computeVerticalScrollExtent() {
		Log.i(TAG, "computeVerticalScrollExtent "+super.computeVerticalScrollExtent());
		return super.computeVerticalScrollExtent();
	}
	
	@Override
	protected int computeVerticalScrollRange() {
		Log.i(TAG, "computeVerticalScrollRange "+super.computeVerticalScrollRange());
		return super.computeVerticalScrollRange();
	}

	private int iMaxScrollOffset;

	public void setMaxScrollOffset(int offset) {
		iMaxScrollOffset = offset;
	}

	private boolean canScrollUp() {
		return mVerticalScrollOffset < iMaxScrollOffset;
	}

	private boolean canScrollDown() {
		return mVerticalScrollOffset >= iMaxScrollOffset;
	}

	private float mLastY;
	private boolean isBeginDrag;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i(TAG, "onInterceptTouchEvent");
		if (canScrollUp()) {
			return super.onInterceptTouchEvent(ev);
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getY();
			super.onInterceptTouchEvent(ev);
			break;
		case MotionEvent.ACTION_MOVE:
			float detla = ev.getY() - mLastY;
			Log.i(TAG, "d:" + detla);
			if (detla > 0 && isReachTop) {
				return super.onInterceptTouchEvent(ev);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			super.onInterceptTouchEvent(ev);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.i(TAG, "onTouchEvent");
		return super.onTouchEvent(ev);
	}


	private boolean isReachTop = false;

	public void setReachTop(boolean isReachTop) {
		this.isReachTop = isReachTop;
	}

}
