package com.diandian.mycall.coreui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.diandian.mycall.R;
import com.diandian.mycall.common.TApplication;

/**
 * HOMEActivity
 * 
 * @author lenovo
 */
@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {

	private HomeActivity context;

	TabHost tab;

	int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		// 初始化Context 对象
		context = this;

		// 得到Application 实例
		TApplication.getInstance().setContext(context);

		// 获取数据
		Intent intent = getIntent();

		// 得到需要选中的页码
		String page = intent.getStringExtra("page");

		if (page != null) {

			currentPage = Integer.parseInt(page);

		}

		initTab();

		initViews();
	}

	@SuppressWarnings("deprecation")
	private void initTab() {
		tab = getTabHost();
	}

	private void initViews() {

		LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

		// =============================ME===================================//
		View common_me = inflater.inflate(R.layout.common_me_bar, null);

		TabHost.TabSpec meSpe = tab.newTabSpec(MeActivity.class.getName())
				.setIndicator(common_me);

		meSpe.setContent(new Intent(HomeActivity.this, MeActivity.class));
		tab.addTab(meSpe);

		// =============================CONTACTS===================================//

		View common_contacts = inflater.inflate(R.layout.common_contacts_bar,
				null);

		TabHost.TabSpec contactsSpec = tab.newTabSpec(
				ContactsActivity.class.getName()).setIndicator(common_contacts);

		contactsSpec.setContent(new Intent(HomeActivity.this,
				ContactsActivity.class));

		tab.addTab(contactsSpec);

		// =============================MESSAGE===================================//

		View common_message = inflater.inflate(R.layout.common_message_bar,
				null);

		TabHost.TabSpec msgSpec = tab.newTabSpec(
				MessageActivity.class.getName()).setIndicator(common_message);

		msgSpec.setContent(new Intent(HomeActivity.this, MessageActivity.class));

		tab.addTab(msgSpec);

		// =============================CALL===================================//

		View common_call = inflater.inflate(R.layout.common_call_bar, null);

		TabHost.TabSpec callSpec = tab.newTabSpec(CallActivity.class.getName())
				.setIndicator(common_call);

		callSpec.setContent(new Intent(HomeActivity.this, CallActivity.class));

		tab.addTab(callSpec);

		// ==================================================================//

		tab.setCurrentTab(currentPage);
	}
}
