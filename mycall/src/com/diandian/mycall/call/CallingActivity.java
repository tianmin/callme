package com.diandian.mycall.call;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandian.mycall.R;

public class CallingActivity extends Activity {

	ImageView image;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.calling);
//		image=(ImageView)findViewById(R.id.imageView2);
//		text=(TextView)findViewById(R.id.textView3);
	}


	public void callEnd(View v){
//		Intent backControl = new Intent(
//				CallingActivity.this, MainActivity.class);
		//startActivity(backControl);
		CallingActivity.this.finish();
	}
	
	public void testCalling(View v){
		image.setVisibility(0x00000000);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String str = sdf.format(new Date());
	    text.setText(str);
	}
	

	
}
