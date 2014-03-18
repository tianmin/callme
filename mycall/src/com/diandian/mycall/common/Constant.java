package com.diandian.mycall.common;

/**
 * 常量
 * 
 * @author lenovo
 * 
 */
public interface Constant {

	public class Common {

	}

	public class Contacts {

		// 广播
		public static String UP_CONTACT_ACTIOIN = "com.mycall.updataContacts";

		public static final String TITLENAME = "通讯录";

		public static final String ALLCONTACT = "全部联系人";
		
		public static final String CONTACT_LEFT = "管理";
		
		public static final String CONTACT_RIGHT = "添加";

		public static final String CONTACT_PAGE = "2";

	}

	public class Message {

		public static String MESSAGE_URI = "content://sms/";

		public static String MESSAGE_THREAD_URI = "content://mms-sms/conversations ? simple = true";

		// 电话的状态
		public static int CALL_IN = 1;

		public static int CALL_OUT = 2;

		public static int CALL_MISS = 3;

	}

	public class MsgType {

		public static int RECEIVE_MSG = 1;

		public static int SEND_MSG = 2;

	}

}
