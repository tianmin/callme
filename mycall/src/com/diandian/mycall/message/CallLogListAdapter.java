package com.diandian.mycall.message;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.common.CommonOperator;
import com.diandian.mycall.common.Constant;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.util.DateUtils;
import com.diandian.mycall.util.PhotoUtil;
import com.diandian.mycall.util.T;
import com.diandian.mycall.view.ActionItem;
import com.diandian.mycall.view.QuickAction;

public class CallLogListAdapter extends BaseAdapter {

	private Context context;

	private List<CallLogBean> listSource;

	private int resId;

	private LayoutInflater inflater;

	private TApplication application;

	// 选择的行
	private CallLogBean callBean;

	// 总的活动区
	QuickAction mQuickAction = null;

	// CALL
	private static final int ID_CALL = 1;
	// SMS
	private static final int ID_SMS = 2;
	// CONTACT
	private static final int ID_CONTACT = 3;
	// DELETE
	private static final int ID_DELETE = 4;

	// Dialog
	AlertDialog alertDialog;

	public CallLogListAdapter(Context context, List<CallLogBean> source,
			int resId) {

		this.context = context;

		this.application = TApplication.getInstance();

		inflater = LayoutInflater.from(context);

		this.resId = resId;

		if (source != null) {

			this.listSource = source;

		} else {

			listSource = new ArrayList<CallLogBean>();

		}

		// ========================初始化界面=============================//

		// 添加三个活动项
		ActionItem calldItem = new ActionItem(ID_CALL, "通话",
				CallLogListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_add));

		ActionItem messageItem = new ActionItem(ID_SMS, "短信",
				CallLogListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_accept));

		ActionItem loopItem = new ActionItem(ID_CONTACT, "联系人",
				CallLogListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		ActionItem exitItem = new ActionItem(ID_DELETE, "删除",
				CallLogListAdapter.this.context.getResources().getDrawable(
						R.drawable.ic_up));

		// 核心的Action
		mQuickAction = new QuickAction(CallLogListAdapter.this.context);
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

						switch (actionId) {
						case ID_CALL:

							CommonOperator.makeCall(
									CallLogListAdapter.this.context,
									callBean.getCallLogNumber());

							break;

						case ID_SMS:

							int thread_id = application.msgOperator
									.getThreadIdbyPhoneNumber(callBean
											.getCallLogNumber());

							String name = "";

							String number = callBean.getCallLogNumber();

							if (callBean.getCallLogName() == null) {

								name = callBean.getCallLogNumber();

							} else {

								name = callBean.getCallLogName();

							}

							// 发送短信操作
							CommonOperator.sendSms(
									CallLogListAdapter.this.context, thread_id,
									name, number);
							break;

						// 已有联系人应该显示姓名
						case ID_CONTACT:

							if (callBean.getCallLogName() != null) {

								Toast.makeText(CallLogListAdapter.this.context,
										"已经是联系人啦!", Toast.LENGTH_LONG).show();

							} else {

								CommonOperator.saveNumber(
										CallLogListAdapter.this.context,
										callBean.getCallLogNumber());
							}

							break;

						case ID_DELETE:

							alertDialog = new AlertDialog.Builder(
									CallLogListAdapter.this.context)

							.setTitle("确认要删除吗?")

							.setPositiveButton("取消", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})

							.setNegativeButton("确认", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// 删除数据
									listSource.remove(callBean);

									CallLogListAdapter.this
											.notifyDataSetChanged();

									T.show(CallLogListAdapter.this.context,
											"删除成功!", 5000);

									alertDialog.dismiss();

									new AsyncTask<String, Void, Boolean>() {
										@Override
										protected Boolean doInBackground(
												String... params) {

											TApplication.getInstance().msgOperator
													.deleteCallLogByNumber(callBean
															.getCallLogNumber());

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

		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = inflater.inflate(resId, null);

			holder.call_imag = (ImageView) convertView
					.findViewById(R.id.call_header_image);

			holder.tv_name = (TextView) convertView
					.findViewById(R.id.call_number);

			holder.tv_date = (TextView) convertView
					.findViewById(R.id.call_message_date);

			holder.tv_content = (TextView) convertView
					.findViewById(R.id.call_item_content);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		CallLogBean callBean = listSource.get(position);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_nav_1_normal);

		bm = ThumbnailUtils.extractThumbnail(bm, 96, 96);

		holder.call_imag.setImageBitmap(PhotoUtil.toRoundCorner(bm, 15));

		if (callBean.getCallLogName() == null) {

			holder.tv_name.setText(callBean.getCallLogNumber() + "");

		} else {

			holder.tv_name.setText(callBean.getCallLogName() + "");

		}

		holder.tv_date.setText(DateUtils.getCurrentDate(callBean
				.getCallLogDate()));

		if (callBean.getCallLogType() == Constant.Message.CALL_IN) {

			holder.tv_content.setText("呼入");

		} else if (callBean.getCallLogType() == Constant.Message.CALL_OUT) {

			holder.tv_content.setText("呼出");

		} else {

			// holder.tv_content.setBackgroundColor(Color.RED);
			holder.tv_content.setText("未接");

		}

		return convertView;
	}

	class ViewHolder {

		public ImageView call_imag;

		public TextView tv_name;

		public TextView tv_date;

		public TextView tv_content;
	}

	public void onChange(List<CallLogBean> callListSource) {

		this.listSource = callListSource;
		notifyDataSetChanged();

	}

	public void doClick(View v, int position) {

		callBean = listSource.get(position);
		mQuickAction.show(v);
	}
}
