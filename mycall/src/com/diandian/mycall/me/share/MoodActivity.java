package com.diandian.mycall.me.share;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.coreui.HomeActivity;

public class MoodActivity extends BaseActivity {

	Button btnOk;

	String mode; // 所有的心情标签
	EditText content; // 输入心情类容
	TextView tvCount; // 记录字数
	TextView tvMode; // 心情标签
	String contentInfo;
	int now;

	String name;
	String number;

	String feelStr;
	String feel_words_Str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.me_mood_layout);

		initData();

		initViews();

		initListener();

	}

	public void initListener() {

		content.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				contentInfo = content.getText().toString();
				int wordNum = 30;
				int usedword = contentInfo.length();
				now = wordNum - usedword;// 目前剩下的字数
				tvCount.setText("还有" + now + "字");

				// 提示字数已满
				if (now == 0) {
					Toast.makeText(MoodActivity.this, "您输入的字数已满",
							Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MoodActivity.this,
						HomeActivity.class);

				startActivity(intent);

				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);

				MoodActivity.this.finish();

			}
		});

	}

	public void initViews() {

		btnOk = (Button) findViewById(R.id.mood_btn_ok);

		// 让键盘自动弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		// 获取类容
		content = (EditText) findViewById(R.id.editText1);
		tvMode = (TextView) findViewById(R.id.textView1);
		tvCount = (TextView) findViewById(R.id.textView4);

		// 接收MeMainActivity传来的数据
		// Intent intent = getIntent();
		// name = intent.getStringExtra("name");
		// number = intent.getStringExtra("phone");
		// feelStr = intent.getStringExtra("feelStr");
		// feel_words_Str = intent.getStringExtra("feel_words_Str");

		// 同步心情标签和心情描述
		// tvMode.setText(feelStr);
		// content.setText(feel_words_Str);
		// // 将光标移到文字末尾
		// content.setSelection(feel_words_Str.length());
		//
		// // 记录字数
		// tvCount.setText("还有" + (30 - feel_words_Str.length()) + "字");

	}

	public void initData() {

	}

	// 心情标签按钮
	public void modeClick(View v) {

		switch (v.getId()) { // 通过按钮的位置来判断哪个按钮被点击了
		case R.id.scro_btn1:
			tvMode.setText(" 我的心情标签 ：喜悦");
			break;

		case R.id.scro_btn2:
			tvMode.setText(" 我的心情标签 ：生气");
			break;

		case R.id.scro_btn3:
			tvMode.setText(" 我的心情标签 ：伤感");
			break;

		case R.id.scro_btn4:
			tvMode.setText(" 我的心情标签 ：平静");
			break;

		case R.id.scro_btn5:
			tvMode.setText(" 我的心情标签 ：孤单");
			break;

		case R.id.scro_btn6:
			tvMode.setText(" 我的心情标签 ：忧郁");
			break;

		case R.id.scro_btn7:
			tvMode.setText(" 我的心情标签 ：思念");
			break;

		case R.id.scro_btn8:
			tvMode.setText(" 我的心情标签 ：感动");
			break;

		case R.id.scro_btn9:
			tvMode.setText(" 我的心情标签 ：害怕");
			break;

		case R.id.scro_btn10:
			tvMode.setText(" 我的心情标签 ：幸福");
			break;

		case R.id.scro_btn11:
			tvMode.setText(" 我的心情标签 ：惊讶");
			break;

		case R.id.scro_btn12:
			tvMode.setText(" 我的心情标签 ：积极");
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Intent intent = new Intent(MoodActivity.this, HomeActivity.class);

		startActivity(intent);

		MoodActivity.this.finish();

		return super.onKeyDown(keyCode, event);
	}
}
