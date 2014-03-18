package com.diandian.mycall.me.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diandian.mycall.R;
import com.diandian.mycall.me.register.PhotoActivity;
import com.diandian.mycall.view.layout.KeyboardListenRelativeLayout;

public class LoginActivity extends Activity implements OnClickListener {

	KeyboardListenRelativeLayout meLog;

	RelativeLayout logRel;

	TextView my;
	TextView call;

	TextView username;
	TextView password;

	Button btnLogin;

	Button btnReg;

	Animation mAnimation = null;
	Animation cAnimation = null;

	Animation usrname_Animation = null;
	Animation password_Animation = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mycall_login);

		initViews();

		initListener();

	}

	public void initListener() {

		btnLogin.setOnClickListener(this);

		btnReg.setOnClickListener(this);

		// meLog.setOnKeyboardStateChangedListener(new
		// IOnKeyboardStateChangedListener() {
		//
		// public void onKeyboardStateChanged(int state) {
		// switch (state) {
		//
		// case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE:// 软键盘隐藏
		//
		// Log.i("tianmin", "监听到啦!");
		//
		// RelativeLayout.LayoutParams paramsHidd = new
		// RelativeLayout.LayoutParams(
		// LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT);
		//
		// paramsHidd.setMargins(0, 80, 0, 0);
		//
		// break;
		//
		// case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW:// 软键盘显示
		//
		// Log.i("tianmin", "监听到啦!");
		//
		// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		// LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT);
		//
		// params.setMargins(0, 25, 0, 0);
		//
		// break;
		// default:
		// break;
		// }
		// }
		// });
	}

	public void initViews() {

		meLog = (KeyboardListenRelativeLayout) findViewById(R.id.me_login_rel);

		logRel = (RelativeLayout) findViewById(R.id.login_log);

		my = (TextView) findViewById(R.id.tv_my);
		call = (TextView) findViewById(R.id.tv_call);

		username = (TextView) findViewById(R.id.login_et_username);
		password = (TextView) findViewById(R.id.login_et_password);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnReg = (Button) findViewById(R.id.btn_register);

		username.setText(getTelNumber());

		mAnimation = AnimationUtils.loadAnimation(this,
				R.anim.login_my_translate);

		cAnimation = AnimationUtils.loadAnimation(this,
				R.anim.login_call_translate);

		usrname_Animation = AnimationUtils.loadAnimation(this,
				R.anim.translate_login);

		password_Animation = AnimationUtils.loadAnimation(this,
				R.anim.translate_login);

		my.startAnimation(mAnimation);

		call.startAnimation(cAnimation);

		username.startAnimation(usrname_Animation);

		password.startAnimation(password_Animation);

		btnLogin.startAnimation(password_Animation);

		password.setFocusableInTouchMode(true);

		password.requestFocus();

	}

	public String getTelNumber() {

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);

		return tm.getLine1Number();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login:

			break;

		case R.id.btn_register:

			Intent intent = new Intent(LoginActivity.this, PhotoActivity.class);

			startActivity(intent);

			LoginActivity.this.finish();

			break;
		}

	}
}
