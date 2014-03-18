package com.diandian.mycall.message;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.common.CommonOperator;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.util.DateUtils;
import com.diandian.mycall.util.PhotoUtil;
import com.diandian.mycall.util.T;
import com.diandian.mycall.view.ActionItem;
import com.diandian.mycall.view.QuickAction;

/**
 * 短消息数据的Adapter
 * 
 * @author lenovo
 */
public class MessageListAdapter extends BaseAdapter {

	private Context context;

	private List<MessageBean> listSource;

	private int resId;

	private LayoutInflater inflater;

	// 总的活动区
	QuickAction mQuickAction = null;

	// CALL
	private static final int ID_CHECK = 1;
	// SMS
	private static final int ID_CALL = 2;
	// CONTACT
	private static final int ID_MANAGER = 3;
	// DELETE
	private static final int ID_DELETE = 4;

	MessageBean msgBean;

	// 异步加载短信的名称
	AsyncLoadContactName loadContactName;

	private TApplication application;

	// 删除Dialog
	AlertDialog alertDialog;

	public MessageListAdapter(Context context, List<MessageBean> source,
			int resId, final ListView lvMsg) {

		this.context = context;

		this.application = TApplication.getInstance();

		this.inflater = LayoutInflater.from(context);

		this.resId = resId;

		if (source != null) {

			this.listSource = source;

		} else {

			listSource = new ArrayList<MessageBean>();

		}

		// =========================Load===============================//

		loadContactName = new AsyncLoadContactName(new Callback() {

			@Override
			public void loadContactName(String number, String contactName) {

				TextView tv = (TextView) lvMsg.findViewWithTag(number);

				if (tv != null && contactName != null) {
					tv.setText(contactName);
				}

			}
		});

		// ========================初始化界面=============================//

		// 添加四个活动项

		ActionItem calldItem = new ActionItem(ID_CHECK, "回短信",
				MessageListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_add));

		ActionItem messageItem = new ActionItem(ID_CALL, "电话",
				MessageListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_accept));

		ActionItem loopItem = new ActionItem(ID_MANAGER, "联系人",
				MessageListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		ActionItem exitItem = new ActionItem(ID_DELETE, "删除",
				MessageListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		// 核心的Action
		mQuickAction = new QuickAction(MessageListAdapter.this.context);
		// 添加ActonItem
		mQuickAction.addActionItem(calldItem);
		mQuickAction.addActionItem(messageItem);
		mQuickAction.addActionItem(loopItem);
		mQuickAction.addActionItem(exitItem);

		// ============================AddListener===============================//
		// setup the action item click listener 设置动作项点击监听器
		mQuickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {

					String name = "";

					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {

						// ActionItem actionItem =
						// quickAction.getActionItem(pos);
						String contactName = application.msgOperator
								.getContactNameByPhoneNumber(
										MessageListAdapter.this.context,
										msgBean.getSmsAdress());

						switch (actionId) {

						// 聊天
						case ID_CHECK:

							if (contactName == null) {

								name = msgBean.getSmsAdress();

							} else {

								name = contactName;
							}

							CommonOperator.sendSms(
									MessageListAdapter.this.context,
									msgBean.getThreadId(), name,
									msgBean.getSmsAdress());
							break;

						// 通话
						case ID_CALL:

							CommonOperator.makeCall(
									MessageListAdapter.this.context,
									msgBean.getSmsAdress());
							break;

						// 管理 (需要变成联系人)
						case ID_MANAGER:
							// 跳转到其他的Activity

							if (contactName == null || contactName.equals("")) {

								CommonOperator.saveNumber(
										MessageListAdapter.this.context,
										msgBean.getSmsAdress());
							} else {

								Toast.makeText(MessageListAdapter.this.context,
										"联系人已经存在!", Toast.LENGTH_LONG).show();

							}

							break;

						// 删除这一对话
						case ID_DELETE:

							mQuickAction.dismiss();

							alertDialog = new Builder(
									MessageListAdapter.this.context).

							setTitle("确认删除吗?")

							.setPositiveButton("取消", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})

							.setNegativeButton("确定", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									MessageListAdapter.this.listSource
											.remove(msgBean);

									MessageListAdapter.this
											.notifyDataSetChanged();

									T.show(MessageListAdapter.this.context,
											"删除成功!", 5000);

									alertDialog.dismiss();

									new AsyncTask<String, Void, Boolean>() {

										@Override
										protected Boolean doInBackground(
												String... params) {

											// 进行删除
											application.msgOperator
													.DeleteMsgByThreadId(msgBean
															.getThreadId());

											application.msgOperator
													.DeleteMsgbyId(msgBean
															.getSmsId());

											Log.i("tianmin", "代码执行啦 thread_id");

											return true;

										}

									}.execute("");

								}
							})

							.create();

							alertDialog.show();

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

	public void onChange(List<MessageBean> source) {

		this.listSource = source;

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return listSource.size();
	}

	@Override
	public Object getItem(int position) {

		return listSource.get(position);
	}

	@Override
	public long getItemId(int position) {

		return listSource.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			convertView = inflater.inflate(resId, null);
			holder = new ViewHolder();

			holder.header_img = (ImageView) convertView
					.findViewById(R.id.message_header_image);

			holder.tv_name = (TextView) convertView
					.findViewById(R.id.message_title);

			holder.tv_content = (TextView) convertView
					.findViewById(R.id.message_item_content);

			holder.is_read = (TextView) convertView
					.findViewById(R.id.message_isread);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}
		// 获取实体数据
		MessageBean bean = listSource.get(position);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_nav_1_normal);

		bm = ThumbnailUtils.extractThumbnail(bm, 96, 96);

		holder.header_img.setImageBitmap(PhotoUtil.toRoundCorner(bm, 15));

		holder.tv_name.setTag(bean.getSmsAdress());

		String contactName = loadContactName.loadContactName(bean
				.getSmsAdress());

		if (contactName != null && !contactName.equals("")) {

			holder.tv_name.setText(contactName);

		} else {

			holder.tv_name.setText(bean.getSmsAdress());

		}

		holder.tv_content.setText(bean.getSmsBody());

		// 已读唯独没有做
		// 时间需要在次处理

		if (bean.getRead() == 0) {

			holder.is_read.setText("未读" + "|"
					+ DateUtils.getCurrentDate(bean.getSmsDate()));

		} else {

			holder.is_read.setText("已读" + "|"
					+ DateUtils.getCurrentDate(bean.getSmsDate()));

		}

		return convertView;
	}

	class ViewHolder {

		public ImageView header_img;
		public TextView tv_name;
		public TextView is_read;
		public TextView tv_content;
		public TextView tv_date;

	}

	public void doClick(View v, int position) {

		msgBean = listSource.get(position);

		mQuickAction.show(v);

	}

	public void quit() {

		loadContactName.isLoop = false;

	}

}
