package com.diandian.mycall.message;

import java.util.ArrayList;
import java.util.List;

import com.diandian.mycall.R;
import com.diandian.mycall.common.Constant;
import com.diandian.mycall.util.DateUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 消息内容的Adapter type == 1 接收的到的数据 type == 2 发出去的数据
 * 
 * @author lenovo
 * 
 */
public class ChatContentAdapter extends BaseAdapter {

	private String send_username;

	private Context context;

	private List<MessageDetail> list_source;

	private int right_res;

	private int left_res;

	private LayoutInflater inflater;

	public ChatContentAdapter(Context context, List<MessageDetail> source,
			int right_res, int left_res, String send_username) {

		this.context = context;

		this.inflater = LayoutInflater.from(context);

		this.right_res = right_res;

		this.left_res = left_res;

		if (source == null) {

			this.list_source = new ArrayList<MessageDetail>();

		} else {

			this.list_source = source;
		}

		this.send_username = send_username;
	}

	public void onChange(List<MessageDetail> list_source) {
		this.list_source = list_source;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list_source.size();
	}

	@Override
	public Object getItem(int position) {
		return list_source.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list_source.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MessageDetail msgDetail = this.list_source.get(position);

		ViewHolder holder = null;

		if (convertView == null) {

			if (msgDetail.getSmsDetailType() == Constant.MsgType.RECEIVE_MSG) {
				convertView = inflater.inflate(left_res, null);
			} else {
				convertView = inflater.inflate(right_res, null);
			}

			holder = new ViewHolder();

			holder.tv_senddate = (TextView) convertView
					.findViewById(R.id.tv_sendtime);

			holder.iv_headimg = (ImageView) convertView
					.findViewById(R.id.iv_userhead);

			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_username);

			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_senddate.setText(DateUtils.friendlyTime((int) msgDetail
				.getSmsDetailDate()));

		if (msgDetail.getSmsDetailType() == Constant.MsgType.RECEIVE_MSG) {

			holder.tv_name.setText(send_username);

		} else {

			holder.tv_name.setText("小敏子!");
		}

		holder.tv_content.setText(msgDetail.getSmsDetailBody());

		return convertView;
	}

	class ViewHolder {

		public TextView tv_senddate;

		public ImageView iv_headimg;

		public TextView tv_content;

		public TextView tv_name;
	}
}
