package com.diandian.mycall.common;

import com.diandian.mycall.contacts.AddContactsActivity;
import com.diandian.mycall.message.ChatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 1.打电话 2.发短信
 * 
 * @author lenovo
 * 
 */
public class CommonOperator {

	/**
	 * 启动打电话这个操作
	 * 
	 * @param context
	 * @param number
	 */
	public static void makeCall(Context context, String number) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		context.startActivity(intent);
	}

	/**
	 * 打开发短信界面的操作
	 * 
	 * @param context
	 * @param thread_id
	 * @param name
	 * @param number
	 */
	public static void sendSms(Context context, int thread_id, String name,
			String number) {
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra("thread_id", thread_id);
		intent.putExtra("phone", number);
		intent.putExtra("name", name);
		context.startActivity(intent);
	}

	/**
	 * 保存号码
	 * 
	 * @param context
	 * @param number
	 */
	public static void saveNumber(Context context, String number
			) {

		Intent intent = new Intent(context, AddContactsActivity.class);

		intent.putExtra("phone", number);

		context.startActivity(intent);
	}

}
