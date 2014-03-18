package com.diandian.mycall.contacts;

import java.io.Serializable;

/**
 * 联系人组
 * 
 * @author lenovo
 * 
 */
public class GroupEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int groupId;

	private String groupName;

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}
}
