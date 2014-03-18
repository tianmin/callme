package com.diandian.mycall.message;

public class CallLogBean {

	private int callLogId;
	private String callLogNumber;
	private String callLogName;
	private int callLogType;
	private long callLogDate;
	private String callLogDateFormat;
	private int callLogCount;

	// ContactBean callLogContactBean;
	public int getCallLogId() {
		return callLogId;
	}

	public void setCallLogId(int callLogId) {
		this.callLogId = callLogId;
	}

	public String getCallLogNumber() {
		return callLogNumber;
	}

	public void setCallLogNumber(String callLogNumber) {
		this.callLogNumber = callLogNumber;
	}

	public String getCallLogName() {
		return callLogName;
	}

	public void setCallLogName(String callLogName) {
		this.callLogName = callLogName;
	}

	public int getCallLogType() {
		return callLogType;
	}

	public void setCallLogType(int callLogType) {
		this.callLogType = callLogType;
	}

	public long getCallLogDate() {
		return callLogDate;
	}

	public void setCallLogDate(long callLogDate) {
		this.callLogDate = callLogDate;
	}

	public String getCallLogDateFormat() {
		return callLogDateFormat;
	}

	public void setCallLogDateFormat(String callLogDateFormat) {
		this.callLogDateFormat = callLogDateFormat;
	}

	public int getCallLogCount() {
		return callLogCount;
	}

	public void setCallLogCount(int callLogCount) {
		this.callLogCount = callLogCount;
	}
}
