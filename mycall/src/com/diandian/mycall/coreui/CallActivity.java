package com.diandian.mycall.coreui;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.common.CommonOperator;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.view.SimpleSideDrawer;

/**
 * 打电话的Activity
 * 
 * 
 */
public class CallActivity extends Activity implements OnClickListener {

	// 标题
	// TextView tvBar;

	Vibrator vibrator;
	private Button n1, n2, n3, n4, n5, n6, n7, n8, n9, n0,// 数字按键0~9
			star, shap, plus, // 符号按键
			del, use;// 功能按键

	private TextView textView; // 显示键盘的按键信息

	String a = ""; // 装输入数字和符号的容器

	private SimpleSideDrawer mNav;

	// 桌面
	View destop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phoneboard);

		// 添加标题
		// tvBar = (TextView) findViewById(R.id.title_bar_text);
		// tvBar.setText("键盘");

		// 给按钮增加震动效果
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

		// 获取按钮
		n1 = (Button) findViewById(R.id.button1);
		n2 = (Button) findViewById(R.id.button2);
		n3 = (Button) findViewById(R.id.button3);
		n4 = (Button) findViewById(R.id.button4);
		n5 = (Button) findViewById(R.id.button5);
		n6 = (Button) findViewById(R.id.button6);
		n7 = (Button) findViewById(R.id.button7);
		n8 = (Button) findViewById(R.id.button8);
		n9 = (Button) findViewById(R.id.button9);
		n0 = (Button) findViewById(R.id.button0);
		star = (Button) findViewById(R.id.star);
		shap = (Button) findViewById(R.id.shap);
		plus = (Button) findViewById(R.id.plus);
		del = (Button) findViewById(R.id.del);
		use = (Button) findViewById(R.id.use); // "使用"按钮
		// 获取textView
		textView = (TextView) findViewById(R.id.textView1);
		// 申明点击按钮的lister的响应
		n1.setOnClickListener(this);
		n2.setOnClickListener(this);
		n3.setOnClickListener(this);
		n4.setOnClickListener(this);
		n5.setOnClickListener(this);
		n6.setOnClickListener(this);
		n7.setOnClickListener(this);
		n8.setOnClickListener(this);
		n9.setOnClickListener(this);
		n0.setOnClickListener(this);
		star.setOnClickListener(this);
		shap.setOnClickListener(this);
		plus.setOnClickListener(this);
		del.setOnClickListener(this);

		// 退格键长按清空
		del.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				vibrator.vibrate(11); // 震动时长为0.01秒
				a = "";
				textView.setText("");
				return true;
			}
		});

		mNav = new SimpleSideDrawer(this);

		destop = mNav
				.setRightBehindContentView(R.layout.activity_behind_right_simple);

		addDeskListener();
	}

	private void addDeskListener() {

		// 通话
		Button btnCall = (Button) destop.findViewById(R.id.call_btn);
		btnCall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vibrator.vibrate(11); // 震动时长为0.01秒

				// 通话
				makeCall(a);

			}
		});

		// 发短信
		Button btnMessage = (Button) destop.findViewById(R.id.message_btn);
		btnMessage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vibrator.vibrate(11); // 震动时长为0.01秒
			}
		});

		// 添加联系人
		Button btnContact = (Button) destop.findViewById(R.id.addcontact_btn);
		btnContact.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vibrator.vibrate(11); // 震动时长为0.01秒

			}
		});

		// 返回到拨号界面
		Button btnBack = (Button) destop.findViewById(R.id.back_btn);
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vibrator.vibrate(11); // 震动时长为0.01秒
				CallActivity.this.mNav.closeRightSide();

			}
		});

	}

	/************************ 使用按钮 ：弹出自定义菜单 *****************/
	public void useClick(View v) {
		vibrator.vibrate(11); // 震动时长为0.01秒
		mNav.toggleRightDrawer();
	}

	/****************************************************************/

	public void onClick(View v) {

		// 给textView设置滚动
		// 这里需要自动滚动（待开发）
		textView.setMaxLines(20);
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());

		a = textView.getText().toString().trim();

		switch (v.getId()) {
		case R.id.button1:
			vibrator.vibrate(10);// 震动时长为0.01秒
			a = a + 1;
			textView.setText(a);
			break;
		case R.id.button2:
			vibrator.vibrate(10);
			a = a + 2;
			textView.setText(a);
			break;
		case R.id.button3:
			vibrator.vibrate(10);
			a = a + 3;
			textView.setText(a);
			break;
		case R.id.button4:
			vibrator.vibrate(10);
			a = a + 4;
			textView.setText(a);
			break;
		case R.id.button5:
			vibrator.vibrate(10);
			a = a + 5;
			textView.setText(a);
			break;
		case R.id.button6:
			vibrator.vibrate(10);
			a = a + 6;
			textView.setText(a);
			break;
		case R.id.button7:
			vibrator.vibrate(10);
			a = a + 7;
			textView.setText(a);
			break;
		case R.id.button8:
			vibrator.vibrate(10);
			a = a + 8;
			textView.setText(a);
			break;
		case R.id.button9:
			vibrator.vibrate(10);
			a = a + 9;
			textView.setText(a);
			break;
		case R.id.button0:
			vibrator.vibrate(10);
			a = a + 0;
			textView.setText(a);
			break;
		case R.id.star:
			vibrator.vibrate(10);
			a = a + "*";
			textView.setText(a);
			break;
		case R.id.shap:
			vibrator.vibrate(10);
			a = a + "#";
			textView.setText(a);
			break;
		case R.id.plus:
			vibrator.vibrate(10);
			a = a + "+";
			textView.setText(a);
			break;
		case R.id.del:// 短按键-退格
			vibrator.vibrate(11);
			if (a.length() - 1 >= 0) {
				a = a.substring(0, a.length() - 1);
				textView.setText(a);
			} else {
				warning();// 提醒
			}
			break;

		}
	}

	public void warning() {
		Toast.makeText(this, "请输入！", Toast.LENGTH_LONG).show();
	}

	public void makeCall(String number) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		CallActivity.this.startActivity(intent);
	}
}
