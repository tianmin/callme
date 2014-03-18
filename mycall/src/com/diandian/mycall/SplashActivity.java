package com.diandian.mycall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.diandian.mycall.coreui.BaseActivity;
import com.diandian.mycall.me.login.LoginActivity;

public class SplashActivity extends BaseActivity {

	// | 进入应用主界面的情况

	// | 用户第一次使用mycall

	// | 用户已经退出mycall,再次进入

	// | 用户在登陆状态下进入

	// 偏好設置
	SharedPreferences preferences;

	// 飞溅界面
	View splashView;

	// 登陆的界面
	View loginView;

	String username;

	String userpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		test();

		// preferences = getSharedPreferences("user_info", MODE_PRIVATE);
		//
		// splashView = View.inflate(this, R.layout.splash, null);
		//
		// loginView = View.inflate(this, R.layout.mycall_login, null);
		//
		// int isFirst = preferences.getInt("isfirst", 0);
		//
		// // 非第一次进入
		// if(isFirst == 1){
		//
		// //查看用户的登陆状态
		// int status = preferences.getInt("loginstatus", 0);
		//
		// //未登录
		// if(status == 0){
		// //取出 用户名 和 密码 启动登陆界面
		//
		// username = preferences.getString("username", "");
		//
		// userpwd = preferences.getString("userpwd", "");
		//
		// setContentView(loginView);
		//
		// initLoginViews();
		//
		// initLoginListener();
		//
		// //已经登陆
		// }else{
		//
		// //启动我UI
		//
		// Intent intent = new Intent(SplashActivity.this,
		// HomeActivity.class);
		//
		// startActivity(intent);
		//
		// overridePendingTransition(android.R.anim.slide_in_left,
		// android.R.anim.slide_out_right);
		//
		// SplashActivity.this.finish();
		//
		// //异步访问服务器 ,进行登陆的操作
		//
		// }
		//
		// // 第一次进入
		// }else{
		//
		// //启动照相
		//
		//
		//
		//
		// }
	}

	/**
	 * 初始化登陆的监听
	 */
	public void initLoginListener() {

	}

	/**
	 * 初始化登陆的界面
	 */
	public void initLoginViews() {

	}

	public void test() {

		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

		startActivity(intent);

		this.finish();

	}

}
