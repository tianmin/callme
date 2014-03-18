package com.diandian.mycall.me.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.diandian.mycall.R;
import com.diandian.mycall.coreui.BaseActivity;

public class RegisterActivity extends BaseActivity {

	public EditText etNumber, etUserName, etUserPwd, etConfirmPwd;

	public Button btnRegister;

	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_user_info);

		initData();

		initViews();

		initListener();

	}

	public void initData() {

		Intent intent = getIntent();

		bundle = intent.getExtras();

	}

	public void initListener() {

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 联网进行注册

				// 进行跳转
				Intent intent = new Intent(RegisterActivity.this,
						MoreInfoActivity.class);

				intent.putExtras(bundle);

				startActivity(intent);

				RegisterActivity.this.finish();

			}
		});

	}

	public void initViews() {

		etNumber = (EditText) findViewById(R.id.reg_et_phonenumber);

		etNumber.setText(getTelNumber());

		etUserName = (EditText) findViewById(R.id.reg_et_username);

		etUserPwd = (EditText) findViewById(R.id.reg_et_password);

		etConfirmPwd = (EditText) findViewById(R.id.reg_et_two_password);

		btnRegister = (Button) findViewById(R.id.reg_btn_ok);

		etUserName.setFocusable(true);

		etUserName.setFocusableInTouchMode(true);

		etUserName.requestFocus();

		// 让键盘自动弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	public String getTelNumber() {

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);

		return tm.getLine1Number();
	}

}
