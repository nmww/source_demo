package com.zhg.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MusicDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String musicName = intent.getCharSequenceExtra("musicName").toString();
		TextView tv = new TextView(this);
		tv.setText(musicName);
		setContentView(tv);
	}
}
