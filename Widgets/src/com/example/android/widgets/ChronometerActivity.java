package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class ChronometerActivity extends Activity {
	//ChronometerActivity������
	private Chronometer chronometer;
	private Button button;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chronometer_activity);
		//Chronometerһ�����Ϳ�ʼ��ʱ
		chronometer = (Chronometer)findViewById(R.id.Chronometer01);
		chronometer.start();
		//����Button01��ťʱ��Button01����Ƴ��ГQ���o�����Կ�ʼ��ʱ��ֹͣ��ʱ
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
