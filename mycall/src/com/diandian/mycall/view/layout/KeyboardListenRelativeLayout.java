package com.diandian.mycall.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class KeyboardListenRelativeLayout extends RelativeLayout {

	public KeyboardListenRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.i("lanyan", "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i("lanyan", "onLayout");
		super.onLayout(changed, l, t, r, b);
	}

	/**
	 * 当前活动主窗口大小改变时调用
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.i("lanyan", "onSizeChanged");
		super.onSizeChanged(w, h, oldw, oldh);
	}
}
