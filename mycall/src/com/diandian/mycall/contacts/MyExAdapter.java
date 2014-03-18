package com.diandian.mycall.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.common.CommonOperator;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.view.ActionItem;
import com.diandian.mycall.view.QuickAction;

/**
 * Adapter
 * 
 * @author lenovo
 * 
 */
public class MyExAdapter extends BaseExpandableListAdapter {

	private Context context;

	// 组数据(大的)
	private List<GroupEntity> source;

	// 各组数据(分发数据)
	private HashMap<Integer, List<ContactEntity>> contacts;

	// 被点击 的view
	View view = null;

	// 总的活动区
	QuickAction mQuickAction = null;

	// 被点击孩子的下标
	int positon = 0;

	// 点击时的数据
	ContactEntity contactEntity;

	// 应用
	TApplication application;

	private int record = 0;

	// CALL
	private static final int ID_CALL = 1;
	// SMS
	private static final int ID_SMS = 2;
	// LOOK
	private static final int ID_DELETE = 3;
	// EXIT
	private static final int ID_MANAGER = 4;

	public MyExAdapter(Context applicationContext,
			List<GroupEntity> listSource,
			HashMap<Integer, List<ContactEntity>> listContacts) {

		this.context = applicationContext;

		if (listSource == null) {

			this.source = new ArrayList<GroupEntity>();

		} else {

			this.source = listSource;

		}

		if (listContacts == null) {

			this.contacts = new HashMap<Integer, List<ContactEntity>>();

		} else {

			this.contacts = listContacts;
		}

		application = TApplication.getInstance();

		// ========================初始化界面=============================//

		// 添加三个活动项

		ActionItem calldItem = new ActionItem(ID_CALL, "通话",
				MyExAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_add));

		ActionItem messageItem = new ActionItem(ID_SMS, "短信",
				MyExAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_accept));

		ActionItem loopItem = new ActionItem(ID_DELETE, "删除",
				MyExAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		ActionItem exitItem = new ActionItem(ID_MANAGER, "管理",
				MyExAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		// 核心的Action
		mQuickAction = new QuickAction(MyExAdapter.this.context);
		// 添加ActonItem
		mQuickAction.addActionItem(calldItem);
		mQuickAction.addActionItem(messageItem);
		mQuickAction.addActionItem(loopItem);
		mQuickAction.addActionItem(exitItem);

		// =============================================================//

		// ============================AddListener===============================//
		// setup the action item click listener 设置动作项点击监听器
		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						// ActionItem actionItem =
						// quickAction.getActionItem(pos);

						switch (actionId) {
						// CALL
						case ID_CALL:

							// contactEntity

							CommonOperator.makeCall(MyExAdapter.this.context,
									contactEntity.getTelNumber());

							break;

						// SMS
						case ID_SMS:

							int thread_id = application.msgOperator
									.getThreadIdbyPhoneNumber(contactEntity
											.getTelNumber());

							String name = "";

							if (contactEntity.getContactName() != null
									&& TextUtils.isEmpty(contactEntity
											.getContactName())) {

								name = contactEntity.getContactName();
							}

							CommonOperator.sendSms(MyExAdapter.this.context,
									thread_id, name,
									contactEntity.getTelNumber());

							break;

						// DELETE
						case ID_DELETE:

							// 删除联系人

							AlertDialog.Builder deleteDialog = new AlertDialog.Builder(
									MyExAdapter.this.context);

							if (contactEntity.getContactName() != null) {

								deleteDialog.setTitle("确认删除"
										+ contactEntity.getContactName() + "?");

							} else {

								deleteDialog.setTitle("确认删除"
										+ contactEntity.getContactName() + "?");

							}

							deleteDialog.setNegativeButton("确认",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 进行删除操作

											application.contactOperator
													.delContact(
															MyExAdapter.this.context,
															contactEntity
																	.getContactId());

											// 利用Toast 提醒用户操作成功!

											Toast.makeText(
													MyExAdapter.this.context,
													"删除成功!", Toast.LENGTH_SHORT)
													.show();
											// 记录数据改变
											record = 1;

											MyExAdapter.this
													.notifyDataSetChanged();

										}
									});

							deleteDialog.setPositiveButton("取消",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									});

							AlertDialog alertDialog = deleteDialog.create();

							alertDialog.show();

							break;

						case ID_MANAGER:

							// 进入联系人管理的界面

							break;

						}
					}
				});

		// 对话框杯干掉的时候
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {

			}
		});

	}

	// 得到大组成员的view
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(context);

			convertView = inflater.inflate(R.layout.contact_listview_title,
					null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView
					.findViewById(R.id.content_001);

			holder.imgBd = (ImageView) convertView.findViewById(R.id.tubiao);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(getGroup(groupPosition).toString());// 设置大组成员名称

		if (isExpanded) {// 大组展开时的箭图标

			holder.imgBd.setBackgroundResource(R.drawable.group_unfold_arrow);

		} else {

			// 大组合并时的箭头图标
			holder.imgBd.setBackgroundResource(R.drawable.group_fold_arrow);
		}

		return convertView;
	}

	class ViewHolder {

		private TextView tvName;

		private ImageView imgBd;

	}

	// 得到大组成员的id
	public long getGroupId(int groupPosition) {

		return groupPosition;

	}

	// 得到大组成员名称
	public Object getGroup(int groupPosition) {

		return source.get(groupPosition).getGroupName();

	}

	// 得到大组成员总数
	public int getGroupCount() {

		return source.size();
	}

	// 得到小组成员的view
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(context);

			convertView = inflater
					.inflate(R.layout.contact_listview_item, null);

			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.name_item);// 显示用户名

			holder.imgBd = (ImageView) convertView
					.findViewById(R.id.imageView_item);// 显示用户头像，其实还可以判断是否在线，选择黑白和彩色头像，我这里未处理。

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		// 此时的id值
		final String name = contacts
				.get(source.get(groupPosition).getGroupId()).get(childPosition)
				.getContactName();

		holder.tvName.setText(name);// 大标题

		holder.imgBd.setImageResource(R.drawable.ic_nav_1_normal);

		return convertView;
	}

	// 得到小组成员id
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// 得到小组成员的名称
	public Object getChild(int groupPosition, int childPosition) {
		return contacts.get(source.get(groupPosition).getGroupId())
				.get(childPosition).getContactName();
	}

	// 得到小组成员的数量
	public int getChildrenCount(int groupPosition) {

		int groupId = source.get(groupPosition).getGroupId();

		List<ContactEntity> list = new ArrayList<ContactEntity>();

		// 没有发生刷新操作
		if (record == 0) {

			if (this.contacts.get(groupId) == null) {

				if (groupId == -1) {

					list = application.contactOperator.getAllContacts();

				} else {

					list = application.contactOperator
							.getAllContactsByGroupId(groupId);
				}

				this.contacts.put(groupId, list);
			}

		} else {

			if (groupId == -1) {

				list = application.contactOperator.getAllContacts();

			} else {

				list = application.contactOperator
						.getAllContactsByGroupId(groupId);
			}

			this.contacts.put(groupId, list);

		}

		return contacts.get(groupId).size();
	}

	/**
	 * Indicates whether the child and group IDs are stable across changes to
	 * the underlying data. 表明大組和小组id是否稳定的更改底层数据。
	 * 
	 * @return whether or not the same ID always refers to the same object
	 * @see Adapter#hasStableIds()
	 */
	public boolean hasStableIds() {

		return true;
	}

	// 得到小组成员是否被选择
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}

	/**
	 * 这个方法是我自定义的，用于下拉刷新好友的方法
	 * 
	 * @param group
	 *            传递进来的新数据
	 */
	public void updata(List<GroupEntity> groups,
			HashMap<Integer, List<ContactEntity>> contacts) {

		this.source = groups;

		this.contacts = contacts;

		this.notifyDataSetChanged();
	}

	/**
	 * @param v
	 * @param groupPosition
	 * @param childPosition
	 */
	public void doClick(View v, int groupPosition, int childPosition) {

		contactEntity = contacts.get(source.get(groupPosition).getGroupId())
				.get(childPosition);

		mQuickAction.show(v);
	}

}
