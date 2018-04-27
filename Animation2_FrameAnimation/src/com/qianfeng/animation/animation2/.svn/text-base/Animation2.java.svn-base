package com.qianfeng.animation.animation2;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;


public class Animation2 extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 定义UI组件
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		final Button b3 = (Button) findViewById(R.id.button3);
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);

		// 定义点击监听器
		View.OnClickListener ocl = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AnimationDrawable ad = (AnimationDrawable) iv.getBackground();
				AnimationDrawable ad2 = (AnimationDrawable) b3.getBackground();

				switch (v.getId()) {
				case R.id.button1:
					ad.start();
					ad2.start();
					break;
				case R.id.button2:
					ad.stop();
					ad2.stop();
					break;
				}
			}
		};

		// 绑定监听器
		b1.setOnClickListener(ocl);
		b2.setOnClickListener(ocl);
	}
}