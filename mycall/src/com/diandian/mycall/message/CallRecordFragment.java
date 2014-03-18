package com.diandian.mycall.message;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.diandian.mycall.R;
import com.diandian.mycall.common.TApplication;

public class CallRecordFragment extends Fragment {

	// 显示所有的电话数据
	ListView listCall;

	// 电话记录的Adapter
	CallLogListAdapter adapter;

	// 显示短信数据的View
	View smsView;

	// Application
	TApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		smsView = inflater.inflate(R.layout.callrecord, null);

		initData();

		initViews();

		initListener();

		return smsView;
	}

	public void initData() {

		application = TApplication.getInstance();

	}

	public void initViews() {

		// View listCall
		listCall = (ListView) smsView.findViewById(R.id.callrecord_list);

		// 创建CallLogListAdatper
		adapter = new CallLogListAdapter(getActivity(), null,
				R.layout.call_message_item);

		if (application.callData.isEmpty()) {

			// 开始异步操作 获取手机中所有的短信记录的数据
			new AsyncTask<String, Void, Boolean>() {

				@Override
				protected Boolean doInBackground(String... params) {
					// 得到所有的电话记录数据 初始化到 application 对象当中
					application.callData = application.msgOperator
							.getAllCallData();

					if (!application.callData.isEmpty()) {

						return true;

					}
					return false;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					if (result) {

						adapter.onChange(new ArrayList<CallLogBean>(
								application.callData.values()));

					}
				}

			}.execute("");

			// 如果数据非空
		} else {

			// adapter = new CallLogListAdapter(getActivity(),
			// new ArrayList<CallLogBean>(),
			// R.layout.call_message_item);

			// OnChange
			adapter.onChange(new ArrayList<CallLogBean>(application.callData
					.values()));

		}

		listCall.setAdapter(adapter);
	}

	/**
	 * 添加监听
	 */
	public void initListener() {

		listCall.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				adapter.doClick(view, position);

			}
		});
	}
}
