package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerActivity extends Activity {
	public Spinner spinner;
	//SpinnerActivity主程序
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner_activity);
		spinner = (Spinner)findViewById(R.id.Spinner01);
		//设定字符序列数组-adapter
		ArrayAdapter<CharSequence> adapter = 
			ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
		//自定义下拉菜单-Spinner
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	//下拉选单的选项处理-onItemSelected()
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(parent.getContext(), "你所选的行星是-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		}
		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.    
		}
	}
}

