package com.animi.test;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class animActivity extends Activity implements OnClickListener {
	ImageView iv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		iv = (ImageView) findViewById(R.id.ImageView01);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AnimationDrawable anim = null;
		Object ob = iv.getBackground();
		anim = (AnimationDrawable) ob;
		anim.stop();
		anim.start();
	}
}