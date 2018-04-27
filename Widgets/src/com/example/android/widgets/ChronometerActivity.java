package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class ChronometerActivity extends Activity {
	//ChronometerActivity主程序
	private Chronometer chronometer;
	private Button button;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chronometer_activity);
		//Chronometer一开机就开始计时
		chronometer = (Chronometer)findViewById(R.id.Chronometer01);
		chronometer.start();
		//按下Button01按钮时，Button01被设计成切換按鈕，可以开始计时或停止计时
		button = (Button)findViewById(R.id.Button01);
		button.setOnClickListener(new View.OnClickListener() {				
			public void onClick(View v) {
				if (button.getText().equals("Start")) {
					chronometer.start();
					button.setText("Stop");
				} else {
					chronometer.stop();
					button.setText("Start");
				}
			}		
		});
	}
}
