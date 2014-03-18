package com.diandian.mycall.coreui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.diandian.mycall.R;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.me.share.FriendShareActivity;
import com.diandian.mycall.me.share.MoodActivity;
import com.diandian.mycall.me.share.MyShareActivity;
import com.diandian.mycall.view.corner.BasicItem;
import com.diandian.mycall.view.corner.UITableView;
import com.diandian.mycall.view.corner.UITableView.ClickListener;

/**
 * MeActivity
 * 
 * @author lenovo
 */
public class MeActivity extends BaseActivity {

	TextView tvBar;

	UITableView tableViewOne, tableViewTwo;

	Button btnMood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_me);

		initData();

		initViews();

		initListener();
	}

	public void initData() {

	}

	public void initViews() {

		tvBar = (TextView) findViewById(R.id.title_bar_text);

		tvBar.setText("我");

		tableViewOne = (UITableView) findViewById(R.id.tableview);

		createListOne();

		tableViewOne.commit();

		tableViewTwo = (UITableView) findViewById(R.id.tableview_two);

		createaListTwo();

		tableViewTwo.commit();

		btnMood = (Button) findViewById(R.id.me_btn_mood);

	}

	public void createaListTwo() {

		WeClickListener listener = new WeClickListener();

		tableViewTwo.setClickListener(listener);

		BasicItem item1 = new BasicItem("我的关注");

		item1.setDrawable(R.drawable.user_image);

		tableViewTwo.addBasicItem(item1);

		BasicItem item2 = new BasicItem("我的管理");

		item2.setDrawable(R.drawable.user_image);

		tableViewTwo.addBasicItem(item2);
	}

	public void initListener() {

		btnMood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(TApplication.getInstance()
						.getContext(), MoodActivity.class);

				startActivity(intent);

				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

				MeActivity.this.finish();

				TApplication.getInstance().getContext().finish();

			}
		});

	}

	private void createListOne() {

		CustomClickListener listener = new CustomClickListener();

		tableViewOne.setClickListener(listener);

		BasicItem item1 = new BasicItem("密友分享");

		item1.setDrawable(R.drawable.user_image);

		tableViewOne.addBasicItem(item1);

		BasicItem item2 = new BasicItem("我的分享");

		item2.setDrawable(R.drawable.user_image);

		tableViewOne.addBasicItem(item2);

	}

	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {

			switch (index) {
			// 密友分享
			case 0:

				Intent intentFriend = new Intent(TApplication.getInstance()
						.getContext(), FriendShareActivity.class);

				startActivity(intentFriend);

				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

				MeActivity.this.finish();

				TApplication.getInstance().getContext().finish();

				break;

			// 进入我的分享
			case 1:

				Intent intentMe = new Intent(TApplication.getInstance()
						.getContext(), MyShareActivity.class);

				startActivity(intentMe);

				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

				MeActivity.this.finish();

				TApplication.getInstance().getContext().finish();

				break;

			}

		}

	}

	private class WeClickListener implements ClickListener {

		@Override
		public void onClick(int index) {

			switch (index) {

			// 进入我的分享
			case 0:

				Intent intentMe = new Intent(TApplication.getInstance()
						.getContext(), MyShareActivity.class);

				startActivity(intentMe);

				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);// 自定义右进左出 activity切换

				MeActivity.this.finish();

				TApplication.getInstance().getContext().finish();

				break;

			// 进入密友分享
			case 1:

				Intent intentFriend = new Intent(TApplication.getInstance()
						.getContext(), FriendShareActivity.class);

				startActivity(intentFriend);

				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);// 自定义右进左出 activity切换

				MeActivity.this.finish();

				TApplication.getInstance().getContext().finish();

				break;
			}

		}

	}
}
