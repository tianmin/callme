package com.diandian.mycall.contacts;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.diandian.mycall.common.Constant;
import com.diandian.mycall.common.TApplication;

public class ContactThread extends Thread {
	
	private Context context;
	
	private TApplication application;
	
	public ContactThread(Context context){
		this.context = context;
		this.application = TApplication.getInstance();
	}
	
	
	@Override
	public void run() {
		super.run();
		
		System.out.println("thread   running   running");
		
		//查询系统中所有的联系人
		List<ContactEntity> allContacts = application.contactOperator.getAllContacts();
		if (application.allContacts != null && allContacts.size() == application.allContacts.size()) {
			//数据不需要再加载,也不需要更新
		}else if(application.allContacts != null && allContacts.size() != application.allContacts.size()){
			//修改TApplication 中的数据
			application.allContacts = allContacts;
		}else{
			//加载数据
			application.allContacts = allContacts;
		}
		
		//查询系统中所有的组数据
		List<GroupEntity> allGroupInfo = application.contactOperator.getAllGroupInfo();
		if(application.allGroupInfo == null){
			//加载数据
			application.allGroupInfo = allGroupInfo;
		}else if (application.allGroupInfo != null && application.allGroupInfo.size() == allGroupInfo.size()){
			//数据不需要重新赋值
		}else if(application.allGroupInfo != null && application.allGroupInfo.size() != allGroupInfo.size()){
			//重新赋值
			application.allGroupInfo = allGroupInfo;
		}else{
			//....
		}
		context.sendBroadcast(new Intent("com.tarena.action.updata"));
	}
}
