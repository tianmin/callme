package com.diandian.mycall.coreui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.diandian.mycall.R;
import com.diandian.mycall.message.CallRecordFragment;
import com.diandian.mycall.message.SmsFragment;
import com.diandian.mycall.view.BootstrapButton;

/**
 * MessageActivity
 * 
 * @author bill
 */
public class MessageActivity extends FragmentActivity implements
		OnClickListener {

	// 短消息
	SmsFragment smsFragment;
	
	// 电话记录
	CallRecordFragment callRecordFragment;

	// 控件
	Button btnCall, btnMessage;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_message);

		initViews();

		initListener();
	}

	/**
	 * 初始化界面
	 */
	public void initViews() {
		
		// 电话记录
		btnCall = (Button) findViewById(R.id.call_record);

		// 短信记录
		btnMessage = (Button) findViewById(R.id.message_record);
		
		//创建 短消息 和 电话记录的 Fragment
		smsFragment = new SmsFragment();

		callRecordFragment = new CallRecordFragment();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// 初始化Fragment 初始的值是 电话的记录
		ft.replace(R.id.message_content, callRecordFragment).commit();

	}

	/**
	 * 初始化监听
	 */
	public void initListener() {

		btnCall.setOnClickListener(this);

		btnMessage.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		switch (v.getId()) {

		case R.id.call_record:

			ft.replace(R.id.message_content, callRecordFragment).commit();

			break;

		case R.id.message_record:

			ft.replace(R.id.message_content, smsFragment).commit();

			break;
		}
	}
}
