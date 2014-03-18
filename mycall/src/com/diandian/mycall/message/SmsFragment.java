package com.diandian.mycall.message;

import java.util.ArrayList;

import com.diandian.mycall.R;
import com.diandian.mycall.common.TApplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class SmsFragment extends Fragment {

	ListView listMsg;

	MessageListAdapter adapter;

	View mainView;

	TApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.sms_content, null);

		initData();

		initViews();

		initListener();

		return mainView;
	}

	/**
	 * 初始化界面
	 */
	public void initViews() {

		listMsg = (ListView) mainView.findViewById(R.id.sms_list);

		if (application.msgData.isEmpty()) {

			adapter = new MessageListAdapter(getActivity(), null,
					R.layout.message_item, listMsg);

			new AsyncTask<String, Void, Boolean>() {

				@Override
				protected Boolean doInBackground(String... params) {

					application.msgData = application.msgOperator
							.getAllMessage();

					if (!application.msgData.isEmpty()) {

						return true;
					}

					return false;
				}

				protected void onPostExecute(Boolean result) {
					if (result) {

						adapter.onChange(new ArrayList<MessageBean>(
								application.msgData.values()));
					}
				};

			}.execute("");
		} else {
			adapter = new MessageListAdapter(getActivity(),
					new ArrayList<MessageBean>(application.msgData.values()),
					R.layout.message_item, listMsg);

		}
		listMsg.setAdapter(adapter);
	}

	public void initData() {

		application = TApplication.getInstance();
	}

	/**
	 * 添加监听
	 */
	public void initListener() {

		listMsg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				adapter.doClick(view, position);
			}
		});
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		// 退出,停止工作线程
		adapter.quit();
	}

}
