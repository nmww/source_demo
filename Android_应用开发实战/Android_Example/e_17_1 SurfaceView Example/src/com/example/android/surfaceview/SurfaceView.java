package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SurfaceView extends Activity {
	//11个范例的菜单名称和应用程序Class
	private Object[] activities = {
			"Click",							Click.class,
			"Hello",							Hello.class,
			"AntiAlias",						AntiAlias.class,
			"Game Sample (View)", 				GameSampleWithView.class,			
			"Game Sample (SurfaceView)",	 	GameSampleWithSurfaceView.class,			
			"Game Sample (BitmapDrawable)", 	GameSampleWithBitmapDrawable.class,			
			"Save and Restore",					SaveAndRestore.class,
			"KeyEvent",							KeyEventSample.class,
			"Touch and Trackball Event",		TouchAndTrackballEvent.class,
			"Transparent SurfaceView",			Transparent.class,
			"Translucent View",			TranslucentView.class,
	};
	//SurfaceView主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//建立11个范例菜单名称的数组list
		CharSequence[] list = new CharSequence[activities.length / 2];
		for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}
		//将11个范例菜单名称放置在listView
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//按下菜单名称指向相关的应用程序Class
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SurfaceView.this, (Class<?>)activities[position * 2 + 1]);
				startActivity(intent);
			}
		});
	}
}
