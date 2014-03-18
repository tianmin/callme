package com.diandian.mycall.message;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.diandian.mycall.R;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.util.PhotoUtil;
import com.diandian.mycall.util.T;

public class ChatActivity extends BaseActivity implements OnClickListener {

	public static String tag = "ChatContentAdapter";

	// ==================Data======================//
	private String name;

	private int thread_id;

	private String phone_number;

	private String msg_content;

	private LinkedHashMap<Integer, MessageDetail> allMsgData = new LinkedHashMap<Integer, MessageDetail>();

	// ========================View==========================//
	private ImageView imgHeader;

	private TextView tvName;

	private ListView listChat;

	private ImageButton btnFace;

	private EditText etContent;

	private Button btnSend;

	// =================ContentAdapter=====================//
	private ChatContentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.message_chat);

		initData();

		initViews();

		initListener();
	}

	public void initData() {

		Intent intent = getIntent();

		name = intent.getStringExtra("name");

		thread_id = intent.getIntExtra("thread_id", -1);

		phone_number = intent.getStringExtra("phone");

	}

	public void initViews() {

		// 初始化控件
		imgHeader = (ImageView) findViewById(R.id.chat_img);

		tvName = (TextView) findViewById(R.id.chat_name_text);

		listChat = (ListView) findViewById(R.id.msg_chat_content);

		adapter = new ChatContentAdapter(getApplicationContext(), null,
				R.layout.message_chat_item_right,
				R.layout.message_chat_item_left, name);

		listChat.setAdapter(adapter);

		listChat.setSelection(adapter.getCount() - 1);

		btnFace = (ImageButton) findViewById(R.id.expression_btn);

		etContent = (EditText) findViewById(R.id.et_message_content);

		btnSend = (Button) findViewById(R.id.btn_send);

		// 图片
		Bitmap bm = BitmapFactory.decodeResource(
				ChatActivity.this.getResources(), R.drawable.love1);

		bm = ThumbnailUtils.extractThumbnail(bm, 42, 42);

		imgHeader.setImageBitmap(PhotoUtil.toRoundCorner(bm, 10));

		tvName.setText("正在于 " + name + " 对话");

		getDataByThreadId();
	}

	public void getDataByThreadId() {

		if (thread_id == -1 || thread_id == -2) {
			allMsgData.clear();
		} else {

			allMsgData = TApplication.getInstance().msgOperator
					.getAllMessageByThreadId(thread_id);

			if (!allMsgData.isEmpty()) {

				adapter.onChange(new ArrayList<MessageDetail>(allMsgData
						.values()));

				listChat.setSelection(adapter.getCount() - 1);

				Log.i(tag, "返回的是true");

			} else {

				Log.i(tag, "返回的是false");

			}
		}

	}

	public void initListener() {

		btnFace.setOnClickListener(this);

		btnSend.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_send:

			msg_content = etContent.getText().toString().trim();

			sendSms(phone_number, msg_content);

			getDataByThreadId();

			etContent.setText("");

			T.showLong(ChatActivity.this, "发送成功!");

			break;

		case R.id.expression_btn:

			break;
		}

	}

	public void sendSms(String number, String message) {

		SmsManager sms = SmsManager.getDefault();

		String action = "SENT_SMS_ACTION";
		Intent sendIntent = new Intent(action);

		// 发送的Action
		PendingIntent sendPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, sendIntent, 0);

		// 监控对方是否接收到的Action
		String receive_action = "DELIVERED_SMS_ACTION";

		Intent deliverIntent = new Intent(receive_action);

		PendingIntent deliverPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, deliverIntent, 0);

		if (message.length() > 70) {

			ArrayList<String> msg = sms.divideMessage(message);

			for (String str : msg) {

				sms.sendTextMessage(number, null, str, sendPI, deliverPI);

			}

		} else {

			sms.sendTextMessage(number, null, message, sendPI, deliverPI);

		}

		// 将发送短信插入数据库
		ContentValues values = new ContentValues();

		values.put("date", System.currentTimeMillis());

		values.put("read", 0);

		values.put("type", 2);

		values.put("address", number);

		values.put("body", msg_content);

		getContentResolver().insert(Uri.parse("content://sms"), values);

	}
}
