package com.diandian.mycall.me.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.diandian.mycall.R;
import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.coreui.HomeActivity;

public class ShareActivity extends BaseActivity implements OnClickListener {

	Button btnExit, btnShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.share_layout);

		initData();

		initViews();

		initListener();

	}

	public void initListener() {

		btnExit.setOnClickListener(this);

		btnShare.setOnClickListener(this);

	}

	public void initViews() {

		btnExit = (Button) findViewById(R.id.share_btn_exit);

		btnShare = (Button) findViewById(R.id.share_btn_post);

		// 让键盘自动弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

	}

	public void initData() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.share_btn_exit:

			Intent intent = new Intent(ShareActivity.this,
					MyShareActivity.class);

			startActivity(intent);

			// 自定义右进左出 //activity切换
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			
			ShareActivity.this.finish();
			break;

		case R.id.share_btn_post:

			break;
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Intent intent = new Intent(ShareActivity.this, MyShareActivity.class);

		startActivity(intent);

		// 自定义右进左出 //activity切换
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

		ShareActivity.this.finish();

		return super.onKeyDown(keyCode, event);
	}
}
