package com.example.android.sensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HelloSensor extends Activity {
	//5个范例的菜单名称和应用程序Class
	private Object[] activities = { 
		"Compass",				CompassDemo.class,
		"Orientation",			OrientationDemo.class,
		"Accelerometer",		AccelerometerDemo.class,
		"Magnetic Field",		MagneticFieldDemo.class,	
		"Temperature",			TemperatureDemo.class,
	};
	//HelloSensor主程式
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//建立5个范例菜单名称的数组list
		CharSequence[] list = new CharSequence[activities.length / 2];
		for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}
		//将5个范例菜单名称放置在listView
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//按下菜单名称指向相关的应用程序Class
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(HelloSensor.this, (Class<?>)activities[position * 2 + 1]);
				startActivity(intent);
			}
		});
	}
}
