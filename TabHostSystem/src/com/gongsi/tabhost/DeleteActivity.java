package com.gongsi.tabhost;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DeleteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Delete Activity");
		setContentView(tv);
	}

}
