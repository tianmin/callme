package com.diandian.mycall.contacts;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.mycall.R;
import com.diandian.mycall.common.Constant;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.coreui.HomeActivity;
import com.diandian.mycall.util.PhotoUtil;
import com.diandian.mycall.util.T;
import com.diandian.mycall.view.BootstrapButton;
import com.diandian.mycall.view.BootstrapEditText;
import com.diandian.mycall.view.MyDialog;

public class AddContactsActivity extends BaseActivity implements
		OnClickListener {

	// 头像
	ImageView headImg;

	// 三个编辑区
	BootstrapEditText etUsername, etPhoneNumber, etNote;

	// 三个操作按钮
	BootstrapButton btnAll, btnExit, btnSave;

	// 组选择控件
	Spinner spinner;

	// 全局的应用
	TApplication application = TApplication.getInstance();

	// 所有的组数据
	List<GroupEntity> groupInfo;

	// 所有的组信息
	List<String> groups = new ArrayList<String>();

	// 选中的数据
	String chooseData = "";

	// 传送过来的电话号码
	String phoneNumber;

	// 可能被添加控件的数据
	BootstrapEditText et_nickname, et_phone, et_mail, et_address, et_company,
			et_brithday;

	// 滚动内容
	LinearLayout contentView;

	// 滚动区域
	ScrollView scorllView;

	// 监听控件是否进行过添加
	private boolean nick_used, photo_used, mail_used, address_used,
			company_used, brithday_used;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_contacts);

		initData();

		initViews();

		initListener();

	}

	public void initData() {

		// 获取电话号码 启动AddActivity 有两种方式,一种自然启动,一种是另一模块中启动
		Intent intent = getIntent();

		phoneNumber = intent.getStringExtra("phone");

		// 获取所有的组数据
		groups.add("全部联系人");

		groupInfo = application.contactOperator.getAllGroupInfo();

		for (GroupEntity groupEntity : groupInfo) {

			groups.add(groupEntity.getGroupName());

		}

	}

	@SuppressLint("NewApi")
	public void initViews() {

		headImg = (ImageView) findViewById(R.id.head_img);

		headImg.setImageBitmap(PhotoUtil.toRoundCorner(BitmapFactory
				.decodeResource(this.getResources(), R.drawable.love1), 30));

		etUsername = (BootstrapEditText) findViewById(R.id.et_username);
		etPhoneNumber = (BootstrapEditText) findViewById(R.id.et_phone_number);

		// 初始化 etNumber中的数据
		if (phoneNumber != null && !phoneNumber.equals("")) {

			etPhoneNumber.setText(phoneNumber);

		}

		etNote = (BootstrapEditText) findViewById(R.id.et_remark);
		btnAll = (BootstrapButton) findViewById(R.id.more_info);
		btnExit = (BootstrapButton) findViewById(R.id.contact_exit);
		btnSave = (BootstrapButton) findViewById(R.id.contact_save);
		spinner = (Spinner) findViewById(R.id.choose_group);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, groups);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(0);

		// 滚动内容
		contentView = (LinearLayout) findViewById(R.id.contacts_content_group);

		scorllView = (ScrollView) findViewById(R.id.scroll_view);

	}

	public void initListener() {

		etUsername.setOnClickListener(this);
		
		etPhoneNumber.setOnClickListener(this);
		
		btnAll.setOnClickListener(this);
		
		btnExit.setOnClickListener(this);
		
		btnSave.setOnClickListener(this);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				chooseData = groups.get(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		// 更多
		case R.id.more_info:

			LayoutInflater inflater = LayoutInflater
					.from(AddContactsActivity.this);
			View view = inflater.inflate(R.layout.add_more_contacts_info, null);
			final MyDialog dialog = new MyDialog(AddContactsActivity.this, 0,
					0, view, R.style.dialog);
			ListView listItem = (ListView) view.findViewById(R.id.list_item);
			String args[] = { "咪称", "固定电话 ", "邮箱地址", "住址 ", "公司", " 生日 " };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					args);
			listItem.setAdapter(adapter);
			Window window = dialog.getWindow();
			// 设置Dialog的位置
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.x = 10;
			lp.y = 80;
			dialog.show();
			// 设置Dialog 的大小
			window.setGravity(1);
			window.setLayout(300, 450);
			listItem.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (position) {
					// nickname
					case 0:
						if (!nick_used) {

							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("昵称");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_nickname = new BootstrapEditText(
									AddContactsActivity.this);
							et_nickname.setId(100001);
							et_nickname.roundedCorners = true;
							et_nickname.setState("success");
							et_nickname.setGravity(Gravity.CENTER_VERTICAL);
							et_nickname.setHint(getResources().getString(
									R.string.input_contact_nickname));
							LinearLayout.LayoutParams params_nickname = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_nickname.setLayoutParams(params_nickname);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_nickname);
							contentView.addView(linearLayout1);

							// ------------------------------Setting------------------------------------//
							nick_used = true;
							dialog.dismiss();
						} else {
							dialog.dismiss();
						}

						break;
					// phone
					case 1:
						if (!photo_used) {

							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("电话");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_phone = new BootstrapEditText(
									AddContactsActivity.this);
							et_phone.setId(100002);
							et_phone.roundedCorners = true;
							et_phone.setState("success");
							et_phone.setGravity(Gravity.CENTER_VERTICAL);
							et_phone.setHint(getResources().getString(
									R.string.input_contact_phone));
							LinearLayout.LayoutParams params_mail = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_phone.setLayoutParams(params_mail);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_phone);
							contentView.addView(linearLayout1);

							// ------------------------------Setting------------------------------------//
							photo_used = true;
							dialog.dismiss();

						} else {
							dialog.dismiss();
						}

						break;
					// mail
					case 2:
						if (!mail_used) {

							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("邮箱");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_mail = new BootstrapEditText(
									AddContactsActivity.this);
							et_mail.setId(100003);
							et_mail.roundedCorners = true;
							et_mail.setState("success");
							et_mail.setGravity(Gravity.CENTER_VERTICAL);
							et_mail.setHint(getResources().getString(
									R.string.input_contact_email));
							LinearLayout.LayoutParams params_mail = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_mail.setLayoutParams(params_mail);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_mail);
							contentView.addView(linearLayout1);

							// ========================Setting=====================//
							mail_used = true;
							dialog.dismiss();
						} else {
							dialog.dismiss();
						}

						break;

					// address
					case 3:

						if (!address_used) {
							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("住址");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_address = new BootstrapEditText(
									AddContactsActivity.this);
							et_address.setId(100003);
							et_address.roundedCorners = true;
							et_address.setState("success");
							et_address.setGravity(Gravity.CENTER_VERTICAL);
							et_address.setHint(getResources().getString(
									R.string.input_contact_address));
							LinearLayout.LayoutParams params_address = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_address.setLayoutParams(params_address);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_address);
							contentView.addView(linearLayout1);

							// ========================Setting=====================//
							address_used = true;
							dialog.dismiss();
						} else {
							dialog.dismiss();
						}

						break;

					// company
					case 4:

						if (!company_used) {
							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("公司");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_company = new BootstrapEditText(
									AddContactsActivity.this);
							et_company.setId(100003);
							et_company.roundedCorners = true;
							et_company.setState("success");
							et_company.setGravity(Gravity.CENTER_VERTICAL);
							et_company.setHint(getResources().getString(
									R.string.input_contact_company));
							LinearLayout.LayoutParams params_company = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_company.setLayoutParams(params_company);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_company);
							contentView.addView(linearLayout1);

							// ========================Setting=======//
							company_used = true;
							dialog.dismiss();

						} else {
							dialog.dismiss();
						}

						break;

					// brithday
					case 5:
						if (!brithday_used) {

							// ---------------------------------------------------------------------//
							LinearLayout linearLayout1 = new LinearLayout(
									AddContactsActivity.this);
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
									dip2px(AddContactsActivity.this, 270),
									dip2px(AddContactsActivity.this, 50));
							params1.topMargin = dip2px(
									AddContactsActivity.this, 15);
							linearLayout1
									.setOrientation(LinearLayout.HORIZONTAL);
							linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
							linearLayout1.setLayoutParams(params1);

							// ---------------------------TextView-----------------------------------//

							TextView tv1 = new TextView(
									AddContactsActivity.this);
							LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.MATCH_PARENT);
							tvLayout.rightMargin = dip2px(
									AddContactsActivity.this, 10);
							tv1.setTextSize(20);
							tv1.setText("生日");
							tv1.setGravity(Gravity.CENTER_VERTICAL);
							tv1.setLayoutParams(tvLayout);

							// ----------------------------EditText------------------------------------//

							et_brithday = new BootstrapEditText(
									AddContactsActivity.this);
							et_brithday.setId(100003);
							et_brithday.roundedCorners = true;
							et_brithday.setState("success");
							et_brithday.setGravity(Gravity.CENTER_VERTICAL);
							et_brithday.setHint(getResources().getString(
									R.string.input_contact_brithday));
							LinearLayout.LayoutParams params_brithday = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
									android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
							et_brithday.setLayoutParams(params_brithday);

							// -----------------------------AddView----------------------------------//

							linearLayout1.addView(tv1);
							linearLayout1.addView(et_brithday);
							contentView.addView(linearLayout1);

							// ========================Setting=======//
							brithday_used = true;
							dialog.dismiss();
						} else {
							dialog.dismiss();
						}
						break;
					}
				}
			});
			break;

		// 保存联系人
		case R.id.contact_save:
			// 如果用户选择的是全部联系人
			if (chooseData.equals(Constant.Contacts.ALLCONTACT)) {
				// step1: 直接进行用户数据的添加
				String name = etUsername.getText().toString().trim();
				String mobile_phone = etPhoneNumber.getText().toString().trim();
				String note = etNote.getText().toString();
				String nickname = "";
				if (et_nickname != null) {
					nickname = et_nickname.getText().toString().trim();
				}

				String fixed_phone = "";
				if (et_phone != null) {
					fixed_phone = et_phone.getText().toString().trim();
				}

				String address = "";
				if (et_address != null) {
					address = et_address.getText().toString().trim();

				}

				String email = "";
				if (et_mail != null) {
					email = et_mail.getText().toString().trim();
				}

				String company = "";

				if (et_company != null) {
					company = et_company.getText().toString().trim();
				}
				//
				// String brithday = "";
				//
				// if(et_brithday != null){
				//
				//
				// }

				application.contactOperator.addContact(
						AddContactsActivity.this, name, fixed_phone,
						mobile_phone, email, address, nickname, note);
				T.show(AddContactsActivity.this, "修改成功!", Toast.LENGTH_LONG);
				// step2: 通知数据发生改变

				// step3: 进行跳转
				Intent intent = new Intent(AddContactsActivity.this,
						HomeActivity.class);
				intent.putExtra("page", Constant.Contacts.CONTACT_PAGE);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				// step4: 干掉Activity
				AddContactsActivity.this.finish();
			} else {

				// 有问题

				// step1:找的组id
				int groupId = 0;
				for (GroupEntity group : groupInfo) {
					if (chooseData.equals(group.getGroupName())) {
						groupId = group.getGroupId();
					}
				}
				// step2:添加联系人得到联系人的id
				String name = etUsername.getText().toString().trim();
				String phone = etPhoneNumber.getText().toString().trim();
				// long personId = operator.addContact(AddContactsActivity.this,
				// name, "", phone, "", "", "", "");
				// step3:通过联系人ID将其添加到特定的组当中
				// operator.addContactByGroupId(groupId, (int) personId);
				// step4:最后发送数据已经改变的广播

				// 马上写

				// step5:进行跳转
				Intent intent = new Intent(AddContactsActivity.this,
						HomeActivity.class);
				// 存储当前页
				intent.putExtra("page", Constant.Contacts.CONTACT_PAGE);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				// step6:结束掉activity
				AddContactsActivity.this.finish();
			}

			finish();
			break;

		// 退出

		case R.id.contact_exit:

			Intent intent = new Intent(AddContactsActivity.this,
					HomeActivity.class);

			intent.putExtra("page", Constant.Contacts.CONTACT_PAGE);

			startActivity(intent);

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);

			finish();

			break;
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
