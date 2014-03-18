package com.diandian.mycall.me.register;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.diandian.mycall.R;
import com.diandian.mycall.coreui.HomeActivity;
import com.diandian.mycall.util.PhotoUtil;
import com.diandian.mycall.view.BootstrapEditText;
import com.diandian.mycall.view.MyDialog;

public class MoreInfoActivity extends Activity implements OnClickListener {

	Bitmap photo;

	ImageView headImg;

	BootstrapEditText etUserName, etPhoneNumber;

	Button btnMore, btnFinish;

	// 可能被添加控件的数据
	BootstrapEditText et_nickname, et_phone, et_mail, et_address, et_company,
			et_brithday;

	// 监听控件是否进行过添加
	private boolean nick_used, photo_used, mail_used, address_used,
			company_used, brithday_used;

	// 添加的内容区域
	LinearLayout contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_more_user_info);

		initData();

		initViews();

		initListener();

	}

	public void initListener() {

		btnMore.setOnClickListener(this);

		btnFinish.setOnClickListener(this);

	}

	public void initViews() {

		contentView = (LinearLayout) findViewById(R.id.more_content);

		etUserName = (BootstrapEditText) findViewById(R.id.moreinfo_et_username);

		etPhoneNumber = (BootstrapEditText) findViewById(R.id.moreinfo_et_phone_number);

		btnMore = (Button) findViewById(R.id.more_btn_add_info);

		btnFinish = (Button) findViewById(R.id.more_btn_finish);

		headImg = (ImageView) findViewById(R.id.more_head_img);

		headImg.setImageBitmap(PhotoUtil.toRoundCorner(photo, 15));

	}

	public void initData() {

		Bundle bundleHead = getIntent().getExtras();

		photo = bundleHead.getParcelable("data");

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		// photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);

		photo.compress(Bitmap.CompressFormat.JPEG, 90, stream);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.more_btn_add_info:

			addMoreInfoDialog();

			break;

		case R.id.more_btn_finish:

			Intent intent = new Intent(MoreInfoActivity.this,
					HomeActivity.class);

			startActivity(intent);

			MoreInfoActivity.this.finish();

			break;

		}

	}

	public void addMoreInfoDialog() {

		LayoutInflater inflater = LayoutInflater.from(MoreInfoActivity.this);
		View view = inflater.inflate(R.layout.add_more_contacts_info, null);
		final MyDialog dialog = new MyDialog(MoreInfoActivity.this, 0, 0, view,
				R.style.dialog);
		ListView listItem = (ListView) view.findViewById(R.id.list_item);
		String args[] = { "咪称", "固定电话 ", "邮箱地址", "住址 ", "公司", " 生日 " };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, args);
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("昵称");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_nickname = new BootstrapEditText(
								MoreInfoActivity.this);
						et_nickname.setId(100001);
						et_nickname.roundedCorners = true;
						et_nickname.setState("info");
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("电话");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_phone = new BootstrapEditText(MoreInfoActivity.this);
						et_phone.setId(100002);
						et_phone.roundedCorners = true;
						et_phone.setState("info");
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("邮箱");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_mail = new BootstrapEditText(MoreInfoActivity.this);
						et_mail.setId(100003);
						et_mail.roundedCorners = true;
						et_mail.setState("info");
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("住址");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_address = new BootstrapEditText(
								MoreInfoActivity.this);
						et_address.setId(100003);
						et_address.roundedCorners = true;
						et_address.setState("info");
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("公司");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_company = new BootstrapEditText(
								MoreInfoActivity.this);
						et_company.setId(100003);
						et_company.roundedCorners = true;
						et_company.setState("info");
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
								MoreInfoActivity.this);
						LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
								dip2px(MoreInfoActivity.this, 270), dip2px(
										MoreInfoActivity.this, 40));
						params1.topMargin = dip2px(MoreInfoActivity.this, 15);
						linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
						linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
						linearLayout1.setLayoutParams(params1);

						// ---------------------------TextView-----------------------------------//

						TextView tv1 = new TextView(MoreInfoActivity.this);
						LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
								android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
								LinearLayout.LayoutParams.MATCH_PARENT);
						tvLayout.rightMargin = dip2px(MoreInfoActivity.this, 10);
						tv1.setTextSize(20);
						tv1.setText("生日");
						tv1.setGravity(Gravity.CENTER_VERTICAL);
						tv1.setLayoutParams(tvLayout);

						// ----------------------------EditText------------------------------------//

						et_brithday = new BootstrapEditText(
								MoreInfoActivity.this);
						et_brithday.setId(100003);
						et_brithday.roundedCorners = true;
						et_brithday.setState("info");
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
