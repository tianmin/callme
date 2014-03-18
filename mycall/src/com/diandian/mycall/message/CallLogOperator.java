package com.diandian.mycall.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

public class CallLogOperator {

	private Context context;

	public CallLogOperator(Context context) {
		this.context = context;
	}

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
			if (callLogMapData.get(strCallNumber) != null) {
				int count = callLogMapData.get(strCallNumber).getCallLogCount();
				callLogMapData.get(strCallNumber).setCallLogCount(count += 1);
				;
			}
			// 不存在对应的电话号码，则将CallLogBean实例
			// 压入(put)Map中
			else {
				try {
					CallLogBean nowCallLogBean = new CallLogBean();

					nowCallLogBean.setCallLogNumber(strCallNumber);
					nowCallLogBean.setCallLogCount(1);
					// 组织数据
					nowCallLogBean.setCallLogType(callLogCursor
							.getInt(callLogCursor.getColumnIndex("type")));

					nowCallLogBean.setCallLogDate(callLogCursor
							.getLong(callLogCursor.getColumnIndex("date")));

					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");

					nowCallLogBean.setCallLogDateFormat(sdf.format(new Date(
							nowCallLogBean.getCallLogDate())));

					nowCallLogBean.setCallLogName(callLogCursor
							.getString(callLogCursor.getColumnIndex("name")));

					callLogMapData.put(nowCallLogBean.getCallLogNumber(),
							nowCallLogBean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		callLogCursor.close();
		return callLogMapData;
	}
}
