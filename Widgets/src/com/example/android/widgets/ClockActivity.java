package com.example.android.widgets;

import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClockActivity extends Activity {
	//ClockActivity主程序
	public Calendar mCalendar;
	public int mHour;
	public int mMinutes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_activity);		
		
		final TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		final Button mButton01 = (Button)findViewById(R.id.Button01);
		//按下[签到时间]按钮
		mButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				//取得系统时间time
				long time = System.currentTimeMillis();
				//建立日历mCalendar
				final Calendar mCalendar = Calendar.getInstance();
				mCalendar.setTimeInMillis(time);
				//读到当时的时/分
				mHour = mCalendar.get(Calendar.HOUR);
				mMinutes = mCalendar.get(Calendar.MINUTE);
				mTextView01.setText("早安，你的签到時間: "+mHour+" : "+mMinutes);
			}
		});
	}
}
