package com.diandian.mycall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.diandian.mycall.R;

public class SuperButton extends Button  implements OnTouchListener {
	public SuperButton(Context context) {
		super(context);
		setOnTouchListener(this);
	}

	public SuperButton(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		setOnTouchListener(this);
	}

	// 按钮激活的颜色的设置
	public void setEnabled(boolean flag) {
		super.setEnabled(flag);
		if (flag) {
			this.setTextColor(getResources().getColor(
					R.color.button_green_color));
		} else {
			this.setTextColor(getResources().getColor(
					R.color.button_disable_color));
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (isEnabled()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				this.setTextColor(getResources().getColor(
						R.color.button_green_color));
				break;
			case MotionEvent.ACTION_DOWN:
				this.setTextColor(getResources().getColor(
						R.color.button_press_color));
				break;
			case MotionEvent.ACTION_MOVE:
				this.setTextColor(getResources().getColor(
						R.color.button_press_color));
				break;
			case MotionEvent.ACTION_OUTSIDE:
				this.setTextColor(getResources().getColor(
						R.color.button_green_color));
				break;
			}
			return false;
		}
		return true;
	}

}
