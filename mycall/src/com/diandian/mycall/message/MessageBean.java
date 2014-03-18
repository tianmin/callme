package com.diandian.mycall.message;

import java.io.Serializable;

import com.diandian.mycall.contacts.ContactEntity;

/**
 * 消息实体
 * 
 * @author lenovo
 */
public class MessageBean implements Serializable{
	
	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**消息id*/
	private int smsId;
	
	/**某一消息对话的id*/
	private int threadId;
	
	/**是与哪一个发生的*/
	private String smsAdress;
	
	/**短信的内容*/
	private String smsBody;

	/**短信的日期*/
	private long smsDate;
	
	/**类型*/
	private int type;
	
	/**消息的个数*/
	private int smsCount;
	
	/**人*/
	private String person;

	/**是否阅读*/
	private int read;
	
	/**联系人实体*/
	private ContactEntity contact;

	public int getSmsId() {
		return smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public String getSmsAdress() {
		return smsAdress;
	}

	public void setSmsAdress(String smsAdress) {
		this.smsAdress = smsAdress;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public long getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(long smsDate) {
		this.smsDate = smsDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

}
