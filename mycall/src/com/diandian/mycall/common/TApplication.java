package com.diandian.mycall.common;

import java.util.LinkedHashMap;
import java.util.List;

import android.app.Application;
import android.content.Context;

import com.diandian.mycall.contacts.ContactEntity;
import com.diandian.mycall.contacts.ContactsOperator;
import com.diandian.mycall.contacts.GroupEntity;
import com.diandian.mycall.coreui.HomeActivity;
import com.diandian.mycall.message.CallLogBean;
import com.diandian.mycall.message.MessageBean;
import com.diandian.mycall.message.MessageContentOperator;

public class TApplication extends Application {

	// HomeActivity Context
	private HomeActivity context;

	// ======================================================================//
	// 联系人数据操作对象
	public ContactsOperator contactOperator;

	// 消息数据操作对象
	public MessageContentOperator msgOperator;

	// =====================================================================//

	public static TApplication application;

	// 所有联系人的数据
	public List<ContactEntity> allContacts;

	// 所有组的数据
	public List<GroupEntity> allGroupInfo;

	// 所有短消息的数据
	public LinkedHashMap<Integer, MessageBean> msgData = new LinkedHashMap<Integer, MessageBean>();

	// 所有电话记录的数据
	public LinkedHashMap<String, CallLogBean> callData = new LinkedHashMap<String, CallLogBean>();

	public static TApplication getInstance() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		TApplication.application = this;
		if (contactOperator == null) {
			contactOperator = new ContactsOperator(getApplicationContext());
		}
		if (msgOperator == null) {
			msgOperator = new MessageContentOperator(getApplicationContext());
		}
	}

	public HomeActivity getContext() {
		return context;
	}

	public void setContext(HomeActivity context) {
		this.context = context;
	}
}
