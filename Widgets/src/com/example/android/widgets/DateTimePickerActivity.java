package com.example.android.widgets;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class DateTimePickerActivity extends Activity {
	//DateTimePickerActivity主程序
	private TextView textView01;
	private TextView textView02;
	private Button button01;
	private Button button02;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_time_picker_activity);
		//建立GregorianCalendar类别变量calendar
		GregorianCalendar calendar = new GregorianCalendar();
		
		textView01 = (TextView)findViewById(R.id.TextView01);
		textView02 = (TextView)findViewById(R.id.TextView02);
		button01 = (Button)findViewById(R.id.Button01);
		button02 = (Button)findViewById(R.id.Button02);
		//执行DatePickerDialog()，设定/读出年、月、日，并显示在文本框TextView01
		datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {		
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				textView01.setText(year + "/" + monthOfYear + 1 + "/" + dayOfMonth);
			}	
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		//执行TimePickerDialog()，设定/读出时、分，并显示在文本框TextView02
		timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				textView02.setText((hourOfDay > 12 ? hourOfDay - 12 : hourOfDay) + ":" + minute + " " + (hourOfDay > 12 ? "PM" : "AM"));
			}		
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
		//按下Button01按钮时，执行DatePickerDialog()
		button01.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				datePickerDialog.show();
			}		
		});
		//按下Button02按钮时，执行TimePickerDialog()
		button02.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				timePickerDialog.show();
			}		
		});	
	}
}
