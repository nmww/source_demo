package com.example.android.web02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class WebViewJavaScript extends Activity {
	//4个范例的菜单名称和应用程序Class
	private Object[] activities = {
			"JavaScript Combo", 	JExample01.class,
			"JavaScript Handle",	JExample02.class,
			"Google Translation",	JExample03.class,
			"JavaScript Change",	JExample04.class,
		};
	//WebViewJavaScript主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//建立4个范例菜单名称的数组list
		CharSequence[] list = new CharSequence[activities.length / 2];
		for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}
		//将4个范例菜单名称放置在listView
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//按下菜单名称指向相关的应用程序Class
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//菜单范例需要传送url的內容
				Intent intent = new Intent(WebViewJavaScript.this, (Class<?>)activities[position * 2 + 1]);
				if (position == 0) intent.putExtra("url", "http://www.uenocity.com/WebViewJavaScript/javascript01.HTML");
				if (position == 1) intent.putExtra("url", "http://www.uenocity.com/WebViewJavaScript/javascript02.HTML");
				if (position == 2) intent.putExtra("url", "http://www.uenocity.com/WebViewJavaScript/javascript03.HTML");
				if (position == 3) intent.putExtra("url", "http://www.uenocity.com/WebViewJavaScript/javascript04.HTML");
				startActivity(intent);
			}		
		});	
	}
}