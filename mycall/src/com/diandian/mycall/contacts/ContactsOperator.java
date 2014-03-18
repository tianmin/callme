package com.diandian.mycall.contacts;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.SipAddress;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

/**
 * 对联系人操作
 * 
 * @author lenovo
 */
public class ContactsOperator {

	private Context context;

	public ContactsOperator(Context context) {
		this.context = context;
	}

	/**
	 * 得到所有组的信息
	 * 
	 * @return
	 */
	public List<GroupEntity> getAllGroupInfo() {
		List<GroupEntity> groupList = new ArrayList<GroupEntity>();
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(Groups.CONTENT_URI,
					null, null, null, null);
			while (cursor.moveToNext()) {
				GroupEntity ge = new GroupEntity();
				int groupId = cursor.getInt(cursor.getColumnIndex(Groups._ID)); // 组id
				String groupName = cursor.getString(cursor
						.getColumnIndex(Groups.TITLE)); // 组名
				ge.setGroupId(groupId);
				ge.setGroupName(groupName);
				Log.i("MainActivity", "group id:" + groupId + ">>groupName:"
						+ groupName);
				groupList.add(ge);
				ge = null;
			}
			return groupList;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * 根据 groupId 获取所有的联系人
	 * 
	 * @param groupId
	 * @return
	 */
	public List<ContactEntity> getAllContactsByGroupId(int groupId) {

		String[] RAW_PROJECTION = new String[] { ContactsContract.Data.RAW_CONTACT_ID, };

		String RAW_CONTACTS_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
				+ "=?"
				+ " and "
				+ ContactsContract.Data.MIMETYPE
				+ "="
				+ "'"
				+ ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
				+ "'";

		// 通过分组的id 查询得到RAW_CONTACT_ID
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,
				RAW_CONTACTS_WHERE, new String[] { groupId + "" }, "data1 asc");

		List<ContactEntity> contactList = new ArrayList<ContactEntity>();

		while (cursor.moveToNext()) {
			// RAW_CONTACT_ID
			int col = cursor.getColumnIndex("raw_contact_id");
			int raw_contact_id = cursor.getInt(col);

			ContactEntity ce = new ContactEntity();

			ce.setContactId(raw_contact_id);

			Uri dataUri = Uri.parse("content://com.android.contacts/data");
			Cursor dataCursor = context.getContentResolver().query(dataUri,
					null, "raw_contact_id=?",
					new String[] { raw_contact_id + "" }, null);

			while (dataCursor.moveToNext()) {
				String data1 = dataCursor.getString(dataCursor
						.getColumnIndex("data1"));

				String mime = dataCursor.getString(dataCursor
						.getColumnIndex("mimetype"));

				if ("vnd.android.cursor.item/phone_v2".equals(mime)) {

					ce.setTelNumber(data1);

				} else if ("vnd.android.cursor.item/name".equals(mime)) {

					ce.setContactName(data1);

				}
			}

			dataCursor.close();
			contactList.add(ce);
			ce = null;

		}
		cursor.close();

		return contactList;
	}

	/**
	 * 获得所有的联系人
	 * 
	 * @return
	 */
	public List<ContactEntity> getAllContacts() {

		// 1.查询raw_contacts表 获取所有联系人的id

		Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");

		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		List<ContactEntity> contaList = new ArrayList<ContactEntity>();

		Cursor idCursor = context.getContentResolver().query(rawUri,
				new String[] { "contact_id" }, null, null, null);

		while (idCursor.moveToNext()) {

			int id = idCursor.getInt(0); // 得到联系人的id

			ContactEntity ce = new ContactEntity();

			ce.setContactId(id);

			Cursor dataCursor = context.getContentResolver().query(dataUri,
					null, "raw_contact_id=?", new String[] { id + "" }, null);

			while (dataCursor.moveToNext()) {

				String data1 = dataCursor.getString(dataCursor
						.getColumnIndex("data1"));

				String mime = dataCursor.getString(dataCursor
						.getColumnIndex("mimetype"));

				String photoId = dataCursor.getString(dataCursor
						.getColumnIndex("photo_id"));

				ce.setPhotoId(photoId);

				if ("vnd.android.cursor.item/phone_v2".equals(mime)) {

					ce.setTelNumber(data1);

				} else if ("vnd.android.cursor.item/name".equals(mime)) {

					ce.setContactName(data1);

				}
			}

			dataCursor.close();

			if (ce.getContactName() != null) {

				contaList.add(ce);
			}

			ce = null;

		}
		idCursor.close();
		return contaList;
	}

	/**
	 * 新建一个组
	 */
	public void addGroup(String name) {
		ContentValues values = new ContentValues();
		values.put(Groups.TITLE, name);
		context.getContentResolver().insert(Groups.CONTENT_URI, values);

	}

	/**
	 * 删除一个小组
	 */
	public void deleteGroup(int groupId) {
		context.getContentResolver().delete(
				Uri.parse(Groups.CONTENT_URI + "?"
						+ ContactsContract.CALLER_IS_SYNCADAPTER + "=true"),
				Groups._ID + "=" + groupId, null);
	}

	/**
	 * 更新组的名字
	 * 
	 * @param oldName
	 *            老名字
	 * @param newName
	 *            新名字
	 * @param groupId
	 *            组ID
	 */
	public void renameGroup(String oldName, String newName, int groupId) {
		Uri uri = ContentUris.withAppendedId(Groups.CONTENT_URI, groupId);
		ContentValues values = new ContentValues();
		values.put(Groups.TITLE, newName);
		context.getContentResolver().update(uri, values, null, null);

	}

	/**
	 * 将某一人员添加在某一组内
	 * 
	 * @param groupId
	 * @param personId
	 */
	public void addContactByGroupId(int groupId, int personId) {
		ContentValues values = new ContentValues();
		values.put(
				ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
				personId);

		values.put(
				ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
				groupId);

		values.put(
				ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
				ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
				values);

	}

	/***
	 * 根据id 删除联系人组
	 * 
	 * @param groupId
	 * @param personId
	 */
	public void deleteContactByGroupId(int groupId, int personId) {
		ContentResolver resolver = context.getContentResolver();
		resolver.delete(
				ContactsContract.Data.CONTENT_URI,
				ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID
						+ "=? and "
						+ ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
						+ "=? and "
						+ ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE
						+ "=?",
				new String[] {
						"" + personId,
						"" + groupId,
						ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE });
	}
	
	/**
	 * 根据名字删除联系人
	 * @param context
	 * @param name
	 */
	public void delContact(Context context, String name) {
		// 根据名字找到id,在进行删除
		Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,
				new String[] { Data.RAW_CONTACT_ID },
				ContactsContract.Contacts.DISPLAY_NAME + "=?",
				new String[] { name }, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		if (cursor.moveToFirst()) {
			do {
				long Id = cursor.getLong(cursor
						.getColumnIndex(Data.RAW_CONTACT_ID));
				ops.add(ContentProviderOperation
						.newDelete(
								ContentUris.withAppendedId(
										RawContacts.CONTENT_URI, Id)).build());
				try {
					context.getContentResolver().applyBatch(
							ContactsContract.AUTHORITY, ops);
				} catch (Exception e) {
				}
			} while (cursor.moveToNext());
			cursor.close();
		}
	}

	/**
	 * 根据id 来删除联系人
	 * @param context
	 * @param rawId
	 */
	public void delContact(Context context, int rawId) {

		Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");

		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		int flag1 = context.getContentResolver().delete(rawUri, "contact_id=?",
				new String[] { rawId + "" });

		int flag2 = context.getContentResolver().delete(dataUri,
				"raw_contact_id=?", new String[] { rawId + "" });

	}

	/**
	 * 添加联系人
	 * 
	 * @param context
	 * @param name
	 * @param organisation
	 * @param phone
	 * @param fax
	 * @param email
	 * @param address
	 * @param website
	 */
	@SuppressLint("InlinedApi")
	public long addContact(Context context, String name, String phone,
			String mobilePhone, String email, String address, String nickName,
			String note) {
		ContentResolver cr = context.getContentResolver();

		// 向原始联系人表中插入一个空行，并获取返回的联系人id
		ContentValues values = new ContentValues();
		Uri uri = cr.insert(RawContacts.CONTENT_URI, values);
		long raw_contact_id = ContentUris.parseId(uri);

		// 根据原始联系人的id，在data表中插入姓名信息
		if (!name.equals("") && name != null) {
			values.clear();
			values.put(StructuredName.RAW_CONTACT_ID, raw_contact_id);
			values.put(StructuredName.MIMETYPE,
					StructuredName.CONTENT_ITEM_TYPE);
			values.put(StructuredName.DISPLAY_NAME, name);
			cr.insert(Data.CONTENT_URI, values);
		}
		if (!phone.equals("") && phone != null) {
			values.clear();
			values.put(Phone.RAW_CONTACT_ID, raw_contact_id);
			values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			values.put(Phone.NUMBER, phone);
			values.put(Phone.TYPE, Phone.TYPE_HOME);
			cr.insert(Data.CONTENT_URI, values);
		}

		if (!mobilePhone.equals("") && mobilePhone != null) {
			values.clear();
			values.put(Phone.RAW_CONTACT_ID, raw_contact_id);
			values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			values.put(Phone.NUMBER, mobilePhone);
			values.put(Phone.TYPE, Phone.TYPE_MOBILE);
			cr.insert(Data.CONTENT_URI, values);
		}

		if (!email.equals("") && email != null) {
			values.clear();
			values.put(Email.RAW_CONTACT_ID, raw_contact_id);
			values.put(Email.MIMETYPE, Email.CONTENT_ITEM_TYPE);
			values.put(Email.DATA, email);
			values.put(Email.TYPE, Email.TYPE_HOME);
			cr.insert(Data.CONTENT_URI, values);
		}

		if (!address.equals("") && address != null) {
			values.clear();
			values.put(SipAddress.RAW_CONTACT_ID, raw_contact_id);
			values.put(SipAddress.MIMETYPE, Nickname.CONTENT_ITEM_TYPE);
			values.put(SipAddress.DATA, address);
			values.put(SipAddress.TYPE, SipAddress.TYPE_WORK);
			cr.insert(Data.CONTENT_URI, values);

		}

		if (!nickName.equals("") && nickName != null) {
			values.clear();
			values.put(Nickname.RAW_CONTACT_ID, raw_contact_id);
			values.put(Nickname.MIMETYPE, Nickname.CONTENT_ITEM_TYPE);
			values.put(Nickname.DATA, nickName);
			values.put(Nickname.TYPE, Nickname.NAME);
			cr.insert(Data.CONTENT_URI, values);
		}

		if (!note.equals("") && note != null) {
			values.clear();
			values.put(Note.RAW_CONTACT_ID, raw_contact_id);
			values.put(Note.MIMETYPE, Note.CONTENT_ITEM_TYPE);
			values.put(Note.DATA1, note);
			cr.insert(Data.CONTENT_URI, values);
		}
		return raw_contact_id;
	}

}
