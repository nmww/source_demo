package com.example.android.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LayoutExample extends Activity {
//画面布局Layout主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//4个范例的选单
		final String[] layouts = {
			"TableLayout",
			"LinearLayout",
			"RelativeLayout",
			"AbsoluteLayout",
		};
		//将4个范例选单名称layouts放置在画面布局ListView01
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, layouts);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//按下选单名称指向相关的应用程序Class
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
				try {
					Intent intent = new Intent(LayoutExample.this, Class.forName(getClass().getPackage().getName() + "." + layouts[position] + "Activity"));
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}	
		});
	}
}
