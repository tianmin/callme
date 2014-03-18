package com.diandian.mycall.me.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.diandian.mycall.R;
import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.coreui.HomeActivity;
import com.diandian.mycall.coreui.MeActivity;

public class MyShareActivity extends BaseActivity implements OnClickListener {

	Button btn_exit, btn_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.me_share);

		initData();

		initViews();

		initListener();

	}

	public void initListener() {

		btn_exit.setOnClickListener(this);

		btn_share.setOnClickListener(this);

	}

	public void initViews() {

		btn_exit = (Button) findViewById(R.id.btn_me_exit);

		btn_share = (Button) findViewById(R.id.btn_me_share);

	}

	public void initData() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_me_exit:

			Intent intent = new Intent(MyShareActivity.this, HomeActivity.class);

			startActivity(intent);

			// 自定义右进左出 //activity切换
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

			MyShareActivity.this.finish();

			break;

		case R.id.btn_me_share:

			Intent intentShare = new Intent(MyShareActivity.this,
					ShareActivity.class);

			startActivity(intentShare);

			// 自定义右进左出 //activity切换
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

			MyShareActivity.this.finish();

			break;
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Intent intent = new Intent(MyShareActivity.this, HomeActivity.class);

		startActivity(intent);

		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

		MyShareActivity.this.finish();

		return super.onKeyDown(keyCode, event);
	}

}
