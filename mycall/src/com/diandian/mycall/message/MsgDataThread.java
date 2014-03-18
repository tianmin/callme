package com.diandian.mycall.message;

import android.content.Context;
import android.content.Intent;

import com.diandian.mycall.common.TApplication;


public class MsgDataThread extends Thread{
	
	private Context context;
	
	public MsgDataThread(Context context){
			this.context = context;
	}
	
	private TApplication application = TApplication.getInstance();
	
	@Override
	public void run() {
		//step1: 得到短消息的数据
		//application.msgData = application.msgOperator.getAllMessage();
		
		application.callData = application.msgOperator.getAllCallData();
		
//		//step2: 通知数据已经更新
//		Intent intent = new Intent();
//		intent.setAction("com.tarena.msg.updata");
//		context.sendBroadcast(intent);
	}

}
