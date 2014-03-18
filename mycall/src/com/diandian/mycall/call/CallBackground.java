package com.diandian.mycall.call;


import com.diandian.mycall.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class CallBackground extends BroadcastReceiver {

	TelephonyManager tm;
	Context context;
	View view;
	WindowManager wManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		
		//ThreadLocalb<T>
		
		if (context == null) {
			this.context = context;
		}

		if (wManager == null) {

			this.wManager = (WindowManager) context
					.getSystemService(context.WINDOW_SERVICE);
		}
		this.context = context;

		Log.i("tianmin", intent.getAction() + "");

		// if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
		//
		// Log.i("tianmin", "执行啦！ 哈哈！");
		//
		// showLocation();
		//
		// } else {
		if (tm == null) {

			tm = (TelephonyManager) context
					.getSystemService(context.TELEPHONY_SERVICE);
			tm.listen(new phoneState(), PhoneStateListener.LISTEN_CALL_STATE);

		}

		//
		// }
	}

	/**
	 * 
	 * @author lenovo
	 * 
	 */
	class phoneState extends PhoneStateListener {
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:

				// if (view != null) {
				// wManager.removeView(view);
				// }

				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if (incomingNumber.equals("")) {
					// Intent intent = new Intent();
					// intent.setClass(context, CallConnectedDisplay.class);
					// intent.setAction(CallConnectedDisplay.class.getName());
					// intent.setFlags(
					// Intent.FLAG_ACTIVITY_NEW_TASK
					// | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					// context.startActivity(intent);
				}

				// if (view != null) {
				//
				// wManager.removeView(view);
				// }

				// wManager.removeView(view);

				break;
			case TelephonyManager.CALL_STATE_RINGING:
				// Intent intent = new Intent();
				// intent.setClass(context, CallingActivity.class);
				// intent.setAction(CallingActivity.class.getName());
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				// | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				// // intent.putExtra("callername", name);
				// context.startActivity(intent);

				// showLocation();

				break;
			}
		}
	}

	public void showLocation() {

		WindowManager.LayoutParams params = new WindowManager.LayoutParams();

		params.height = WindowManager.LayoutParams.WRAP_CONTENT;

		params.width = WindowManager.LayoutParams.WRAP_CONTENT;

		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

		params.format = PixelFormat.TRANSLUCENT;

		params.type = WindowManager.LayoutParams.TYPE_TOAST;

		params.setTitle("Toast");

		params.gravity = Gravity.TOP;

		params.x = 0;

		params.y = 0;

		view = View.inflate(context, R.layout.calling_layout, null);

		wManager.addView(view, params);

	}
	
	
	
}
