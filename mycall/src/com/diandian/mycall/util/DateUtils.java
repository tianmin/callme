package com.diandian.mycall.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String friendlyTime(int timestamp) {

		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - (long) timestamp;// 与现在时间相差秒数

		long toZero = currentSeconds / (24 * 60 * 60) * (24 * 60 * 60);
		long todayGap = currentSeconds - toZero;

		String timeStr = null;
		if (timeGap > 24 * 60 * 60 || timeGap > todayGap) {// 1天以上
			// timeStr = timeGap/(24*60*60)+"天前";
			timeStr = getStandardTimeWithDate((long) timestamp);
		} else if (timeGap > 60 * 60 && timeGap < todayGap) {// 1小时-24小时
			timeStr = "今天  " + getStandardTimeWithHour((long) timestamp);
		} else if (timeGap > 60 && timeGap < 3600) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else if (timeGap > 0 && timeGap < 60) {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	public static String getStandardTimeWithDate(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	public static String getStandardTimeWithHour(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}
	
	
	public static String getCurrentDate(long time) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(time);
	}
}
