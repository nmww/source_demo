package com.example.android.widgets;

import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClockActivity extends Activity {
	//ClockActivity������
	public Calendar mCalendar;
	public int mHour;
	public int mMinutes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_activity);		
		
		final TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		final Button mButton01 = (Button)findViewById(R.id.Button01);
		//����[ǩ��ʱ��]��ť
		mButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				//ȡ��ϵͳʱ��time
				long time = System.currentTimeMillis();
				//��������mCalendar
				final Calendar mCalendar = Calendar.getInstance();
				mCalendar.setTimeInMillis(time);
				//������ʱ��ʱ/��
				mHour = mCalendar.get(Calendar.HOUR);
				mMinutes = mCalendar.get(Calendar.MINUTE);
				mTextView01.setText("�簲�����ǩ���r�g: "+mHour+" : "+mMinutes);
			}
		});
	}
}
