package com.diandian.mycall.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.diandian.mycall.common.Constant;
import com.diandian.mycall.util.L;

public class MessageContentOperator {

	public static String tag = "CallLogListAdapter";

	public Context context;

	public MessageContentOperator(Context context) {
		this.context = context;
	}

	/**
	 * 得到所有的短信数据
	 */

	public LinkedHashMap<Integer, MessageBean> getAllMessage() {

		LinkedHashMap<Integer, MessageBean> smsData = new LinkedHashMap<Integer, MessageBean>();

		Cursor smsCursor = context.getContentResolver()
				.query(Uri.parse(Constant.Message.MESSAGE_URI), null, null,
						null, null);

		while (smsCursor.moveToNext()) {
			MessageBean msg = new MessageBean();
			msg.setSmsId(smsCursor.getInt(smsCursor.getColumnIndex("_id")));

			msg.setThreadId(smsCursor.getInt(smsCursor
					.getColumnIndex("thread_id")));

			msg.setSmsBody(smsCursor.getString(smsCursor.getColumnIndex("body")));

			msg.setSmsDate(smsCursor.getLong(smsCursor.getColumnIndex("date")));

			msg.setSmsAdress(smsCursor.getString(smsCursor
					.getColumnIndex("address")));

			// int pid = smsCursor.getInt(smsCursor.getColumnIndex("person"));
			//
			// String person = pid + "";
			//
			// if (person != null) {
			//
			// msg.setPerson(person);
			// }

			msg.setRead(smsCursor.getInt(smsCursor.getColumnIndex("read")));

			smsData.put(msg.getThreadId(), msg);
		}
		smsCursor.close();
		return smsData;
	}

	/**
	 * 根据id 删除某一条短信
	 * 
	 * @param id
	 */
	public void DeleteMsgbyId(int id) {

		int smsId = context.getContentResolver().delete(
				Uri.parse(Constant.Message.MESSAGE_URI), "_id = ?",
				new String[] { id + "" });

	}

	/**
	 * 根据thread_id删除某一对话信息
	 * 
	 * @param threadId
	 */
	public void DeleteMsgByThreadId(int threadId) {

		int smsId = context.getContentResolver().delete(
				Uri.parse(Constant.Message.MESSAGE_URI), "thread_id=?",
				new String[] { threadId + "" });

	}

	/**
	 * 根据id 获得 某一对话的个数
	 * 
	 * @param threadId
	 * @return
	 */
	public int getMessageCount(int threadId) {

		int count = -1;

		Cursor cursor = context.getContentResolver().query(
				Uri.parse(Constant.Message.MESSAGE_THREAD_URI),
				new String[] { "message_count" }, "_id = ?",
				new String[] { threadId + "" }, null);
		while (cursor.moveToNext()) {
			count = cursor.getInt(cursor.getColumnIndex("message_count"));
		}
		cursor.close();
		return count;
	}

	/**
	 * 得到某一对话的所有信息
	 * 
	 * @param thread_id
	 */
	public LinkedHashMap<Integer, MessageDetail> getAllMessageByThreadId(
			int thread_id) {

		LinkedHashMap<Integer, MessageDetail> smsData = new LinkedHashMap<Integer, MessageDetail>();
		// Uri.parse(Constant.Message.MESSAGE_URI);
		Cursor smsCursor = context.getContentResolver().query(
				Uri.parse(Constant.Message.MESSAGE_URI), null, "thread_id=?",
				new String[] { thread_id + "" }, "date");

		while (smsCursor.moveToNext()) {

			MessageDetail msgDetail = new MessageDetail();

			msgDetail.setSmsDetailId(smsCursor.getInt(smsCursor
					.getColumnIndex("_id")));

			msgDetail.setSmsDetailThreadId(smsCursor.getInt(smsCursor
					.getColumnIndex("thread_id")));

			msgDetail.setSmsDetailBody(smsCursor.getString(smsCursor
					.getColumnIndex("body")));

			msgDetail.setSmsDetailDate(smsCursor.getLong(smsCursor
					.getColumnIndex("date")));

			msgDetail.setSmsDetailAddress(smsCursor.getString(smsCursor
					.getColumnIndex("address")));

			msgDetail.setSmsDetailType(smsCursor.getInt(smsCursor
					.getColumnIndex("type")));

			smsData.put(msgDetail.getSmsDetailId(), msgDetail);

		}

		smsCursor.close();

		return smsData;

	}

	/**
	 * 获取所有的电话数据
	 * 
	 * @return
	 */
	public LinkedHashMap<String, CallLogBean> getAllCallData() {

		// Data
		LinkedHashMap<String, CallLogBean> callLogMapData = new LinkedHashMap<String, CallLogBean>();

		// Uri
		Uri callLogUri = CallLog.Calls.CONTENT_URI;

		// Cursor
		Cursor callLogCursor = context.getContentResolver().query(callLogUri,
				null, null, null, "date desc");

		// 遍历所有的通话记录
		// 将通话记录每一条都创建对应的Bean对象
		// 将这些通话记录Bean对象逐一插入到List集合中
		// 整个方法返回通话记录的List集合

		while (callLogCursor.moveToNext()) {

			// 判断电话号码是否已经存在Map中
			String strCallNumber = callLogCursor.getString(callLogCursor
					.getColumnIndex("number"));

			// 如果存在电话号码，则通话数量+1
			// 改电话号码已经存在
			if (callLogMapData.get(strCallNumber) != null) {

				// 得到该对象的电话记录的个数
				int count = callLogMapData.get(strCallNumber).getCallLogCount();

				// 然后在通话记录的count 加上1
				callLogMapData.get(strCallNumber).setCallLogCount(count += 1);

			}

			// 不存在对应的电话号码，则将CallLogBean实例
			// 压入(put)Map中

			else {

				try {

					CallLogBean nowCallLogBean = new CallLogBean();

					nowCallLogBean.setCallLogNumber(strCallNumber);

					nowCallLogBean.setCallLogCount(1);

					// 组织数据 1:打进来的电话 2:打出的电话 3:未接电话
					// TYPE
					nowCallLogBean.setCallLogType(callLogCursor
							.getInt(callLogCursor.getColumnIndex("type")));

					// DATE
					nowCallLogBean.setCallLogDate(callLogCursor
							.getLong(callLogCursor.getColumnIndex("date")));

					// DATE FORMAT
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");

					nowCallLogBean.setCallLogDateFormat(sdf.format(new Date(
							nowCallLogBean.getCallLogDate())));

					nowCallLogBean.setCallLogName(callLogCursor
							.getString(callLogCursor.getColumnIndex("name")));

					if (!nowCallLogBean.getCallLogNumber().equals("-1" + "")) {

						callLogMapData.put(nowCallLogBean.getCallLogNumber(),
								nowCallLogBean);
					}

				} catch (Exception e) {

					e.printStackTrace();

				}
			}
		}

		callLogCursor.close();

		return callLogMapData;
	}

	public void deleteCallLogByNumber(String number) {

		// Uri
		Uri callLogUri = CallLog.Calls.CONTENT_URI;
		// Cursor
		int callId = context.getContentResolver().delete(callLogUri,
				"number=?", new String[] { number });
		L.i(tag, "id:" + callId);

	}

	public String getContactNameByPhoneNumber(Context context, String address) {

		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		// 将自己添加到 msPeers 中
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				projection, // Which columns to return.
				ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
						+ address + "'", // WHERE clause.
				null, // WHERE clause value substitution
				null); // Sort order.

		if (cursor == null) {

			cursor.close();

			return null;
		}
		while (cursor.moveToNext()) {
			// 取得联系人名字
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);

			String name = cursor.getString(nameFieldColumnIndex);

			cursor.close();
			return name;
		}

		cursor.close();

		return null;
	}

	/**
	 * 根据电话号码获取 thread_id
	 * 
	 * @param number
	 * @return
	 */
	public int getThreadIdbyPhoneNumber(String number) {
		int thread_id = -2;
		// Uri.parse(Constant.Message.MESSAGE_URI)
		Cursor smsCursor = context.getContentResolver().query(
				Uri.parse(Constant.Message.MESSAGE_URI), null, "address=?",
				new String[] { number }, null);

		L.i(tag, "count:" + smsCursor.getCount());

		if (smsCursor.moveToNext()) {
			L.i(tag, "true");
			thread_id = smsCursor.getInt(smsCursor.getColumnIndex("thread_id"));
			smsCursor.close();
			return thread_id;
		}

		smsCursor.close();
		return thread_id;
	}

}
